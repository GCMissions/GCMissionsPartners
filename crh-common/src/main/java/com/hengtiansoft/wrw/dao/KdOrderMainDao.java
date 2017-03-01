package com.hengtiansoft.wrw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.KdOrderMainEntity;

public interface KdOrderMainDao extends JpaRepository<KdOrderMainEntity, Long>,
    JpaSpecificationExecutor<KdOrderMainEntity>{

    KdOrderMainEntity findByOrderId(String orderId);
    
    @Query(value = "select o.ORDER_ID,o.ACTUAL_AMOUNT+o.SHIP_AMOUNT,o.ACTUAL_AMOUNT,o.`STATUS`,o.PHONE,o.SHIP_AMOUNT,o.CREATE_DATE, "
            + " o.`TYPE`,o.EXPRESS_INFO,concat(o.CONTACT,'，',o.PHONE,'，',o.ADDRESS_INFO),vip.id "
            + " from kd_order_main o left join m_vip_order vip on o.order_id = vip.order_main_id "
            + " where o.order_id = ?1", nativeQuery = true)
    List<Object[]> findInfoByOrderId(String orderId);
    
    List<KdOrderMainEntity> findByStatus(String status);
    
    @Query(value = "select count(1),ifnull(sum(ACTUAL_AMOUNT),0) from kd_order_main where member_id = ?1 and status not in ?2", nativeQuery = true)
    List<Object[]> getKdOrderCountAndAmount(Long memberId, String[] removeOrderStatus);
    
    Integer countByStatusAndMemberId(String status, Long memberId);
}
