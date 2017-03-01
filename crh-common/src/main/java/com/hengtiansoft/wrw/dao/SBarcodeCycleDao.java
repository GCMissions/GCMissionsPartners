package com.hengtiansoft.wrw.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.SBarcodeCycleEntity;

/**
 * 
 * Class Name: SBarcodeCycleDao Description: 条码流水DAO
 * 
 * @author zhisongliu
 *
 */
public interface SBarcodeCycleDao extends JpaRepository<SBarcodeCycleEntity, Integer>, JpaSpecificationExecutor<SBarcodeCycleEntity> {

      @Query("select r from SBarcodeCycleEntity r where r.prefixCode =?1 and r.status =?2 ")
      SBarcodeCycleEntity findByPrefixCodeAndStatus(String prefixCode, String status);

      @Query("select count(r) from SBarcodeCycleEntity r where r.prefixCode =?1 ")
      Integer findByPrefixCode(String prefixCode);

      @Query("select count(r) from SBarcodeCycleEntity r where r.prefixCode in(?1) and r.status =?2 ")
      Integer findCountByPrefixCodesAndStatus(List<String> prefixCodes, String status);

      @Query("select r.prefixCode from SBarcodeCycleEntity r ,SBasicCodeEntity s where r.orderId in(?1) and r.status =?2 and r.prefixCode = s.prefixCode and s.prefixCode != s.packCode")
      List<String> findbyOrderIdAndStatus(List<String> orderId, String key);

      @Query("select r.orderId from SBarcodeCycleEntity r where r.prefixCode in(?1) and r.status =?2 group by r.orderId")
      List<String> findByPrefixCodesAndStatus(List<String> codeList, String key);

      @Query("select r from SBarcodeCycleEntity r where r.prefixCode in(?1) and r.status =?2 ")
      List<SBarcodeCycleEntity> findAllByPrefixCodesAndStatus(List<String> sbList, String key);

      /**
       * 
       * Description: 查询某个条码的整个流水，按时间倒叙排序
       *
       * @param prefixCode
       * @return
       */
      @Query("select r from SBarcodeCycleEntity r where r.prefixCode = ?1 order by r.createDate desc ")
      List<SBarcodeCycleEntity> findAllCycleOrderByCreateDateDesc(String prefixCode);

      /**
       * 
       * Description: 查询二维码页面的列表。需要查询用户名
       *
       * @param prefixCode
       * @param pageable
       * @return
       */
      @Query("select a.content,a.createDate,a.cycleId,a.fromId,a.fromName,a.orderId,a.source,a.toId,a.toName,a.status,b.userName from SBarcodeCycleEntity a,SUserEntity b where a.createId=b.id AND a.prefixCode=?1 ORDER BY a.createDate DESC")
      Page<Object[]> findAllByPrefixCode(String prefixCode, Pageable pageable);
      
      @Query("select a from SBarcodeCycleEntity a where a.prefixCode in(?2) and a.orderId = ?1 ")
      List<SBarcodeCycleEntity> findbyOrderIdsAndcodes(String order, List<String> codes);
      
      @Query("select a.prefixCode from SBarcodeCycleEntity a where a.orderId =?1 and a.prefixCode in(?2) and a.status =?3")
      List<String> findOrderIdAndCodesAndStatus(String orderId, List<String> codes, String key);
      
      @Query("select r from SBarcodeCycleEntity r where r.prefixCode =?1 and r.status =?2 order by r.createDate desc")
      List<SBarcodeCycleEntity> findByPrefixCodeAndStatusCreateDateDesc(String prefixCode, String key);

      
      
}
