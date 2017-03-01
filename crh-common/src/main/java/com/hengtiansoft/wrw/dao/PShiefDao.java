package com.hengtiansoft.wrw.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.PShiefEntity;
import com.hengtiansoft.wrw.entity.PProductEntity;

/**
 * Class Name: PShiefDao
 * Description: PShiefDao
 * 
 * @author zhisongliu
 */
public interface PShiefDao extends JpaRepository<PShiefEntity, Long>, JpaSpecificationExecutor<PShiefEntity> {

    PShiefEntity findByProductIdAndRegionId(Long productId, Integer regionId);
    
    List<PShiefEntity> findByProductIdInAndRegionId(Collection<Long> listIds, Integer regionId);

    List<PShiefEntity> findByProductIdInAndRegionIdAndSaleFlagNot(Collection<Long> listIds, Integer regionId, String saleFlag);

    List<PShiefEntity> findByProductIdInAndRegionIdAndSaleFlag(Collection<Long> listIds, Integer regionId, String saleFlag);
    
    @Query(value = "select productId from PShiefEntity where productId in (?1) and regionId = ?2 and saleFlag = ?3")
    List<Long> findProductIdByProductIdInAndRegionIdAndSaleFlag(Collection<Long> productIds, Integer regionId, String saleFlag);

    /**
     * Description: 商品的价格维护以及上下架情况(同一个商品在不同地区)
     *
     * @param productId
     * @return
     */
    @Query(value = "SELECT A.ID,A.NAME,A.PARENT_ID,A.LEVEL_TYPE, P.SALE_FLAG ,SUBSTRING_INDEX(SUBSTRING_INDEX(MERGER_NAME,',',2),',',-1) as PROVINCE , p.SHIEF_DATE,p.UNSHIEF_DATE"
            + "	 FROM S_REGION A  LEFT JOIN P_SHIEF P ON A.ID= P.REGION_ID  AND P.PRODUCT_ID = ?1 " + " WHERE (level_type=2) or A.ID =100000 ", nativeQuery = true)
    List<Object[]> findPriceIdByProductId(Long productId);

    /**
     * Description: 通过商品ID和地区IDs来查询商品信息
     *
     * @param productId
     * @param regionIds
     * @return
     */
    @Query("select p from PShiefEntity p where p.productId =?1 and p.regionId in (?2)")
    List<PShiefEntity> findByProductIdAndRegionIds(Long productId, List<Integer> regionIds);

    /**
     * Description: 查询商品的上下架情况，返回id和对应的count
     *
     * @param listIds
     * @return
     */
    @Query(value = "SELECT p.PRODUCT_ID,count(*) FROM P_SHIEF p where p.PRODUCT_ID in (?1) and p.SALE_FLAG=1 group by p.PRODUCT_ID", nativeQuery = true)
    List<Object[]> findByProductIds(List<Long> listIds);

    
    
    /**
     * 查询所有已上架的商品ID
     * 
     * @param saleStatus
     * @return
     */
    @Query(value = "SELECT p.PRODUCT_ID FROM P_SHIEF p where  p.sale_flag =1 group by p.PRODUCT_ID HAVING COUNT(*) > 0 ", nativeQuery = true)
    List<Long> findByShelf();

    /**
     * 查询所有未上架的商品ID (原理：将所有上架商品的ID，去product表中not in一把,)
     * 
     * @param saleStatus
     * @return
     */
    @Query(value = "SELECT p.PRODUCT_ID FROM P_PRODUCT p where  p.PRODUCT_ID NOT IN (?1) AND p.status =?2 ", nativeQuery = true)
    List<Long> findbyUnShelf(List<Long> productIds, String status);

    /**
     * Description: 通过地区ID和商品IDs来查询该地区的商品的上架情况
     *
     * @param listIds
     * @param regionId
     * @return
     */
    @Query(value = "SELECT p.PRODUCT_ID,p.SALE_FLAG FROM P_SHIEF p where p.REGION_ID = ?2 and p.PRODUCT_ID in (?1) ", nativeQuery = true)
    List<Object[]> findByProductIdsAndRegionId(List<Long> listIds, Integer regionId);

