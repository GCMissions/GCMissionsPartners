package com.hengtiansoft.wrw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.SIntersectionEntity;

public interface SIntersectionDao extends JpaRepository<SIntersectionEntity, Long>,
    JpaSpecificationExecutor<SIntersectionEntity>{

    SIntersectionEntity findByIdAndStatus(Long id, String status);
    
    @Query(value = "select * from s_intersection where id in ?1 and status = ?2", nativeQuery = true)
    List<SIntersectionEntity> findByIdsAndStatus(List<Long> ids, String status);
}
