package com.hengtiansoft.wrw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.SStockRecordEntity;

public interface SStockRecordDao extends JpaRepository<SStockRecordEntity, Integer>, JpaSpecificationExecutor<SStockRecordEntity> {
    
    @Query(value = "select * from S_STOCK_RECORD WHERE STOCK_ID = ?1", nativeQuery = true)
    List<SStockRecordEntity> findByStockId(Long stockId);
}
