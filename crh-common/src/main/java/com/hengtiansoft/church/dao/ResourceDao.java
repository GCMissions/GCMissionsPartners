package com.hengtiansoft.church.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.church.entity.ResourceEntity;

public interface ResourceDao extends JpaRepository<ResourceEntity, Long>,
    JpaSpecificationExecutor<ResourceEntity>{

    @Query(value = "select * from resource where del_flag = '1' order by sort", nativeQuery = true)
    List<ResourceEntity> findAllBySort();
}
