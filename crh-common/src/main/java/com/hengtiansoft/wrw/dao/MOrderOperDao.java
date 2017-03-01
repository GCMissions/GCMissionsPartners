package com.hengtiansoft.wrw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.MOrderOperEntity;

/**
 * Created by qiuyanlv on 6/2/2016.
 */
public interface MOrderOperDao extends JpaRepository<MOrderOperEntity, String>, JpaSpecificationExecutor<MOrderOperEntity> {
    @Query(value = "select * from M_ORDER_OPER p where p.order_id=?1 and p.OPER_TYPE=?2  order by p.oper_date desc", nativeQuery = true)
    List<MOrderOperEntity> findOperBystatus(String orderId, String status);

    List<MOrderOperEntity> findByOrderId(String orderId);
    
    MOrderOperEntity findByOrderIdAndOpertype(String orderId, String operType);
}
