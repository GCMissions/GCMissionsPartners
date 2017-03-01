package com.hengtiansoft.wrw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.PTypeAttrEntity;

public interface PTypeAttrDao extends JpaRepository<PTypeAttrEntity, Long>{
	
	@Query("select a.id.attrId from PTypeAttrEntity a where a.id.typeId = ?1")
	List<Long> selectByPK(Long id);
	
	
	@Query("delete PTypeAttrEntity a where a.id.typeId =?1")
	@Modifying
	void deleteByPK(Long typeId);

	@Query("select a.id.typeId from PTypeAttrEntity a where a.id.attrId =?1")
	List<Long> selectByAttrId(Long id);
	

}
