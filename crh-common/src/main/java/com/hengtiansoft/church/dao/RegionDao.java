package com.hengtiansoft.church.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.church.entity.RegionEntity;

public interface RegionDao extends JpaRepository<RegionEntity, Long>,
    JpaSpecificationExecutor<RegionEntity>{

    @Query(value = "select * from region where del_flag = ?1 order by create_date desc,id desc", nativeQuery = true)
    List<RegionEntity> findByDelFlagAndSort(String delFlag);
    
    @Query(value = "select * from region where del_flag = ?1 and id != ?2 order by create_date desc,id desc", nativeQuery = true)
    List<RegionEntity> findAllRegionNotEqId(String delFlag, Long id);
}
