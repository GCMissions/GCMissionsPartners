package com.hengtiansoft.wrw.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.SOrderRewardEntity;

public interface SOrderRewardDao extends JpaRepository<SOrderRewardEntity, Long>, JpaSpecificationExecutor<SOrderRewardEntity> {

    @Query("SELECT SUM(amount) FROM SOrderRewardEntity where orgId = ?1 and type = ?4 and createDate between ?2 and ?3")
    Long sumAmountByOrgIdAndType(Long orgId, Date beginTime, Date endTime, String type);

    @Query(value = "select ifnull(SUM(AMOUNT),0) from "
            + " S_ORG o left join  S_ORDER_REWARD re on o.ORG_ID = re.org_id and re.REWARD_DATE between ?2 and ?3 and type = ?4 "
            + " where o.org_id in ?1 " + " group by  o.ORG_ID order by o.ORG_ID ", nativeQuery = true)
    List<BigDecimal> sumAmountByOrgIdAndType(List<Long> orgIds, Date beginTime, Date endTime, String type);

    @Query(value = "select sum(amount) from SOrderRewardEntity where orderId = ?1 and type = ?2")
    Long sumAmountByOrderIdAndType(String orderId, String type);

    @Query(value = "SELECT SUM(AMOUNT), TYPE "
            + " FROM S_ORDER_REWARD WHERE REWARD_DATE BETWEEN ?2 AND ?3 AND ORG_ID IN ?1 "
            + " GROUP BY TYPE" ,nativeQuery = true)
    List<Object[]> sumReward(List<Long> orgIds, Date beginTime, Date endTime);

}
