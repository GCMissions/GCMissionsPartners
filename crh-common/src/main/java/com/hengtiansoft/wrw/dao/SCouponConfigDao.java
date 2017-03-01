package com.hengtiansoft.wrw.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.SCouponConfigEntity;

public interface SCouponConfigDao extends JpaRepository<SCouponConfigEntity, Long>, JpaSpecificationExecutor<SCouponConfigEntity> {

    SCouponConfigEntity findByCouponName(String name);
    
    @Query(value = "select distinct(couponId) from SCouponConfigEntity where type = ?1 and mobileUse = ?2 and status = ?3")
    List<Long> findCouponIdByTypeAndMobileUseAndStatus(String type, String mobileUse, String status);

    @Query(value = "select couponId from SCouponConfigEntity where webUse = ?1 and status = ?2")
    List<Long> findCouponIdByWebUseAndStatus(String webUse, String status);
    
    @Query(value = "select couponId from SCouponConfigEntity where couponId in (?1) and mobileUse = ?2 and limitValue <= ?3")
    List<Long> findCouponIdByCouponIdInAndMobileUseAndLimitValue(List<Long> couponIds, String mobileUse, Long limitValue);
    
    @Query(value = "select distinct(couponId) from SCouponConfigEntity where mobileUse = ?1 and status = ?2")
    List<Long> findCouponIdByMobileUseAndStatus(String mobileUse, String status);
    
    @Query(value = "select distinct(couponId) from SCouponConfigEntity where mobileUse = ?1 and status = ?2 and effectDate < ?3 and invalidDate > ?3")
    List<Long> findValidCouponIdBydMobileUseAndStatus(String mobileUse, String status, Date date);

    @Query("SELECT cc FROM SCouponConfigEntity cc , SRechargeCouponEntity rc where cc.couponId = rc.id.couponId ")
    List<SCouponConfigEntity> findAllByRecharge();

    @Query("SELECT cc FROM SCouponConfigEntity cc , SRechargeCouponEntity rc where cc.couponId = rc.id.couponId and rc.id.configId = ?1 ")
    List<SCouponConfigEntity> findAllByRechargeConfig(Long configId);
    
    @Query(value = "select couponId from SCouponConfigEntity where couponId in (?1) and webUse = ?2 and limitValue <= ?3")
    List<Long> findCouponIdByCouponIdInAndWebUseAndLimitValue(List<Long> couponIds, String webUse, Long limitValue);

    @Query(value = "select a from SCouponConfigEntity a where TYPE=1 and STATUS = 1 and WEB_USE= 1 and BEGIN_DATE<=now() and END_DATE >= now() and INVALID_DATE>now() and SEND_NUM<TOTAL_NUM and coupon_id not in (select coupConId from MCouponEntity where memberId = ?1)")
    List<SCouponConfigEntity> findWebCouponGetableByMemberID(Long memberId);
    
    @Query(value = "select c from SCouponConfigEntity c where couponId in (?1) and mobileUse = ?2 and status = ?3")
    List<SCouponConfigEntity> findCouponByCouponIdInAndMobileUseAndStatus(Collection<Long> couponIds, String mobileUse, String status);
    
    @Query(value = "select s from SCouponConfigEntity s where couponId in (?1)  and status='1' ")
    List<SCouponConfigEntity> findCouponByCouponIds(List<Long> couponIds);
    
    @Modifying
    @Query("update SCouponConfigEntity s set s.status = ?1,s.modifyId = ?3,s.modifyDate = now() where s.couponId = ?2")
    void updateCouponStatus(String status,Long couponId,Long modifyId);
    
    @Modifying
    @Query(value = "update SCouponConfigEntity set status = ?1 where  invalidDate < now() ")
    void updateCouponConfigStatus(String status);
    
    /**
     * Description: 支付完成后找到可以奖励的优惠券
     *
     * @param cateId
     * @param actualAmount
     * @return
     */
     @Query(value = "select * from s_coupon_config where STATUS = '1' AND "
             + "(FETCH_TYPE = 1 or FETCH_TYPE_DETAIL REGEXP ?1) "
             + "AND FETCH_VALUE_LIMIT <= ?2 "
             + "order by VALUE DESC limit 1", nativeQuery = true)
     SCouponConfigEntity findCoupons(String cateId, Long actualAmount);
    
}
