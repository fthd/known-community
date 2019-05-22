package com.known.service;


import com.known.common.model.ThirdUser;
import com.known.common.model.User;
import com.known.common.vo.UserVo;
import com.known.exception.BussinessException;

import java.util.List;

public interface UserService {

	void register(User user) throws BussinessException;
	
	User findUserByUserName(String userName);
	
	User findUserByEmail(String email);

	User findUserByUserid(Integer userid);
	
	User login(String account, String password) throws BussinessException;

	User login(String account, String password, boolean isNeedEncoder) throws BussinessException;

	void sendCheckCode(String email) throws BussinessException;

	void modifyPassword(String email, String password, String checkcode) throws BussinessException;

	void addMark(int mark, int userid);

	Integer changeMark(int userid, int mark);

	User findUserInfo4UserHome(Integer userId) throws BussinessException;

	void updateUserInfo(User user) throws BussinessException;

	/**
	 * 激活用户状态
	 * @param userName 用户名
	 * @param activationCode 激活码
	 * @throws BussinessException
	 */
	void updateUserActivate(String userName, String activationCode) throws BussinessException;

	void updatePassword(Integer userId, String oldPassword, String newPassword) throws BussinessException;
	
	void updateUserWithoutValidate(User user);
	
	String findHeadIcon(String account)throws BussinessException;

	List<User> findAllUsers();
	
	void deleteUser(Integer[] userIds) throws BussinessException;
	
	List<UserVo> findUserVoList();
	
	void updateUserRole(Integer userId, Integer[] roleIds)throws BussinessException;
	
	void updateBatchUserRole(Integer[] userId, Integer[] roleIds)throws BussinessException;
	
	void markChangeAdvice(Integer[] userIds, Integer mark, String des)throws BussinessException;


	/** 查询第三方帐号用户Id */
	Long queryUserIdByThirdParty(ThirdUser param);

	/** 保存第三方帐号 */
	User insertThirdPartyUser(ThirdUser thirdPartyUser);


}
