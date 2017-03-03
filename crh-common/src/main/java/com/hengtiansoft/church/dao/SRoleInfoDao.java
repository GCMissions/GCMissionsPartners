package com.hengtiansoft.church.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.church.entity.SRoleInfoEntity;

public interface SRoleInfoDao extends JpaRepository<SRoleInfoEntity, Long>,JpaSpecificationExecutor<SRoleInfoEntity>{
    
    @Query("select r from SRoleInfoEntity r , SUserRoleEntity ur where r.roleId = ur.roleId and ur.userId = ?1")
    List<SRoleInfoEntity> findByUserId(Long userId);
    
    @Query("select count(a.status) from  SRoleInfoEntity a where a.role =?1 and a.status =?2")
	int findByRoleAndStatus(String role, String code);
    @Query("select count(a) from SRoleInfoEntity a , SUserRoleEntity b , SUserEntity c where a.roleId=?1 and a.roleId=b.roleId and b.userId=c.userId and c.status =1 ")
	int findCountByRoleId(Long id);
    
    @Query("select r from SRoleInfoEntity r where r.id > 3")
	List<SRoleInfoEntity> findAllByID();
    
    @Query(value = "select * from s_role_info where role = ?1 order by role_id desc limit 1", nativeQuery = true)
    SRoleInfoEntity findByRole(String role);

    @Query(value = "select r.* from s_role_info r where r.STATUS = '1' ",nativeQuery = true)
    List<SRoleInfoEntity> findAllByStatus();
}
