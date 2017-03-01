package com.hengtiansoft.wrw.dao;

import com.hengtiansoft.wrw.entity.MAcctRechargeEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by bingnichen on 5/18/2016.
 */
public interface MAcctRechargeDao extends JpaRepository<MAcctRechargeEntity, String>, JpaSpecificationExecutor<MAcctRechargeEntity> {
    List<MAcctRechargeEntity> findByAcctId(Long acctId);

    MAcctRechargeEntity findByRechargeIdAndPayStatus(String id, String status);
}
