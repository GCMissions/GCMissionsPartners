package com.hengtiansoft.common.util.pay;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

public class CommonUtil {

    /**
     * Discription：Get the IP address
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * Description: Gets the parameter Map in HttpRequest
     *
     * @param request
     * @return
     */
    @SuppressWarnings({ "unchecked" })
    public static Map<String, String> getRequestMap(HttpServletRequest request) {
        Map<String, String> params = new HashMap<String, String>();
        Map<Object, Object> requestParams = request.getParameterMap();
        for (Entry<Object, Object> entry : requestParams.entrySet()) {
            String name = (String) entry.getKey();
            String[] values = (String[]) entry.getValue();

            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }

        return params;
    }
}
