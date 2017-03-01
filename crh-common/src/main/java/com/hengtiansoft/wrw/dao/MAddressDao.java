package com.hengtiansoft.wrw.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.MAddressEntity;

public interface MAddressDao extends JpaRepository<MAddressEntity, Long> {
    
    MAddressEntity findByMemberIdAndDefFlagAndStatus(Long memberId, String defFalg, String status);
    
    List<MAddressEntity> findByMemberId(Long memberId);
    
    @Query(value="select a from MAddressEntity a where a.memberId=?1 and a.status=?2 order by a.createDate desc")
    List<MAddressEntity> findByMemberIdAndStatus(Long memberId, String status);
    
    @Query("select a from MAddressEntity a where a.status=?2 and a.memberId=?1 order by a.defFlag desc, a.modifyDate desc, a.createDate desc")
    List<MAddressEntity> findByMemberIdAndStatusOrderByStatus(Long memberId, String status);
    
	@Modifying
	@Query(value="update M_ADDRESS set MODIFY_DATE=?1, DEF_FLAG = CASE ADDRESS_ID when ?2 then ?3 else ?4 end where MEMBER_ID=?5", nativeQuery=true)
	void setDefaultAddress(Date mofidyDate, Long newDefId, String defCode, String notDefCode, Long memberId);
	
	@Query("select a from MAddressEntity a where a.status=1 and a.defFlag=?2 and a.memberId=?1")
	MAddressEntity findDefAddress(Long memberId, String defFlag);
	
	@Modifying
	@Query("update MAddressEntity set status=?2 where id=?1")
	void deleteAdress(Long id, String status);
	
	/**
     * 
     * Description: 获取指定用户的 地址数量
     *
     * @param memberId
     * @return
     */
    @Query("select count(1) from MAddressEntity a where a.memberId=?1 and a.status = 1")
    Long getAddressNum(Long memberId);
    
    @Query(value="select * from M_ADDRESS a where a.DEF_FLAG=0 and a.status=1 and a.MEMBER_ID=?1 order by CREATE_DATE desc limit 1", nativeQuery=true)
    MAddressEntity findLatestAddress(Long memberId);
    
    @Modifying
    @Query(value="update MAddressEntity set defFlag=?2 where memberId=?1 and defFlag=?3")
    void updateDefAddress2NoneDef(Long memberId, String noneDefFlag, String defFlag);
}
