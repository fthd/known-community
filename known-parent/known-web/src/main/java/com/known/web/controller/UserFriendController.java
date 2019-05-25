package com.known.web.controller;

import com.known.common.config.UrlConfig;
import com.known.common.config.UserConfig;
import com.known.common.enums.CodeEnum;
import com.known.common.model.SessionUser;
import com.known.common.model.UserFriend;
import com.known.common.vo.OutResponse;
import com.known.common.vo.PageResult;
import com.known.manager.query.UserFriendQuery;
import com.known.service.UserFriendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/userFriend")
public class UserFriendController extends BaseController {

	@Autowired
	private UserFriendService userFriendService;


	@Resource
	private UserConfig userConfig;

	@Resource
	private UrlConfig urlConfig;


	private Logger logger = LoggerFactory.getLogger(UserFriendController.class);

	@RequestMapping("/loadUserFriend")
	public OutResponse<Object> loadUserFriend(HttpSession session, int pageNum){
		OutResponse<Object> outResponse = new OutResponse<>();
		SessionUser sessionUser = (SessionUser) session.getAttribute(userConfig.getSession_User_Key());
		if(sessionUser==null){
			outResponse.setCode(CodeEnum.BUSSINESSERROR);
			outResponse.setMsg("请先登录");
			return outResponse;
		}
		int userId = this.getUserid(session);
		UserFriendQuery userFriendQuery = new UserFriendQuery();
		userFriendQuery.setUserId(userId);
		userFriendQuery.setPageNum(pageNum);
		PageResult<UserFriend> pageResult = this.userFriendService.findFriendList(userFriendQuery);
		outResponse.setData(pageResult);
		return outResponse;
	}
}
