package com.hengtiansoft.wrw.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.MFeedbackEntity;

public interface MFeedbackDao extends JpaRepository<MFeedbackEntity, Long>, JpaSpecificationExecutor<MFeedbackEntity> {
    
    @Modifying
    @Query(value = "update M_FEEDBACK  set  STATUS = ?1 where  FEEDBACK_ID = ?2 ", nativeQuery = true)
    void updateById(String status,Long attrId);
}
