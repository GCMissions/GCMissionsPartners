package com.hengtiansoft.wrw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.MPointDetailEntity;

public interface MPointDetailDao extends JpaRepository<MPointDetailEntity, Long>{
	@Query("select sum(changeValue) from MPointDetailEntity where memberId=?1 and type=1")
	Long getConsumptionAmount(Long memberId);
	
	List<MPointDetailEntity> findByOrderId(String orderId);
	
	List<MPointDetailEntity> findByOrderIdAndMemberId(String orderId,Long memberId);
}
