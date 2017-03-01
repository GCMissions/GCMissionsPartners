package com.hengtiansoft.wrw.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.MCouponEntity;

public interface MCouponDao extends JpaRepository<MCouponEntity, Long>, JpaSpecificationExecutor<MCouponEntity> {

    @Query(value = "select c from MCouponEntity c where c.memberId = ?1 order by c.createDate desc")
    List<MCouponEntity> findByMemberId(Long memberId);

    List<MCouponEntity> findByCoupCode(String coupCode);

    @Query(value = "select distinct(coupConId) from MCouponEntity where memberId = ?1")
    List<Long> findCoupConIdByMemberId(Long memberId);

    @Query(value = "select distinct(coupConId) from MCouponEntity where couponId in (?1)")
    List<Long> findCoupConIdByCouponIdIn(List<Long> couponId);

    List<MCouponEntity> findByMemberIdAndStatus(Long memberId, String status);

    List<MCouponEntity> findByMemberIdAndUsedOrderIdAndStatus(Long memberId, String orderId, String status);
    
    List<MCouponEntity> findByMemberIdAndUsedOrderId(Long memberId, String orderId);

    List<MCouponEntity> findByMemberIdAndStatusAndCoupConIdIn(Long memberId, String status, Collection<Long> coupConfIds);

    List<MCouponEntity> findByMemberIdAndCoupConIdIn(Long memberId, Collection<Long> coupConfIds);

    Long countByMemberIdAndStatusAndCoupConIdIn(Long memberId, String status, List<Long> coupConfIds);

    Long countByMemberIdAndStatus(Long memberId, String status);

    Long countByCoupConId(Long coupConId);

    Long countByMemberIdAndCoupConId(Long memberId, Long coupConId);

    Long countByCoupConIdAndMemberId(Long coupConId, Long memberId);

    @Query(value = "select coupConId, count(coupConId) from MCouponEntity where coupConId in (?1) group by coupConId")
    List<Object[]> findCountCoupConId(List<Long> coupConfIds);

    @Query(value = "select count(1) from MCouponEntity where coupConId = ?1 and status = '2'")
    Long getUsedNum(Long couponId);

    @Query(value = "select count(1) from MCouponEntity where coupConId = ?1 and memberId is not null ")
    Long getSendNum(Long couponId);

    @Query(value = "select count(1) from M_COUPONS where MEMBER_ID = ?1 and status in ?2 " + "and EFFECT_DATE < now() and INVALID_DATE > now()", nativeQuery = true)
    Integer getEffectiveCouponCount(Long memberId, List<String> status);

    @Query(value = "SELECT  a.* FROM M_COUPONS a,S_COUPON_CONFIG b where a.STATUS in (?2) and MEMBER_ID= ?1 and a.COUP_CON_ID=b.COUPON_ID order by a.CREATE_DATE desc", nativeQuery = true)
    List<MCouponEntity> findByMemberIdAndStatusIn(Long memberId, String[] status);

    @Query("select coupCode from MCouponEntity where coupCode is not null and coupConId = ?1")
    List<String> findCouponCodeList(Long couponId);

    @Query("select count(1) from MCouponEntity where memberId=?1 and status=?2")
    Integer getCouponsCountByStatus(Long memberId, String status);

    @Query(value = "select * from M_COUPONS m where m.COUP_CON_ID=?2 and m.MEMBER_ID=?1 and m.STATUS=?3 limit 1", nativeQuery = true)
    MCouponEntity findOneByCouponConfigAndMemberAndStatus(Long memberId, Long configId, String status);

    @Query("SELECT SUM(cc.value) FROM MCouponEntity c,SCouponConfigEntity cc, SCouponProductEntity cp WHERE c.coupConId = cc.couponId AND cp.id.couponId = cc.couponId AND cp.id.productId = ?1 AND c.usedOrderId = ?2")
    Long countValueByOrderIdAndProdId(Long productId, String orderId);
    
    @Modifying
    @Query(value = "update MCouponEntity set status = ?1 where coupConId = ?2 and status = ?3 ")
    void updateCouponStatus(String status, Long coupConId,String orgStatus);

    @Modifying
    @Query(value = "update MCouponEntity set status = '0' where usedOrderId=?1 and  status = '1' ")
    void releaseCouponByCancelOrder(String orderId);
    
    @Modifying
    @Query(value = "update s_coupon_config s inner join m_coupons m on m.COUP_CON_ID=s.COUPON_ID set m.`STATUS`='9'  where s.COUPON_ID=?1 and (m.`STATUS`='0' or m.`STATUS`='4') ",nativeQuery = true)
    void deleteCouponByAdmin(Long coupConId);
    
    @Modifying
    @Query(value = "update MCouponEntity set status = '0' where usedOrderId=?1 and  status = '2' ")
    void releaseCouponByReturnAllAmount(String orderId);
}
