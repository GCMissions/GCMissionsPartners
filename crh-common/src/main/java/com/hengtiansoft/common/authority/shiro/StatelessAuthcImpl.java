/*
 * Project Name: zc-collector-supplier
 * File Name: StatelessAuthImpl.java
 * Class Name: StatelessAuthImpl
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
package com.hengtiansoft.common.authority.shiro;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.authority.domain.FunctionInfo;
import com.hengtiansoft.common.authority.domain.RoleInfo;
import com.hengtiansoft.common.authority.domain.UserInfo;
import com.hengtiansoft.common.authority.service.AuthorityService;
import com.hengtiansoft.common.xmemcached.constant.CacheType;

/**
 * Class Name: StatelessAuthImpl Description: 权限认证实现
 * 
 * @author jialiangli
 *
 */
public class StatelessAuthcImpl {

    @Autowired
    private AuthorityService authorityService;

    private final Random random = new Random(System.currentTimeMillis());

    @Cacheable(value = CacheType.DEFAULT, key = "#principals.getPrimaryPrincipal().token + '_SimpleAuthorizationInfo'")
    public SimpleAuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        UserInfo userInfo = (UserInfo) principals.getPrimaryPrincipal();
        // 获得该用户的全部角色
        List<RoleInfo> roleInfos = authorityService.findRoleInfosByUserId(userInfo.getUserId());
        if (roleInfos != null) {
            Set<String> roles = new HashSet<String>();
            Set<String> permissions = new HashSet<String>();
            Set<Long> roleIds = new HashSet<Long>();
            for (RoleInfo roleInfo : roleInfos) {
                roles.add(roleInfo.getRole());
                roleIds.add(roleInfo.getRoleId());
            }
            // 获得该用户的全部权限
            if (!roleIds.isEmpty()) {
                List<FunctionInfo> functionInfos = authorityService.findFunctionsByRoleIds(roleIds);
                if (functionInfos != null) {
                    for (FunctionInfo functionInfo : functionInfos) {
                        permissions.add(functionInfo.getPermission());
                    }
                }
            }
            authorizationInfo.addRoles(roles);
            authorizationInfo.addStringPermissions(permissions);
        }
        return authorizationInfo;
    }

    @Cacheable(value = CacheType.DEFAULT, condition = "#token instanceof T(com.hengtiansoft.common.authority.shiro.StatelessToken)", key = "#token.getPrincipal() + '_SimpleAuthenticationInfo'")
    public SimpleAuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
        String tokenStr = null;
        UserInfo userInfo = null;
        SimpleAuthenticationInfo authenticationInfo = null;
        if (token instanceof StatelessToken) {
            tokenStr = (String) token.getPrincipal();
            userInfo = authorityService.findUserInfoByToken(tokenStr);
            if (userInfo == null) {
                throw new ExpiredCredentialsException("Token[" + tokenStr + "]不正确或已失效");
            }
        } else if (token instanceof UsernamePasswordToken) {
            String loginId = ((UsernamePasswordToken) token).getUsername();
            String password = String.valueOf(((UsernamePasswordToken) token).getPassword());
            userInfo = authorityService.authcUser(loginId, password);
            if (userInfo != null) {
                // 清除该用户当前的Token缓存
                tokenStr = authorityService.getToken(userInfo);
                if (tokenStr != null) {
                    AuthorityContext.clearUserInfoCache(tokenStr);
                    AuthorityContext.clearAuthzCache(tokenStr);
                    AuthorityContext.clearAuthcCache(tokenStr);
                }
                // 设置新的token
                tokenStr = getRandomToken();
                authorityService.setLoginInfo(userInfo, tokenStr);
            } else {
                throw new IncorrectCredentialsException("用户[" + loginId + "]认证失败");
            }
        }
        if (userInfo != null) {
            userInfo.setToken(tokenStr);
            authenticationInfo = new SimpleAuthenticationInfo(userInfo, userInfo.getPassword(), userInfo.getLoginId());
        }
        return authenticationInfo;
    }

    /**
     * Description: 获得一个随机数作为Token
     *
     * @return
     */
    private String getRandomToken() {
        return Long.toString(System.currentTimeMillis(), 31) + Long.toString(System.nanoTime(), 19)
                + Long.toString(random.nextLong(), 23);
    }

}
