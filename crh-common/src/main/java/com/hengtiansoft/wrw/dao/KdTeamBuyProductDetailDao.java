package com.hengtiansoft.wrw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.KdTeamBuyProductDetailEntity;

public interface KdTeamBuyProductDetailDao extends JpaRepository<KdTeamBuyProductDetailEntity, Long>,
    JpaSpecificationExecutor<KdTeamBuyProductDetailEntity>{

    @Modifying
    @Query(value = "update kd_team_buy_product_detail set status = '0' where team_buy_id = ?1", nativeQuery = true)
    void updateStatusByTeamBuyId(Long teamBuyId);
    
    List<KdTeamBuyProductDetailEntity> findByTeamBuyIdAndStatus(Long teamBuyId, String status);
    
    /**
     * 
     * Description: 获取某团购活动中规格的最小价格
     *
     * @param teamId
     * @return
     */
    @Query(value = "select min(r.price) from KdTeamBuyProductDetailEntity r where r.teamBuyId =?1 ")
    Long findMinPriceByTeamId(Long teamId);

    /**
     * 
     * Description: 获取某团购活动中规格的最大价格
     *
     * @param teamId
     * @return
     */
    @Query(value = "select max(r.price) from KdTeamBuyProductDetailEntity r where r.teamBuyId =?1 ")
    Long findMaxPriceByTeamId(Long teamId);
    
    /**
     * 
    * Description:  获取单个团购活动的规格信息
    *
    * @param actId
    * @return
     */
    @Query(value ="select d.SPEC_INFO from kd_team_buy_product p ,kd_team_buy_product_detail d where p.ID =?1 and p.ID = d.TEAM_BUY_ID and d.STATUS = 1",nativeQuery=true)
    List<String> findAllByActId(Long actId);
    
    @Query(value = "select r from KdTeamBuyProductDetailEntity r where r.teamBuyId =?1 and r.status = 1 ")
    List<KdTeamBuyProductDetailEntity> findAllByTeamId(Long teamId);
}
