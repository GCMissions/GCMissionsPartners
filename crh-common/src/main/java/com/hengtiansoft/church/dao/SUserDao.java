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
* @author taochen
*
 */
public interface SUserDao extends JpaRepository<SUserEntity, Long>, JpaSpecificationExecutor<SUserEntity> {

    /**
     * 
    * Description: Find all users by token
    *
    * @param token
    * @return
     */
    List<SUserEntity> findByWebToken(String token);

    /**
     * 
    * Description: Verify that the user exists, through loginId, password, and status
    *
    * @param loginId
    * @param password
    * @param status
    * @return
     */
    List<SUserEntity> findByLoginIdAndPasswordAndStatus(String loginId, String password, String status);

    /**
     * 
    * Description: Verify login ID and status
    *
    * @param loginId
    * @param status
    * @return
     */
    SUserEntity findByLoginIdAndStatus(String loginId, String status);

    /**
     * 
    * Description: Verify that the current loginId exists
    *
    * @param loginId
    * @return
     */
    @Query("select count(t) from SUserEntity t where t.loginId =?1 and t.status != '3'")
    int findbyLoginIdAndOrgId(String loginId);

    /**
     * 
    * Description: Use orgId to query the user's information
    *
    * @param orgId
    * @return
     */
    SUserEntity findByOrgId(Long orgId);

    /**
     * 
    * Description: Use loginId to query the user's information
    *
    * @param loginId
    * @return
     */
    SUserEntity findByLoginId(String loginId);

    SUserEntity findOneByOrgId(Long orgId);
    
    @Modifying
    @Query(value = "update user set status = '3' where org_id = ?1", nativeQuery = true)
    void deleteUser(Long orgId);

    @Query(value = "select * from user where email = ?1 and status != ?2", nativeQuery = true)
    List<SUserEntity> findByEmailAndNotEqStatus(String email, String status);
}
