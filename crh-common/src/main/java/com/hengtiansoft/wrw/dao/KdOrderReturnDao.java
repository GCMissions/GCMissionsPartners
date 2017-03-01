package com.hengtiansoft.wrw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.KdOrderReturnEntity;

public interface KdOrderReturnDao extends JpaRepository<KdOrderReturnEntity, String>,
    JpaSpecificationExecutor<KdOrderReturnEntity>{

    List<KdOrderReturnEntity> findByOrderMainId(String orderMainId);
    
    Integer countByOrderMainId(String orderId);
    
    @Query(value = "select sum(return_amount) from kd_order_return where order_main_id = ?1", nativeQuery = true)
    Integer findSumByOrderId(String orderId);
    
    @Query(value = "select count(1) from kd_order_return mr left join kd_order_main o on mr.ORDER_MAIN_ID = o.ORDER_ID where o.MEMBER_ID = ?1", nativeQuery = true)
    Long getKdOrderReturnAmount(Long memberId);
}
