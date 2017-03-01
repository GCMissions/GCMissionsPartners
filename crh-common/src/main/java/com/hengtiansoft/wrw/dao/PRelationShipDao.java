package com.hengtiansoft.wrw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.PRelationShipEntity;

public interface PRelationShipDao extends JpaRepository<PRelationShipEntity, Long>{
	
	@Query("select p.relatId from PRelationShipEntity p where p.productId =?1 ")
	List<Long> findByProductId(Long id);
	
	@Query("delete PRelationShipEntity p  where p.productId =?1")
	@Modifying
	void deleteByProductId(Long productId);

}
