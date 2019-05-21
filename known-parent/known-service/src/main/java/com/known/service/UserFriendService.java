package com.known.service;

import com.known.common.model.UserFriend;
import com.known.common.vo.PageResult;
import com.known.exception.BussinessException;
import com.known.manager.query.UserFriendQuery;

public interface UserFriendService {
	/**
	 * 
	 * @param userFriendQuery
	 * @return关注的用户
	 */
	PageResult<UserFriend> findFriendList(UserFriendQuery userFriendQuery);
	
	int findCount(UserFriendQuery userFriendQuery);
	
	PageResult<UserFriend> findFansList(UserFriendQuery userFriendQuery);
	
	void addFocus(UserFriend userFriend) throws BussinessException;
	
	void cancelFocus(UserFriend userFriend) throws BussinessException;
	
	int findFocusType4UserHome(UserFriendQuery userFriendQuery);
}
