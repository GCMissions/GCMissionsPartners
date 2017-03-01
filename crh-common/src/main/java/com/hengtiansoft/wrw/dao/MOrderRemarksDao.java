package com.hengtiansoft.wrw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hengtiansoft.wrw.entity.MOrderRemarksEntity;

public interface MOrderRemarksDao extends JpaRepository<MOrderRemarksEntity, String>, JpaSpecificationExecutor<MOrderRemarksEntity>{

    List<MOrderRemarksEntity> findByOrderIdOrderByCreateDateDesc(String orderId);

}
