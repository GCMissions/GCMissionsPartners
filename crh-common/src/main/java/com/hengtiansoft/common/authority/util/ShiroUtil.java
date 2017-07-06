package com.hengtiansoft.common.authority.util;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.hengtiansoft.common.authority.domain.UserInfo;
import com.hengtiansoft.common.authority.service.AuthorityService;
import com.hengtiansoft.common.util.ApplicationContextUtil;
import com.hengtiansoft.common.util.BizUtil;

public class ShiroUtil {
    /**
     * Description: Get token from cookie or header
     *
     * @param request
     * @return
     */
    public static String getToken(ServletRequest request) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = null;
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies == null) {
            token = httpServletRequest.getHeader("token");
        } else {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    token = cookie.getValue();
                }
            }
        }
        return token;
    }

    public static UserInfo getUserByToken(HttpServletRequest request) {
        String token = getToken(request);
        if (BizUtil.isEmpty(token)) {
            return null;
        } else {
            AuthorityService authorityService = ApplicationContextUtil.getBean(AuthorityService.class);
            return authorityService.findUserInfoByToken(token);
        }

    }
}
