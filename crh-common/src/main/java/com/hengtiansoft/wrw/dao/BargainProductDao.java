package com.hengtiansoft.wrw.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.BargainProductEntity;

/**
 * 
 * Class Name: BargainProductDao Description: 砍价dao
 * 
 * @author chenghongtu
 *
 */
public interface BargainProductDao extends JpaRepository<BargainProductEntity, Long>,
        JpaSpecificationExecutor<BargainProductEntity> {

    @Modifying
    @Query(value = "update bargain_product set STATUS = '1' where STATUS = '0' and EFFECTIVE_START_DATE <=now() and now() < EFFECTIVE_END_DATE ",nativeQuery = true)
    Integer updateBargainProductInStatus();
    
    @Modifying
    @Query(value = "update bargain_product set STATUS = '2' where  now() > EFFECTIVE_END_DATE ",nativeQuery = true)
    Integer updateBargainProductOutStatus();
}