    /**
     * 通过地区ID来查询出该地区所有的上架商品
     * 
     * @param regionId
     */
    @Query("select p.productId from PShiefEntity p where p.regionId =?1 and p.saleFlag > 0")
    List<Long> findByRegionId(Integer regionId);

//    /**
//     * Description: 通过productIds来筛选出符合价格区间的商品
//     *
//     * @param sprice
//     * @param eprice
//     * @param productIds
//     * @return
//     */
//    @Query("select p.productId from PShiefEntity p where p.productId in (?3) and p.saleFlag >0 and p.regionId =?4 and p.price between ?1 and ?2 group by p.productId ")
//    List<Long> findbyPriceAndProductIds(Long sprice, Long eprice, List<Long> productIds, Integer regionId);

    /**
     * Description: 通过所有的productIds来查询出所有的商品
     *
     * @param listIds
     * @return
     */
    @Query("select p from PShiefEntity p where p.saleFlag >0 and p.productId in (?1) and p.regionId =?2")
    List<PShiefEntity> findByProductIdsAndStatus(List<Long> listIds, Integer regionId);

    /**
     * Description: 查询商品的上架情况
     *
     * @param id
     * @return
     */
    @Query("select count(p) from PShiefEntity p where p.saleFlag>0 and p.productId =?1 ")
    int findShiefByProductId(Long id);

    @Query("select p from PShiefEntity p where productId in (?1) and regionId=?2")
    List<PShiefEntity> findProductsPrice(List<Long> productIds, Integer region);

    /**
     * 获取region地区所有价格，用于app搜索页面条件
     */
//    @Query("select p.price,p.productId from PShiefEntity p where saleFlag <> ?2 and regionId=?1")
//    List<Object[]> findPriceRange(Integer region, String saleFlag);

    @Query("select pp from PShiefEntity p,PProductEntity pp  where p.productId = pp.productId and pp.productCode in (?1) and p.regionId=?2")
    List<PProductEntity> findOnMarketProductIds(List<String> codes, Integer region);

    /**
     * Description: 通过productId来获取价格不为0商品信息
     *
     * @param id
     * @return
     */
//    @Query("select p from PShiefEntity p  where p.productId = ?1 and p.price >0 ")
//    List<PShiefEntity> findByProductId(Long id);

    @Query("delete PShiefEntity p where p.productId =?1 and p.regionId not in (?2) ")
    @Modifying
    void deleteByRegionIdsAndProductId(Long productId, List<Integer> regionIds);

    @Query("delete PShiefEntity p where p.productId =?1 ")
    @Modifying
    void deleteByProductId(Long productId);
    
    @Query("SELECT p FROM PShiefEntity p , MOrderDetailEntity od where p.productId = od.productId and od.orderId = ?1 and p.regionId = ?2")
    List<PShiefEntity> findByOrderAndRegion(String orderId, Integer region);
    
    @Query(value = "SELECT PRODUCT_ID,(SELECT C.CATE_NAME FROM P_CATEGORY C WHERE P.CATE_ID = C.CATE_ID) FIRST_CATE_NAME, (SELECT C.CATE_NAME  FROM P_CATEGORY C WHERE C.CATE_ID =(SELECT C.PARENT_ID FROM P_CATEGORY C WHERE P.CATE_ID = C.CATE_ID AND C.PARENT_ID != 0)) SECOND_CATE_NAME,P.CATE_ID from P_PRODUCT P where  p.PRODUCT_ID in (?1)", nativeQuery = true)
    List<Object[]> findCateNameByProductIds(List<Long> listIds);

    @Query(value = "select * from p_shief where product_id = ?1 and sale_flag = ?2 order by price_id desc limit 1", nativeQuery = true)
    PShiefEntity findByProductIdAndStatus(Long productId, String saleFlag);

    @Query(value = "update PShiefEntity set saleFlag=?1 where saleFlag = '1' and  NOW()>=unShiefDate  ")
    @Modifying
    void unShiefProduct(String sale_flag);

    @Query(value = "update PShiefEntity set saleFlag=?1 where saleFlag = '2' and  NOW()>=shiefDate  ")
    @Modifying
    void shiefProduct(String key);
    
    List<PShiefEntity> findByProductIdAndSaleFlag(Long productId, String saleFlag);
    
    @Query(value = "select count(1) from p_shief where product_id = ?1 and sale_flag = '1'", nativeQuery = true)
    int findProductShief(Long productId);
}
