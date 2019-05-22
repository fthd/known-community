package com.known.common.utils;

import org.springframework.beans.factory.annotation.Value;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 字符串工具类
 *
 * @author tangjunxiang
 * @version 1.0
 * @date 2019-05-20 23:22
 */
public class StringUtil {

    //判断字符串是否为空
    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str)) {
            return true;
        } else if ("".equals(str.trim())) {
            return true;
        }
        return false;
    }

    //判断是否是数字
    public static boolean isNumber(String str) {
        String checkNumber = "^[0-9]+$";
        if (str == null) {
            return false;
        }
        if (!str.matches(checkNumber)) {
            return false;
        }
        return true;
    }

    //判断是否为邮箱
    public static boolean isEmail(String str) {
        String checkEmail = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$";
        if (!isEmpty(str)) {
            return str.matches(checkEmail);
        } else {
            return false;
        }
    }

    //判断是否是用户名
    public static boolean isUserName(String str) {
        String checkUserName = "^[\\w\\u4e00-\\u9fa5]+$";
        if (!isEmpty(str)) {
            return str.matches(checkUserName);
        } else {
            return false;
        }
    }

    //判断是否是密码
    public static boolean isPassword(String str) {
        String checkPassword = "^[0-9a-zA-Z]+$";
        if (!isEmpty(str)) {
            return str.matches(checkPassword);
        } else {
            return false;
        }
    }

    //md5加密
    public static String encode(String str) {
        MessageDigest md = null;
        String dstr = null;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            dstr = new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return dstr;
    }

    //生成邮件激活码
    public static String getActivationCode(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString().toUpperCase();
    }

    /*
     * 给内容添加链接
     */
    public static String addLink(String str) {
        String regex = "((http:|https:)//|www.)[^[A-Za-z0-9\\._\\?%&+\\-=/#]]*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            StringBuffer replace = new StringBuffer();
            if (matcher.group().contains("http")) {
                replace.append("<a href=").append(matcher.group());
            } else {
                replace.append("<a href=http://").append(matcher.group());
            }
            replace.append(" target=\"_blank\">" + matcher.group() + "</a>");
            matcher.appendReplacement(result, replace.toString());
        }
        matcher.appendTail(result);
        return result.toString();
    }

    //得到一个随机头像
    public static String getRandomUserIcon() {
        int x = 1 + (int) (Math.random() * 10); //1-10
        return x + ".jpg";
    }

    //得到一个随机背景
    public static String getRandomUserBg() {
        int x = 1 + (int) (Math.random() * 10);
        return "bg" + x + ".jpg";
    }

    public static String escapeHtml(String html) {
        if (!isEmpty(html)) {
            html = html.replaceAll("\n", "<br>");
            html = html.replaceAll(" ", "&nbsp;");
        }
        return html;
    }

    public static String cleanHtmlTag(String str) {
        if (isEmpty(str)) {
            return str;
        } else {
            return str.replaceAll("<[.[^<]]*>", "").replaceAll("[\\n|\\r]", "").replace("&nbsp;", "");
        }
    }

    public static String replaceLast(String text, String regex, String replacement) {
        return text.replaceFirst("(?s)(.*)" + regex, "$1" + replacement);
    }
}
