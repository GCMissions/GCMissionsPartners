package com.hengtiansoft.wrw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hengtiansoft.wrw.entity.MAccountEnity;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MAccountDao extends JpaRepository<MAccountEnity, Long>, JpaSpecificationExecutor<MAccountEnity> {

    MAccountEnity findByMemberIdAndStatus(Long memberId, String status);

    List<MAccountEnity> findAllByMemberIdAndStatus(Long memberId, String status);

    MAccountEnity findByMemberId(Long memberId);

    @Modifying
    @Query(value = "update M_ACCOUNT set BALANCE = BALANCE + ?1, MODIFY_DATE = now() where ACCT_ID = ?2", nativeQuery = true)
    void updateAccountBalance(Long amount, Long accountId);
    
    @Query(value = "select acc from MAccountEnity acc where acc.balance>=?1")
    List<MAccountEnity> findByBalanceStart(Long balance);
    
    @Query(value = "select acc from MAccountEnity acc where acc.balance<=?1")
    List<MAccountEnity> findByBalanceEnd(Long balance);
    
    @Query(value = "select acc from MAccountEnity acc where acc.balance>=?1 and acc.balance<=?2")
    List<MAccountEnity> findByBalanceStartAndBalanceEnd(Long balanceStart,Long balanceEnd);
   
}
