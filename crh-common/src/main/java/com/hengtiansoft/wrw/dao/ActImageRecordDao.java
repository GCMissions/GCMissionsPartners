package com.hengtiansoft.wrw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.ActImageRecordEntity;

public interface ActImageRecordDao extends JpaRepository<ActImageRecordEntity, Long>, JpaSpecificationExecutor<ActImageRecordEntity> {
    
    @Query(value = "select * from act_image_record where act_stock_id = ?2 and type=?1 and status='1' order by create_date desc limit 6", nativeQuery = true)
    List<ActImageRecordEntity> findByTypeAndStockId(String type, Long actStockId);
    
    @Query(value = "insert into act_image_record (act_stock_id, type,url,status,create_date) values (?1,'1', ?2, '1', now())", nativeQuery = true)
    @Modifying
    void addActivityImage(Long actStockId, String imageUrl);
    
    @Query(value = "update act_image_record set status = ?1 where id in ?2", nativeQuery = true)
    @Modifying
    void updateStatus(String status, List<Long> actImageRecordId);
}
