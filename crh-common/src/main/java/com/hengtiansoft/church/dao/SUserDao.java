package com.hengtiansoft.church.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.church.entity.SUserEntity;

/**
 * 
* Class Name: SUserDao
* Description: userDao
* @author zhisongliu
*
 */
public interface SUserDao extends JpaRepository<SUserEntity, Long>, JpaSpecificationExecutor<SUserEntity> {

    /**
     * 
    * Description: 通过token查询出所有用户
    *
    * @param token
    * @return
     */
    List<SUserEntity> findByWebToken(String token);

    /**
     * 
    * Description: 通过用户登录ID，密码，以及状态验证用户是否存在
    *
    * @param loginId
    * @param password
    * @param status
    * @return
     */
    List<SUserEntity> findByLoginIdAndPasswordAndStatus(String loginId, String password, String status);

    /**
     * 
    * Description: 验证登录ID和状态
    *
    * @param loginId
    * @param status
    * @return
     */
    SUserEntity findByLoginIdAndStatus(String loginId, String status);

    /**
     * 
    * Description: 验证当前登录loginId是否存在
    *
    * @param loginId
    * @return
     */
    @Query("select count(t) from SUserEntity t where t.loginId =?1")
    int findbyLoginIdAndOrgId(String loginId);

    /**
     * 
    * Description: 通过orgId来查询用户信息
    *
    * @param orgId
    * @return
     */
    SUserEntity findByOrgId(Long orgId);

    /**
     * 
    * Description: 通过登录ID来查询用户信息 
    *
    * @param loginId
    * @return
     */
    SUserEntity findByLoginId(String loginId);

    SUserEntity findOneByOrgId(Long orgId);
    
    @Modifying
    @Query(value = "update s_user set status = '3' where org_id = ?1", nativeQuery = true)
    void deleteUser(Long orgId);

}
