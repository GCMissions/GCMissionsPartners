package com.hengtiansoft.wrw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.PTypeBrandEntity;

public interface PTypeBrandDao extends JpaRepository<PTypeBrandEntity, Long>{
	
	
	@Query("select b.id.brandId from PTypeBrandEntity b where b.id.typeId =?1")
	List<Long> selectByPK(Long id);
	
	@Query("delete PTypeBrandEntity b where b.id.typeId =?1")
	@Modifying
	void deleteByPK(Long typeId);
	
	@Query("select b.id.typeId from PTypeBrandEntity b where b.id.brandId =?1")
	List<Long> selectByBrandId(Long id);
}
