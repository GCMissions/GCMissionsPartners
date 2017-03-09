package com.hengtiansoft.church.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.church.entity.SlidesEntity;

public interface SlidesDao extends JpaRepository<SlidesEntity, Long>,
    JpaSpecificationExecutor<SlidesEntity>{

    @Query(value = "select * from slides where del_flag = '1' order by sort", nativeQuery = true)
    List<SlidesEntity> findAllAndSort();
    
    @Query(value = "select * from slides where del_flag = '1' order by sort desc limit 1", nativeQuery = true)
    SlidesEntity findLastSortEntity();
    
    @Query(value = "select * from slides where del_flag = '1' and sort = ?1 order by id desc limit 1", nativeQuery = true)
    SlidesEntity findBySort(String sort);
    
    @Modifying
    @Query(value = "update slides set sort = sort - 1 where sort > ?1 and del_flag = '1'", nativeQuery = true)
    void updateSort(String sort);
}
