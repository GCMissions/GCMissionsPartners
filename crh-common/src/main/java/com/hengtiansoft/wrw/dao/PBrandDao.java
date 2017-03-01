package com.hengtiansoft.wrw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.PBrandEntity;

/**
 * 
 * @author jiekaihu
 *
 */
public interface PBrandDao extends JpaRepository<PBrandEntity, Long>, JpaSpecificationExecutor<PBrandEntity> {

    @Query("select b from PBrandEntity b  where b.brandName = ?1 and b.status ='1'")
    PBrandEntity findByBrandName(String brandName);
    
    @Query("select b.brandName from PBrandEntity b  where b.brandId in (?1) ")
	List<String> findByIds(List<Long> brandIds);
    
    @Query("select b from PBrandEntity b where b.status =?1 ")
	List<PBrandEntity> findAllByStatus(String code);

}
