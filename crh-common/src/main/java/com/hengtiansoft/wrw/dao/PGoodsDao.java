package com.hengtiansoft.wrw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.PGoodsEntity;

public interface PGoodsDao extends JpaRepository<PGoodsEntity, Long>, JpaSpecificationExecutor<PGoodsEntity> {

    @Query("select count(p) from PGoodsEntity p where p.goodName=?1 and p.goodsId <> ?2")
    int findCountByGoodsName(String goodName, Long goodsId);

    @Query("select count(p) from PGoodsEntity p where p.goodCode=?1 and p.goodsId <> ?2")
    int findCountByGoodCode(String goodCode, Long goodsId);

    PGoodsEntity findByGoodsId(Long goodsId);

    @Query("from PGoodsEntity p where p.goodsId in ?1")
    List<PGoodsEntity> findByGoodsIds(List<Long> goodsIds);

    @Modifying
    @Query("update PGoodsEntity p set p.goodCode = ?1, p.goodName = ?2, p.specs = ?3, p.price = ?4, p.modifyDate = now(), p.modifyId = ?5 where p.goodsId = ?6")
    void update(String goodCode, String goodName, String specs, Long price, Long modifyId, Long goodsId);

    @Query("select r.goodsId from PGoodsEntity r where r.goodCode = ?1 ")
    List<Long> findbyGoodsCode(String goodsCode);
    

    @Query("select r.goodsId from PGoodsEntity r where r.goodName like ?1 ")
    List<Long> findbyGoodsName(String name);

    @Query("select r.goodsId from PGoodsEntity r where r.goodCode = ?1 and r.goodName like ?2 ")
    List<Long> findbyNameAndCode(String code, String name);

    @Query("from PGoodsEntity p where p.goodCode in ?1 ")
    List<PGoodsEntity> findByGoodsCodes(List<String> goodsCodes);
    
    
    PGoodsEntity findByGoodCode(String goodCode);

}
