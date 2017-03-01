package com.hengtiansoft.wrw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hengtiansoft.wrw.entity.SMessageOrgEntity;

public interface SMessageOrgDao extends JpaRepository<SMessageOrgEntity, Long>,JpaSpecificationExecutor<SMessageOrgEntity> {

    @Query("select s.orgId from SMessageOrgEntity s where s.messageId =?1 ")
    List<Long> finByMessageId(Long id);


}
