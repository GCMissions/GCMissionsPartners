package com.hengtiansoft.wrw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.PAttributeEntity;

public interface PAttributeDao extends JpaRepository<PAttributeEntity, Long>, JpaSpecificationExecutor<PAttributeEntity> {

    @Query("select a.attrName  from PAttributeEntity a where a.attrId in (?1) ")
    List<String> findByIds(List<Long> attrIds);
    
    @Query("select count(a.attrId) from PAttributeEntity a where a.attrName =?1 and a.status =?2 ")
	int findByNameAndStatus(String attrName,String status);
    
    @Query("select a from PAttributeEntity a where a.status =?1 ")
	List<PAttributeEntity> findAllByStatus(String code);
    
    @Query("select a from PAttributeEntity a where a.status =?1 and a.searchable =?2 ")
    List<PAttributeEntity> findByStatusAndSearchable(String code, String searchable);
    
}
