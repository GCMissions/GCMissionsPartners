package com.hengtiansoft.wrw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hengtiansoft.wrw.entity.KdOrderOperEntity;

public interface KdOrderOperDao extends JpaRepository<KdOrderOperEntity, Long>,
    JpaSpecificationExecutor<KdOrderOperEntity>{

    List<KdOrderOperEntity> findByOrderIdAndOperType(String orderId, String operType);
}
