package com.hengtiansoft.church.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.church.entity.SUserRoleEntity;

public interface SUserRoleDao extends JpaRepository<SUserRoleEntity, Long>{
	
	
	@Query("delete SUserRoleEntity s where s.userId =?1  ")
	@Modifying
	void deleteByUserId(Long id);
	
	@Query("select a from SUserRoleEntity a where a.userId =?1 ")
	List<SUserRoleEntity> findByUserId(Long id);

	@Query("select a from SUserRoleEntity a where a.roleId =?1 ")
	SUserRoleEntity findByRole(String role);
}
