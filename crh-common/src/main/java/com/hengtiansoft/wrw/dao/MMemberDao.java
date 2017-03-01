package com.hengtiansoft.wrw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.MMemberEntity;

public interface MMemberDao extends JpaRepository<MMemberEntity, Long>, JpaSpecificationExecutor<MMemberEntity> {

    List<MMemberEntity> findByWebToken(String token);

    List<MMemberEntity> findByWechatToken(String token);

    List<MMemberEntity> findByAppToken(String token);

    List<MMemberEntity> findByLoginIdAndPasswordAndStatus(String loginId, String password, String status);

    @Modifying
    @Query(value = "UPDATE M_MEMBER SET PASSWORD=?2 WHERE ID=?1", nativeQuery = true)
    void resetPwd(Long id, String pwd);

    @Query("select t from MMemberEntity t where t.loginId=?1 and t.status =?2")
    MMemberEntity findByLoginId(String loginId, String status);

    @Query(value = "select * from  M_MEMBER t  WHERE t.email=?1 and t.status =?2", nativeQuery = true)
    MMemberEntity findByEmailAndStatus(String email, String status);
    
    List<MMemberEntity> findByLoginIdAndStatus(String loginId, String code);

    @Modifying
    @Query(value = "UPDATE M_MEMBER SET PASSWORD=?2 WHERE LOGIN_ID=?1", nativeQuery = true)
    void updatePwdByPhone(Long phone, String pwd);

    @Modifying
    @Query(value = "UPDATE MMemberEntity SET password=?2 WHERE loginId=?1")
    void updatePwdByPhone(String phone, String pwd);

    MMemberEntity findByLoginId(String loginId);

    @Query("select userId from MMemberEntity where loginId = ?1 ")
    Integer findMemberIdByLoginId(String memberPhone);

    @Query("select m.id from MMemberEntity m where m.memberName =?1")
    List<Integer> findMemberIdByName(String memberName);

    @Query(value = "select * from  M_MEMBER t  WHERE t.email=?1 and t.status =?2 and t.id!=?3", nativeQuery = true)
    MMemberEntity findByEmailAndStatusAndId(String email, String code, Long userId);
    
    @Query("select m from MMemberEntity m where m.status='1' ")
    List<MMemberEntity> findAllMember();
    
    @Query(value = "select m.* from  M_MEMBER m where m.LOGIN_ID in ?1 and m.status = '1' ",nativeQuery = true)
    List<MMemberEntity> findAllByPhones(List<String> memberPhones);
    
    MMemberEntity findByUserId(Long userId);

    /**
     * Description: 清除 绑定过该微信号的用户 的绑定关系
     *
     * @param openId
     */
     @Modifying
     @Query(value = "UPDATE m_member SET OPENID = null WHERE OPENID = ?1", nativeQuery = true)
     void clearOpenID(String openId);
     
     
     /**
    * Description: 根据OPEN_ID查询用户
    *
    * @param openId
    * @return
    */
    List<MMemberEntity> findByOpenId(String openId);
     
    
    Long countByStatus(String status);
}
