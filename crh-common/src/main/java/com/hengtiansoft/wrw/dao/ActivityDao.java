package com.hengtiansoft.wrw.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.ActivityEntity;
/**
 * 
* Class Name: ActivityDao
* Description: TODO
* @author chenghongtu
*
 */
public interface ActivityDao extends JpaRepository<ActivityEntity, Long>, JpaSpecificationExecutor<ActivityEntity> {
    
    ActivityEntity findByActivityName(String name);
    
    @Query("select count(a) from ActivityEntity a where a.activityName =?1")
    int findCountByActivityName(String activityName);

}
