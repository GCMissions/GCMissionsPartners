package com.hengtiansoft.wrw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.KdTeamBuyProductRecordCycleEntity;

public interface KdTeamBuyProductRecordCycleDao extends JpaRepository<KdTeamBuyProductRecordCycleEntity, Long>,
    JpaSpecificationExecutor<KdTeamBuyProductRecordCycleEntity>{

    @Query(value = "select kc.* from kd_team_buy_product_record_cycle kc join kd_order_main o on kc.ORDER_ID = o.ORDER_ID "
            + " where o.`STATUS` in ('1','2','3','4','7') and kc.record_id = ?1 and kc.status = ?2", nativeQuery = true)
    List<KdTeamBuyProductRecordCycleEntity> findByRecordIdAndStatus(Long recordId, String status);
}
