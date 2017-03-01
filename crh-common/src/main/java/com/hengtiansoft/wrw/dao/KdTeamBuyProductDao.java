package com.hengtiansoft.wrw.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.KdTeamBuyProductEntity;

public interface KdTeamBuyProductDao extends JpaRepository<KdTeamBuyProductEntity, Long>,
    JpaSpecificationExecutor<KdTeamBuyProductEntity>{
 
    @Modifying
    @Query(value = "update KD_TEAM_BUY_PRODUCT set STATUS = '3' where STATUS = '2' and EFFECTIVE_START_DATE <=now() and now() < EFFECTIVE_END_DATE ",nativeQuery = true)
    void updateStartStatus();
    
    @Modifying
    @Query(value = "update KD_TEAM_BUY_PRODUCT set STATUS = '4' where  now() > EFFECTIVE_END_DATE AND STATUS = '3' ",nativeQuery = true)
    void updateEndStatus();
    
    @Query(value = "select * from KD_TEAM_BUY_PRODUCT where product_id = ?1 and status = '3'", nativeQuery = true)
    List<KdTeamBuyProductEntity> findNormalTeamBuy(Long productId);
    
    @Modifying
    @Query(value = "update KD_TEAM_BUY_PRODUCT set status = ?2 where product_id = ?1 and status = ?3", nativeQuery = true)
    void updateStatus(Long productId, String status, String oldStatus);
    
    @Query(value = "select * from KD_TEAM_BUY_PRODUCT where (product_id = ?1 and EFFECTIVE_START_DATE < ?2 and EFFECTIVE_END_DATE > ?2 and status in ('2','3')) or "
            + " (product_id = ?1 and EFFECTIVE_START_DATE < ?3 and EFFECTIVE_END_DATE > ?3 and status in ('2','3')) ", nativeQuery = true)
    List<KdTeamBuyProductEntity> findSameTimeGroupBuy(Long productId, Date startDate, Date endDate);
    
    @Query(value = "select * from KD_TEAM_BUY_PRODUCT where (product_id = ?1 and EFFECTIVE_START_DATE < ?2 and EFFECTIVE_END_DATE > ?2 and status in ('2','3') and id != ?4) or "
            + " (product_id = ?1 and EFFECTIVE_START_DATE < ?3 and EFFECTIVE_END_DATE > ?3 and status in ('2','3') and id != ?4) ", nativeQuery = true)
    List<KdTeamBuyProductEntity> findSameTimeGroupBuyNotSelf(Long productId, Date startDate, Date endDate, Long id);
    
    @Query(value = "select * from KD_TEAM_BUY_PRODUCT where product_id = ?1 and status in ('2','3')", nativeQuery = true)
    List<KdTeamBuyProductEntity> findByProductId(Long productId);
    
    @Modifying
    @Query(value = "update KD_TEAM_BUY_PRODUCT set status = ?1,modify_date=now(),modify_id=?3 where id in ?2", nativeQuery = true)
    Integer updateStatus(String status, List<Long> teamBuyIds, Long userId);
    
    /**
     * 
     * Description: 通过团购活动ID，来获取参团总记录数
     *
     * @param actId
     * @return
     */
    @Query(value = "select sum(d.PRODUCT_COUNT) from kd_team_buy_product p "
            + " left join kd_team_buy_product_record r on p.ID = r.TEAM_ID "
            + " left join kd_team_buy_product_record_cycle c on c.RECORD_ID = r.RECORD_ID "
            + " left join kd_order_detail d on c.ORDER_ID = d.ORDER_ID " + " where p.ID = ?1 ", nativeQuery = true)
    Integer findCountNumByTeamId(Long actId);
}
