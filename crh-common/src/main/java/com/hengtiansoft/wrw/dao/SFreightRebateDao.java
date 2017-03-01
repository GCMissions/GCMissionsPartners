package com.hengtiansoft.wrw.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hengtiansoft.wrw.entity.SFreightRebateEntity;

public interface SFreightRebateDao extends JpaRepository<SFreightRebateEntity, Long> {

    SFreightRebateEntity findByRegionId(Integer regionId);

}
