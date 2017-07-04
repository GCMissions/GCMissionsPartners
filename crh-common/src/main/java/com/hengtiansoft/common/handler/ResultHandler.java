/*
 * Project Name: zc-collect-common
 * File Name: ResultHandler.java
 * Class Name: ResultHandler
 *
 * Copyright 2014 Hengtian Software Inc
 *
 * Licensed under the Hengtiansoft
 *
 * http://www.hengtiansoft.com
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hengtiansoft.common.handler;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.AuthorizationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.authority.util.ShiroUtil;
import com.hengtiansoft.common.util.AppConfigUtil;
import com.hengtiansoft.common.util.web.WebUtil;

/**
 * Class Name: ResultHandler Description: TODO
 * 
 * @author jialiangli
 *
 */
@Aspect
@Component
public class ResultHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResultHandler.class);

    @Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object handlerRequestMapping(final ProceedingJoinPoint joinPoint) throws Throwable {
        final Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String requestUrl = getMappingUrl(method);
        // Determine whether the user has permission to range the interface
        if (!hasPermission(requestUrl)) {
            throw new AuthorizationException("No access to the resource");
        }
        // Initialize parameters
        String token = AuthorityContext.getCurrentToken();
        if (token != null) {
            LOGGER.debug("Set the current user cache");
            AuthorityContext.setCurrentUser();
        }
        // If you return to a page, add a parameter to the page
        if (String.class.equals(method.getReturnType()) || ModelAndView.class.equals(method.getReturnType())) {
            LOGGER.debug("Set the page parameters");
            HttpServletRequest request = WebUtil.getThreadRequest();
            AuthorityContext ac = new AuthorityContext();
            request.setAttribute("auth", ac);
            request.setAttribute("userInfo", ShiroUtil.getUserByToken(request));
            //request.setAttribute("region", ShiroUtil.getRegionByRequest(request));
            request.setAttribute("ftpPath", AuthorityContext.getFtpPath());
            request.setAttribute("uin", AuthorityContext.getQqPath());
            if (AppConfigUtil.isDevEnv()) {
                LOGGER.debug("Development environment, static resources for the machine");
                request.setAttribute("staticPath", request.getContextPath());
            } else {
                LOGGER.debug("Non-development environment, static resources for the static resource server");
                request.setAttribute("staticPath", AuthorityContext.getStaticPath());
            }
        }
        // Implementation method
        return joinPoint.proceed();
    }

    private String getMappingUrl(Method method) {
        String url = "";
        RequestMapping clazzMapping = method.getDeclaringClass().getAnnotation(RequestMapping.class);
        RequestMapping methodMapping = method.getAnnotation(RequestMapping.class);
        if (clazzMapping != null) {
            url += clazzMapping.value()[0];
        }
        if (methodMapping != null) {
            url += methodMapping.value()[0];
        }
        return url;
    }

    private boolean hasPermission(String url) {
        boolean result = true;
        String permission = AuthorityContext.getPermission(url);
        if (permission != null) {
            result = AuthorityContext.hasPermission(permission);
        }
        return result;
    }
}
