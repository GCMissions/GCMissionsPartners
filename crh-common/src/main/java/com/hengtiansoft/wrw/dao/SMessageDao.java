package com.hengtiansoft.wrw.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hengtiansoft.wrw.entity.SMessageEntity;

public interface SMessageDao extends JpaRepository<SMessageEntity,Long>,JpaSpecificationExecutor<SMessageEntity>{

}
