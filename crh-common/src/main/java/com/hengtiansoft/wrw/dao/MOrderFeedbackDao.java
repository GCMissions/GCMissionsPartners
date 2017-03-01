package com.hengtiansoft.wrw.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.MOrderFeedbackEntity;

public interface MOrderFeedbackDao extends JpaRepository<MOrderFeedbackEntity, Long>, 
    JpaSpecificationExecutor<MOrderFeedbackEntity>{

    MOrderFeedbackEntity findByOrderId(String orderId);
    
    @Query(value = "select count(1) from m_order_feedback where PRODUCT_ID = ?1 ", nativeQuery = true)
    Long countByProductId(Long id);
    
    @Query(value = "select avg(star)  from m_order_feedback where PRODUCT_ID = ?1 ", nativeQuery = true)
    Double countAvgStarByProductId(Long id);
    
}
