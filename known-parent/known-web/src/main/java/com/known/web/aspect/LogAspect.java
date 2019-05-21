package com.known.web.aspect;

import com.known.common.model.LoginLog;
import com.known.common.model.SysLog;
import com.known.common.model.UserRedis;
import com.known.common.utils.Constants;
import com.known.common.utils.IpUtil;
import com.known.common.utils.PointUtil;
import com.known.common.utils.StringUtils;
import com.known.common.vo.Data;
import com.known.common.vo.Point;
import com.known.service.LoginLogService;
import com.known.service.SysLogService;
import com.known.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Enumeration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Aspect
@Component
public class LogAspect {

	@Autowired
	private LoginLogService loginLogService;

	@Autowired
	private SysLogService sysLogService;
	
	@Autowired
	private UserService userService;

	private ExecutorService executorService = Executors.newFixedThreadPool(30);

	@Pointcut("execution(* com.known.web.controller.*.*(..))")
	public void cutController() {
	}

	@Around("cutController()")
	public Object recordLog(ProceedingJoinPoint point) throws Throwable {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				String strMethodName = point.getSignature().getName();
				String strClassName = point.getTarget().getClass().getName();
				Object[] params = point.getArgs();
				StringBuffer bfParams = new StringBuffer();
				Enumeration<String> paraNames = null;
				//HttpSer	vletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
				if (params != null && params.length > 0) {
					paraNames = request.getParameterNames();
					String key;
					String value;
					while (paraNames.hasMoreElements()) {
						key = paraNames.nextElement();
						value = request.getParameter(key);
						bfParams.append(key).append("=").append(value).append("&");
					}
					if (StringUtils.isEmpty(bfParams.toString())) {
						bfParams.append(request.getQueryString());
					}
				}

				if (strMethodName.equals("logindo")) {
					LoginLog loginLog = new LoginLog();
					String ip = IpUtil.getIpAddr(request);
					loginLog.setLoginIp(ip);
					loginLog.setLoginTime(new Date());
					Point ipPoint = PointUtil.getPoint(ip);
					Data data = ipPoint.getData();
					if (data != null) {
						loginLog.setLat(data.getLnt());//两个搞混了= =
						loginLog.setAddress(data.getCity());
						loginLog.setLng(data.getLat());
					}
					String account = request.getParameter("account");
					loginLog.setAccount(account);
					loginLogService.addLoginLog(loginLog);
				}

				else {
					SysLog sysLog = new SysLog();
					String ip = request.getRemoteAddr();
					String strMessage = String.format("[类名]:%s,[方法]:%s,[参数]:%s", strClassName, strMethodName,
							bfParams.toString());
					HttpSession session = request.getSession();
					UserRedis sessionUser = (UserRedis) session.getAttribute(Constants.SESSION_USER_KEY);
					/*Point ipPoint = PointUtil.getPoint(ip);
					Data data = ipPoint.getData();
					if(data != null){
						sysLog.setAddress(data.getCity());
					}*/
					sysLog.setCreateTime(new Date());
					sysLog.setClientIp(ip);
					sysLog.setOpContent(strMessage);
					if (sessionUser != null) {
						String account = sessionUser.getUserName();
						sysLog.setAccount(account);
					}
					sysLogService.addSysLog(sysLog);
				}
				try {
					Thread.sleep(1*100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		return point.proceed();
	}

}
