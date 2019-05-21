package com.known.web.controller;


import com.known.common.model.User;
import com.known.common.model.UserRedis;
import com.known.common.utils.Constants;
import com.known.common.vo.AjaxResponse;
import com.known.common.vo.PageResult;
import com.known.exception.BussinessException;
import com.known.manager.query.AskQuery;
import com.known.manager.query.BlogQuery;
import com.known.manager.query.KnowledgeQuery;
import com.known.manager.query.TopicQuery;
import com.known.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.known.common.enums.BlogStatusEnum;
import com.known.common.enums.ResponseCode;
import com.known.common.model.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.Date;

/**
 * 登录、注册
 *
 * @author tangjunxiang
 * @version 1.0
 * @date 2019-05-06 17:17
 */
@Controller
public class UserController extends BaseController{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SignInService signInService;
	
	@Autowired
	private TopicService topicService;
	
	@Autowired
	private KnowledgeService knowledgeService;
	
	@Autowired
	private AskService askService;
	
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private SolrService solrService;

	@Value("${SESSION_USER_KEY}")
	private String SESSION_USER_KEY;
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);

	/**
	 * 注册页面
	 * @return
	 */
	@RequestMapping("/register")
	public String register(){
		return "/page/register";
	}

	/**
	 * 注册功能实现
	 *
	 * @param session
	 * @param user 用户信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/register.do")
	public AjaxResponse<Object> registerdo(HttpSession session, User user){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<>();
		try {
			userService.register(user);
			ajaxResponse.setResponseCode(ResponseCode.SUCCESS);
			UserRedis sessionUser = new UserRedis();
			sessionUser.setUserid(user.getUserid());
			sessionUser.setUserName(user.getUserName());
			sessionUser.setUserIcon(user.getUserIcon());
			session.setAttribute(Constants.SESSION_USER_KEY, sessionUser);
		} catch (BussinessException e) {
			ajaxResponse.setErrorMsg(e.getLocalizedMessage());
			ajaxResponse.setResponseCode(ResponseCode.BUSSINESSERROR);
			logger.error("用户注册失败,用户名:{}邮箱:{}", user.getUserName(), user.getEmail());
		}catch (Exception e) {
			ajaxResponse.setErrorMsg(ResponseCode.SERVERERROR.getDesc());
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
			logger.error("用户注册失败,用户名:{}邮箱:{}", user.getUserName(), user.getEmail());
		}
		return ajaxResponse;
	}
	
	@RequestMapping("/")
	public ModelAndView index(HttpSession session){
		Integer userid = this.getUserid(session);
		ModelAndView view = new ModelAndView("/page/index");
		if(userid !=null){
			SignInfo signInfo = this.signInService.findSignInfoByUserid(userid);
			view.addObject("signInfo", signInfo);
		}
		PageResult<Topic> topics = this.topicService.findTopicByPage(new TopicQuery());
		view.addObject("topics", topics);
		view.addObject("knowledges", this.knowledgeService.findKnowledgeByPage(new KnowledgeQuery()));
		view.addObject("asks", this.askService.findAskByPage(new AskQuery()));
		BlogQuery blogQuery = new BlogQuery();
		blogQuery.setStatus(BlogStatusEnum.PUBLIC);
		view.addObject("blogs", this.blogService.findBlogByPage(blogQuery));
		return view;
	}
	
	@RequestMapping("donate")
	public ModelAndView donate(HttpSession session){
		Integer userid = this.getUserid(session);
		ModelAndView view = new ModelAndView("/page/donate");
		if(userid !=null){
			SignInfo signInfo = this.signInService.findSignInfoByUserid(userid);
			view.addObject("signInfo", signInfo);
		}
		return view;
	}
	
	@RequestMapping("search")
	public ModelAndView search(HttpSession session, String keyword, String articleType){
		Integer userid = this.getUserid(session);
		ModelAndView view = new ModelAndView("/page/search");
		if(userid !=null){
			SignInfo signInfo = this.signInService.findSignInfoByUserid(userid);
			view.addObject("signInfo", signInfo);
		}
		view.addObject("keyword", keyword);
		view.addObject("articleType", articleType);
		return view;
	}
	
	@ResponseBody
	@RequestMapping("searchArticle")
	public AjaxResponse<Object> searchArticle(HttpSession session, String keyword,
                                              String articleType, Integer pageNum, Integer countTotal){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<Object>();
		try {
			PageResult<SolrBean> pageResult = this.solrService.findSolrBeanByPage(keyword, articleType, pageNum, countTotal);
			ajaxResponse.setData(pageResult);
			ajaxResponse.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("搜索异常", e);
			ajaxResponse.setErrorMsg("搜索出错，请重试");
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
		}
		return ajaxResponse;
	}
	
	@RequestMapping("aboutWebmaster")
	public ModelAndView aboutWebmaster(HttpSession session){
		Integer userid = this.getUserid(session);
		ModelAndView view = new ModelAndView("/page/aboutWebmaster");
		if(userid !=null){
			SignInfo signInfo = this.signInService.findSignInfoByUserid(userid);
			view.addObject("signInfo", signInfo);
		}
		return view;
	}
	
	@RequestMapping("faq")
	public ModelAndView faq(HttpSession session){
		Integer userid = this.getUserid(session);
		ModelAndView view = new ModelAndView("/page/FAQ");
		if(userid !=null){
			SignInfo signInfo = this.signInService.findSignInfoByUserid(userid);
			view.addObject("signInfo", signInfo);
		}
		return view;
	}
	
	@RequestMapping("login")
	public String login(){
		return "/page/login";
	}

	/**
	 * 登录功能
	 * @param request
	 * @param response
	 * @param account
	 * @param password
	 * @param rememberMe
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/login.do")
	public AjaxResponse<Object> logindo(HttpServletRequest request, HttpServletResponse response,
                                        String account, String password, String rememberMe){
		final String REMEMBERME = "1";
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<>();
		HttpSession session = request.getSession();
		User user;
		try {
			//用户登录
			user = userService.login(account, password);
			user.setLastLoginTime(new Date());
			ajaxResponse.setResponseCode(ResponseCode.SUCCESS);

			//用户信息存入Redis
			UserRedis userRedis = new UserRedis(user.getUserid(), user.getUserName(), user.getUserIcon());


			UserRedis sessionUser = new UserRedis();
			sessionUser.setUserid(user.getUserid());
			sessionUser.setUserName(user.getUserName());
			sessionUser.setUserIcon(user.getUserIcon());
			session.setAttribute(SESSION_USER_KEY, sessionUser);


			if(REMEMBERME.equals(rememberMe)){    // 清除之前的Cookie 信息    
				String infor = URLEncoder.encode(account.toString(), "utf-8") + "|" + user.getPassword();  
			    Cookie cookie = new Cookie(Constants.COOKIE_USER_INFO, null);
			    cookie.setPath("/");    
			    cookie.setMaxAge(0);    
			    // 建用户信息保存到Cookie中    
			    cookie = new Cookie(Constants.COOKIE_USER_INFO, infor);
			    cookie.setPath("/");    
			    // 设置最大生命周期为1年。    
			    cookie.setMaxAge(31536000);    
			    response.addCookie(cookie);   
			} else {
			    Cookie cookie = new Cookie(Constants.COOKIE_USER_INFO, null);
			    cookie.setMaxAge(0);    
			    cookie.setPath("/");    
			    response.addCookie(cookie);
			}
		} catch (BussinessException e) {
			ajaxResponse.setErrorMsg(e.getLocalizedMessage());
			ajaxResponse.setResponseCode(ResponseCode.BUSSINESSERROR);
			logger.error("用户登录失败,账号:{}",account);
		}catch (Exception e) {
			ajaxResponse.setErrorMsg(ResponseCode.SERVERERROR.getDesc());
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
			logger.error("用户登录失败,账号:{}", account);
		}
		return ajaxResponse;
	}
	
	@RequestMapping("findpassword")
	public String findPassword(){
		return "/page/findpassword";
	}
	
	@ResponseBody
	@RequestMapping("sendCheckCode")
	public  AjaxResponse<Object> sendCheckCode(String email){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<Object>();
		try {
			userService.sendCheckCode(email);
			ajaxResponse.setResponseCode(ResponseCode.SUCCESS);
		} catch (BussinessException e) {
			ajaxResponse.setErrorMsg(e.getLocalizedMessage());
			ajaxResponse.setResponseCode(ResponseCode.BUSSINESSERROR);
			logger.error("验证码发送失败,邮箱:{}" );
		}catch (Exception e) {
			ajaxResponse.setErrorMsg(ResponseCode.SERVERERROR.getDesc());
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
			logger.error("验证码发送失败,邮箱:{}", email);
		}
		return ajaxResponse;
	}
	
	@ResponseBody
	@RequestMapping("findPassword.do")
	public AjaxResponse<Object> findPassworddo(String email, String password, String checkcode){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<Object>();
		try {
			userService.modifyPassword(email, password, checkcode);
			ajaxResponse.setResponseCode(ResponseCode.SUCCESS);
		} catch (BussinessException e) {
			ajaxResponse.setErrorMsg(e.getLocalizedMessage());
			ajaxResponse.setResponseCode(ResponseCode.BUSSINESSERROR);
			logger.error("密码修改失败,邮箱{}", email);
		}
		catch (Exception e) {
			ajaxResponse.setErrorMsg(ResponseCode.SERVERERROR.getDesc());
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
			logger.error("密码修改失败,邮箱:{}", email);
		}
		return ajaxResponse;
	}
	
	@RequestMapping("logout")
	public ModelAndView logout(HttpSession session, HttpServletResponse response){
		ModelAndView view = new ModelAndView("page/login");
		session.invalidate();
		Cookie cookie = new Cookie(Constants.COOKIE_USER_INFO, null);
	    cookie.setMaxAge(0);    
	    cookie.setPath("/");    
	    response.addCookie(cookie);
		return view;
	}
	
	
	/**
	 * blur事件得到用户头像
	 * @param account
	 * @return
	 */
	@ResponseBody
	@RequestMapping("findHeadImage")
	public AjaxResponse<Object> findHeadImage(String account){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<Object>();
		String headIcon = null;
		try {
			headIcon = userService.findHeadIcon(account);
			ajaxResponse.setResponseCode(ResponseCode.SUCCESS);
			ajaxResponse.setData(headIcon);
		} catch (BussinessException e) {
			ajaxResponse.setErrorMsg(e.getLocalizedMessage());
			ajaxResponse.setResponseCode(ResponseCode.BUSSINESSERROR);
			logger.error("头像获取失败,账户{}异常{}", account, e.getLocalizedMessage());
		}
		catch (Exception e) {
			ajaxResponse.setErrorMsg(ResponseCode.SERVERERROR.getDesc());
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
			logger.error("头像获取失败,账户{}异常{}", account, e.getLocalizedMessage());
		}
		return ajaxResponse;
	}

}
