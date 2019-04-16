package com.inc.web.controller;

import com.inc.web.api.ISsoApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SsoWebApi {

    @Autowired
    private ISsoApi iSsoApi;

    @RequestMapping(value="/login", method = RequestMethod.POST)
    public String login() {
        return iSsoApi.login();
    }

}
