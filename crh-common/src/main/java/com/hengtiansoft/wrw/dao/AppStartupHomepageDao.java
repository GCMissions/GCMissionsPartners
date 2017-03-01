/*
 * Project Name: wrw-common
 * File Name: AppStartupHomepageDao.java
 * Class Name: AppStartupHomepageDao
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

import com.hengtiansoft.wrw.entity.AppStartupHomepageEntity;

/**
 * Class Name: AppStartupHomepageDao Description: TODO
 * 
 * @author qianqianzhu
 *
 */
public interface AppStartupHomepageDao
        extends JpaRepository<AppStartupHomepageEntity, Long>, JpaSpecificationExecutor<AppStartupHomepageEntity> {

    @Modifying
    @Query(value = "update app_startup_homepage set status = ?2 where id=?1", nativeQuery = true)
    void deleteEntity(Long id, String status);

}
