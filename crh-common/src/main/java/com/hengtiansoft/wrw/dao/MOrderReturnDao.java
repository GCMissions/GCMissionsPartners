/*
 * Project Name: wrw-common
 * File Name: MOrderReturnDao.java
 * Class Name: MOrderReturnDao
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
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.MOrderReturnEntity;

/**
 * Class Name: MOrderReturnDao
 * Description:
 * 
 * @author xianghuang
 */
public interface MOrderReturnDao extends JpaRepository<MOrderReturnEntity, String>, JpaSpecificationExecutor<MOrderReturnEntity> {

    MOrderReturnEntity findByOrderMainId(String orderMainId);

    @Query(value = "SELECT * FROM M_ORDER_RETURN  where BALANCE_FLAG = 0 and RETURN_STATUS in('3') and RETURN_TYPE = '3' limit 100 ", nativeQuery = true)
    List<MOrderReturnEntity> findAllNoBalance();

    @Modifying
    @Query(value = "update M_ORDER_RETURN set  BALANCE_FLAG = ?2 where order_Id = ?1", nativeQuery = true)
    void updateOrderBalance(String orderId, String balanceFlag);
    
    /**
     * Description: 获取退款总金额
     *
     * @param orderMainId
     * @return
     */
    @Query(value = "select count(*) as times, ifnull(sum(return_amount), 0) as amount "
            + "from m_order_return where order_main_id = ?1", nativeQuery = true)
    List<Object[]> findReturnDetail(String orderMainId);
    
    /**
     * Description: 检查订单是否还可以退款
     *
     * @param orderMainId
     * @return
     */
    @Query(value = "select a.actutal_amount > b.return_amount from m_order_main a left join "
            + "(select ?1 as order_main_id, ifnull(sum(return_amount), 0) as return_amount "
            + " from m_order_return where order_main_id = ?1) as b "
            + "on a.order_id = b.order_main_id where a.order_id = ?1", nativeQuery = true)
    int isReturnable(String orderMainId);
    
    @Query(value = "select count(1) from m_order_return mr left join m_order_main o on mr.ORDER_MAIN_ID = o.ORDER_ID where o.MEMBER_ID = ?1", nativeQuery = true)
    Long getOrderReturnAmount(Long memberId);
    
}
