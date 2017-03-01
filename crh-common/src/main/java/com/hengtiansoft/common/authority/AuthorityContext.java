/*
 * Project Name: standard-code-base-trunk
 * File Name: SecurityContext.java
 * Class Name: SecurityContext
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

package com.hengtiansoft.common.authority;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hengtiansoft.common.authority.domain.FunctionInfo;
import com.hengtiansoft.common.authority.domain.UserInfo;
import com.hengtiansoft.common.authority.service.AuthorityService;
import com.hengtiansoft.common.util.ApplicationContextUtil;
import com.hengtiansoft.common.util.CookieUtils;
import com.hengtiansoft.common.util.web.WebUtil;

/**
 * Class Name: AuthorityContext Description: 权限容器
 * 
 * @author jialiangli
 *
 */
public class AuthorityContext {

    private static final Logger                LOGGER    = LoggerFactory.getLogger(AuthorityContext.class);

    private final static ThreadLocal<UserInfo> UESR_INFO = new ThreadLocal<>();

    /**
     * Description: 获取当前用户的基本信息。
     * 
     * @return
     */
    public static UserInfo getCurrentUser() {
        return UESR_INFO.get();
    }

    /**
     * Description: 设置当前用户的基本信息。
     * 
     * @return
     */
    public static void setCurrentUser() {
        Subject currentUser = SecurityUtils.getSubject();
        UserInfo userInfo = (UserInfo) currentUser.getPrincipal();
        if (userInfo != null) {
            UESR_INFO.set(userInfo);
        }
    }

    /**
     * Description: 获得当前用户Token
     *
     * @return
     */
    public static String getCurrentToken() {
        Subject currentUser = SecurityUtils.getSubject();
        UserInfo userInfo = (UserInfo) currentUser.getPrincipal();
        if (userInfo != null) {
            return userInfo.getToken();
        }
        return null;
    }

    /**
     * Description: 登录验证该用户名, 登陆登陆成功后会创建新的会话。
     * 
     * @param userId
     * @param password
     * 
     */
    public static String login(String userName, String password) throws IncorrectCredentialsException, LockedAccountException {
        long start = System.currentTimeMillis();
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.login(token);
        UserInfo userInfo = (UserInfo) currentUser.getPrincipal();
        String tokenStr = null;
        if (userInfo != null) {
            tokenStr = userInfo.getToken();
            UESR_INFO.set(userInfo);
        }
        LOGGER.debug("User {} login successfully, token {}", userName, tokenStr);
        long end = System.currentTimeMillis();
        LOGGER.debug("login() completed for user {}, total time spent: {}ms", userName, end - start);
        return tokenStr;
    }

    /**
     * Description: 把token设置到cookie中
     *
     * @param response
     * @param cookieDomain
     */
    public static void setTokenCookie(HttpServletResponse response, String cookieDomain) {
        CookieUtils.addCookie(response, "token", AuthorityContext.getCurrentToken(), -1, WebUtil.getFullUrlBasedOn("/"), cookieDomain, null);
    }

    /**
     * Description: 验证当前用户是否拥有该权限。
     *
     * @param permission
     * @return
     */
    public static boolean hasPermission(String permission) {
        Subject subject = SecurityUtils.getSubject();
        return subject == null ? false : subject.isPermitted(permission);
    }

    /**
     * Description: 验证当前用户是否拥有所有以下权限。
     *
     * @param permissions
     * @return
     */
    public static boolean hasAllPermissions(String... permissions) {
        Subject subject = SecurityUtils.getSubject();
        return subject == null ? false : subject.isPermittedAll(permissions);
    }

