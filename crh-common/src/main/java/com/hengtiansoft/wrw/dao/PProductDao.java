package com.hengtiansoft.wrw.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.PProductEntity;

public interface PProductDao extends JpaRepository<PProductEntity, Long>, JpaSpecificationExecutor<PProductEntity> {

    PProductEntity findByProductName(String name);

    @Query("select p from PProductEntity p where p.productCode =?1")
    PProductEntity findByProductCodeNew(String productCode);

    @Query("select count(p) from PProductEntity p where p.productCode =?1")
    int findByProductCode(String productCode);

    @Query(value = "select p.PRODUCT_ID,p.BRAND_ID,p.CATE_ID,p.TYPE_ID,p.BRAND_NAME,p.CATE_NAME,p.PRODUCT_CODE,p.PRODUCT_NAME,r.PRICE,p.IMAGE,p.NOTE,p.DESCRIPTION,p.PRICE salePrice ,p.URL,p.PROMOTION from P_PRODUCT p inner join P_PRICE r on p.PRODUCT_ID=r.PRODUCT_ID where r.SALE_FLAG <> ?4 and p.STATUS=?3 and p.PRODUCT_ID=?1 and r.REGION_ID=?2", nativeQuery = true)
    List<Object[]> findProductInfo(Long productId, Integer regionId, String status, String saleFlag);

    PProductEntity findByProductIdAndStatus(Long productId, String status);

    @Query(value = "select p.PRODUCT_ID,p.BRAND_ID,p.CATE_ID,p.TYPE_ID,p.BRAND_NAME,p.CATE_NAME,p.PRODUCT_CODE,p.PRODUCT_NAME,p.PRICE,p.IMAGE,p.NOTE,p.DESCRIPTION from P_PRODUCT p where p.SALE_STATUS=1 and p.STATUS=1 and p.PRODUCT_ID=?1", nativeQuery = true)
    List<Object[]> findProductInfo(Long productId);

    @Query("select count(p) from PProductEntity p where p.productName =?1")
    int findCountByProductName(String productName);

    List<PProductEntity> findByCateId(Long cateId);

    @Query(value = "select * from P_PRODUCT p where p.PRODUCT_ID =?1", nativeQuery = true)
    PProductEntity findByProductId(Long id);

    @Query(value = "select p.PRODUCT_ID,p.BRAND_ID,p.CATE_ID,p.TYPE_ID,p.BRAND_NAME,p.CATE_NAME,p.PRODUCT_CODE,p.PRODUCT_NAME,p.PRICE,p.IMAGE,p.NOTE,r.PRICE salePrice,f.sort from P_PRODUCT p inner join P_FLOOR f on p.PRODUCT_ID=f.PRODUCT_ID inner join P_PRICE r on p.PRODUCT_ID=r.PRODUCT_ID where p.STATUS=?5 and f.FLOOR_TYPE=?1 and f.STATUS=?5 and f.REGION_ID=?2 and r.REGION_ID=?2 and r.SALE_FLAG != ?6 order by f.SORT, p.SALE_NUM desc limit ?3, ?4", nativeQuery = true)
    List<Object[]> getIndexPageProduct(String floor, Integer region, int start, int end, String status, String saleFlag);

    @Query(value = "select p.PRODUCT_ID,p.PRODUCT_CODE,p.PRODUCT_NAME,p.PRICE,p.IMAGE,p.NOTE,p.DESCRIPTION,r.PRICE salePrice from P_PRODUCT p inner join P_PRICE r on p.PRODUCT_ID=r.PRODUCT_ID inner join P_RELATIONSHIP l on p.PRODUCT_ID=l.RELAT_ID where r.SALE_FLAG != ?4 and p.STATUS=?3 and l.PRODUCT_ID=?1 and r.REGION_ID=?2", nativeQuery = true)
    List<Object[]> getRelatedProducts(Long productId, Integer region, String status, String saleFlag);

    @Query(value = "select f.MEMBER_ID,f.ATTITUDE_LEVEL,f.SHIPMENT_LEVLE,f.SERVICE_LEVEL,f.CREATE_DATE,m.MEMBER_NAME from M_ORDER_FEEDBACK f, M_ORDER_DETAIL d, M_MEMBER m where f.MEMBER_ID=m.ID and f.ORDER_ID=d.ORDER_ID and d.PRODUCT_ID=?1 order by f.CREATE_DATE desc limit ?2, ?3", nativeQuery = true)
    List<Object[]> getProductComments(Long productId, int start, int end);

    @Query(value = "select count(1) from M_ORDER_FEEDBACK f, M_ORDER_DETAIL d, M_MEMBER m where f.MEMBER_ID=m.ID and f.ORDER_ID=d.ORDER_ID and d.PRODUCT_ID=?1", nativeQuery = true)
    int getProductCommentsCount(Long productId);

