package com.known.web;

import com.known.common.model.User;
import com.known.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author tangjunxiang
 * @version 1.0
 * @date 2019-05-20 22:42
 */
@Controller
public class Test {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String GoIndex() {
        return "page/login";
    }

    @RequestMapping("/user")
    @ResponseBody
    public String testUser() {
        List<User> allUsers = userService.findAllUsers();
        return  allUsers.get(0).toString();
    }
}
