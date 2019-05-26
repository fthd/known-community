package com.known.service;


import com.known.common.model.User;
import com.known.common.vo.UserVo;
import com.known.exception.BussinessException;

import java.util.List;

public interface UserService {

	void register(User user) throws BussinessException;
	
	User findUserByUserName(String userName);
	
	User findUserByEmail(String email);

	User findUserByUserid(String userid);
	
	User login(String account, String password) throws BussinessException;

	User login(String account, String password, boolean isNeedEncoder) throws BussinessException;

	void sendCheckCode(String email) throws BussinessException;

	void modifyPassword(String email, String password, String checkcode) throws BussinessException;

	void addMark(int mark, String userid);

	Integer changeMark(String userid, int mark);

	/**
	 * 获取用户信息
	 * @param userId 用户id
	 * @return
	 * @throws BussinessException
	 */
	User findUserInfo4UserHome(String userId) throws BussinessException;

	void updateUserInfo(User user) throws BussinessException;

	/**
	 * 激活用户状态
	 * @param userName 用户名
	 * @param activationCode 激活码
	 * @throws BussinessException
	 */
	void updateUserActivate(String userName, String activationCode) throws BussinessException;

	void updatePassword(String userId, String oldPassword, String newPassword) throws BussinessException;
	
	void updateUserWithoutValidate(User user);
	
	String findHeadIcon(String account)throws BussinessException;

	List<User> findAllUsers();
	
	void deleteUser(String[] userIds) throws BussinessException;
	
	List<UserVo> findUserVoList();
	
	void updateUserRole(String userId, String[] roleIds)throws BussinessException;
	
	void updateBatchUserRole(String[] userId, String[] roleIds)throws BussinessException;
	
	void markChangeAdvice(String[] userIds, Integer mark, String des)throws BussinessException;


}
