package com.hengtiansoft.wrw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.MPointsDetailEntity;


public interface MPointsDetailDao  extends JpaRepository<MPointsDetailEntity,Long>, JpaSpecificationExecutor<MPointsDetailEntity>{
    
    @Query("select p from MPointsDetailEntity p where p.memberId=?1 order by p.createDate desc")
    List<MPointsDetailEntity> findByMemberId(Long memberId);

    @Query("select sum(changeValue) from MPointsDetailEntity where memberId=?1 and type=1")
	Long getConsumptionAmount(Long memberId);
}