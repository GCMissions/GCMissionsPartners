package com.hengtiansoft.wrw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.SOrgEntity;

public interface SOrgDao extends JpaRepository<SOrgEntity, Long>, JpaSpecificationExecutor<SOrgEntity> {

    @Query(value = "select orgId from SOrgEntity where region in ?1 and orgType = ?2")
    List<Long> findOrgIdByRegionInAndOrgType(List<Integer> regionId, String type);

    /**
     * Description: 查询商家库存量
     *
     * @param productId
     * @return
     */
    @Query("select s.orgId,sum(s.stockNum) from SStockEntity s,SOrgEntity o where s.orgId=o.orgId and s.orgId=?1 group by s.orgId")
    List<Object[]> findByOrgId(Long orgId);

    @Query(value = "select orgId from SOrgEntity where orgCode = ?1")
    Long findOrgIdByOrgCode(String orgCode);

    @Query("select count(1) from SOrgEntity where   status = ?1 and parentId = ?2 ")
    Integer countAllUsedOrg(String status, Long parentId);

    List<SOrgEntity> findByParentIdAndOrgType(Long parentId, String orgType);

    SOrgEntity findByOrgName(String orgName);

    @Query(value = "select orgId from SOrgEntity where parentId = ?1")
    List<Long> getOrgIdByPId(Long orgId);

    @Query("select s.orgName from SOrgEntity s where s.orgId = ?1")
    String findOrgNameByOrgId(Long orgId);

    @Query("from SOrgEntity s where s.orgType = 'P' and s.status = 1")
    List<SOrgEntity> findAllP();

    @Query("select orgId from SOrgEntity where orgName like %?1%")
    List<Long> findByOrgNameLike(String orgName);

    @Query("from SOrgEntity s where s.orgId in (select o.parentId from SOrgEntity o where o.orgId in ?1 and o.orgType = 'Z')")
    List<SOrgEntity> findPNameByZOrgId(List<Long> orgIds);

    @Query(value = "select * from S_ORG where parent_id = ?1", nativeQuery = true)
    List<SOrgEntity> findZOrgByPId(Long pId);

    @Query("SELECT orgId FROM SOrgEntity WHERE parentId IN ?1")
    List<Long> findOrgIdByParentId(List<Long> orgIds);

    @Query("select orgId from SOrgEntity where orgType = ?1")
    List<Long> findOrgIdByType(String orgType);

    @Query("select orgId from SOrgEntity where orgType = ?1 and orgName like ?2")
    List<Long> findOrgIdByTypeLikeName(String orgType, String name);

    SOrgEntity findByOrgIdAndStatus(Long orgId, String status);

    @Query("select s from SOrgEntity s where s.orgType=?1 order by s.createDate desc")
    List<SOrgEntity> findOrgNew(String orgType);
    
    @Query("from SOrgEntity a where a.orgId in ?1")
    List<SOrgEntity> findByOrgId(List<Long> orgIds);
    
    List<SOrgEntity> findByOrgType(String orgType);

    List<SOrgEntity> findByStatus(String status);
    
    @Query(value = "select s from SOrgEntity s where s.orgName = ?1 and s.status = '1'")
    List<SOrgEntity> getByOrgName(String orgName);
    
    @Query(value = "select s from SOrgEntity s where s.orgName = ?1 and s.status = '1' and s.orgCode != ?2")
    List<SOrgEntity> getByOrgNameExceptSelf(String orgName, String orgCode);
    
    SOrgEntity findByOrgCode(String orgCode);
    
    @Modifying
    @Query(value = "update s_org set org_name=?1,phone=?2,contact=?3,businesser=?4,registration_license=?5,service_phone=?6,introduce=?7,modify_date=now() where org_code=?8", nativeQuery = true)
    void updateOrg(String orgName, String phone, String contact, String businesser, String registrationLicense, String servicePhone, String introduce, String orgCode);
    
    @Modifying
    @Query(value = "update s_org set status = '0' where org_code = ?1", nativeQuery = true)
    void updateStatus(String orgCode);
}
