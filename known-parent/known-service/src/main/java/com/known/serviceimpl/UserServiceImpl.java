package com.known.serviceimpl;

import com.known.common.model.ThirdUser;
import com.known.common.model.User;
import com.known.common.vo.UserVo;
import com.known.exception.BussinessException;
import com.known.manager.mapper.UserMapper;
import com.known.manager.query.UserQuery;
import com.known.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper<User, UserQuery> userMapper;

	@Override
	public void register(User user) throws BussinessException {

	}

	@Override
	public User findUserByUserName(String userName) {
		return null;
	}

	@Override
	public User findUserByEmail(String email) {
		return null;
	}

	@Override
	public User findUserByUserid(Integer userid) {
		return null;
	}

	@Override
	public User login(String account, String password) throws BussinessException {
		return null;
	}

	@Override
	public User login(String account, String password, boolean isNeedEncoder) throws BussinessException {
		return null;
	}

	@Override
	public void sendCheckCode(String email) throws BussinessException {

	}

	@Override
	public void modifyPassword(String email, String password, String checkcode) throws BussinessException {

	}

	@Override
	public void addMark(int mark, int userid) {

	}

	@Override
	public Integer changeMark(int userid, int mark) {
		return null;
	}

	@Override
	public User findUserInfo4UserHome(Integer userId) throws BussinessException {
		return null;
	}

	@Override
	public void updateUserInfo(User user) throws BussinessException {

	}

	@Override
	public void updatePassword(Integer userId, String oldPassword, String newPassword) throws BussinessException {

	}

	@Override
	public void copyUserIcon(User user, String source, String dest, String userId) {

	}

	@Override
	public void updateUserWithoutValidate(User user) {

	}

	@Override
	public String findHeadIcon(String account) throws BussinessException {
		return null;
	}

	@Override
	public List<User> findAllUsers() {
		return this.userMapper.selectList(new UserQuery());
	}

	@Override
	public void deleteUser(Integer[] userIds) throws BussinessException {

	}

	@Override
	public List<UserVo> findUserVoList() {
		return null;
	}

	@Override
	public void updateUserRole(Integer userId, Integer[] roleIds) throws BussinessException {

	}

	@Override
	public void updateBatchUserRole(Integer[] userId, Integer[] roleIds) throws BussinessException {

	}

	@Override
	public void markChangeAdvice(Integer[] userIds, Integer mark, String des) throws BussinessException {

	}

	@Override
	public Long queryUserIdByThirdParty(ThirdUser param) {
		return null;
	}

	@Override
	public User insertThirdPartyUser(ThirdUser thirdPartyUser) {
		return null;
	}
	
	/*@Autowired
	private MailConfig mailConfig;
	
	@Autowired
	private SysUserRoleMapper<SysUserRole, SysUserRoleQuery> sysUserRoleMapper;
	
	@Autowired
	private MessageService messageService;
	
	public void register(User user) throws BussinessException {
		String userName = user.getUserName();
		String password = user.getPassword();
		String email = user.getEmail();
		if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(email) || StringUtils.isEmpty(email)
				|| userName.length() < Constants.LENGTH_1 || userName.length() > Constants.LENGTH_20
				|| password.length() < Constants.LENGTH_6 || password.length() > Constants.LENGTH_16
				|| !StringUtils.isUserName(userName) || !StringUtils.isPassword(password)|| !StringUtils.isEmail(email)
				){
			throw new BussinessException("输入参数不合法");
		}
		if(this.findUserByUserName(userName) != null){
			throw new BussinessException("用户名已存在");
		}
		if(this.findUserByEmail(email) != null){
			throw new BussinessException("邮箱已存在");
		}
		Date date = new Date();
		user.setRegisterTime(date);
		user.setLastLoginTime(date);
		user.setUserIcon(StringUtils.getRandomUserIcon());
		user.setUserBg(StringUtils.getRandomUserBg());
		//md5加密密码
		user.setPassword(StringUtils.encode(password));
		this.userMapper.insert(user);
		String dest = "user_icon/" + user.getUserid() + ".jpg";
		copyUserIcon(user,user.getUserIcon(), dest,user.getUserid()+"");
		//user.setUserIcon(dest);
		updateUserWithoutValidate(user);
	}

	public User findUserByUserName(String userName) {	
		UserQuery userQuery = new UserQuery();
		userQuery.setUserName(userName);
		List<User> userList = userMapper.selectList(userQuery);
		if(userList.size() == 1){
			return userList.get(0);
		}
		return null;
	}

	public User findUserByEmail(String email) {
		UserQuery userQuery = new UserQuery();
		userQuery.setEmail(email);
		List<User> userList = userMapper.selectList(userQuery);
		if(userList.size() == 1){
			return userList.get(0);
		}
		return null;
	}
	public User findUserByUserid(Integer userid) {
		UserQuery userQuery = new UserQuery();
		userQuery.setUserid(userid);
		List<User> userList = userMapper.selectList(userQuery);
		if(userList.size() == 1){
			return userList.get(0);
		}
		return null;
	}

	public User login(String account, String password)
			throws BussinessException {
		if(StringUtils.isEmpty(account) || StringUtils.isEmpty(password)){
			throw new BussinessException("输入参数不合法");
		}
		User user = null;
		if(account.contains("@")){
			user = this.findUserByEmail(account);
		}
		else {
			user = this.findUserByUserName(account);
		}
		if(user == null){
			throw new BussinessException("用户不存在");
		}
		if(!StringUtils.encode(password).equals(user.getPassword())){
			throw new BussinessException("密码错误");
		}
		user.setLastLoginTime(new Date());
		this.userMapper.update(user);
		return user;
	}
	
	@Override
	public String findHeadIcon(String account) throws BussinessException{
		if(StringUtils.isEmpty(account)){
			throw new BussinessException("用户名或者邮箱不能为空");
		}
		User user = null;
		if(account.contains("@")){
			user = this.findUserByEmail(account);
		}
		else {
			user = this.findUserByUserName(account);
		}
		if(user == null){
			throw new BussinessException("用户不存在");
		}
		return user.getUserIcon();
	}
	
	public User login(String account, String password,boolean isNeedEncoder)
			throws BussinessException {
		if(StringUtils.isEmpty(account) || StringUtils.isEmpty(password)){
			throw new BussinessException("输入参数不合法");
		}
		User user = null;
		if(account.contains("@")){
			user = this.findUserByEmail(account);
		}
		else {
			user = this.findUserByUserName(account);
		}
		if(user == null){
			throw new BussinessException("用户不存在");
		}
		if(isNeedEncoder){
			if(!StringUtils.encode(password).equals(user.getPassword())){
				throw new BussinessException("密码错误");
			}
		}
		else{
			if(!password.equals(user.getPassword())){
				throw new BussinessException("密码错误");
			}
		}
		user.setLastLoginTime(new Date());
		this.userMapper.update(user);
		return user;
	}

	public void sendCheckCode(String email) throws BussinessException {
			if(StringUtils.isEmpty(email) || !StringUtils.isEmail(email)){
				throw new BussinessException("输入参数不合法");
			}
			User user = this.findUserByEmail(email);
			
			if(user == null){
				throw new BussinessException("邮箱不存在");
			}
			
			String checkCode = StringUtils.getActivationCode(6);
			
			String subject = "八一社区系统通知邮件";
			
			StringBuffer content = new StringBuffer("亲爱的 【" + user.getUserName() + "】用户<br><br>");
			content.append("欢迎您使用<a href='http://www.81wda.com'>81问答BLOG</a>的找回密码功能<br><br>");
			content.append("您的验证码是<h3 style='color:red;'>" + checkCode + "</h3>");
			try {
				MailUtils.sendMail(mailConfig.getSendUserName(), mailConfig.getSendPassword(), email,
						subject, new String(content));
			} catch (Exception e) {
				throw new BussinessException("发送邮件失败,请稍后再试");
			}
			user.setActivationCode(checkCode);
			this.userMapper.update(user);
	}

	public void modifyPassword(String email, String password, String checkcode) throws BussinessException {
		if(StringUtils.isEmpty(email) || StringUtils.isEmpty(password) || StringUtils.isEmpty(checkcode) ||
				 password.length() < Constants.LENGTH_6 || password.length() > Constants.LENGTH_16				
				){
				throw new BussinessException("输入参数不合法");
		}
		User user = this.findUserByEmail(email);
		if(user == null){
			throw new BussinessException("邮箱不存在");
		}
		if(!checkcode.equalsIgnoreCase(user.getActivationCode())){
			throw new BussinessException("验证码错误");
		}
		user.setPassword(StringUtils.encode(password));
		this.userMapper.update(user);	
	}

	public void addMark(int mark, int userid) {
		changeMark(userid, mark);
	}
	
	@Transactional(propagation= Propagation.REQUIRES_NEW)
	public Integer changeMark(int userid, int mark){
		return this.userMapper.changeUserMark(mark, userid);
	}
	
	public User findUserInfo4UserHome(Integer userId)throws BussinessException{
		User user = this.findUserByUserid(userId);
		if(user == null){
			throw new BussinessException("用户不存在");
		}
		user.setPassword(null);
		user.setActivationCode(null);
		return user;
	}

	
	public void updateUserInfo(User user) throws BussinessException {
		if(user.getAddress().length() > TextLengthEnum.TEXT_50_LENGTH.getLength() ||
				user.getWork().length() > TextLengthEnum.TEXT_50_LENGTH.getLength() ||
				user.getCharacters().length() > TextLengthEnum.TEXT_200_LENGTH.getLength() ||
				user.getSex().length() > 1
				){
			throw new BussinessException("输入参数不合法");
		}
		this.userMapper.update(user);
	}
	
	
	public void updatePassword(Integer userId, String oldPassword,
			String newPassword) throws BussinessException {
		if(StringUtils.isEmpty(oldPassword) || StringUtils.isEmpty(newPassword) ||
				 oldPassword.length() < Constants.LENGTH_6 || oldPassword.length() > Constants.LENGTH_16||
				 newPassword.length() < Constants.LENGTH_6 || newPassword.length() > Constants.LENGTH_16	
				){
				throw new BussinessException("输入参数不合法");
		}
		User user = findUserByUserid(userId);
		if(!user.getPassword().equals(StringUtils.encode(oldPassword))){
			throw new BussinessException("原密码错误");
		}
		user.setPassword(StringUtils.encode(newPassword));
		this.userMapper.update(user);	
	}

	public void copyUserIcon(User user,String source, String dest,String userId) {
		File sourceFile = new File(ServerUtils.getRealPath() + "/resources/images/" + source);
		File destFile = new File(ServerUtils.getRealPath() + "/resources/images/" + dest);
		try {
			FileUtils.copyFile(sourceFile, destFile);
			OSSClient ossClient= AliyunOSSClientUtil.getOSSClient();
			AliyunOSSClientUtil.uploadObject2OSS(ossClient, destFile, "user_icon/");
			user.setUserIcon(AliyunOSSClientUtil.getUrl(ossClient,"user_icon/"+userId+".jpg"));
			destFile.delete();
			//sourceFile.delete();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	public void updateUserWithoutValidate(User user) {
		this.userMapper.update(user);	
	}

	@Override
	public List<User> findAllUsers() {
		return this.userMapper.selectList(new UserQuery());
	}

	@Override
	public void deleteUser(Integer[] userIds) throws BussinessException {
		if(userIds.length == 0){
			throw new BussinessException("参数错误");
		}
		this.userMapper.delete(userIds);
	}

	@Override
	public List<UserVo> findUserVoList() {
		return userMapper.selectUserVoList();
	}
	
	@Override
	@Transactional(propagation= Propagation.REQUIRES_NEW, rollbackFor=BussinessException.class)
	public void updateUserRole(Integer userId, Integer[] roleIds) throws BussinessException {
		if(userId == null){
			throw new BussinessException("参数异常");
		}
		
		SysUserRole sysUserRole = new SysUserRole();
		sysUserRole.setUserId(userId);
		sysUserRoleMapper.delete(sysUserRole);
		
		if(roleIds != null && roleIds.length!=0){
			sysUserRoleMapper.insertBatch(userId, roleIds);
		}
		
	}
	
	@Override
	@Transactional(propagation= Propagation.REQUIRES_NEW, rollbackFor=BussinessException.class)
	public void updateBatchUserRole(Integer[] userId, Integer[] roleIds) throws BussinessException {
		for(int i = 0; i < userId.length; i++){
			updateUserRole(userId[i], roleIds);
		}
	}
	
	
	@Override
	@Transactional(propagation= Propagation.REQUIRES_NEW, rollbackFor=BussinessException.class)
	public void markChangeAdvice(Integer[] userIds, Integer mark, String des) throws BussinessException {
		if(userIds == null && userIds.length == 0 || mark == null || StringUtils.isEmpty(des) ||
				des.length() > TextLengthEnum.TEXT_200_LENGTH.getLength()
				){
			throw new BussinessException("参数错误");
		}
		
		Set<Integer> userIdSet = new HashSet<>();
		for(int i = 0; i < userIds.length;i ++){
			changeMark(userIds[i], mark);
			userIdSet.add(userIds[i]);
		}
		
		MessageParams messageParams = new MessageParams();
		messageParams.setMessageType(MessageType.SYSTEM_MARK);
		messageParams.setDes(des.trim());
		messageParams.setReceiveUserIds(userIdSet);
		messageService.createMessage(messageParams);
	}

	@Override
	public Long queryUserIdByThirdParty(ThirdUser param) {
		return null;
	}

	@Override
	public User insertThirdPartyUser(ThirdUser thirdPartyUser) {
		return null;
	}*/

}
