package com.hengtiansoft.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.KdTwentyFourHoursEntity;
/**
 * 
* Class Name: TwentyFourHoursRepository
* Description: 24小时活动状态变更
* @author chengchaoyin
*
 */
public interface TwentyFourHoursRepository extends JpaRepository<KdTwentyFourHoursEntity, Long> {

    
    @Modifying
    @Query(value = "update kd_twenty_four_hours set STATUS = '3' where STATUS = '2' and EFFECTIVE_START_DATE <=now() and now() < EFFECTIVE_END_DATE and DEL_FLAG = '1' ",nativeQuery = true)
    Integer updateBargainProductInStatus();
    
    @Modifying
    @Query(value = "update kd_twenty_four_hours set STATUS = '4' where STATUS = '3' and  now() > EFFECTIVE_END_DATE and DEL_FLAG = '1' ",nativeQuery = true)
    Integer updateBargainProductOutStatus();
}
