package com.known.common.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 用户相关配置
 * @author tangjunxiang
 * @version 1.0
 * @date 2019-05-22 23:01
 */
@Data
@NoArgsConstructor
@Component
public class UserConfig {

    @Value("${Session_User_Key}")
    private String Session_User_Key;

    @Value("${Cookie_User_Info}")
    private String Cookie_User_Info;

}
