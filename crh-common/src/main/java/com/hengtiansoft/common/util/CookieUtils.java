package com.hengtiansoft.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * @Title: CookieUtils.java
 * 
 * @Package com.hengtiansoft.common.util
 * 
 * @Description: cookie tool class
 * 
 * @author taochen
 * 
 * @date
 * 
 * @version V1.0
 * 
 */
public class CookieUtils {

    private static final Logger log = LoggerFactory.getLogger(CookieUtils.class);

    /**
     * add cookie
     * 
     * @param response
     *            HttpServletResponse
     * @param name
     *            cookie name
     * @param value
     *            cookie value
     * @param maxAge
     *            Valid (in seconds)
     * @param path
     * 
     * @param domain
     * 
     * @param secure
     *            whether encryption is enabled
     */
    public static void addCookie(HttpServletResponse response, String name, String value, Integer maxAge, String path,
            String domain, Boolean secure) {
        Assert.notNull(response);
        Assert.hasText(name);
        try {
            name = URLEncoder.encode(name, "UTF-8");
            value = URLEncoder.encode(value, "UTF-8");
            Cookie cookie = new Cookie(name, value);
            if (maxAge != null) {
                cookie.setMaxAge(maxAge);
            }
            if (StringUtils.isNotEmpty(path)) {
                cookie.setPath(path);
            }
            if (StringUtils.isNotEmpty(domain)) {
                cookie.setDomain(domain);
            }
            if (secure != null) {
                cookie.setSecure(secure);
            }
            response.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            log.error("msg", e);
        }
    }

    /**
     * add cookie
     * 
     * @param response
     *            HttpServletResponse
     * @param name
     *            cookie name
     * @param value
     *            cookie value
     * @param maxAge
     *            Valid (in seconds)
     */
    public static void addCookie(HttpServletResponse response, String name, String value, Integer maxAge) {
        addCookie(response, name, value, maxAge, "/", "", null);
    }

    /**
     * add cookie
     * 
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @param name
     *            cookie name
     * @param value
     *            cookie value
     */
    public static void addCookie(HttpServletResponse response, String name, String value) {
        addCookie(response, name, value, null, "/", "", null);
    }

    /**
     * get cookie
     * 
     * @param request
     *            HttpServletRequest
     * @param name
     *            cookie name
     * @return returns null if it does not exist
     */
    public static String getCookie(HttpServletRequest request, String name) {
        Assert.notNull(request);
        Assert.hasText(name);
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            try {
                name = URLEncoder.encode(name, "UTF-8");
                for (Cookie cookie : cookies) {
                    if (name.equals(cookie.getName())) {
                        return URLDecoder.decode(cookie.getValue(), "UTF-8");
                    }
                }
            } catch (UnsupportedEncodingException e) {
                log.error("msg", e);
            }
        }
        return null;
    }

    /**
     * removed cookie
     * 
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @param name
     *            cookie name
     * @param path
     * 
     * @param domain
     * 
     */
    public static void removeCookie(HttpServletResponse response, String name, String path, String domain) {
        Assert.notNull(response);
        Assert.hasText(name);
        try {
            name = URLEncoder.encode(name, "UTF-8");
            Cookie cookie = new Cookie(name, null);
            cookie.setMaxAge(0);
            if (StringUtils.isNotEmpty(path)) {
                cookie.setPath(path);
            }
            if (StringUtils.isNotEmpty(domain)) {
                cookie.setDomain(domain);
            }
            response.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            log.error("msg", e);
        }
    }

    /**
     * removed cookie
     * 
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @param name
     *            cookie name
     */
    public static void removeCookie(HttpServletResponse response, String name) {
        removeCookie(response, name, "/", "");
    }
}
