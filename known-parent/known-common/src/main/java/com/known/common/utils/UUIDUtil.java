package com.known.common.utils;

import com.known.common.model.Ask;

import java.util.UUID;

/**
 * uuid生成工具类
 *
 * @author tangjunxiang
 * @version 1.0
 * @date 2019-05-22 18:02
 */
public class UUIDUtil {

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static void main(String[] args) {
        Ask ask = new Ask();

        for(int i =0; i< 10; i++) {
            ask.setAskId(UUIDUtil.getUUID());
            System.out.println(ask.getAskId());
        }
    }
}
