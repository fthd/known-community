package com.known.web.controller;

import com.known.common.model.UserRedis;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;


/**
 * controller基础类，所有controller必须继承此类
 *
 * @author tangjunxiang
 * @version 1.0
 * @date 2019-05-06 17:22
 */
public class BaseController {

	@Value("${SESSION_USER_KEY}")
	private String SESSION_USER_KEY;
	
	public void setUserBaseInfo(Class<?> clazz, Object obj, HttpSession session){
		UserRedis sessionUser = (UserRedis) session.getAttribute(SESSION_USER_KEY);
		Integer userId = sessionUser.getUserid();
		String userName = sessionUser.getUserName();
		String userIcon = sessionUser.getUserIcon();
		try {
			Method UserIdMethod = clazz.getDeclaredMethod("setUserId", Integer.class);
			UserIdMethod.invoke(obj, userId);
			Method UserNameMethod = clazz.getDeclaredMethod("setUserName", String.class);
			UserNameMethod.invoke(obj, userName);
			Method UserIconMethod = clazz.getDeclaredMethod("setUserIcon", String.class);
			UserIconMethod.invoke(obj, userIcon);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
	public Integer getUserid(HttpSession session){
		Object sessionObject = session.getAttribute(SESSION_USER_KEY);
		if(sessionObject != null){
			return ((UserRedis)sessionObject).getUserid();
		}
		return null;
	}
}
