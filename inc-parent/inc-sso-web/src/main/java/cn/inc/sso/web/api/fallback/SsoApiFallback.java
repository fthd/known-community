package cn.inc.sso.web.api.fallback;


import cn.inc.common.dto.UserDto;
import cn.inc.sso.web.api.ISsoApi;
import org.springframework.stereotype.Component;


@Component
public class SsoApiFallback implements ISsoApi {

    @Override
    public String login(String username, String password) {
        System.out.println("=====================================");
        System.out.println("服务连接失败");
        System.out.println("=====================================");
        return "login error";
    }

    @Override
    public boolean checkUsername(String username) {
        System.out.println("=====================================");
        System.out.println("服务连接失败");
        System.out.println("=====================================");
        return false;
    }

    @Override
    public int register(UserDto userDto) {
        System.out.println("=====================================");
        System.out.println("服务连接失败");
        System.out.println("=====================================");
        return 503;} //连接超时
}
