/*
 * Project Name: wrw-common
 * File Name: SFreightRegionDao.java
 * Class Name: SFreightRegionDao
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

import com.hengtiansoft.wrw.entity.SFreightRegionEntity;
import com.hengtiansoft.wrw.entity.SFreightRegionPK;

/**
 * Class Name: SFreightRegionDao
 * Description: TODO
 * 
 * @author chengminmiao
 */
public interface SFreightRegionDao extends JpaRepository<SFreightRegionEntity, SFreightRegionPK> {

    @Query("select fr.id.regionId from SFreightRegionEntity fr where fr.id.configId = ?1")
    List<Integer> findRegionIdsByConfigId(Long configId);

    @Query("select r.name From SFreightRegionEntity fr , SRegionEntity r where r.id = fr.id.regionId and fr.id.configId = ?1")
    List<String> findAllRegionNames(Long configId);

    @Query("delete from SFreightRegionEntity where id.configId in ?1 ")
    @Modifying
    void delByConfigIds(List<Long> delList);
    
    @Query(value = "select fr.id.configId from SFreightRegionEntity fr where fr.id.regionId = ?1")
    List<Long> findConfigIdByRegionId(Integer regionId);

}
