package cn.inc.sso.web;

import cn.inc.common.dto.UserDto;
import cn.inc.common.utils.MD5Util;
import cn.inc.common.view.ResultMap;
import cn.inc.sso.web.api.ISsoApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/sso")
public class SsoWeb {

    @Autowired
    private ISsoApi iSsoApi;

    @RequestMapping(value="/login", method=RequestMethod.POST)
    public String login(String username, String password, Model model) {
        String md = MD5Util.getMD5String(password);
        String name = iSsoApi.login(username, md);
        if(name != null && name.equals(username)) {
            model.addAttribute("username", name);
            return "index";
        }
        return "error";
    }

    /**
     * 检查用户是否存在
     * @param username
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/checkUsername", method=RequestMethod.POST)
    public boolean checkUsername(String username) {
        return iSsoApi.checkUsername(username);
    }

    /**
     * @RequestBody 使用需要加载MappingJackson2HttpMessageConverter
     *  但是SpringBoot的官方文档提到 这个是默认已经加载的了
     *  而且json字符串和javabean也没有书写的错误
     *  因此考虑到应该是请求Content-Type的问题
     *  因为使用浏览器输入url的方式没有办法定义Content-Type
     *  因此spring无法发现request body
     *  去掉 @RequestBody
     * @param userDto
     * @return
     */
    @RequestMapping(value="/register", method=RequestMethod.POST)
    public ResultMap register(UserDto userDto) {

        ResultMap resultMap = new ResultMap();
        //使用MD5加密
        String md = MD5Util.getMD5String(userDto.getPassword());
        userDto.setPassword(md);
        int flag = iSsoApi.register(userDto);
        resultMap.setCode(flag);
        if(flag ==1) {
            resultMap.setMsg("注册成功！");
            return resultMap;
        }
        resultMap.setMsg("注册失败！");
        return resultMap;
    }


}
