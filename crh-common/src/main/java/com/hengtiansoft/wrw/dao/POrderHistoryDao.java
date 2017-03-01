package com.hengtiansoft.wrw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.POrderHistoryEntity;
/**
 * 
* Class Name: ActivityDao
* Description: TODO
* @author chenghongtu
*
 */
public interface POrderHistoryDao extends JpaRepository<POrderHistoryEntity, Long>, JpaSpecificationExecutor<POrderHistoryEntity> {
    
    /**
     * 
    * Description: 查询历史商品对应的订单数量
    *
    * @return
    * @author chengchaoyin
     */
    @Query(value = "select PRODUCT_ID,ORDER_COUNT from p_order_history ",nativeQuery = true)
    List<Object[]> findProductOrderCount();

}
