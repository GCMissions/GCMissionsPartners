/*
 * Project Name: wrw-common
 * File Name: KdCharityDao.java
 * Class Name: KdCharityDao
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

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.KdCharityEntity;

/**
 * Class Name: KdCharityDao
 * Description: 酷袋公益活动
 * @author zhongyidong
 *
 */
public interface KdCharityDao extends JpaRepository<KdCharityEntity, Long>{

    /**
     * Description: 根据名字查询活动数量
     *
     * @param name
     * @param isDeleted
     * 
     * @return
     */
    @Query(value = "select count(1) from kd_charity where name = ?1 and is_deleted = ?2", nativeQuery = true)
    int findByName(String name, String isDeleted);
    
    /**
     * Description: 检查是否有时间重叠的活动
     *
     * @param startTime
     * @param endTime
     * @param featureId
     * @param status
     * @param isDeleted
     * @return
     */
    @Query(value = "select count(1) from kd_charity where feature_id = ?3 and status != ?4 and is_deleted = ?5 "
            + "and start_time < ?2 and end_time > ?1 ", nativeQuery = true)
    int checkDate(Date startTime, Date endTime, Long featureId, String status, String isDeleted);
    
    /**
     * Description: 删除公益活动
     *
     * @param charityIds
     * @param isDeleted
     * @return
     */
    @Modifying
    @Query(value = "update kd_charity set is_deleted = ?2 where id in ?1", nativeQuery = true)
    int deleteCharity(List<Long> charityIds, String isDeleted);
    
    /**
     * Description: 保存公益活动详情
     *
     * @param charityId
     * @param detail
     * @return
     */
    @Modifying
    @Query(value = "update kd_charity set detail_desc = ?2 where id = ?1", nativeQuery = true)
    int saveDetail(Long charityId, String detail);
    
    /**
     * Description: 保存公益活动反馈详情
     *
     * @param charityId
     * @param feedback
     * @return
     */
    @Modifying
    @Query(value = "update kd_charity set feedback = ?2 where id = ?1", nativeQuery = true)
    int saveFeedback(Long charityId, String feedback);
    
    /**
     * Description: 修改活动状态（将要开始）
     * @param oldstatus
     * @param newStatus
     * @return
     */
    @Modifying
    @Query(value = "update kd_charity set status = ?2, modify_id = 0, modify_date = now() "
            + "where start_time <= now() and end_time > now() and status = ?1 and is_deleted = '0'", nativeQuery = true)
    int startActivity(String oldstatus, String newStatus);
    
    /**
     * Description: 修改活动状态（将要结束）
     *
     * @param oldstatus
     * @param newStatus
     * @return
     */
    @Modifying
    @Query(value = "update kd_charity set status = ?2, modify_id = 0, modify_date = now() "
            + "where end_time <= now() and status = ?1 and is_deleted = '0'", nativeQuery = true)
    int endActivity(String oldstatus, String newStatus);
    
    /**
     * Description: 修改公益活动状态
     *
     * @param status
     * @param userId
     * @param featureId
     * @return
     */
    @Modifying
    @Query(value = "update kd_charity set status= ?1, modify_id = ?2, modify_date = now() where feature_id = ?3", nativeQuery = true)
    int updateStatus(String status, Long userId, Long featureId);
    
    /**
     * Description: 检查是否有绑定的公益活动
     *
     * @param featureId
     * @param status
     * @param isDeleted
     * @return
     */
    @Query(value = "select count(*) from kd_charity where feature_id = ?1 and status = ?2 and is_deleted = ?3", nativeQuery = true)
    int countOngoingByFeature(Long featureId, String status, String isDeleted);
    
    /**
     * Description: 统计某个状态的公益活动数量
     *
     * @param charityIds
     * @param status
     * @param isDeleted
     * @return
     */
    @Query(value = "select count(*) from kd_charity where id in ?1 and status = ?2 and is_deleted = ?3", nativeQuery = true)
    int countByStatus(List<Long> charityIds, String status, String isDeleted);
    
}
