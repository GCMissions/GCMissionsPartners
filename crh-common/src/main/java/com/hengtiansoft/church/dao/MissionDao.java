package com.hengtiansoft.church.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.church.entity.MissionEntity;

public interface MissionDao extends JpaRepository<MissionEntity, Long>,
    JpaSpecificationExecutor<MissionEntity>{

    @Query(value = "select * from mission where del_flag = ?1 order id", nativeQuery = true)
    List<MissionEntity> findByDelFlag(String delFlag);
}
