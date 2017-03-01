/*
 * Project Name: wrw-common
 * File Name: SFreightConfigDao.java
 * Class Name: SFreightConfigDao
 * Copyright 2014 Hengtian Software Inc
 * Licensed under the Hengtiansoft
 * http://www.hengtiansoft.com
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hengtiansoft.wrw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.SFreightConfigEntity;

/**
 * Class Name: SFreightConfigDao
 * Description: TODO
 * 
 * @author chengminmiao
 */
public interface SFreightConfigDao extends JpaRepository<SFreightConfigEntity, Long> {

    @Query("select fc from SFreightConfigEntity fc where freightType = ?1 and configId > 2")
    List<SFreightConfigEntity> findByFreightTypeNotDef(String type);

    @Query("select configId from SFreightConfigEntity where freightType = ?1")
    List<Long> findConfigIdByType(String type);

    @Query("delete from SFreightConfigEntity where configId in ?1")
    @Modifying
    void delByConfigIds(List<Long> delList);

    List<SFreightConfigEntity> findByFreightTypeAndConfigIdIn(String type, List<Long> configIds);

    SFreightConfigEntity findByFreightTypeAndConfigId(String type, Long configId);

}
