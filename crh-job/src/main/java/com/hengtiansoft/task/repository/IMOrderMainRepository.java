package com.hengtiansoft.task.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.MOrderMainEntity;


public interface IMOrderMainRepository extends JpaRepository<MOrderMainEntity, String> {
	
    List<MOrderMainEntity> findByStatus(String status);
    
    @Query(value = "select orderId from MOrderMainEntity where status = ?1")
    List<String> findOrderIdByStatus(String status);
    
    /**
    * Description: 定时将活动日期已过期的订单修改为待评价订单
    *
    */
    @Modifying
    @Query(value = "update m_order_main orderMain join "
            + "(select t.order_id, t.act_date from "
            + "(select od.order_id, MAX(od.act_date) act_date from m_order_detail od group by od.order_id) "
            + "t where DATE(t.act_date) < CURDATE()) tb on orderMain.order_id = tb.order_id "
            + "set orderMain.status = '2_1',orderMain.MODIFY_DATE = NOW() where orderMain.status = '2'", nativeQuery = true)
    void updatePastActivities();
    
}
