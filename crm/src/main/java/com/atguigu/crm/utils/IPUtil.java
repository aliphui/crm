package com.atguigu.crm.utils;

import jdk.dynalink.beans.StaticClass;

import javax.servlet.http.HttpServletRequest;

/**
 * @create 2021-12-09 13:47
 */
public class IPUtil {
    /**
     * 获取请求IP地址
     * @param request
     * @return
     * @throws Exception
     */
    public static String getIp(HttpServletRequest request) throws Exception {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip != null) {
            if (!ip.isEmpty() && !"unKnown".equalsIgnoreCase(ip)) {
                int index = ip.indexOf(",");
                if (index != -1) {
                    return ip.substring(0, index);
                } else {
                    return ip;
                }
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (ip != null) {
            if (!ip.isEmpty() && !"unKnown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }
        return request.getRemoteAddr();
    }

}