    /**
     * 
     * Description: 通过分类ID和商品ID来筛选所有的商品
     *
     * @param cateIds
     * @param productIds
     * @param status
     * @return
     */
    @Query("select p.productId from PProductEntity p where p.cateId in ?1 and p.productId in ?2 and p.status =?3 ")
    List<Long> findByProductIdAndCateId(List<Long> cateIds, List<Long> productIds, String status);

    List<PProductEntity> findByProductIdIn(Set<Long> strings);

    @Query(value = "select p.PRODUCT_ID, p.PRODUCT_NAME,p.IMAGE,p.NOTE,r.PRICE from P_PRODUCT p, P_PRICE r where p.PRODUCT_ID=r.PRODUCT_ID and r.REGION_ID=?1 and r.SALE_FLAG<>?4 and p.STATUS=?5 order by p.SALE_NUM desc limit ?2,?3", nativeQuery = true)
    List<Object[]> findPageTopSellsProducts(Integer region, int start, int end, String saleFlag, String status);

    @Query(value = " SELECT R.PRODUCT_ID,P.PRODUCT_NAME,P.NOTE,P.PRICE,P.IMAGE FROM P_PRICE R ,P_PRODUCT P WHERE R.PRODUCT_ID =P.PRODUCT_ID AND R.REGION_ID =?1 AND R.SALE_FLAG > 0 AND P.STATUS = ?2 ORDER BY P.SALE_NUM DESC limit ?3", nativeQuery = true)
    List<Object[]> findAllBySaleNumDesc(Integer regionId, String status, int end);

    /**
     * 
     * Description: 获取 浏览了该商品的用户还购买了 的商品LIST
     *
     * @param productId
     * @param limitEnd
     * @param limitStart
     * @param endOrderStatus
     * @param startOrderStatus
     * @param regionId
     * @return
     */
    @Query(value = "SELECT P.PRODUCT_ID,P.PRODUCT_NAME,P.NOTE,P.IMAGE,P.PRICE,P.SALE_NUM " + " FROM P_LIKEGUESS L "
            + " LEFT JOIN P_PRODUCT P ON L.OTHER_ID = P.PRODUCT_ID "
            + " LEFT JOIN P_PRICE PP ON L.OTHER_ID = PP.PRODUCT_ID "
            + " WHERE L.PRODUCT_ID = ?1 AND PP.SALE_FLAG<> ?2  AND PP.REGION_ID =?5 "
            + " ORDER BY L.BUY_NUM LIMIT ?3,?4 ", nativeQuery = true)
    List<Object[]> findOtherProductByProductId(Long productId, String code, Integer limitStart, Integer limitEnd,
            Integer regionId);

    List<PProductEntity> findByGoodId(Long goodId);

    List<PProductEntity> findByGoodIdAndSpecNum(Long goodId, Integer specNum);

    @Query("from PProductEntity p where p.goodId = ?1 and p.specNum = ?2 and p.productId <> ?3")
    List<PProductEntity> findByGoodIdAndSpecNum(Long goodId, Integer specNum, Long productId);
    
    @Query("select p from PProductEntity p where p.productName like %?1%")
    List<PProductEntity> findByProductNameLike(String productName);

    @Query("select count(p) from PProductEntity p where p.productName =?1 and p.productId != ?2")
    int findCountByProductNameAndId(String activityName, Long id);
    
    @Query("select p from PProductEntity p where p.productName like %?1% and p.typeId = '0' and status = '1'")
    List<PProductEntity> findActByProductNameLike(String productName);
    
    @Modifying
    @Query(value="UPDATE P_PRODUCT P SET P.STATUS =?1,P.MODIFY_DATE =?2,P.MODIFY_ID =?3 WHERE P.PRODUCT_ID IN ?4 ", nativeQuery = true)
    void deleProductByStatusAndProductId(String status,Date date,Long modifyId,List<String> productId);

    @Query(value = "select p.* from p_product p join act_stock a on p.product_id = a.product_id where a.id = ?1", nativeQuery = true)
    PProductEntity findProductByActStockId(Long actStockId);

    List<PProductEntity> findByCateIdAndStatus(Long cateId, String status);
    
    @Modifying
    @Query(value="UPDATE P_PRODUCT P SET P.SHARE_COUNT =?2 WHERE P.PRODUCT_ID = ?1 ", nativeQuery = true)
    void updateProductShareCount(Long productId,Long shareCount);
    
    @Query(value = "select * from p_product where org_id = ?1 and status = '1'", nativeQuery = true)
    List<PProductEntity> findByOrgIdAndStatus(Long orgId);
    
    /**
     * Description: 根据订单ID找到商品信息
     *
     * @param orderId
     * @return
     */
     @Query(value = "select product.* from m_order_detail detail "
             + "join p_product product on detail.product_id = product.product_id "
             + "where detail.order_id = ?1", nativeQuery = true)
     List<PProductEntity> findProductsByOrderId(String orderId);
}
