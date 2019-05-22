package com.known.common.utils;

import java.util.UUID;

/**
 * uuid生成工具类
 *
 * @author tangjunxiang
 * @version 1.0
 * @date 2019-05-22 18:02
 */
public class UUIDUtil {

    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
