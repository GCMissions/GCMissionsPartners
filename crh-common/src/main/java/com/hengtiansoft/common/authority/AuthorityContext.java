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
 * Class Name: AuthorityContext Description: Permission container
 * 
 * @author jialiangli
 *
 */
public class AuthorityContext {

    private static final Logger                LOGGER    = LoggerFactory.getLogger(AuthorityContext.class);

    private final static ThreadLocal<UserInfo> UESR_INFO = new ThreadLocal<>();

    /**
     * Description: Gets the basic information of the current user.
     * 
     * @return
     */
    public static UserInfo getCurrentUser() {
        return UESR_INFO.get();
    }

    /**
     * Description: set the basic information of the current user.
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
     * Description:Gets the token of the current user.
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
     * Description:Login to verify the user name, login and landing will create a new session.
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
     * Description: Set the token to the cookie
     *
     * @param response
     * @param cookieDomain
     */
    public static void setTokenCookie(HttpServletResponse response, String cookieDomain) {
        CookieUtils.addCookie(response, "token", AuthorityContext.getCurrentToken(), -1, WebUtil.getFullUrlBasedOn("/"), cookieDomain, null);
    }

    /**
     * Description: Verify that the current user has this permission.
     *
     * @param permission
     * @return
     */
    public static boolean hasPermission(String permission) {
        Subject subject = SecurityUtils.getSubject();
        return subject == null ? false : subject.isPermitted(permission);
    }

    /**
     * Description: Verify that the current user has all of the following permissions. The
     *
     * @param permissions
     * @return
     */
    public static boolean hasAllPermissions(String... permissions) {
        Subject subject = SecurityUtils.getSubject();
        return subject == null ? false : subject.isPermittedAll(permissions);
    }

    /**
     * Description: Verify that the current user has any of the following permissions
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
     * Check if there is permission, if nothing else throws an exception.
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
     * Description: Verify that the current user belongs to all of the following roles. Please judge by permission rather than role, such as hasPermission.
     *
     * @param roles
     * @return
     */
    @Deprecated
    public static boolean hasAllRoles(Collection<String> roles) {
        return SecurityUtils.getSubject().hasAllRoles(roles);
    }

    /**
     * Description: Verify that the current user belongs to any of the following roles. Please judge by permission rather than role, such as hasPermission.
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
     * Description: Clear the authorization information cache for the specified user.
     *
     * @param userId
     */
    public static void clearAuthzCache(String token) {
        AuthorityService userService = ApplicationContextUtil.getBean(AuthorityService.class);
        userService.clearAuthzCache(token);
    }

    /**
     * Description: Clears the authentication information cache for the specified user.
     *
     * @param userId
     */
    public static void clearAuthcCache(String token) {
        AuthorityService userService = ApplicationContextUtil.getBean(AuthorityService.class);
        userService.clearAuthcCache(token);
    }

    /**
     * Description: Get static resource address
     *
     * @param userId
     */
    public static String getStaticPath() {
        AuthorityService userService = ApplicationContextUtil.getBean(AuthorityService.class);
        return userService.getStaticPath();
    }

    /**
     * Description: Get FTPaddress
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
     * Description: Clear the user's personal information cache.
     *
     * @param userId
     */
    public static void clearUserInfoCache(String token) {
        AuthorityService userService = ApplicationContextUtil.getBean(AuthorityService.class);
        userService.clearUserInfoCache(token);
    }

    /**
     * Description: logout
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
     * Description:Get URL permissions
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
