package com.known.service_impl;

import com.known.common.enums.PageSizeEnum;
import com.known.common.model.User;
import com.known.common.model.UserFriend;
import com.known.common.vo.Page;
import com.known.common.vo.PageResult;
import com.known.exception.BussinessException;
import com.known.manager.mapper.UserFriendMapper;
import com.known.manager.query.UserFriendQuery;
import com.known.service.UserFriendService;
import com.known.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserFriendServiceImpl implements UserFriendService {
	@Autowired
	private UserFriendMapper<UserFriend, UserFriendQuery> userFriendMapper;
	
	@Autowired
	private UserService userService;
	
	public PageResult<UserFriend> findFriendList(UserFriendQuery userFriendQuery) {
		int count = userFriendMapper.selectCount(userFriendQuery);
		int size = PageSizeEnum.PAGE_SIZE21.getSize();
		int pageNum = 1;
		if(userFriendQuery.getPageNum() != 1){
			pageNum = userFriendQuery.getPageNum();
		}
		Page page = new Page(pageNum, count, size);
		userFriendQuery.setPage(page);
		List<UserFriend> list = userFriendMapper.selectList(userFriendQuery);
		return new PageResult<>(page,	list);
	}

	public PageResult<UserFriend> findFansList(UserFriendQuery userFriendQuery) {
		int count = userFriendMapper.selectCount(userFriendQuery);
		int size = PageSizeEnum.PAGE_SIZE21.getSize();
		int pageNum = 1;
		if(userFriendQuery.getPageNum() != 1){
			pageNum = userFriendQuery.getPageNum();
		}
		Page page = new Page(pageNum, count, size);
		userFriendQuery.setPage(page);
		List<UserFriend> list = userFriendMapper.selectList(userFriendQuery);
		return new PageResult<>(page,	 list);
	}

	public void addFocus(UserFriend userFriend) throws BussinessException {
		if(userFriend.getFriendUserId() == null || userFriend.getUserId() == null){
			throw new BussinessException("参数错误");
		}
		User friendUser = userService.findUserByUserid(userFriend.getFriendUserId());
		userFriend.setFriendUserName(friendUser.getUserName());
		userFriend.setFriendUserIcon(friendUser.getUserIcon());
		userFriend.setCreateTime(new Date());
		userFriendMapper.insert(userFriend);
	}

	public void cancelFocus(UserFriend userFriend) throws BussinessException {
		if(userFriend.getFriendUserId() == null || userFriend.getUserId() == null){
			throw new BussinessException("参数错误");
		}
		userFriendMapper.delete(userFriend);
	}

	public int findFocusType4UserHome(UserFriendQuery userFriendQuery) {

		//未登录
		if(userFriendQuery.getUserId() == null){
			return 1;
		}
		//本人
		if(userFriendQuery.getUserId().equals(userFriendQuery.getFriendUserId())){
			return 0;
		}
		List<UserFriend> list = userFriendMapper.selectList(userFriendQuery);
		if(list.isEmpty()){
			// 未关注
			return 1;
		} else{
			// 已关注
			return 2;
		}
	}

	public int findCount(UserFriendQuery userFriendQuery) {
		if (userFriendQuery.getFriendUserId() == null && userFriendQuery.getUserId() == null) {
			return 0;
		}
		return userFriendMapper.selectCount(userFriendQuery);
	}

}
