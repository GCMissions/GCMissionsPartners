package com.hengtiansoft.wrw.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.MSmsEntity;

public interface MSmsDao extends JpaRepository<MSmsEntity, Long>, JpaSpecificationExecutor<MSmsEntity> {
    
    @Query(value = "select * from M_SMS where MEMBER_ID = ?1 and TYPE = ?2 order by CREATE_DATE desc limit 1", nativeQuery = true)
    MSmsEntity findByMemberIdAndType(Long memberId, String status);
    
    Long countByPhoneAndStatusAndCreateDateGreaterThan(String phone, String status, Date createDate);
}
