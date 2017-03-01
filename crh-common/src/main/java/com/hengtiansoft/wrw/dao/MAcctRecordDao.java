package com.hengtiansoft.wrw.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.MAcctRecordEntity;

import java.util.List;

public interface MAcctRecordDao extends JpaRepository<MAcctRecordEntity, Long>, JpaSpecificationExecutor<MAcctRecordEntity> {

    List<MAcctRecordEntity> findByAcctId(Long acctId);

    /**
     * 根据ACCT_ID获取相应账户明细，并从产品表获取明细记录中消费订单的商品及图片
     *
     * @param acctId 账户ID
     * @return
     */
    @Query(value = "select r.ORDER_ID, r.CHANGE_VALUE, r.TYPE, r.REMARK, r.CREATE_DATE, pg.NUM, pg.PRODUCT_INFO from M_ACCT_RECORD r "
            + "left join (select o.ORDER_ID, count(p.PRODUCT_NAME) as NUM, group_concat(p.PRODUCT_NAME, '[', IFNULL(p.IMAGE,''), ']') as PRODUCT_INFO "
            + "from M_ORDER_DETAIL o, P_PRODUCT p where p.PRODUCT_ID = o.PRODUCT_ID group by o.ORDER_ID) pg on r.ORDER_ID = pg.ORDER_ID "
            + "where r.ACCT_ID = ?1 order by r.CREATE_DATE desc", nativeQuery = true)
    List<Object[]> findRecordsByAcctId(Long acctId);

    /**
     * 根据ACCT_ID分页获取相应账户明细，并从产品表获取明细记录中消费订单的商品及图片
     *
     * @param acctId 账户ID
     * @param startIndex
     * @param num
     * @return
     */
    @Query(value = "select r.ORDER_ID, r.CHANGE_VALUE, r.TYPE, r.REMARK, r.CREATE_DATE, pg.NUM, pg.PRODUCT_INFO from M_ACCT_RECORD r "
            + "left join (select o.ORDER_ID, count(p.PRODUCT_NAME) as NUM, group_concat(p.PRODUCT_NAME, '[', IFNULL(p.IMAGE,''), ']') as PRODUCT_INFO "
            + "from M_ORDER_DETAIL o, P_PRODUCT p where p.PRODUCT_ID = o.PRODUCT_ID group by o.ORDER_ID) pg on r.ORDER_ID = pg.ORDER_ID "
            + "where r.ACCT_ID = ?1 order by r.CREATE_DATE desc limit ?2, ?3", nativeQuery = true)
    List<Object[]> findRecordsByAcctId(Long acctId, int startIndex, int num);

    @Query("select sum(changeValue) from MAcctRecordEntity where acctId=?1 and type='1'")
    Long getConsumptionAmount(Long acctId);
    
    @Query("select sum(changeValue) from MAcctRecordEntity where acctId=?1 and type=?2")
    Long getAcctSumAmount(Long acctId, String type);

    List<MAcctRecordEntity> findByAcctIdAndTypeIn(Long acctId, String[] recordTypes);

    /**
     * 根据ACCT_ID和明细类型获取相应账户明细，并从产品表获取明细记录中消费订单的商品及图片
     *
     * @param acctId 账户ID
     * @param recordTypes 明细类型
     * @return
     */
    @Query(value = "select r.ORDER_ID, r.CHANGE_VALUE, r.TYPE, r.REMARK, r.CREATE_DATE, pg.NUM, pg.PRODUCT_INFO from M_ACCT_RECORD r "
            + "left join (select o.ORDER_ID, count(p.PRODUCT_NAME) as NUM, group_concat(p.PRODUCT_NAME, '[', IFNULL(p.IMAGE,''), ']') as PRODUCT_INFO "
            + "from M_ORDER_DETAIL o, P_PRODUCT p where p.PRODUCT_ID = o.PRODUCT_ID group by o.ORDER_ID) pg on r.ORDER_ID = pg.ORDER_ID "
            + "where r.ACCT_ID = ?1 and r.TYPE in ?2 order by r.CREATE_DATE desc", nativeQuery = true)
    List<Object[]> findRecordsByAcctIdAndTypes(Long acctId, String[] recordTypes);

    /**
     * 根据ACCT_ID和明细类型分页获取相应账户明细，并从产品表获取明细记录中消费订单的商品及图片
     *
     * @param acctId 账户ID
     * @param recordTypes 明细类型
     * @return
     */
    @Query(value = "select r.ORDER_ID, r.CHANGE_VALUE, r.TYPE, r.REMARK, r.CREATE_DATE, pg.NUM, pg.PRODUCT_INFO from M_ACCT_RECORD r "
            + "left join (select o.ORDER_ID, count(p.PRODUCT_NAME) as NUM, group_concat(p.PRODUCT_NAME, '[', IFNULL(p.IMAGE,''), ']') as PRODUCT_INFO "
            + "from M_ORDER_DETAIL o, P_PRODUCT p where p.PRODUCT_ID = o.PRODUCT_ID group by o.ORDER_ID) pg on r.ORDER_ID = pg.ORDER_ID "
            + "where r.ACCT_ID = ?1 and r.TYPE in ?2 order by r.CREATE_DATE desc limit ?3, ?4", nativeQuery = true)
    List<Object[]> findRecordsByAcctIdAndTypes(Long acctId, String[] recordTypes, int startIndex, int num);

    @Query(value = "select count(*) from M_ACCT_RECORD where ACCT_ID = ?1", nativeQuery = true)
    long findRecordCountByAcct(Long acctId);

    @Query(value = "select count(*) from M_ACCT_RECORD where ACCT_ID = ?1 and TYPE in ?2", nativeQuery = true)
    long findRecordCountByAcct(Long acctId, String[] recordTypes);

}
