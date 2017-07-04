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
 * Class Name: AuthorityService Description: Permission Validation Service
 * 
 * @author jialiangli
 *
 */
public interface AuthorityService {

    /**
     * Description: Obtain user information based on Token
     *
     * @param token
     * @return
     */
    @Cacheable(value = CacheType.DEFAULT, key = "#token + '_UserInfo'")
    public UserInfo findUserInfoByToken(String token);

    /**
     * Description: Verify login information
     *
     * @param loginId
     * @param password
     * @return
     */
    public UserInfo authcUser(String loginId, String password);

    /**
     * Description: Get logined user's information
     *
     * @param userInfo
     * @return
     */
    public String getToken(UserInfo userInfo);

    /**
     * Description: Set the login information
     *
     * @param userInfo
     */
    public void setLoginInfo(UserInfo userInfo, String token);

    /**
     * Description: clear user's token
     *
     * @param userId
     */
    @Transactional
    public void clearToken(Long userId);

    /**
     * Description: Obtain user role information based on Token
     *
     * @param token
     * @return
     */
    public List<RoleInfo> findRoleInfosByUserId(Long userId);

    /**
     * Description: According to the role of access to relevant authority
     *
     * @param roleIds
     * @return
     */
    public List<FunctionInfo> findFunctionsByRoleIds(Iterable<Long> roleIds);

    /**
     * Description: get perssion
     *
     * @return
     */
    //@Cacheable(value = CacheType.DEFAULT, key = "T(com.hengtiansoft.common.util.ApplicationContextUtil).getApplicationName()+'_Functions'")
    @Cacheable(value = CacheType.DEFAULT, key = "T(com.hengtiansoft.common.util.AppConfigUtil).getAppName()+'_Functions'")
    Map<String, FunctionInfo> getFunctions();

    /**
     * Description: Clear the user's personal information cache.
     *
     * @param userId
     */
    @CacheEvict(value = CacheType.DEFAULT, key = "#token+ '_UserInfo'")
    void clearUserInfoCache(String token);

    /**
     * Description: Clears the authentication information cache for the specified user.
     *
     * @param userId
     */
    @CacheEvict(value = CacheType.DEFAULT, key = "#token+ '_SimpleAuthenticationInfo'")
    void clearAuthcCache(String token);

    /**
     * Description: Clear the authorization information cache for the specified user.
     *
     * @param userId
     */
    @CacheEvict(value = CacheType.DEFAULT, key = "#token+ '_SimpleAuthorizationInfo'")
    void clearAuthzCache(String token);

    /**
     * Description: Get static resource address
     *
     * @return
     */
    @Cacheable(value = CacheType.DEFAULT, key = "'_StaticPath'")
    String getStaticPath();

    /**
     * Description: Get the image resource address
     *
     * @return
     */
    @Cacheable(value = CacheType.DEFAULT, key = "'_FtpPath'")
    String getFtpPath();
    
    
    /**
     * Description: get qq address 
     *
     * @return
     */
    @Cacheable(value = CacheType.DEFAULT, key = "'_QqPath'")
    String getQqPath();


}
