package com.known.web.controller;


import com.known.common.config.UserConfig;
import com.known.common.model.User;
import com.known.common.model.SessionUser;
import com.known.common.utils.Constants;
import com.known.common.vo.OutResponse;
import com.known.common.vo.PageResult;
import com.known.exception.BussinessException;
import com.known.manager.query.AskQuery;
import com.known.manager.query.BlogQuery;
import com.known.manager.query.KnowledgeQuery;
import com.known.manager.query.TopicQuery;
import com.known.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.known.common.enums.BlogStatusEnum;
import com.known.common.enums.Code;
import com.known.common.model.*;

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
@Controller
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
    public String register() {
        return "/page/register";
    }

    /**
     * 注册功能实现
     *
     * @param session
     * @param user    用户信息
     * @return
     */
    @ResponseBody
    @RequestMapping("/register.do")
    public OutResponse<Object> registerdo(HttpSession session, User user) {

        OutResponse<Object> outResponse = new OutResponse<>();
        try {
            userService.register(user);
            outResponse.setCode(Code.SUCCESS);

        } catch (BussinessException e) {
            outResponse.setMsg(e.getLocalizedMessage());
            outResponse.setCode(Code.BUSSINESSERROR);
            logger.error("用户注册失败,用户名:{}邮箱:{}", user.getUserName(), user.getEmail());
        } catch (Exception e) {
            outResponse.setMsg(Code.SERVERERROR.getDesc());
            outResponse.setCode(Code.SERVERERROR);
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
            outResponse.setCode(Code.SUCCESS);
            outResponse.setMsg("尊敬的【"+userName+"】用户，恭喜您账户激活成功");
        } catch (BussinessException e) {
            outResponse.setMsg(e.getLocalizedMessage());
            outResponse.setCode(Code.BUSSINESSERROR);
            logger.error("用户激活失败,用户名:{}", userName);
        } catch (Exception e) {
            outResponse.setMsg(Code.SERVERERROR.getDesc());
            outResponse.setCode(Code.SERVERERROR);
            logger.error("用户激活失败,用户名:{}", userName);
        }
        view.addObject("outResponse", outResponse);

        return view;
    }

    @RequestMapping("/login")
    public String login() {
        return "/page/login";
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
    @ResponseBody
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
            outResponse.setCode(Code.SUCCESS);

            SessionUser sessionUser = new SessionUser();
            sessionUser.setUserid(user.getUserid());
            sessionUser.setUserName(user.getUserName());
            sessionUser.setUserIcon(user.getUserIcon());
            session.setAttribute(userConfig.getSession_User_Key(), sessionUser);


            if (REMEMBERME.equals(rememberMe)) {    // 清除之前的Cookie 信息
                String infor = URLEncoder.encode(account.toString(), "utf-8") + "|" + user.getPassword();
                Cookie cookie = new Cookie(userConfig.getCookie_User_Info(), null);
                cookie.setPath("/");
                cookie.setMaxAge(0);
                // 建用户信息保存到Cookie中
                cookie = new Cookie(userConfig.getCookie_User_Info(), infor);
                cookie.setPath("/");
                // 设置最大生命周期为1年。
                cookie.setMaxAge(31536000);
                response.addCookie(cookie);
            } else {
                Cookie cookie = new Cookie(userConfig.getCookie_User_Info(), null);
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        } catch (BussinessException e) {
            outResponse.setMsg(e.getLocalizedMessage());
            outResponse.setCode(Code.BUSSINESSERROR);
            logger.error("用户登录失败,账号:{}", account);
        } catch (Exception e) {
            outResponse.setMsg(Code.SERVERERROR.getDesc());
            outResponse.setCode(Code.SERVERERROR);
            logger.error("用户登录失败,账号:{}", account);
        }
        return outResponse;
    }

    @RequestMapping("/findpassword")
    public String findPassword() {
        return "/page/findpassword";
    }

    /**
     *
     * @param email
     * @return
     */
    @ResponseBody
    @RequestMapping("/sendCheckCode")
    public OutResponse<Object> sendCheckCode(String email) {
        OutResponse<Object> outResponse = new OutResponse<>();
        try {
            userService.sendCheckCode(email);
            outResponse.setCode(Code.SUCCESS);
        } catch (BussinessException e) {
            outResponse.setMsg(e.getLocalizedMessage());
            outResponse.setCode(Code.BUSSINESSERROR);
            logger.error("验证码发送失败,邮箱:{}");
        } catch (Exception e) {
            outResponse.setMsg(Code.SERVERERROR.getDesc());
            outResponse.setCode(Code.SERVERERROR);
            logger.error("验证码发送失败,邮箱:{}", email);
        }
        return outResponse;
    }

    @ResponseBody
    @RequestMapping("/findPassword.do")
    public OutResponse<Object> findPassworddo(String email, String password, String checkcode) {
        OutResponse<Object> outResponse = new OutResponse<Object>();
        try {
            userService.modifyPassword(email, password, checkcode);
            outResponse.setCode(Code.SUCCESS);
        } catch (BussinessException e) {
            outResponse.setMsg(e.getLocalizedMessage());
            outResponse.setCode(Code.BUSSINESSERROR);
            logger.error("密码修改失败,邮箱{}", email);
        } catch (Exception e) {
            outResponse.setMsg(Code.SERVERERROR.getDesc());
            outResponse.setCode(Code.SERVERERROR);
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
    @ResponseBody
    @RequestMapping("/findHeadImage")
    public OutResponse<Object> findHeadImage(String account) {

        OutResponse<Object> outResponse = new OutResponse<>();
        String headIcon;
        try {
            headIcon = userService.findHeadIcon(account);
            outResponse.setCode(Code.SUCCESS);
            outResponse.setData(headIcon);
        } catch (BussinessException e) {
            outResponse.setMsg(e.getLocalizedMessage());
            outResponse.setCode(Code.BUSSINESSERROR);
            logger.error("头像获取失败,账户{}异常{}", account, e.getLocalizedMessage());
        } catch (Exception e) {
            outResponse.setMsg(Code.SERVERERROR.getDesc());
            outResponse.setCode(Code.SERVERERROR);
            logger.error("头像获取失败,账户{}异常{}", account, e.getLocalizedMessage());
        }
        return outResponse;
    }

}
