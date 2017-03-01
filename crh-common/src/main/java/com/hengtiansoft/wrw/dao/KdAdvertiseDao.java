package com.hengtiansoft.wrw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.KdAdvertiseEntity;

public interface KdAdvertiseDao extends JpaRepository<KdAdvertiseEntity, Long>,
    JpaSpecificationExecutor<KdAdvertiseEntity>{

    @Modifying
    @Query(value = "insert into kd_advertise (name,skip_type,skip_url,type,act_id,act_type,image_url,status,create_date,is_available) values (?1,?2,?3,?4,?5,?6,?7,'1',now(),?8)", nativeQuery = true)
    void addRecord(String name, String skipType, String skipUrl, String type, Long actId, String actType, String imageUrl, String isAvailable);
    
    @Modifying
    @Query(value = "update kd_advertise set name=?1,skip_type=?2,skip_url=?3,type=?4,act_id=?5,act_type=?6,image_url=?7,modify_date=now(),modify_id=?8 where id=?9", nativeQuery = true)
    void editorRecord(String name, String skipType, String skipUrl, String type, Long actId, String actType, String imageUrl, Long userId, Long id);
    
    KdAdvertiseEntity findByIdAndStatus(Long id, String status);
    
    @Query(value = "select * from kd_advertise where status = ?1 and type = ?2 order by create_date", nativeQuery = true)
    List<KdAdvertiseEntity> findByStatusAndType(String status, String type);
    
    @Modifying
    @Query(value = "update kd_advertise set status = '0' where id = ?1", nativeQuery = true)
    Integer deleteAdvertise(Long advertiseId);
    
    @Modifying
    @Query(value = "update kd_advertise set is_available = ?1 where type = ?2 and status = '1'", nativeQuery = true)
    Integer switchStatus(String isAvailable, String type);
    
    List<KdAdvertiseEntity> findBySkipTypeAndActIdAndStatus(String skipType, Long actId, String status);
    
    @Modifying
    @Query(value = "update kd_advertise set STATUS = ?1 where ACT_TYPE = ?2 and ACT_ID in ?3 ", nativeQuery = true)
    Integer updateAdvertiseStatus(String status, String actType,List<Long> actIds);
    
    @Modifying
    @Query(value = "update kd_advertise set STATUS = ?1 where LOCATE(?2,SKIP_URL)> 0 ", nativeQuery = true)
    Integer updateAdvertiseStatus(String status,String specificStr);
}
