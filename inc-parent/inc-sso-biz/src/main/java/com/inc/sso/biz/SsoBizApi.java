package com.inc.sso.biz;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SsoBizApi {
    @RequestMapping(value="/login", method = RequestMethod.POST)
    String login() {
        return "哈哈哈！";
    }
}
