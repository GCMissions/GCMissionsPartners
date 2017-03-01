/*
 * Project Name: zc-collector-supplier
 * File Name: AuthorityService.java
 * Class Name: AuthorityService
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
package com.hengtiansoft.common.authority.service;

import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.common.authority.domain.FunctionInfo;
import com.hengtiansoft.common.authority.domain.RoleInfo;
import com.hengtiansoft.common.authority.domain.UserInfo;
import com.hengtiansoft.common.xmemcached.constant.CacheType;

/**
 * Class Name: AuthorityService Description: 权限验证Service
 * 
 * @author jialiangli
 *
 */
public interface AuthorityService {

    /**
     * Description: 根据Token获得用户信息
     *
     * @param token
     * @return
     */
    @Cacheable(value = CacheType.DEFAULT, key = "#token + '_UserInfo'")
    public UserInfo findUserInfoByToken(String token);

    /**
     * Description: 验证登陆信息
     *
     * @param loginId
     * @param password
     * @return
     */
    public UserInfo authcUser(String loginId, String password);

    /**
     * Description: 获得用户登录信息
     *
     * @param userInfo
     * @return
     */
    public String getToken(UserInfo userInfo);

    /**
     * Description: 设置登录信息
     *
     * @param userInfo
     */
    public void setLoginInfo(UserInfo userInfo, String token);

    /**
     * Description: 清除用户token
     *
     * @param userId
     */
    @Transactional
    public void clearToken(Long userId);

    /**
     * Description: 根据Token获得用户角色信息
     *
     * @param token
     * @return
     */
    public List<RoleInfo> findRoleInfosByUserId(Long userId);

    /**
     * Description: 根据角色获得相关权限
     *
     * @param roleIds
     * @return
     */
    public List<FunctionInfo> findFunctionsByRoleIds(Iterable<Long> roleIds);

    /**
     * Description: 获得权限
     *
     * @return
     */
    //@Cacheable(value = CacheType.DEFAULT, key = "T(com.hengtiansoft.common.util.ApplicationContextUtil).getApplicationName()+'_Functions'")
    @Cacheable(value = CacheType.DEFAULT, key = "T(com.hengtiansoft.common.util.AppConfigUtil).getAppName()+'_Functions'")
    Map<String, FunctionInfo> getFunctions();

    /**
     * Description: 清除指定用户的个人信息缓存。
     *
     * @param userId
     */
    @CacheEvict(value = CacheType.DEFAULT, key = "#token+ '_UserInfo'")
    void clearUserInfoCache(String token);

    /**
     * Description: 清除指定用户的认证信息缓存。
     *
     * @param userId
     */
    @CacheEvict(value = CacheType.DEFAULT, key = "#token+ '_SimpleAuthenticationInfo'")
    void clearAuthcCache(String token);

    /**
     * Description: 清除指定用户的授权信息缓存。
     *
     * @param userId
     */
    @CacheEvict(value = CacheType.DEFAULT, key = "#token+ '_SimpleAuthorizationInfo'")
    void clearAuthzCache(String token);

    /**
     * Description: 获得静态资源地址
     *
     * @return
     */
    @Cacheable(value = CacheType.DEFAULT, key = "'_StaticPath'")
    String getStaticPath();

    /**
     * Description: 获得图片资源地址
     *
     * @return
     */
    @Cacheable(value = CacheType.DEFAULT, key = "'_FtpPath'")
    String getFtpPath();
    
    
    /**
     * Description: 获得Qq地址
     *
     * @return
     */
    @Cacheable(value = CacheType.DEFAULT, key = "'_QqPath'")
    String getQqPath();


}
