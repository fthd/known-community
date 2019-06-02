package com.known.web.controller;


import com.known.common.config.UserConfig;
import com.known.common.enums.CodeEnum;
import com.known.common.model.SessionUser;
import com.known.common.model.User;
import com.known.common.vo.OutResponse;
import com.known.exception.BussinessException;
import com.known.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.Date;

/**
 * 登录、注册
 *
 * @author tangjunxiang
 * @version 1.0
 * @date 2019-05-06 17:17
 */
@RestController
@RequestMapping(value = "/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Resource
    private UserConfig userConfig;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * 注册页面
     *
     * @return
     */
    @RequestMapping("/register")
    public ModelAndView register() {
        return new ModelAndView("/page/register");
    }

    /**
     * 注册功能实现
     *
     * @param user    用户信息
     * @return
     */
    @ResponseBody
    @RequestMapping("/register.do")
    public OutResponse<Object> registerdo( User user) {

        OutResponse<Object> outResponse = new OutResponse<>();
        try {
            userService.register(user);
            outResponse.setCode(CodeEnum.SUCCESS);

        } catch (BussinessException e) {
            outResponse.setMsg(e.getLocalizedMessage());
            outResponse.setCode(CodeEnum.BUSSINESSERROR);
            logger.error("用户注册失败,用户名:{}邮箱:{}", user.getUserName(), user.getEmail());
        } catch (Exception e) {
            outResponse.setMsg(CodeEnum.SERVERERROR.getDesc());
            outResponse.setCode(CodeEnum.SERVERERROR);
            logger.error("用户注册失败,用户名:{}邮箱:{}", user.getUserName(), user.getEmail());
        }
        return outResponse;
    }

    @RequestMapping("/activate")
    public ModelAndView activate(String userName, String activationCode) {

        OutResponse<Object> outResponse = new OutResponse<>();
        ModelAndView view = new ModelAndView("/page/active");
        try {
            userService.updateUserActivate(userName, activationCode);
            outResponse.setCode(CodeEnum.SUCCESS);
            outResponse.setMsg("尊敬的【"+userName+"】用户，恭喜您账户激活成功");
        } catch (BussinessException e) {
            outResponse.setMsg(e.getLocalizedMessage());
            outResponse.setCode(CodeEnum.BUSSINESSERROR);
            logger.error("用户激活失败,用户名:{}", userName);
        } catch (Exception e) {
            outResponse.setMsg(CodeEnum.SERVERERROR.getDesc());
            outResponse.setCode(CodeEnum.SERVERERROR);
            logger.error("用户激活失败,用户名:{}", userName);
        }
        view.addObject("outResponse", outResponse);

        return view;
    }

    @RequestMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("/page/login");
    }

    /**
     * 登录功能
     *
     * @param request
     * @param response
     * @param account
     * @param password
     * @param rememberMe
     * @return
     */
    @RequestMapping("/login.do")
    public OutResponse<Object> logindo(HttpServletRequest request, HttpServletResponse response,
                                        String account, String password, String rememberMe) {
        final String REMEMBERME = "1";
        OutResponse<Object> outResponse = new OutResponse<>();
        HttpSession session = request.getSession();
        User user;
        try {
            //用户登录
            user = userService.login(account, password);
            user.setLastLoginTime(new Date());
            outResponse.setCode(CodeEnum.SUCCESS);

            SessionUser sessionUser = new SessionUser();
            sessionUser.setUserid(user.getUserid());
            sessionUser.setUserName(user.getUserName());
            sessionUser.setUserIcon(user.getUserIcon());
            session.setAttribute(userConfig.getSession_User_Key(), sessionUser);

            if (REMEMBERME.equals(rememberMe)) {
                String infor = URLEncoder.encode(account, "utf-8") + "|" + user.getPassword();
                // 清除之前的Cookie 信息
                Cookie cookie = new Cookie(userConfig.getCookie_User_Info(), null);
                cookie.setPath("/");
                cookie.setMaxAge(0);
                // 建用户信息保存到Cookie中
                cookie = new Cookie(userConfig.getCookie_User_Info(), infor);
                cookie.setPath("/");
                // 设置七日内自动登录
                cookie.setMaxAge(7*24*60*60);
                response.addCookie(cookie);
            } else {
                Cookie cookie = new Cookie(userConfig.getCookie_User_Info(), null);
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        } catch (BussinessException e) {
            outResponse.setMsg(e.getLocalizedMessage());
            outResponse.setCode(CodeEnum.BUSSINESSERROR);
            logger.error("用户登录失败,账号:{}", account);
        } catch (Exception e) {
            outResponse.setMsg(CodeEnum.SERVERERROR.getDesc());
            outResponse.setCode(CodeEnum.SERVERERROR);
            logger.error("用户登录失败,账号:{}", account);
        }
        return outResponse;
    }

    @RequestMapping("/findpassword")
    public ModelAndView findPassword() {
        return new ModelAndView("/page/findpassword");
    }

    /**
     *
     * @param email
     * @return
     */
    @RequestMapping("/sendCheckCode")
    public OutResponse<Object> sendCheckCode(String email) {
        OutResponse<Object> outResponse = new OutResponse<>();
        try {
            userService.sendCheckCode(email);
            outResponse.setCode(CodeEnum.SUCCESS);
        } catch (BussinessException e) {
            outResponse.setMsg(e.getLocalizedMessage());
            outResponse.setCode(CodeEnum.BUSSINESSERROR);
            logger.error("验证码发送失败,邮箱:{}");
        } catch (Exception e) {
            outResponse.setMsg(CodeEnum.SERVERERROR.getDesc());
            outResponse.setCode(CodeEnum.SERVERERROR);
            logger.error("验证码发送失败,邮箱:{}", email);
        }
        return outResponse;
    }

    @RequestMapping("/findPassword.do")
    public OutResponse<Object> findPassworddo(String email, String password, String checkcode) {
        OutResponse<Object> outResponse = new OutResponse<Object>();
        try {
            userService.modifyPassword(email, password, checkcode);
            outResponse.setCode(CodeEnum.SUCCESS);
        } catch (BussinessException e) {
            outResponse.setMsg(e.getLocalizedMessage());
            outResponse.setCode(CodeEnum.BUSSINESSERROR);
            logger.error("密码修改失败,邮箱{}", email);
        } catch (Exception e) {
            outResponse.setMsg(CodeEnum.SERVERERROR.getDesc());
            outResponse.setCode(CodeEnum.SERVERERROR);
            logger.error("密码修改失败,邮箱:{}", email);
        }
        return outResponse;
    }

    /**
     * 登出
     * @param session
     * @param response
     * @return
     */
    @RequestMapping("/logout")
    public ModelAndView logout(HttpSession session, HttpServletResponse response) {
        ModelAndView view = new ModelAndView("page/login");
        session.invalidate();
        Cookie cookie = new Cookie(userConfig.getCookie_User_Info(), null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return view;
    }


    /**
     * blur事件得到用户头像
     *
     * @param account
     * @return
     */
    @RequestMapping("/findHeadImage")
    public OutResponse<Object> findHeadImage(String account) {

        OutResponse<Object> outResponse = new OutResponse<>();
        String headIcon;
        try {
            headIcon = userService.findHeadIcon(account);
            outResponse.setCode(CodeEnum.SUCCESS);
            outResponse.setData(headIcon);
        } catch (BussinessException e) {
            outResponse.setMsg(e.getLocalizedMessage());
            outResponse.setCode(CodeEnum.BUSSINESSERROR);
            logger.error("头像获取失败,账户{}异常{}", account, e.getLocalizedMessage());
        } catch (Exception e) {
            outResponse.setMsg(CodeEnum.SERVERERROR.getDesc());
            outResponse.setCode(CodeEnum.SERVERERROR);
            logger.error("头像获取失败,账户{}异常{}", account, e.getLocalizedMessage());
        }
        return outResponse;
    }

}
