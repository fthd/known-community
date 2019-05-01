package cn.inc.manager.controller;



import cn.inc.manager.mapper.UserMapper;
import cn.inc.manager.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/list")
    public List<User> list() {
        return null;
    }


    @GetMapping("/")
    public String index(String username){
        System.out.println(username);
        System.out.println(userMapper.findByUsername(username));
        return "aaaaaaaaaaaaa";
    }

}