package com.known.web.aspect;

import com.known.common.config.UserConfig;
import com.known.common.model.LoginLog;
import com.known.common.model.SessionUser;
import com.known.common.model.SysLog;
import com.known.common.utils.IpUtil;
import com.known.common.utils.PointUtil;
import com.known.common.utils.StringUtil;
import com.known.common.vo.Data;
import com.known.common.vo.Point;
import com.known.service.LoginLogService;
import com.known.service.SysLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Enumeration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 定义日志打印切面类
 * @author tangjunxiang
 * @version 1.0
 * @date 2019-05-25 11:49
 */
@Aspect
@Component
public class LogAspect {

	@Autowired
	private LoginLogService loginLogService;

	@Autowired
	private SysLogService sysLogService;

	@Resource
	private UserConfig userConfig;

	//获取一个定长的线程池
	private ExecutorService executorService = Executors.newFixedThreadPool(30);

	/**
	 * 定义切入点表达式
	 */
	@Pointcut("execution(* com.known.web.controller..*.*(..))")
	public void cutController() {
	}

	@Around("cutController()")
	public Object recordLog(ProceedingJoinPoint point) throws Throwable {
		// 获取HttpServletRequest
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

		executorService.execute(new Runnable() {
			@Override
			public void run() {

				// 获取执行类名称
				String strClassName = point.getTarget().getClass().getName();
				// 获取执行方法名称
				String strMethodName = point.getSignature().getName();
				// 获取执行方法参数
				Object[] params = point.getArgs();
				StringBuffer bfParams = new StringBuffer();
				Enumeration<String> paraNames;
				if (params != null && params.length > 0) {
					paraNames = request.getParameterNames();
					String key;
					String value;
					while (paraNames.hasMoreElements()) {
						key = paraNames.nextElement();
						value = request.getParameter(key);
						bfParams.append(key).append("=").append(value);
						if (paraNames.hasMoreElements()) {
							bfParams.append("&");
						}
					}
					if (StringUtil.isEmpty(bfParams.toString())) {
						// 获取查询字符串, 只对get请求有效
						bfParams.append(request.getQueryString());
					}
				}

				// 如果请求方法为登录
				if (strMethodName.equals("logindo")) {
					LoginLog loginLog = new LoginLog();
					// 获取ip地址
					String ip = IpUtil.getIpAddr(request);
					loginLog.setLoginIp(ip);
					loginLog.setLoginTime(new Date());
					Point ipPoint = PointUtil.getPoint(ip);
					if(ipPoint != null) {
						Data data = ipPoint.getData();
						if (data != null) {
							// 纬度
							loginLog.setLat(data.getLat());
							// 城市
							loginLog.setAddress(data.getCity());
							// 经度
							loginLog.setLng(data.getLnt());
						}
					}

					// 截取用户名
					loginLog.setAccount(bfParams.substring(8,bfParams.indexOf("&")));
					loginLogService.addLoginLog(loginLog);
				} else {
					// 系统日志记录
					SysLog sysLog = new SysLog();
					String ip = request.getRemoteAddr();
					String strMessage = String.format("[类名]:%s,[方法]:%s,[参数]:%s", strClassName, strMethodName,
							bfParams.toString());
					HttpSession session = request.getSession();
					SessionUser sessionUser = null;
					if(session != null) {
						sessionUser = (SessionUser) session.getAttribute(userConfig.getSession_User_Key());
					}
					Point ipPoint = PointUtil.getPoint(ip);
					if(ipPoint != null) {
						Data data = ipPoint.getData();
						if (data != null) {
							sysLog.setAddress(data.getCity());
						}
					}
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
		//调用目标方法
		return point.proceed();
	}

}
