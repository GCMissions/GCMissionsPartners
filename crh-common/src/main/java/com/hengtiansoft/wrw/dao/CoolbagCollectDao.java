package com.hengtiansoft.wrw.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.CoolbagCollectEntity;

public interface CoolbagCollectDao extends JpaRepository<CoolbagCollectEntity, Long> {

    @Query(value = "update kd_feature_collect set is_valid = '0' where goods_id = ?1 and status = '1'", nativeQuery = true)
    @Modifying
    void updateCollect(Long productId);
    
    
    @Query(value = "update kd_feature_collect set is_valid = '1' where goods_id = ?1 and status = '1'", nativeQuery = true)
    @Modifying
    void updateCollectValid(Long productId);
}