    /**
     * Description: 验证当前用户是否拥有以下任意一个权限
     *
     * @param permissions
     * @return
     */
    public static boolean hasAnyPermission(String[] permissions) {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null && permissions != null) {
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                if (permission != null && subject.isPermitted(permission.trim())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 检查是否有权限，若无则抛出异常。
     *
     * @see org.apache.shiro.subject.Subject#checkPermission(String permission)
     * @param permission
     * @throws AuthorizationException
     */
    public static void checkPermission(String permission) throws AuthorizationException {
        Subject subject = SecurityUtils.getSubject();
        if (subject == null) {
            throw new AuthorizationException("No permission as there is no subject bound.");
        }
        subject.checkPermission(permission);
    }

    /**
     * Description: 验证当前用户是否属于以下所有角色。请通过权限而不是角色做判断，比如hasPermission。
     *
     * @param roles
     * @return
     */
    @Deprecated
    public static boolean hasAllRoles(Collection<String> roles) {
        return SecurityUtils.getSubject().hasAllRoles(roles);
    }

    /**
     * Description: 验证当前用户是否属于以下任意一个角色。请通过权限而不是角色做判断，比如hasPermission。
     *
     * @param roleNames
     * @return
     */
    @Deprecated
    public static boolean hasAnyRoles(Collection<String> roleNames) {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null && roleNames != null) {
            for (String role : roleNames) {
                if (role != null && subject.hasRole(role)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Description: 清除指定用户的授权信息缓存。
     *
     * @param userId
     */
    public static void clearAuthzCache(String token) {
        AuthorityService userService = ApplicationContextUtil.getBean(AuthorityService.class);
        userService.clearAuthzCache(token);
    }

    /**
     * Description: 清除指定用户的认证信息缓存。
     *
     * @param userId
     */
    public static void clearAuthcCache(String token) {
        AuthorityService userService = ApplicationContextUtil.getBean(AuthorityService.class);
        userService.clearAuthcCache(token);
    }

    /**
     * Description: 获得静态资源地址
     *
     * @param userId
     */
    public static String getStaticPath() {
        AuthorityService userService = ApplicationContextUtil.getBean(AuthorityService.class);
        return userService.getStaticPath();
    }

    /**
     * Description: 获得FTP地址
     *
     * @return
     */
    public static String getFtpPath() {
        AuthorityService userService = ApplicationContextUtil.getBean(AuthorityService.class);
        return userService.getFtpPath();
    }

    public static Subject getSubject() {
        try {
            return SecurityUtils.getSubject();
        } catch (Exception e) {
            LOGGER.warn("Failed to get Subject, maybe user is not login or session is lost:", e);
            return null;
        }
    }

    /**
     * Description: 清除指定用户的个人信息缓存。
     *
     * @param userId
     */
    public static void clearUserInfoCache(String token) {
        AuthorityService userService = ApplicationContextUtil.getBean(AuthorityService.class);
        userService.clearUserInfoCache(token);
    }

    /**
     * Description: 注销登陆
     *
     * @param token
     */
    public static void logout(HttpServletResponse response, String cookieDomain) {
        String token = AuthorityContext.getCurrentToken();
        AuthorityService userService = ApplicationContextUtil.getBean(AuthorityService.class);
        userService.clearToken(AuthorityContext.getCurrentUser().getUserId());
        userService.clearUserInfoCache(token);
        userService.clearAuthcCache(token);
        userService.clearAuthzCache(token);
        CookieUtils.removeCookie(response, "token", WebUtil.getFullUrlBasedOn("/"), cookieDomain);
        CookieUtils.removeCookie(response, "regionId");
    }

    /**
     * Description: 获得URL权限
     *
     * @param url
     * @return
     */
    public static String getPermission(String url) {
        AuthorityService userService = ApplicationContextUtil.getBean(AuthorityService.class);
        Map<String, FunctionInfo> functions = userService.getFunctions();
        if (functions != null) {
            FunctionInfo function = functions.get(url);
            if (function != null) {
                return function.getPermission();
            }
        }
        return null;
    }

    public static String getQqPath() {
        AuthorityService userService = ApplicationContextUtil.getBean(AuthorityService.class);
        return userService.getQqPath();
    }
}
