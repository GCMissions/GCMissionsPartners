package com.hengtiansoft.church.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.church.entity.SFunctionInfoEntity;

public interface SFunctionInfoDao extends JpaRepository<SFunctionInfoEntity, Long> {

    @Query("select f from SFunctionInfoEntity f , SRoleFunctionEntity rf where f.functionId = rf.functionId and rf.roleId in (?1) ")
    List<SFunctionInfoEntity> findByRoleIds(Iterable<Long> roleIds);
    
    @Query(value = "SELECT * FROM FUNCTION_INFO WHERE LOCATE(?1, PARENT_IDS) > 0 OR FUNCTION_ID = 0 ORDER BY LEVEL DESC, PRIORITY DESC, FUNCTION_ID DESC", nativeQuery = true)
	List<SFunctionInfoEntity> findFunctionTreeList(String string);
    
    @Query("select t from SFunctionInfoEntity t where t.functionId in ?1")
	List<SFunctionInfoEntity> findByFunctionIdIn(List<Long> functionIds);
    
    @Query(value="SELECT function_id FROM FUNCTION_INFO where LOCATE(?1, PARENT_IDS) > 0 or function_id =?2",nativeQuery=true)
	List<Long> findByLevelId(String id, Long functionId);

}
