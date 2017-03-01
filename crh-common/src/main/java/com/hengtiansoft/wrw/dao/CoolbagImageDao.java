/*
 * Project Name: wrw-common
 * File Name: AppImageDao.java
 * Class Name: AppImageDao
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
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.CoolbagImageEntity;

/**
 * Class Name: AppImageDao
 * Description: TODO
 * @author kangruan
 *
 */
public interface CoolbagImageDao extends JpaRepository<CoolbagImageEntity, Long>{
    
    @Query("from CoolbagImageEntity c where c.type =?1 and c.status = ?2 order by c.sort")
    List<CoolbagImageEntity> findByTypeAndStatus(String type, String status);
    
    @Query("select count(c) from CoolbagImageEntity c where c.status =?1 and c.type =?2 ")
	Integer findCountByStatus(String status,String type);
    
    @Query("select max(sort)+1 from CoolbagImageEntity c where c.type =?1 ")
    Integer findMaxSort(String type);
    
    /**
     * Description: 获取专辑所属的标签
     *
     * @param type
     * @param status
     * @param tagIds
     * @return
     */
    @Query(value = "select group_concat(name,'') from coolbag_image where type =?1 and "
            + " status = ?2 and id in ?3 order by sort", nativeQuery = true)
    String findTagNameById(String type, String status, List<Long> tagIds);
}
