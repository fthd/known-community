package cn.inc.sso.web.api;


import cn.inc.common.dto.UserDto;
import cn.inc.sso.web.api.fallback.SsoApiFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "inc-sso-biz", fallback = SsoApiFallback.class)
public interface ISsoApi {

    @RequestMapping(value="/login", method=RequestMethod.POST)
    String login(@RequestParam("username") String username, @RequestParam("password") String password);

    @RequestMapping(value="/checkUsername", method = RequestMethod.POST)
    boolean checkUsername(String username);

    @RequestMapping(value="/register", method=RequestMethod.POST)
    int register(UserDto userDto);

}
