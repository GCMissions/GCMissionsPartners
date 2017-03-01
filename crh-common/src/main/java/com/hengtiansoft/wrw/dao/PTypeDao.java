package com.hengtiansoft.wrw.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.PTypeEntity;

public interface PTypeDao extends JpaRepository<PTypeEntity, Long>,JpaSpecificationExecutor<PTypeEntity>{
	
	@Query("select count(a.typeId) from PTypeEntity a where a.typeName =?1 and a.status =?2 ")
	int findByTypeNameAndStatus(String typeName,String status);

}
