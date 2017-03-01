/*
 * Project Name: wrw-common
 * File Name: AppFeatureDao.java
 * Class Name: AppFeatureDao
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
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.CoolbagFeatureEntity;

/**
 * Class Name: AppFeatureDao Description: TODO
 * 
 * @author kangruan
 *
 */
public interface CoolbagFeatureDao extends JpaRepository<CoolbagFeatureEntity, Long> {

    @Query(value = "select max(sort)+1 from coolbag_feature", nativeQuery = true)
    Integer findMaxSort();
    
    @Query(value = "select count(1) from coolbag_feature", nativeQuery = true)
    Integer countFeature();

    /**
     * 
     * Description: 专辑升序
     *
     * @param sort
     */
    @Modifying
    @Query(value = "UPDATE coolbag_feature t1, coolbag_feature t2 SET t1.sort= t2.sort,t2.sort=t1.sort"
            + " where t1.sort = t2.sort+1 and t1.id =?1 and t1.sort =?2 ", nativeQuery = true)
    void updateSortAsc(Long id, Integer sort);

    /**
     * 
     * Description: 专辑降序
     *
     * @param id
     * @param sort
     */
    @Modifying
    @Query(value = "UPDATE  coolbag_feature t1, coolbag_feature t2 SET t1.sort= t2.sort,t2.sort=t1.sort"
            + " where t1.sort = t2.sort-1 and t1.id =?1 and t1.sort =?2 ", nativeQuery = true)
    void updateSortDesc(Long id, Integer sort);

    CoolbagFeatureEntity findByIdAndStatus(Long id, String status);
    
    @Query(value = "select cf.* from coolbag_feature cf join coolbag_feature_tag_ref cfr on cf.id = cfr.feature_id where cfr.id=?1", nativeQuery = true)
    CoolbagFeatureEntity findByRefId(Long id);
    
    @Query(value = "select group_concat(ci.NAME) from coolbag_feature cf join coolbag_feature_tag_ref cfr on cf.ID = cfr.FEATURE_ID join coolbag_image ci on ci.id = cfr.TAG_ID where cf.ID = ?1 order by ci.SORT", nativeQuery = true)
    String findTagName(Long featureId);
    
    @Query(value ="select min(f.sort) from  coolbag_feature f where f.status= ?1 ", nativeQuery = true)
    Integer findMinSortByStatus(String status);
    
    @Query(value ="select max(f.sort) from  coolbag_feature f where f.status= ?1 ", nativeQuery = true)
    Integer findMaxSortByStatus(String status);
}
