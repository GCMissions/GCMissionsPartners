/*
 * Project Name: wrw-common
 * File Name: KdPImageDao.java
 * Class Name: KdPImageDao
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

import com.hengtiansoft.wrw.entity.KdPImageEntity;

/**
 * Class Name: KdPImageDao
 * Description: 酷袋商品、专享图片
 * @author zhongyidong
 *
 */
public interface KdPImageDao extends JpaRepository<KdPImageEntity, Long>{
    
    /**
     * Description: 查询图片信息
     *
     * @param keyId
     * @param type
     * @param isDeleted
     * 
     * @return
     */
    @Query(value = "select * from kd_p_image where key_id = ?1 and type= ?2 and is_deleted = ?3 order by id asc", nativeQuery = true)
    List<KdPImageEntity> queryImage(Long keyId, String type, String isDeleted);
    
    /**
     * Description: 删除图片信息
     *
     * @param keyId
     * @param type
     * @param isDeleted
     * @return
     */
    @Modifying
    @Query(value = "update kd_p_image set is_deleted = ?3 where key_id = ?1 and type= ?2 ", nativeQuery = true)
    int deleteImage(Long keyId, String type, String isDeleted);
    
    List<KdPImageEntity> findByKeyIdAndTypeAndIsDeleted(Long keyId, String type, String isDeleted);
    
    @Query(value = "select c.imageUrl from KdPImageEntity c where c.keyId =?1 and c.type =?2 and c.isDeleted = 0 ")
    List<String> findByKeyIdAndType(Long actId, String key);
}
