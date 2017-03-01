package com.hengtiansoft.wrw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.KdTeamBuyProductRecordEntity;

public interface KdTeamBuyProductRecordDao extends JpaRepository<KdTeamBuyProductRecordEntity, Long>,
    JpaSpecificationExecutor<KdTeamBuyProductRecordEntity>{

    @Query(value = "select ktr.* from kd_team_buy_product kt join kd_team_buy_product_record ktr on kt.id = ktr.team_id "
            + " where kt.status in ('4','7') and ktr.status = '0' and ktr.is_return = '0' and ktr.record_status = '1'", nativeQuery = true)
    List<KdTeamBuyProductRecordEntity> findOverTimeTeamBuy();
    
    @Query(value = "select count(c.memberId) from KdTeamBuyProductRecordEntity r ,KdTeamBuyProductRecordCycleEntity c where r.teamId =?1 and r.recordId = c.recordId and c.status =1 ")
    int findCountByTeamId(Long id);
    
    @Query(value = "select r.RECORD_ID, r.CREATE_TEAM_ID ,sum(d.PRODUCT_COUNT) num ,m.CUST_NAME , m.CUST_IMAGE ,t.EFFECTIVE_END_DATE "
            + "  from kd_team_buy_product t "
            + " , kd_team_buy_product_record r   "
            + " ,  kd_team_buy_product_record_cycle c  "
            + " ,  m_member m   "
            + " ,  kd_order_detail d   "
            + " where t.ID=?1 and t.ID = r.TEAM_ID and r.status = ?2 and r.record_status = 1 and c.status = 1 and r.RECORD_ID = c.RECORD_ID  and m.ID =r.CREATE_TEAM_ID and  c.ORDER_ID = d.ORDER_ID "
            + " group by r.RECORD_ID, r.CREATE_TEAM_ID ,m.CUST_NAME , m.CUST_IMAGE ,t.EFFECTIVE_END_DATE  "
            + " order by num desc ,t.CREATE_DATE DESC limit ?3,?4 ", nativeQuery = true)
    List<Object[]> findRecordAndCycle(Long id, String status, int fromIndex, int endIndex);
    
    @Query(value = "SELECT IFNULL(SUM(KRC.BUY_NUM),0) FROM KD_TEAM_BUY_PRODUCT_RECORD KR LEFT JOIN "
            + " KD_TEAM_BUY_PRODUCT_RECORD_CYCLE KRC ON KR.RECORD_ID = KRC.RECORD_ID "
            + " WHERE KR.RECORD_STATUS = '1' AND KRC.`STATUS` = '1' AND KR.TEAM_ID = ?1", nativeQuery = true)
    Integer findSaleCountByTeamId(Long teamId);
}
