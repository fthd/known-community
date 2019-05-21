package com.known.config;

/**
 * 加载自定义配置文件
 *
 * @author tangjunxiang
 * @version 1.0
 * @date 2019-05-21 10:03
 */

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = {"classpath:application-config.properties",
        "classpath:application-db.properties"})
public class LoaderPropConfig {

}
