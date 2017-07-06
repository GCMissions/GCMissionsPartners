package com.hengtiansoft.common.authority.shiro;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hengtiansoft.common.authority.util.ShiroUtil;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.util.web.WebUtil;

/**
 * Class Name: StatelessAuthcFilter Description: token verification Filter
 * 
 * @author taochen
 *
 */
public class StatelessAuthcFilter extends AccessControlFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatelessAuthcFilter.class);

    public static final String DEFAULT_UNAUTHORIZED_URL = "/web/login/login";

    private String unauthorizedUrl = DEFAULT_UNAUTHORIZED_URL;

    public String getUnauthorizedUrl() {
        return unauthorizedUrl;
    }

    public void setUnauthorizedUrl(String unauthorizedUrl) {
        this.unauthorizedUrl = unauthorizedUrl;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        String token = ShiroUtil.getToken(request);
        if (token != null) {
            StatelessToken statelessToken = new StatelessToken(token);
            try {
                getSubject(request, response).login(statelessToken);
                return true;
            } catch (Exception e) {
                LOGGER.info("Token:{}Invalid, validation failed", token);
            }
        }
        if (isAjaxRequest((HttpServletRequest) request)) {
            onLoginFail(response);
        } else {
            WebUtils.issueRedirect(request, response, getUnauthorizedUrl());
        }
        return false;
    }

    // The 401 status code is returned by default when the login fails
    private void onLoginFail(ServletResponse response) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ObjectMapper mapper = WebUtil.getObjectMapper();
        String json = mapper.writeValueAsString(ResultDtoFactory.toUnauthorized("Please login again!"));
        LOGGER.info("Permission validation failed");
        httpResponse.getOutputStream().write(json.getBytes("UTF-8"));
    }

    private boolean isAjaxRequest(HttpServletRequest request) {
        String requestedWith = request.getHeader("X-Requested-With");
        return requestedWith != null ? "XMLHttpRequest".equals(requestedWith) : false;
    }
}
