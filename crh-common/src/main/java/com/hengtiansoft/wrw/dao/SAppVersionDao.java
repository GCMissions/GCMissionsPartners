/*
 * Project Name: wrw-common
 * File Name: SAppVersionDao.java
 * Class Name: SAppVersionDao
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
package com.hengtiansoft.wrw.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.SAppVersionEntity;

/**
* Class Name: SAppVersionDao
* Description: 
* @author xianghuang
*
*/
public interface SAppVersionDao extends JpaRepository<SAppVersionEntity, Long>,JpaSpecificationExecutor<SAppVersionEntity>{
    @Query(value = "select a from SAppVersionEntity a where latestVersion=1 and appType in(1)")
    SAppVersionEntity findLatestAppVersion();
    
    @Modifying
    @Query(value = "update S_APP_VERSION set LATEST_VERSION = ?1 where APP_TYPE = ?2",nativeQuery=true)
    void updateLatestVersion(String latestVersion,String appType);
    
    SAppVersionEntity findByVersionNumberAndAppTypeAndUrl(String version,String appType,String Url);
    
    SAppVersionEntity findByVersionNumberAndAppType(String version,String appType);
}
