/*
 * Project Name: wrw-common
 * File Name: CoolbagFeatureTagRefDao.java
 * Class Name: CoolbagFeatureTagRefDao
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

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.CoolbagFeatureTagRefEntity;

/**
 * Class Name: CoolbagFeatureTagRefDao Description: TODO
 * 
 * @author kangruan
 *
 */
public interface CoolbagFeatureTagRefDao extends JpaRepository<CoolbagFeatureTagRefEntity, Long> {

    @Modifying
    @Query(value="delete from coolbag_feature_tag_ref where FEATURE_ID =?1",nativeQuery = true)
    void deleteByFeatureId(Long featureId);
    
    @Query(value="select r.tagId from CoolbagFeatureTagRefEntity r where r.featureId =?1")
    List<Long> findTagIdsByFeatureId(Long featureId);
    
    List<CoolbagFeatureTagRefEntity> findByFeatureId(Long feature);
}
