package com.known.common.utils;

import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;

/**
 * @author tangjunxiang
 * @version 1.0
 * @date 2019-06-10 23:38
 */
public class AbsolutePathUtil {

    private   static String absolutePath() {
        try {
            String path = ResourceUtils.getURL("classpath:").getPath();
            return path;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getAbsolutePath() {
        return absolutePath();
    }

    public static String getAbsoluteStaticPath() {
        return absolutePath()+"static/";
    }

}
