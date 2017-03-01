package com.hengtiansoft.task.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.MCouponEntity;

public interface IMCouponRepository extends JpaRepository<MCouponEntity, Long> {

    List<MCouponEntity> findByStatusAndUsedOrderIdIn(String status, List<String> orderIds);
    
    @Modifying
    @Query(value = "update MCouponEntity set status = ?1 where  invalidDate < now() ")
    void updateCouponStatus(String status);
}
