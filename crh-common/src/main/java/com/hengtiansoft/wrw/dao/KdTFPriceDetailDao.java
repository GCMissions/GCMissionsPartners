package com.hengtiansoft.wrw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.KdTFPriceDetailEntity;
/**
 * 
* Class Name: KdTFPriceDetailDao
* Description: TODO
* @author chengchaoyin
*
 */
public interface KdTFPriceDetailDao extends JpaRepository<KdTFPriceDetailEntity, Long>,
    JpaSpecificationExecutor<KdTFPriceDetailEntity>{
    
    /**
     * Description: 根据商品id查询
     *
     * @param productId
     * @return
     */
    @Query(value = "select * from kd_tf_price_detail where tf_id = ?1 and IS_DELETED='1' ", nativeQuery = true)
    List<KdTFPriceDetailEntity> findByTfId(Long tfId);
    
    @Modifying
    @Query(value = "update kd_tf_price_detail set IS_DELETED = ?2 where tf_id = ?1 and IS_DELETED='1' ",nativeQuery = true)
    void updateDelStatusByTfId(Long tfId,String delFlag);
}
