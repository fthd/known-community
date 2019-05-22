package com.known.common.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 路径相关的配置
 * @author tangjunxiang
 * @version 1.0
 * @date 2019-05-22 21:18
 */
@Data
@NoArgsConstructor
@Component
public class UrlConfig {


    @Value("${Error_404}")
    private String Error_404;

    @Value("${User_Icon_Url}")
    private String User_Icon_Url;

    @Value("${User_Bg_Url}")
    private String User_Bg_Url;

    @Value("${RealPath}")
    private String RealPath;

    @Value("${Absolute_Path}")
    private String Absolute_Path;

    @Value("${LoginAbsolutePath}")
    private String LoginAbsolutePath;

}
