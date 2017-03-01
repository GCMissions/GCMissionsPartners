package com.hengtiansoft.wrw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.KdOrderDetailEntity;

public interface KdOrderDetailDao extends JpaRepository<KdOrderDetailEntity, Long>,
    JpaSpecificationExecutor<KdOrderDetailEntity>{

    @Query(value = "select * from kd_order_detail where order_id = ?1 and product_id = ?2 and type = ?3 order by id desc limit 1", nativeQuery = true)
    KdOrderDetailEntity findDetail(String orderId, Long productId, String type);
    
    
    /**
    * Description: 查询订单详情
    *
    * @param orderId
    * @param productId
    * @return
    */
    @Query(value = "select * from kd_order_detail where order_id = ?1 and product_id = ?2 order by id desc limit 1", nativeQuery = true)
    KdOrderDetailEntity findDetail(String orderId, Long productId);
    
    
    Integer countByOrderId(String orderId);
    
    List<KdOrderDetailEntity> findByOrderId(String orderId);
    
    @Query(value = "select kd.* from kd_order_detail kd join kd_order_main ko on kd.order_id = ko.order_id "
            + " where kd.order_id = ?1 and kd.product_id = ?2 and kd.type = '2' and ko.status in ('1','2','3','4')", nativeQuery = true)
    List<KdOrderDetailEntity> findByOrderIdAndProductId(String orderId, Long productId);
    
    @Query(value = "select sum(real_price) from kd_order_detail kd where kd.order_id = ?1", nativeQuery = true)
    Integer findTotalAmountByOrderId(String orderId);
    
    @Query(value = "SELECT d.PRODUCT_ID,count(distinct m.ORDER_ID) FROM kd_order_main m INNER JOIN kd_order_detail d ON d.ORDER_ID=m.ORDER_ID AND m.tsn is not null group by d.PRODUCT_ID ", nativeQuery = true)
    List<Object[]> getProductSaleCount();
}
