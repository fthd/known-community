package com.inc.web.api;

import com.inc.web.api.fallback.SsoApiFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "inc-sso-biz", fallback = SsoApiFallback.class)
public interface ISsoApi {

    @RequestMapping(value="/login", method = RequestMethod.POST)
    String login() ;
}
