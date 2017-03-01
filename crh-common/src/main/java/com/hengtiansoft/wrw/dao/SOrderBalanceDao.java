package com.hengtiansoft.wrw.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.SOrderBalanceEntity;

public interface SOrderBalanceDao extends JpaRepository<SOrderBalanceEntity, String>,JpaSpecificationExecutor<SOrderBalanceEntity>{

    
    @Query(value = "SELECT o.ORG_ID,o.ORG_NAME, ifnull(SUM(ob.Z_SHIP_PROFIT),0),ifnull(SUM(ob.Z_PROD_PROFIT),0) "
            + " FROM S_ORG o LEFT JOIN S_ORDER_BALANCE ob ON o.ORG_ID = ob.ORG_ID "
            + " and ob.CREATE_DATE between ?2 and ?3"
            + " where o.org_id in ?1 "
            + " GROUP BY o.ORG_ID ORDER BY o.ORG_ID " ,nativeQuery = true)
    List<Object[]> getZFinReport(List<Long> orgIds, Date beginTime, Date endTime);

    @Query(value = " select b.ORG_ID,b.ORG_NAME, ifnull(SUM(ob.P_SHIP_PROFIT),0),ifnull(SUM(ob.P_PROD_PROFIT),0) "
            + " from S_ORG o left join S_ORDER_BALANCE ob on ob.org_id = o.ORG_ID "
            + " and ob.CREATE_DATE between ?2 and ?3 "
            + " left join S_ORG b on  o.PARENT_ID = b.ORG_ID "
            + " where b.org_id in ?1"
            + " group by b.ORG_ID order by b.ORG_ID ",nativeQuery =true)
    List<Object[]> getPFinReport(List<Long> orgIds, Date beginTime, Date endTime);

    @Query(value = "SELECT SUM(ifnull(total_amount,0)), SUM(ACTUAL_AMOUNT), SUM(COUPON_AMOUNT),SUM(ifnull(SHIP_PROFIT,0)+ifnull(PROD_PROFIT,0)),  SUM(P_SHIP_PROFIT+P_PROD_PROFIT), SUM(Z_SHIP_PROFIT+Z_PROD_PROFIT)"
            + " FROM S_ORDER_BALANCE ob "
            + " WHERE ob.CREATE_DATE BETWEEN ?2 AND ?3 AND ob.ORG_ID IN ?1",nativeQuery = true)
    Object[] sumProfit(List<Long> orgIds, Date beginTime, Date endTime);


}
