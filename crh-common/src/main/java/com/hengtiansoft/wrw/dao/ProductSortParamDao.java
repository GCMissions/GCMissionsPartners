package com.hengtiansoft.wrw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.ProductSortParamEntity;

public interface ProductSortParamDao extends JpaRepository<ProductSortParamEntity, Long>, JpaSpecificationExecutor<ProductSortParamEntity> {
    
    @Modifying
    @Query(value = "update product_sort_param set SHARE_COUNT = ?2 where PRODUCT_ID =  ?1", nativeQuery = true)
    void updateShareCount(Long productId,Long shareCount);
    
    
    @Modifying
    @Query(value = "update product_sort_param set SALES = ?2 where PRODUCT_ID =  ?1", nativeQuery = true)
    void updateSaleCount(Long productId,Long saleCount);
    
    @Modifying
    @Query(value = "update product_sort_param set CLICKS = ?2 where PRODUCT_ID =  ?1", nativeQuery = true)
    void updateClickCount(Long productId,Long clickCount);
    
    @Modifying
    @Query(value = "delete from product_sort_param where PRODUCT_ID in ?1",nativeQuery = true)
    void deleteByProductId(List<String> ids);
}
