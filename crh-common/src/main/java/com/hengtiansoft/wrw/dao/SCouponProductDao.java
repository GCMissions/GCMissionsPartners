package com.hengtiansoft.wrw.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.PProductEntity;
import com.hengtiansoft.wrw.entity.SCouponProductEntity;
import com.hengtiansoft.wrw.entity.SCouponProductPK;

public interface SCouponProductDao extends JpaRepository<SCouponProductEntity, SCouponProductPK> {

    @Query(value = "select a.id.couponId from SCouponProductEntity a where a.id.productId = ?1")
    List<Long> findCouponIdByProductId(Long productId);

    @Query(value = "select distinct(a.id.productId) from SCouponProductEntity a where a.id.couponId in (?1) and a.id.productId in (?2)")
    List<Long> findProductIdByCouponIdInAndProductIdIn(List<Long> couponId, List<Long> productId);

    @Query(value = "select a from SCouponProductEntity a where a.id.productId in (?1)")
    List<SCouponProductEntity> findByProductIdIn(List<Long> productId);
    
    @Query(value = "select a from SCouponProductEntity a where a.id.couponId in (?1) and a.id.productId in (?2)")
    List<SCouponProductEntity> findByCouponIdInAndProductIdIn(Collection<Long> couponId, Collection<Long> productId);

    @Query("select p from PProductEntity p ,SCouponProductEntity cp where cp.id.productId = p.productId and cp.id.couponId =?1 ")
    List<PProductEntity> findProductCoupID(Long couponId);
}
