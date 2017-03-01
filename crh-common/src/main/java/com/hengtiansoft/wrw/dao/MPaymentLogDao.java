package com.hengtiansoft.wrw.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hengtiansoft.wrw.entity.MPaymentLogEntity;

public interface MPaymentLogDao extends JpaRepository<MPaymentLogEntity, Long> {
    MPaymentLogEntity findByOrderId(String orderId);
}
