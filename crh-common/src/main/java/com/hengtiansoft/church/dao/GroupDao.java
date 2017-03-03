package com.hengtiansoft.church.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.church.entity.GroupEntity;

public interface GroupDao extends JpaRepository<GroupEntity, Long>,
    JpaSpecificationExecutor<GroupEntity>{

    @Query(value = "select * from group where del_flag = ?1 order by create_date desc,id desc", nativeQuery = true)
    List<GroupEntity> findByDelFlagAndSort(String delFlag);
}
