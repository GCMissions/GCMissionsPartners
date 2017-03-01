package com.hengtiansoft.wrw.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.KdTeamBuyProductEntity;
import com.hengtiansoft.wrw.entity.KdTwentyFourHoursEntity;
/**
 * 
* Class Name: KdTwentyFourHoursDao
* Description: TODO
* @author chengchaoyin
*
 */
public interface KdTwentyFourHoursDao extends JpaRepository<KdTwentyFourHoursEntity, Long>,
    JpaSpecificationExecutor<KdTwentyFourHoursEntity>{

    /**
     * 查询所有可删除的活动
     * 
     * @param saleStatus
     * @return
     */
    @Query(value = "SELECT id FROM kd_twenty_four_hours  where  STATUS !='1' and DEL_FLAG = '1' ", nativeQuery = true)
    List<Long> findByStatus();
    
    @Modifying
    @Query(value = "update kd_twenty_four_hours set DEL_FLAG = ?2 where id in ?1",nativeQuery = true)
    Integer deleteById(List<Long> ids,String delFlag);
    
    
    /**
     * Description: 查询商品相关24H活动
     *
     * @param productID
     * @return
     */
     @Query(value = "SELECT * FROM kd_twenty_four_hours WHERE product_id = ?1 AND DEL_FLAG = '1' "
             + "AND (now() BETWEEN EFFECTIVE_START_DATE AND EFFECTIVE_END_DATE) "
             + "ORDER BY EFFECTIVE_START_DATE DESC LIMIT 1", nativeQuery = true)
     KdTwentyFourHoursEntity findByProductID(Long productID);
     
     /**
      * 
     * Description: 查询商品相关联的24th活动（进行中）
     *
     * @param productId
     * @return
     * @author chengchaoyin
      */
     @Query(value = "select * from kd_twenty_four_hours where product_id = ?1 AND DEL_FLAG = '1' and status = '3'", nativeQuery = true)
     List<KdTwentyFourHoursEntity> findNormalTfByproductId(Long productId);
     
     
     /**
      * 
     * Description: 查询商品相关联的24th活动（剔除已删除及已结束活动）
     *
     * @param productId
     * @return
     * @author chengchaoyin
      */
     @Query(value = "select * from kd_twenty_four_hours where product_id = ?1 AND DEL_FLAG = '1'  and status != '4'", nativeQuery = true)
     List<KdTwentyFourHoursEntity> getTfByProductId(Long productId);
     
     /**
      * 
     * Description: 通过商品id,上下架时间查询是否有重叠的活动
     *
     * @param productId
     * @param onTime
     * @param offTime
     * @return
     * @author chengchaoyin
      */
     @Query(value = "select count(1) from kd_twenty_four_hours where product_id = ?1 AND DEL_FLAG = '1'  and status != '4' and status != '5' and EFFECTIVE_END_DATE > ?2 AND EFFECTIVE_START_DATE < ?3", nativeQuery = true)
     Integer getTfByProductIdAndTime(Long productId,String onTime,String offTime);
     
     @Modifying
     @Query(value = "update kd_twenty_four_hours set status = ?2 where product_id = ?1 and status = ?3", nativeQuery = true)
     void updateStatus(Long productId, String status, String oldStatus);
     
     @Query(value = "select count(1) from kd_twenty_four_hours where product_id = ?1 AND DEL_FLAG = '1'  and status != '4' and status != '5' and EFFECTIVE_END_DATE > ?2 AND EFFECTIVE_START_DATE < ?3 and id !=?4 ", nativeQuery = true)
     Integer getTfByProductIdAndTimeAndTfId(Long productId,String onTime,String offTime,Long tfId);
     
     
     /**
    * Description: 查询存在类似时间的24H小时活动
    *
    * @param productId
    * @param startDate
    * @param endDate
    * @return
    */
    @Query(value = "select * from kd_twenty_four_hours where (product_id = ?1 and EFFECTIVE_START_DATE < ?2 and EFFECTIVE_END_DATE > ?2 and status in ('2','3')) or "
             + " (product_id = ?1 and EFFECTIVE_START_DATE < ?3 and EFFECTIVE_END_DATE > ?3 and status in ('2','3')) ", nativeQuery = true)
     List<KdTeamBuyProductEntity> findSameTimeTF(Long productId, Date startDate, Date endDate);
     
}
