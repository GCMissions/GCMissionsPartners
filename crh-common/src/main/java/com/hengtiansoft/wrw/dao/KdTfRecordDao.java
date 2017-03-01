/*
 * Project Name: kd-wechat
 * File Name: KdTfRecordRepository.java
 * Class Name: KdTfRecordRepository
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
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.KdTfRecordEntity;

public interface KdTfRecordDao extends JpaRepository<KdTfRecordEntity, Long>,
        JpaSpecificationExecutor<KdTfRecordEntity> {

    /**
     * Description: 查询订单相关24H活动信息
     *
     * @param orderID
     * @return
     */
    @Query(value = "select * from kd_tf_record where order_id = ?1 limit 1", nativeQuery = true)
    KdTfRecordEntity findByOrderID(String orderID);

    /**
     * Description: 查询用户是否参加活动
     *
     * @param tfID
     * @param memberID
     * @return
     */
    @Query(value = "SELECT * FROM kd_tf_record " + "WHERE TF_ID = ?1 AND share_id = ?2 AND STATUS = '1' LIMIT 1", nativeQuery = true)
    KdTfRecordEntity findByTFIDAndMemberID(Long tfID, Long memberID);

    @Query(value = "SELECT r.TF_ID,r.RECORD_ID,r.SHARE_TIME,t.NAME,r.SPEC_INFO,r.CURRENT_PRICE,d.REAL_PRICE,d.PRODUCT_COUNT,p.IMG_URL,r.ORDER_ID "
            + "FROM kd_tf_record r LEFT JOIN kd_twenty_four_hours t ON r.TF_ID = t.ID "
            + "LEFT JOIN kd_order_detail d ON r.ORDER_ID = d.ORDER_ID "
            + "LEFT JOIN kd_p_image p ON p.KEY_ID = t.ID WHERE r.SHARE_ID = ?1 AND p.TYPE = '3' and r.STATUS = '1' "
            + "GROUP BY r.RECORD_ID ORDER BY MAX(p.ID) DESC,r.SHARE_TIME DESC", nativeQuery = true)
    List<Object[]> findByShareId(Long memberId);

    @Modifying
    @Query(value = "update kd_tf_record set return_type = '1',return_money = ?1 where record_id = ?2", nativeQuery = true)
    Integer updateReturnType(Integer returnAmount, Long recordId);
    
    @Query(value ="select count(r) from KdTfRecordEntity r where r.tfId =?1 and r.status =1 ")
    Integer findCountByTfId(Long twentyfourID);

}
