package com.known.web.controller;

import com.known.common.enums.Code;
import com.known.common.model.UserFriend;
import com.known.common.model.UserRedis;
import com.known.common.utils.Constants;
import com.known.common.vo.OutResponse;
import com.known.common.vo.PageResult;
import com.known.manager.query.UserFriendQuery;
import com.known.service.UserFriendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/userFriend")
public class UserFriendController extends BaseController {
	@Autowired
	private UserFriendService userFriendService;
	
	private Logger logger = LoggerFactory.getLogger(UserFriendController.class);
	
	@ResponseBody
	@RequestMapping("/loadUserFriend")
	public OutResponse<Object> loadUserFriend(HttpSession session, int pageNum){
		OutResponse<Object> outResponse = new OutResponse<>();
		UserRedis sessionUser = (UserRedis) session.getAttribute(Constants.SESSION_USER_KEY);
		if(sessionUser==null){
			outResponse.setCode(Code.BUSSINESSERROR);
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
