package com.inc.web.api.fallback;

import com.inc.web.api.ISsoApi;
import org.springframework.stereotype.Component;


@Component
public class SsoApiFallback implements ISsoApi {

    @Override
    public String login() {
        return "error";
    }
}
