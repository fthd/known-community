package com.known.common.utils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;


/**
 *  IP地址转化
 *
 * @author tangjunxiang
 * @version 1.0
 * @date 2019-05-21 17:03
 */
public class IpUtil {
	
	/**
	 * 
	 * @Description 获取客户端真实ip
	 * @param request
	 * @return
	 *
	 * @autho
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
        //String ip = request.getHeader("X-real-ip");

        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP"); 
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if(StringUtils.isNotBlank(ip)) {
            ip = ip.split(",")[0];
        }
		return ip;
	}

}