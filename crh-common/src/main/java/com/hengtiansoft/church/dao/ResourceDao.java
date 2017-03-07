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
    
    @Query(value = "select * from resource where del_flag = '1' order by sort desc", nativeQuery = true)
    ResourceEntity findLastSortEntity();
    
    @Query(value = "select * from resource where del_flag = '1' and sort = ?1 order by id desc limit 1", nativeQuery = true)
    ResourceEntity findBySort(String sort);
}
