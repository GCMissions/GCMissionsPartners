package com.hengtiansoft.wrw.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.PPriceEntity;
import com.hengtiansoft.wrw.entity.PProductEntity;

/**
 * Class Name: PPriceDao
 * Description: PPriceDao
 * 
 * @author zhisongliu
 */
public interface PPriceDao extends JpaRepository<PPriceEntity, Long>, JpaSpecificationExecutor<PPriceEntity> {

    PPriceEntity findByProductIdAndRegionId(Long productId, Integer regionId);
    
    List<PPriceEntity> findByProductIdInAndRegionId(Collection<Long> listIds, Integer regionId);

    List<PPriceEntity> findByProductIdInAndRegionIdAndSaleFlagNot(Collection<Long> listIds, Integer regionId, String saleFlag);

    List<PPriceEntity> findByProductIdInAndRegionIdAndSaleFlag(Collection<Long> listIds, Integer regionId, String saleFlag);
    
    @Query(value = "select productId from PPriceEntity where productId in (?1) and regionId = ?2 and saleFlag = ?3")
    List<Long> findProductIdByProductIdInAndRegionIdAndSaleFlag(Collection<Long> productIds, Integer regionId, String saleFlag);

    /**
     * Description: 商品的价格维护以及上下架情况(同一个商品在不同地区)
     *
     * @param productId
     * @return
     */
    @Query(value = "SELECT A.ID,A.NAME,A.PARENT_ID,A.LEVEL_TYPE, P.PRICE_ID,P.PRICE ,P.COST_PRICE , P.PRICE_LEVEL,P.SALE_FLAG ,SUBSTRING_INDEX(SUBSTRING_INDEX(MERGER_NAME,',',2),',',-1) as PROVINCE ,P.OPER_COSTS ,P.MORE_PRICE "
            + "  FROM S_REGION A " + " LEFT JOIN P_PRICE P ON A.ID= P.REGION_ID  AND P.PRODUCT_ID = ?1 " + " WHERE  A.OPEN_FLAG =1  OR A.ID =100000 ", nativeQuery = true)
    List<Object[]> findPriceIdByProductId(Long productId);

    @Query(value = "SELECT pp.PRODUCT_ID,pp.PRODUCT_NAME,pp.PRICE,pp.IMAGE,pp.URL,pp.NOTE,fp.FLOOR_TYPE,fp.SORT,fp.IMAGE ftimage,pr.SALE_FLAG  "
            + "FROM P_PRICE pr "
            + "INNER JOIN P_PRODUCT pp ON pr.PRODUCT_ID = pp.PRODUCT_ID "
            + "INNER JOIN (SELECT a.FLOOR_TYPE, a.PRODUCT_ID, a.SORT, a.IMAGE, a.`status`, a.REGION_ID FROM P_FLOOR a LEFT JOIN P_FLOOR b ON a.FLOOR_TYPE = b.FLOOR_TYPE AND a.PRODUCT_ID >= b.PRODUCT_ID GROUP BY a.FLOOR_TYPE, a.PRODUCT_ID, a.SORT, a.IMAGE, a.`status`, a.REGION_ID HAVING a.floor_type = ?2 AND a.`STATUS` = 1 AND a.REGION_ID = ?1 ORDER BY a.floor_type, a.SORT ASC LIMIT ?3) fp ON pr.PRODUCT_ID = fp.PRODUCT_ID  "
            + "WHERE  pr.REGION_ID = ?1 ", nativeQuery = true)
    List<Object[]> findProductIndex(Integer regionId, String floorType, Integer limit);

    /**
     * Description: 通过商品ID和地区IDs来查询商品信息
     *
     * @param productId
     * @param regionIds
     * @return
     */
    @Query("select p from PPriceEntity p where p.productId =?1 and p.regionId in (?2)")
    List<PPriceEntity> findByProductIdAndRegionIds(Long productId, List<Integer> regionIds);

    /**
     * Description: 查询商品的上下架情况，返回id和对应的count
     *
     * @param listIds
     * @return
     */
    @Query(value = "SELECT p.PRODUCT_ID,count(*) FROM P_PRICE p where p.PRODUCT_ID in (?1) and p.SALE_FLAG>0 group by p.PRODUCT_ID", nativeQuery = true)
    List<Object[]> findByProductIds(List<Long> listIds);

    /**
     * 查询所有已上架的商品ID
     * 
     * @param saleStatus
     * @return
     */
    @Query(value = "SELECT p.PRODUCT_ID FROM P_PRICE p where  p.sale_flag >0 group by p.PRODUCT_ID HAVING COUNT(*) > 0 ", nativeQuery = true)
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
    @Query(value = "SELECT p.PRODUCT_ID,p.SALE_FLAG FROM P_PRICE p where p.REGION_ID = ?2 and p.PRODUCT_ID in (?1) ", nativeQuery = true)
    List<Object[]> findByProductIdsAndRegionId(List<Long> listIds, Integer regionId);

    /**
     * 通过地区ID来查询出该地区所有的上架商品
     * 
     * @param regionId
     */
    @Query("select p.productId from PPriceEntity p where p.regionId =?1 and p.saleFlag > 0")
    List<Long> findByRegionId(Integer regionId);

    /**
     * Description: 通过productIds来筛选出符合价格区间的商品
     *
     * @param sprice
     * @param eprice
     * @param productIds
     * @return
     */
    @Query("select p.productId from PPriceEntity p where p.productId in (?3) and p.saleFlag >0 and p.regionId =?4 and p.price between ?1 and ?2 group by p.productId ")
    List<Long> findbyPriceAndProductIds(Long sprice, Long eprice, List<Long> productIds, Integer regionId);

    /**
     * Description: 通过所有的productIds来查询出所有的商品
     *
     * @param listIds
     * @return
     */
    @Query("select p from PPriceEntity p where p.saleFlag >0 and p.productId in (?1) and p.regionId =?2")
    List<PPriceEntity> findByProductIdsAndStatus(List<Long> listIds, Integer regionId);

    /**
     * Description: 查询商品的上架情况
     *
     * @param id
     * @return
     */
    @Query("select count(p) from PPriceEntity p where p.saleFlag>0 and p.productId =?1 ")
    int findShiefByProductId(Long id);

    @Query("select p from PPriceEntity p where productId in (?1) and regionId=?2")
    List<PPriceEntity> findProductsPrice(List<Long> productIds, Integer region);

    /**
     * 获取region地区所有价格，用于app搜索页面条件
     */
    @Query("select p.price,p.productId from PPriceEntity p where saleFlag <> ?2 and regionId=?1")
    List<Object[]> findPriceRange(Integer region, String saleFlag);

    @Query("select pp from PPriceEntity p,PProductEntity pp  where p.productId = pp.productId and pp.productCode in (?1) and p.regionId=?2")
    List<PProductEntity> findOnMarketProductIds(List<String> codes, Integer region);

    /**
     * Description: 通过productId来获取价格不为0商品信息
     *
     * @param id
     * @return
     */
    @Query("select p from PPriceEntity p  where p.productId = ?1 and p.price >0 ")
    List<PPriceEntity> findByProductId(Long id);

    @Query("delete PPriceEntity p where p.productId =?1 and p.regionId not in (?2) ")
    @Modifying
    void deleteByRegionIdsAndProductId(Long productId, List<Integer> regionIds);

    @Query("SELECT p FROM PPriceEntity p , MOrderDetailEntity od where p.productId = od.productId and od.orderId = ?1 and p.regionId = ?2")
    List<PPriceEntity> findByOrderAndRegion(String orderId, Integer region);

}
