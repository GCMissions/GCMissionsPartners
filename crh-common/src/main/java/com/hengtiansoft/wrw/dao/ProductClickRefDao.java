package com.hengtiansoft.wrw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.ProductClickRefEntity;
/**
 * 
* Class Name: ProductClickRefDao
* Description: TODO
* @author chengchaoyin
*
 */
public interface ProductClickRefDao extends JpaRepository<ProductClickRefEntity, Long>, JpaSpecificationExecutor<ProductClickRefEntity> {
    
    @Query(value = "select p.PRODUCT_ID,count(1) from product_click_ref p group by p.PRODUCT_ID", nativeQuery = true)
    List<Object[]> getProductClickCount();

}
