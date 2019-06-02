package com.known.service_impl;


import com.known.common.config.MailConfig;
import com.known.common.config.UrlConfig;
import com.known.common.enums.MessageTypeEnum;
import com.known.common.enums.TextLengthEnum;
import com.known.common.model.MessageParams;
import com.known.common.model.SysUserRole;
import com.known.common.model.User;
import com.known.common.utils.Constants;
import com.known.common.utils.MailUtil;
import com.known.common.utils.StringUtil;
import com.known.common.utils.UUIDUtil;
import com.known.common.vo.UserVo;
import com.known.exception.BussinessException;
import com.known.manager.mapper.SysUserRoleMapper;
import com.known.manager.mapper.UserMapper;
import com.known.manager.query.SysUserRoleQuery;
import com.known.manager.query.UserQuery;
import com.known.service.MessageService;
import com.known.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper<User, UserQuery> userMapper;

    @Resource
    private MailConfig mailConfig;

    @Resource
    private UrlConfig urlConfig;

    @Autowired
    private SysUserRoleMapper<SysUserRole, SysUserRoleQuery> sysUserRoleMapper;

    @Autowired
    private MessageService messageService;

    @Value("${Absolute_Path}")
    private String Absolute_Path;

    @Value("${Mail_Real_Path}")
    private String Mail_Real_Path;

    public void register(User user) throws BussinessException {
        String userName = user.getUserName();
        String password = user.getPassword();
        String email = user.getEmail();

        //注册验证
        if (StringUtil.isEmpty(userName) || StringUtil.isEmpty(email) || StringUtil.isEmpty(email)
                || userName.length() < Constants.LENGTH_1 || userName.length() > Constants.LENGTH_20
                || password.length() < Constants.LENGTH_6 || password.length() > Constants.LENGTH_16
                || !StringUtil.isUserName(userName) || !StringUtil.isPassword(password) || !StringUtil.isEmail(email)
        ) {
            throw new BussinessException("输入参数不合法");
        }
        if (findUserByUserName(userName) != null) {
            throw new BussinessException("用户名已存在");
        }
        if (findUserByEmail(email) != null) {
            throw new BussinessException("邮箱已存在");
        }
        user.setUserid(UUIDUtil.getUUID());
        Date date = new Date();
        user.setRegisterTime(date);
        user.setLastLoginTime(date);
        user.setUserIcon(urlConfig.getUser_Icon_Url()+ StringUtil.getRandomUserIcon());
        user.setUserBg(urlConfig.getUser_Bg_Url()+ StringUtil.getRandomUserBg());
        //md5加密密码
        user.setPassword(StringUtil.encode(password));
        String activationCode = StringUtil.getActivationCode(6);
        user.setActivationCode(activationCode);
        // 发送激活邮件
        String subject = "知会问答社区系统通知邮件";
        StringBuffer content = new StringBuffer("亲爱的 【" + user.getUserName() + "】用户<br><br>");
        content.append("欢迎您使用知会问答社区！<br><br>");
        content.append("点击<a href='" + Mail_Real_Path + "/user/activate?userName=" + user.getUserName() +
                "&activationCode=" + activationCode + "'>知会问答社区账号激活</a>激活您的账号！");
        try {
            MailUtil.sendMail(mailConfig.getSendUserName(), mailConfig.getSendPassword(), email,
                    subject, new String(content));
        } catch (Exception e) {
            throw new BussinessException("发送邮件失败,请稍后再试");
        }

        userMapper.insert(user);
    }

    public void updateUserActivate(String userName, String activationCode) throws BussinessException {

        //通过用户名查找用户
        User user = findUserByUserName(userName);
        if (user == null) {
            throw new BussinessException("用户【"+userName+"】不存在，请注册用户");
        }
        if (user.getUserPage() != 0) {
            throw new BussinessException("用户【"+userName+"】已激活， 请登录");
        }
        if (!activationCode.equals(user.getActivationCode())) {
            throw new BussinessException("用户【"+userName+"】激活码失效, 请使用最新激活链接");
        }
        userMapper.updateStatus(userName, activationCode);
    }

    public User findUserByUserName(String userName) {
        UserQuery userQuery = new UserQuery();
        userQuery.setUserName(userName);
        List<User> userList = userMapper.selectList(userQuery);
        if (userList.size() == 1) {
            return userList.get(0);
        }
        return null;
    }

    public User findUserByEmail(String email) {
        UserQuery userQuery = new UserQuery();
        userQuery.setEmail(email);
        List<User> userList = userMapper.selectList(userQuery);
        if (userList.size() == 1) {
            return userList.get(0);
        }
        return null;
    }

    public User findUserByUserid(String userid) {
        UserQuery userQuery = new UserQuery();
        userQuery.setUserid(userid);
        List<User> userList = userMapper.selectList(userQuery);
        if (userList.size() == 1) {
            return userList.get(0);
        }
        return null;
    }

    public User login(String account, String password)
            throws BussinessException {
        if (StringUtil.isEmpty(account) || StringUtil.isEmpty(password)) {
            throw new BussinessException("输入参数不合法");
        }
        User user;
        if (account.contains("@")) {
            user = findUserByEmail(account);
        } else {
            user = findUserByUserName(account);
        }
        if (user == null) {
            throw new BussinessException("用户不存在");
        }
        if (!StringUtil.encode(password).equals(user.getPassword())) {
            throw new BussinessException("密码错误");
        }
        if (user.getStatus() == 0) {
            throw new BussinessException("请查收邮件, 激活账户后登录");
        }
        user.setLastLoginTime(new Date());
        userMapper.update(user);
        return user;
    }

    @Override
    public String findHeadIcon(String account) throws BussinessException {
        if (StringUtil.isEmpty(account)) {
            throw new BussinessException("用户名或者邮箱不能为空");
        }
        User user;
        if (account.contains("@")) {
            user = findUserByEmail(account);
        } else {
            user = findUserByUserName(account);
        }
        if (user == null) {
            throw new BussinessException("用户不存在");
        }
        return user.getUserIcon();
    }

    /**
     * 用户登录
     * @param account 用户名
     * @param password 密码
     * @param isNeedEncoder 是否需要md5加密
     * @return
     * @throws BussinessException
     */
    public User login(String account, String password, boolean isNeedEncoder)
            throws BussinessException {
        if (StringUtil.isEmpty(account) || StringUtil.isEmpty(password)) {
            throw new BussinessException("输入参数不合法");
        }
        User user;
        if (account.contains("@")) {
            user = findUserByEmail(account);
        } else {
            user = findUserByUserName(account);
        }
        if (user == null) {
            throw new BussinessException("用户不存在");
        }
        if (isNeedEncoder) {
            if (!StringUtil.encode(password).equals(user.getPassword())) {
                throw new BussinessException("密码错误");
            }
        } else {
            if (!password.equals(user.getPassword())) {
                throw new BussinessException("密码错误");
            }
        }
        if (user.getUserPage() == 0) {
            throw new BussinessException("请查收邮件, 激活账户后登录");
        }
        user.setLastLoginTime(new Date());
        userMapper.update(user);
        return user;
    }

    public void sendCheckCode(String email) throws BussinessException {
        if (StringUtil.isEmpty(email) || !StringUtil.isEmail(email)) {
            throw new BussinessException("输入参数不合法");
        }
        User user = findUserByEmail(email);

        if (user == null) {
            throw new BussinessException("邮箱不存在");
        }

        String checkCode = StringUtil.getActivationCode(6);

        String subject = "知会问答社区系统通知邮件";

        StringBuffer content = new StringBuffer("亲爱的 【" + user.getUserName() + "】用户<br><br>");
        content.append("欢迎您使用<a href='#'>知会问答社区</a>的找回密码功能<br><br>");
        content.append("您的验证码是<h3 style='color:red;'>" + checkCode + "</h3>");
        try {
            MailUtil.sendMail(mailConfig.getSendUserName(), mailConfig.getSendPassword(), email,
                    subject, new String(content));
        } catch (Exception e) {
            throw new BussinessException("发送邮件失败,请稍后再试");
        }
        user.setActivationCode(checkCode);
        userMapper.update(user);
    }

    public void modifyPassword(String email, String password, String checkcode) throws BussinessException {
        if (StringUtil.isEmpty(email) || StringUtil.isEmpty(password) || StringUtil.isEmpty(checkcode) ||
                password.length() < Constants.LENGTH_6 || password.length() > Constants.LENGTH_16
        ) {
            throw new BussinessException("输入参数不合法");
        }
        User user = findUserByEmail(email);
        if (user == null) {
            throw new BussinessException("邮箱不存在");
        }
        if (!checkcode.equalsIgnoreCase(user.getActivationCode())) {
            throw new BussinessException("验证码错误");
        }
        user.setPassword(StringUtil.encode(password));
        userMapper.update(user);
    }

    public void addMark(int mark, String userid) {
        changeMark(userid, mark);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Integer changeMark(String userid, int mark) {
        return userMapper.changeUserMark(mark, userid);
    }

    public User findUserInfo4UserHome(String userId) throws BussinessException {
        
        User user = findUserByUserid(userId);
        if (user == null) {
            throw new BussinessException("用户不存在");
        }
        //设置密码为空
        user.setPassword(null);
        //设置激活码为空
        user.setActivationCode(null);
        return user;
    }


    public void updateUserInfo(User user) throws BussinessException {
        if (user.getAddress().length() > TextLengthEnum.TEXT_50_LENGTH.getLength() ||
                user.getWork().length() > TextLengthEnum.TEXT_50_LENGTH.getLength() ||
                user.getCharacters().length() > TextLengthEnum.TEXT_200_LENGTH.getLength() ||
                user.getSex().length() > 1
        ) {
            throw new BussinessException("输入参数不合法");
        }
        userMapper.update(user);
    }


    public void updatePassword(String userId, String oldPassword,
                               String newPassword) throws BussinessException {
        if (StringUtil.isEmpty(oldPassword) || StringUtil.isEmpty(newPassword) ||
                oldPassword.length() < Constants.LENGTH_6 || oldPassword.length() > Constants.LENGTH_16 ||
                newPassword.length() < Constants.LENGTH_6 || newPassword.length() > Constants.LENGTH_16
        ) {
            throw new BussinessException("输入参数不合法");
        }
        User user = findUserByUserid(userId);
        if (!user.getPassword().equals(StringUtil.encode(oldPassword))) {
            throw new BussinessException("原密码错误");
        }
        user.setPassword(StringUtil.encode(newPassword));
        userMapper.update(user);
    }

    public void updateUserWithoutValidate(User user) {
        userMapper.update(user);
    }

    @Override
    public List<User> findAllUsers() {
        return userMapper.selectList(new UserQuery());
    }

    @Override
    public void deleteUser(String[] userIds) throws BussinessException {
        if (userIds.length == 0) {
            throw new BussinessException("参数错误");
        }
        userMapper.delete(userIds);
    }

    @Override
    public List<UserVo> findUserVoList() {
        return userMapper.selectUserVoList();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = BussinessException.class)
    public void updateUserRole(String userId, String[] roleIds) throws BussinessException {
        if (userId == null) {
            throw new BussinessException("参数异常");
        }

        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserId(userId);
        // 更新之前先删除旧的数据
        sysUserRoleMapper.delete(sysUserRole);

        // 批量插入数据, 一个用户对应多个角色id
        if (roleIds != null && roleIds.length != 0) {
            sysUserRoleMapper.insertBatch(userId, roleIds);
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = BussinessException.class)
    public void updateBatchUserRole(String[] userId, String[] roleIds) throws BussinessException {
        for (int i = 0; i < userId.length; i++) {
            updateUserRole(userId[i], roleIds);
        }
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = BussinessException.class)
    public void markChangeAdvice(String[] userIds, Integer mark, String des) throws BussinessException {
        if (userIds == null && userIds.length == 0 || mark == null || StringUtil.isEmpty(des) ||
                des.length() > TextLengthEnum.TEXT_200_LENGTH.getLength()
        ) {
            throw new BussinessException("参数错误");
        }

        Set<String> userIdSet = new HashSet<>();
        for (int i = 0; i < userIds.length; i++) {
            changeMark(userIds[i], mark);
            userIdSet.add(userIds[i]);
        }

        MessageParams messageParams = new MessageParams();
        messageParams.setMessageType(MessageTypeEnum.SYSTEM_MARK);
        messageParams.setDes(des.trim());
        messageParams.setReceiveUserIds(userIdSet);
        messageService.createMessage(messageParams);
    }

}
