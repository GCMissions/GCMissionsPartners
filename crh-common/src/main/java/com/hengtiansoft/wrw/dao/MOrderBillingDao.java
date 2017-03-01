package com.hengtiansoft.wrw.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hengtiansoft.wrw.entity.MOrderBillingEntity;

public interface MOrderBillingDao extends JpaRepository<MOrderBillingEntity, Long>, JpaSpecificationExecutor<MOrderBillingEntity> {
    
    MOrderBillingEntity findByOrderIdAndBillType(String orderId, String billType);
}
