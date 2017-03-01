package com.hengtiansoft.wrw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.SRoleFunctionEntity;

public interface SRoleFunctionDao extends  JpaRepository<SRoleFunctionEntity, Long>{
	
	@Query("select t from SRoleFunctionEntity t where t.roleId =?1")
	List<SRoleFunctionEntity> findAll(Long id);
	
	@Modifying
	@Query(value="delete from SRoleFunctionEntity t where t.roleId=?1")
	void deleteByRoleId(Long roleId);

}
