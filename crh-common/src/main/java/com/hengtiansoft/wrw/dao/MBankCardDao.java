package com.hengtiansoft.wrw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hengtiansoft.wrw.entity.MBackCardEntity;

public interface MBankCardDao extends JpaRepository<MBackCardEntity, Long> {
            List<MBackCardEntity> findBymerCustId(Long merCustId);
}
