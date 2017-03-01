package com.hengtiansoft.wrw.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.SStockEntity;

public interface SStockDao extends JpaRepository<SStockEntity, Long>, JpaSpecificationExecutor<SStockEntity> {
    
    List<SStockEntity> findByOrgIdInAndGoodsIdIn(List<Long> orgIds, Collection<Long> goodsId);
    
    List<SStockEntity> findByOrgIdInAndProductIdIn(List<Long> orgIds, Collection<Long> productId);
    
    List<SStockEntity> findByOrgIdAndGoodsIdIn(Long orgId, Collection<Long> goodIds);
    
    @Query(value = "select goodsId from SStockEntity where orgId = ?1 and goodsId in (?2)")
    List<Long> findGoodIdByOrgIdAndGoodsIdIn(Long orgId, Collection<Long> goodIds);
    
    List<SStockEntity> findByOrgIdAndGoodsId(Long orgId, Long goodsId);
    
    @Query(value ="select distinct SS.ORG_ID FROM S_STOCK SS WHERE SS.SAFE_NUM > SS.STOCK_NUM AND "
            + "SS.ORG_ID IN (SELECT ORG_ID FROM S_ORG WHERE ORG_TYPE = ?1 AND PARENT_ID = ?2)", nativeQuery = true)
    List<Integer> getZWarnStockCountByP(String orgType,Long orgId);
     
    /**
    * Description: 查询物料关联商家其它物料库存
    *
    * @param productId
    * @return
    */
    @Query(value="SELECT S.ORG_ID,SUM(S.STOCK_NUM),S.GOODS_ID FROM S_STOCK S WHERE S.GOODS_ID=?1 GROUP BY S.GOODS_ID,S.ORG_ID",nativeQuery = true)
    List<Object[]> findByProductId(Long goodsId);
    
    /**
     * Description: 查询单个物料库存
     *
     * @param productId
     * @return
     */
     @Query(value="SELECT SUM(S.STOCK_NUM) FROM S_STOCK S WHERE S.GOODS_ID=?1 GROUP BY S.GOODS_ID",nativeQuery = true)
     List<Object> findStockNumByProductId(Long goodsId);
   
    
    /**
    * Description:查询商家下所有物料库存
    *
    * @param orgId
    * @return
    */
    @Query(value=" SELECT G.GOODS_ID,G.GOOD_CODE,G.GOOD_NAME,G.PRICE,S.SAFE_NUM,S.STOCK_NUM,S.STOCK_ID,S.STANDARD_NUM "
            + " FROM S_STOCK S  INNER JOIN P_GOODS G ON S.GOODS_ID=G.GOODS_ID AND S.ORG_ID=?1 ",nativeQuery=true)
    List<Object[]> findDetailByOrgId(Long orgId);
    
     @Query("select sum(s.stockNum) from SStockEntity s,SOrgEntity o where s.orgId=o.orgId and s.orgId=?1 group by s.orgId")
     List<Object> getStockNumByOrgId(Long orgId);
     
     @Modifying
     @Query(value="UPDATE S_STOCK SET STOCK_NUM=?1 WHERE STOCK_ID=?2", nativeQuery = true)
     void updateStockNumById(Long stockNum,Long stockId);
     
     @Modifying
     @Query(value="UPDATE S_STOCK SET SAFE_NUM=?1,STANDARD_NUM=?2 WHERE STOCK_ID=?3", nativeQuery = true)
     void updateStockById(Long safeNum,Long standardNum,Long stockId);
     
    
     
     @Query(value ="select sum(s.stockNum),s.productId from SStockEntity s where s.productId in(?1) group by s.productId")
	List<Object[]> findByIds(List<Long> listIds);
	
	   /**
	    * Description: 查看配送商预警数量
	    * @return
	    */
    @Query(value = "select count(1) from "
            + "(select SS.PRODUCT_ID,SS.STOCK_NUM,SS.SAFE_NUM "
            + "from S_STOCK SS left join S_ORG SO on SS.ORG_ID = SO.ORG_ID "
            + "WHERE  SO.ORG_TYPE = ?1 and SO.ORG_ID = ?2 " + "group by SS.PRODUCT_ID,SS.STOCK_NUM,SS.SAFE_NUM "
            + "having SS.STOCK_NUM < SS.SAFE_NUM ) A", nativeQuery = true)
    Integer getZzarnStockCount(String orgType, Long orgId);
    
    /**
     * Description: z端收货变更库存数量
     * @return
     */
    @Modifying
    @Query(value="UPDATE S_STOCK SET STOCK_NUM=?3 WHERE PRODUCT_ID=?2 and ORG_ID=?1", nativeQuery = true)
    void updateStockNum(Long orgId, String productId, int newStockNum);
    
    @Query("select r from SStockEntity r where r.orgId =?1 and r.goodsId in(?2) ")
    List<SStockEntity> findByOrgIdAndGoodsIdIn(Long orgId, List<Long> goodsId);
}
