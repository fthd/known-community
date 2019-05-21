package com.known.web.filter;

import com.known.common.model.User;
import com.known.common.model.UserRedis;
import com.known.common.utils.Constants;
import com.known.service.UserService;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;

/**
 * 过滤器
 *
 * 这里直接用@WebFilter就可以进行配置，同样，可以设置url匹配模式，过滤器名称等
 * 这里需要注意一点的是@WebFilter这个注解是Servlet3.0的规范，并不是Spring boot提供的
 * 除了这个注解以外，我们还需在配置类中加另外一个注解：@ServletComponetScan，指定扫描的包
 *
 * @author tangjunxiang
 * @version 1.0
 * @date 2019-05-21 01:39
 */
@WebFilter(urlPatterns = "/*", filterName = "authorityFilter")
public class AuthorityFilter implements Filter {

	private Logger logger = LoggerFactory.getLogger(AuthorityFilter.class);

	/**
	 * 项目路径统一设置
	 */
	private static String realPath = null;

	/**
	 * 附件类型
	 */
	private static final String[] ATTACHMENTTYPE = {"zip", "rar"};

	@Value("${ERROR_404}")
	private String ERROR_404;

	@Value("${SESSION_USER_KEY}")
	private String SESSION_USER_KEY;

	@Value("${COOKIE_USER_INFO}")
	private String COOKIE_USER_INFO;

	@Value("${REALPATH}")
	private String REALPATH;

	@Autowired
	private UserService userService;

	@Override
	public void init(FilterConfig filterConfig)  {

	}

	public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		ServletContext servletContext = req.getSession().getServletContext();

		String req_uri = req.getRequestURI();

		String type = req_uri.substring(req_uri.lastIndexOf(".") + 1);

		//  如果请求路径以rar, zip结尾, 直接返回404页面
		if(ArrayUtils.contains(ATTACHMENTTYPE, type)){
			resp.sendRedirect(ERROR_404);
			return ;
		}
		if(realPath == null){
			realPath = getRealPath(req);
		}
		if(servletContext.getAttribute(REALPATH) == null){
			servletContext.setAttribute(REALPATH,realPath);
		}
		HttpSession session = req.getSession();

		Object sessionUserObj = session.getAttribute(SESSION_USER_KEY);
		// 自动登录
		if (null == sessionUserObj) {
			autoLogin(req, resp);
			sessionUserObj = session.getAttribute(SESSION_USER_KEY);
		}
		chain.doFilter(request, response);
	}
	
	private String getRealPath(HttpServletRequest request){
		String port = request.getServerPort() == 80 ? "": ":" + request.getServerPort();
		String realpath = "http://" + request.getServerName() + port;
		return realpath;
	}
	
	private void autoLogin(HttpServletRequest req, HttpServletResponse response) {
		try {
			Cookie cookieInfo = getCookieByName(req, COOKIE_USER_INFO);
			if (cookieInfo != null) {
				String info = URLDecoder.decode(cookieInfo.getValue(), "utf-8");
				if (info != null && !"".equals(info)) {
					String infos[] = info.split("\\|");

					User user = userService.login(infos[0], infos[1],false);
					if (user != null) {
						UserRedis loginUser = new UserRedis();
						loginUser.setUserid(user.getUserid());
						loginUser.setUserName(user.getUserName());
						loginUser.setUserIcon(user.getUserIcon());
						req.getSession().setAttribute(SESSION_USER_KEY, loginUser);
					}
				}
			}
		} catch (Exception e) {
			// 清楚cookie信息
			Cookie cookie = new Cookie(
					COOKIE_USER_INFO, null);
			cookie.setMaxAge(0);
			cookie.setPath("/");
			response.addCookie(cookie);
			logger.error("自动登录失败：", e);
		}

	}
	private Cookie getCookieByName(HttpServletRequest req, String cookieName){
		Cookie[] cookies = req.getCookies();
		if(cookies != null){
		for(int i =0; i < cookies.length; i++){
			if(cookieName.equals(cookies[i].getName())){
				return new Cookie(cookies[i].getName(), cookies[i].getValue());
			}
		}
		}
		return null;
	}
	public void destroy() {

	}

}
