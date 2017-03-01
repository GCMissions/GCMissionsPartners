package com.hengtiansoft.wrw.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.MMessageEntity;

public interface MMessageDao extends JpaRepository<MMessageEntity, Long>, JpaSpecificationExecutor<MMessageEntity> {
    
    MMessageEntity findByTitle(String title);
    
    @Query(value = "select * from M_MESSAGE where MEMBER_ID = ?1 and TYPE = ?2 order by CREATE_DATE desc limit 1", nativeQuery = true)
    MMessageEntity findLatestMessageByMemberIdAndType(Long memberId, String type);
    
    Long countByMemberIdAndReadStatus(Long memberId, String readStatus);
    
    @Modifying
    @Query(value="update MMessageEntity set readStatus=?1 where memberId=?2")
    void update2ReadStatus(String readStatus, Long memberId);
}
