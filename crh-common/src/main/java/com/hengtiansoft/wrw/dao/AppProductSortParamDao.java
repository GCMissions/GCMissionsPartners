package com.hengtiansoft.wrw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.AppProductSortParamEntity;

public interface AppProductSortParamDao extends JpaRepository<AppProductSortParamEntity, Long>,
    JpaSpecificationExecutor<AppProductSortParamEntity>{

    @Modifying
    @Query(value = "update app_product_sort_param set SHARE_COUNT = ?2 where PRODUCT_ID =  ?1 and TYPE = ?3", nativeQuery = true)
    void updateShareCount(Long productId,Long shareCount, String type);
    
    @Modifying
    @Query(value = "update app_product_sort_param set SALES = ?2 where PRODUCT_ID =  ?1 and TYPE = ?3", nativeQuery = true)
    void updateSaleCount(Long productId,Long saleCount, String type);
    
    @Modifying
    @Query(value = "update app_product_sort_param set CLICKS = ?2 where PRODUCT_ID =  ?1 and TYPE = ?3", nativeQuery = true)
    void updateClickCount(Long productId,Long clickCount, String type);
    
    @Modifying
    @Query(value = "insert into app_product_sort_param(product_id,sales,share_count,clicks,sort,type) values (?1,?2,?3,?4,?5,?6)", nativeQuery = true)
    void saveRecord(Long productId, Long sales, Long shareCount, Long clicks, String sort, String type);
    
    List<AppProductSortParamEntity> findByProductIdAndType(Long productId, String type);
}
