package com.known.web.controller;

import com.known.common.config.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 第三方登录控制类
 * @Author:
 * @Description:
 * @Date: Create in 2018-06-13 13:06
 */
@Controller
public class ThirdPartyLoginController extends BaseController {
    @Autowired
    private Configuration configuration;
    @RequestMapping(value = "/third_login",method = RequestMethod.GET)
    public void thirdLogin(HttpServletRequest request, HttpServletResponse response, @RequestParam("t") String type) {
        String url = getRedirectUrl(request, type);
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getRedirectUrl(HttpServletRequest request, String type) {
        String url = "";
        String host = request.getHeader("host");
        url = configuration.getAuthorizeURLQq() + "?client_id=" + configuration.getAppIdQq()+ "&response_type=code&scope="
                + configuration.getScopeQq() + "&redirect_uri=http://" + host
                + configuration.getRedirectUrlQq();
        return url;
    }


}
