package com.hengtiansoft.wrw.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.PProductAttrEntity;

public interface PProductAttributeDao extends JpaRepository<PProductAttrEntity, Long> {

      List<PProductAttrEntity> findByProductIdAndStatus(Long productId, String status);

      @Query(value = "select p from PProductAttrEntity p,PAttributeEntity a where p.attrId=a.attrId and p.productId=?1 and p.status=?2 order by a.sort, a.createDate desc")
      List<PProductAttrEntity> getProductAttrInOrder(Long productId, String status);

      @Query("update PProductAttrEntity p set p.status =?2,p.modifyDate =?3,p.modifyId =?4 where p.productId =?1")
      @Modifying
      void deletByProductId(Long productId, String code, Date date, Long userId);

      /**
       * Description: 通过属性值ID和商品ID来筛选出所有的商品
       *
       * @param attrValueId
       * @param productIds
       * @return
       */
      @Query("select p.productId from PProductAttrEntity p where p.status = ?1 and p.attrValueId =?2 and p.productId in(?3) ")
      List<Long> findByAttriValue(String status, Long attrValueId, List<Long> productIds);

      @Query("select p.productId from PProductAttrEntity p where p.status = ?1 and p.attrId =?2 and p.attrValue=?3 and p.productId in ?4 ")
      List<Long> findProductByAttr(String status, Long attrId, String attrValue, List<Long> productIds);

      @Query("select p from PProductAttrEntity p where p.status = ?1 and p.attrId in?2 and p.productId in ?3 ")
      List<PProductAttrEntity> findProductAttrValues(String status, List<Long> attrIds, Set<Long> productIds);

      @Query("select count(1) from PProductAttrEntity where status =?2 and attrValueId =?1")
      Integer countByAttrValueIdAndStatus(Long valueId, String code);
      
      @Query("select a from PProductAttrEntity a ,PProductEntity p where p.goodId =?1 and p.specNum =1 and p.productId =a.productId and a.status =?2 ")

      List<PProductAttrEntity> findbyGoodsId(Long goodId,String status);

}
