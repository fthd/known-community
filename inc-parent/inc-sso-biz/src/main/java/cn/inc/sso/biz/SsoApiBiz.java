package cn.inc.sso.biz;


import cn.inc.common.dto.UserDto;
import cn.inc.manager.mapper.UserMapper;
import cn.inc.manager.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
class SsoApiBiz {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value="/login", method= RequestMethod.POST)
    public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
        System.out.println(username +";"+password);
        User user = userMapper.findByUAP(username, password);
        return user != null ? user.getUsername() : "";
    }

    @RequestMapping(value="/checkUsername", method = RequestMethod.POST)
    public boolean checkUsername(@RequestBody String username) {
        System.out.println(username + ":" +userMapper.findByUsername(username));
        return userMapper.findByUsername(username) != null ? true : false;
    }

    @RequestMapping(value="/register", method=RequestMethod.POST)
    public int register(@RequestBody UserDto userDto) {
        return userMapper.insert(new User(userDto));
    }

}
