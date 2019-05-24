package com.known.web.controller;

import com.known.common.config.UserConfig;
import com.known.common.model.SessionUser;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
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

	@Resource
	private UserConfig userConfig;
	
	public void setUserBaseInfo(Class<?> clazz, Object obj, HttpSession session){

		SessionUser sessionUser = (SessionUser) session.getAttribute(userConfig.getSession_User_Key());
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

		Object sessObj = session.getAttribute(userConfig.getSession_User_Key());
		return sessObj != null ? ((SessionUser)sessObj).getUserid() : null;
	}

}
