package com.hengtiansoft.wrw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.MOrderDetailEntity;


public interface MOrderDetailDao extends JpaRepository<MOrderDetailEntity, String> {

    List<MOrderDetailEntity> findByOrderId(String orderId);
    
    @Query(value = "select * from M_ORDER_DETAIL where order_id = ?1 limit 1", nativeQuery = true)
    MOrderDetailEntity findOneByOrderId(String orderId);
    
    @Query(value = "select orderId from MOrderDetailEntity where orderId in (?1) and productName like %?2%")
    List<String> findOrderIdByOrderIdInAndProductNameLike(List<String> orderIds, String productName);
    
    @Query(value = "select distinct(productId) from MOrderDetailEntity where orderId in (?1)")
    List<Long> findProductIdByOrderIdIn(List<String> orderIds);
    
    List<MOrderDetailEntity> findByOrderIdIn(List<String> orderIds);
    
    @Query(value = "select productId from MOrderDetailEntity where orderId = ?1")
    List<Long> findProductIdByOrderId(String orderId);

    @Query(value = "select * from M_ORDER_DETAIL  where order_id = ?1", nativeQuery = true)
    List<MOrderDetailEntity> findProductInfoByOrderId(String orderId);

    @Query(value = "SELECT * FROM M_ORDER_DETAIL  where  product_name like ?1 ", nativeQuery = true)
    List<MOrderDetailEntity> findOrderDetailInfoByName(String name);
    
    @Query(value="select p.PRODUCT_NAME,d.ORDER_ID,p.IMAGE from P_PRODUCT p, M_ORDER_DETAIL d where p.PRODUCT_ID=d.PRODUCT_ID and d.ORDER_ID in (?1)", nativeQuery = true)
    List<Object[]> findFirstProductsByOrderIdIn(List<String> orderIds);
    
    /**
     * 
    * Description: 通过ORDER_ID 来查询出该订单所拥有的商品物料总和。
    *
    * @param orderId
    * @return
     */
    
    @Query(value = "select p.goodId,sum(m.goodNum*m.num),ps.goodName,mo.orgId from MOrderDetailEntity m , PProductEntity p,PGoodsEntity ps,MOrderMainEntity mo  where mo.orderId =m.orderId and m.productId = p.productId and p.goodId = ps.goodsId and m.orderId in (?1) group by p.goodId,ps.goodName,mo.orgId")
    List<Object[]> findNumByOrderId(List<String> orderIds);
    
    /**
     * 
    * Description: 通过ORDER_ID 来查询出该订单所拥有的物料ID
    *
    * @param orderId
    * @return
     */
    @Query(value = "select p.goodId from MOrderDetailEntity m , PProductEntity p where m.productId = p.productId and m.orderId =?1 group by p.goodId ")
    List<Long> findGoodIdByOrderId(String orderId);
    
    @Query("select sum(r.num*r.goodNum) from MOrderDetailEntity r where r.orderId in(?1)")
    Long findCountByOrderIds(List<String> barcodes);
    
    @Query(value = "select * from m_order_detail where product_id = ?1 and VERIFICATION_CODE is not null", nativeQuery = true)
    List<MOrderDetailEntity> findByProductId(Long productId);
    
    @Query(value = "update m_order_detail set CODE_USED = '1' where product_id = ?1 and VERIFICATION_CODE = ?2", nativeQuery = true)
    @Modifying
    void updateCodeUsed(Long productId, String code);
    
    @Query(value = "SELECT d.PRODUCT_ID,count(distinct m.ORDER_ID) FROM m_order_main m INNER JOIN m_order_detail d ON d.ORDER_ID=m.ORDER_ID AND m.tsn is not null group by d.PRODUCT_ID ", nativeQuery = true)
    List<Object[]> getProductSaleCount();
    
    @Query(value = "select * from m_order_detail where order_id = (select order_id from m_order_detail where product_id = ?1 and VERIFICATION_CODE = ?2)", nativeQuery = true)
    List<MOrderDetailEntity> findByProductIdAndCode(Long productId, String code);
    
    @Query(value = "select * from m_order_detail where product_id = ?1 and VERIFICATION_CODE = ?2 order by detail_id desc limit 1", nativeQuery = true)
    MOrderDetailEntity getByProductIdAndCode(Long productId, String code);
    
    /**
     * Description: 查询超过10天未评价的订单中的商品
     * 
     * @param orderIds
     * @return
     */
    @Query(value = "select * from m_order_detail where status = '0' and act_date is not null and "
            + "TIMESTAMPDIFF(DAY, act_date, CURDATE()) > ?1 and order_id in (?2)", nativeQuery = true)
    List<MOrderDetailEntity> findByUnevl(Integer days, List<String> orderIds);
    
    /**
     * Description: 修改商品评价状态（0-未评价；1-已评价）
     *
     * @param detailId
     * @param status
     * 
     * @return
     */
    @Modifying
    @Query(value="update m_order_detail set status = ?2 where detail_id = ?1 ", nativeQuery = true)
    int updateStatus(Long detailId, String status);
    
    /**
     * Description: 查询订单内未评价的商品数量（0-未评价；1-已评价）
     *
     * @param orderId
     * @param productId
     * @param status
     * 
     * @return
     */
    @Query(value="select count(*) from m_order_detail where order_id = ?1 and status = ?2", nativeQuery = true)
    int countUnfeedback(String orderId, String status);
}
