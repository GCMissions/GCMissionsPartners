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
        // 判断用户是否有权限范围该接口
        if (!hasPermission(requestUrl)) {
            throw new AuthorizationException("无权限访问该资源");
        }
        // 初始化参数
        String token = AuthorityContext.getCurrentToken();
        if (token != null) {
            LOGGER.debug("设置当前用户缓存");
            AuthorityContext.setCurrentUser();
        }
        // 如果返回为页面，则为页面添加参数
        if (String.class.equals(method.getReturnType()) || ModelAndView.class.equals(method.getReturnType())) {
            LOGGER.debug("设置页面参数");
            HttpServletRequest request = WebUtil.getThreadRequest();
            AuthorityContext ac = new AuthorityContext();
            request.setAttribute("auth", ac);
            request.setAttribute("userInfo", ShiroUtil.getUserByToken(request));
            //request.setAttribute("region", ShiroUtil.getRegionByRequest(request));
            request.setAttribute("ftpPath", AuthorityContext.getFtpPath());
            request.setAttribute("uin", AuthorityContext.getQqPath());
            if (AppConfigUtil.isDevEnv()) {
                LOGGER.debug("开发环境，静态资源为本机");
                request.setAttribute("staticPath", request.getContextPath());
            } else {
                LOGGER.debug("非开发环境，静态资源为静态资源服务器");
                request.setAttribute("staticPath", AuthorityContext.getStaticPath());
            }
        }
        // 执行方法
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
