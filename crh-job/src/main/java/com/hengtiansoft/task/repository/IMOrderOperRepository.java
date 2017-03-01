package com.hengtiansoft.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hengtiansoft.wrw.entity.MOrderOperEntity;

public interface IMOrderOperRepository extends JpaRepository<MOrderOperEntity, String> {
    
    MOrderOperEntity findByOrderIdAndOpertype(String orderId, String operType);
}
