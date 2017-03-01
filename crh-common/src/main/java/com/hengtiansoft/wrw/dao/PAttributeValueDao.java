package com.hengtiansoft.wrw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.PAttrValueEntity;

public interface PAttributeValueDao extends JpaRepository<PAttrValueEntity, Long> {

    @Query("select p from PAttrValueEntity p where p.attrId = ?1  and p.status =?2 ")
    List<PAttrValueEntity> findByAttrIdAndStatus(Long id, String status);

    @Modifying
    @Query("update PAttrValueEntity p set p.status = ?2 where p.attrId = ?1 ")
    void updateById(Long attrId, String status);

    @Modifying
    @Query("update PAttrValueEntity p set p.status = ?2 where p.attrValueId in ?1 ")
    void updateByValueId(List<Long> attrValueId, String status);

    @Query("select p from PAttrValueEntity p where p.attrId in ?1  and p.status =?2 ")
    List<PAttrValueEntity> findByAttrIdsAndStatus(List<Long> listAttrIds, String code);

    @Query("SELECT av.attrValueId FROM PAttrValueEntity av WHere av.attrId = ?1  and av.status =?2")
    List<Long> findIdByAttrId(Long attrId,String status);

}
