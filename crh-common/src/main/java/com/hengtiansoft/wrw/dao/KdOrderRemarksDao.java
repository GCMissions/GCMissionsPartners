package com.hengtiansoft.wrw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hengtiansoft.wrw.entity.KdOrderRemarksEntity;

public interface KdOrderRemarksDao extends JpaRepository<KdOrderRemarksEntity, Long>,
    JpaSpecificationExecutor<KdOrderRemarksEntity>{

    List<KdOrderRemarksEntity> findByOrderIdAndStatusOrderByCreateDateDesc(String orderId, String status);
}
