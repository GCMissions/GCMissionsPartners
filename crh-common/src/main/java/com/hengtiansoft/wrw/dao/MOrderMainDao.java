package com.hengtiansoft.wrw.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.MOrderMainEntity;

public interface MOrderMainDao extends JpaRepository<MOrderMainEntity, String>,
        JpaSpecificationExecutor<MOrderMainEntity> {

    Long countByMemberIdAndStatus(Long memberId, String status);

    @Query(value = "select o  from MOrderMainEntity o where o.memberId = ?1 order by o.createDate desc")
    List<MOrderMainEntity> findByMemberId(Long memberId);

    @Query(value = "select orderId from MOrderMainEntity where memberId = ?1 and status != ?2")
    List<String> findOrderIdByMemberIdAndStatusNot(Long memberId, String status);

    @Query(value = "select o  from MOrderMainEntity o where o.memberId = ?1 and o.status = ?2 order by o.createDate desc")
    List<MOrderMainEntity> findByMemberIdAndStatus(Long memberId, String status);

    @Modifying
    @Query(value = "delete from MOrderMainEntity where orderId in ?1")
    void deleteOrdersById(String[] orderId);

    @Query(value = "select  count(ORDER_ID) from M_ORDER_MAIN where STATUS =?2  and ORG_ID = ?1 and ORG_ID!=0", nativeQuery = true)
    Integer getNewOrderCount(Long orgId, String status);

    @Query(value = "select  count(ORDER_ID) from M_ORDER_MAIN where STATUS =?2  and ORG_ID = ?1 ", nativeQuery = true)
    Integer getPlatNewOrderCount(Long orgId, String status);

    @Query(value = "select  ORDER_ID from M_ORDER_MAIN where STATUS =?2  and ORG_ID = ?1 and ORG_ID!=0 order by CREATE_DATE desc", nativeQuery = true)
    List<String> getNewOrders(Long orgId, String status);

    @Query(value = "select  ORDER_ID from M_ORDER_MAIN where STATUS =?2  and ORG_ID = ?1 order by CREATE_DATE desc limit 0,1", nativeQuery = true)
    String getPlatNewOrder(Long orgId, String status);

    List<MOrderMainEntity> findByStatus(String status);

    @Query(value = "select orderId from MOrderMainEntity where paymentType = ?2 and status in ?1")
    List<MOrderMainEntity> findByStatusAndPaymentType(List<String> status, String paymentType);

    @Query(value = "select orderId from MOrderMainEntity where paymentType in ?2 and status in ?1 and tsn is not null and createDate < ?3")
    List<String> findByStatusAndPaymentTypes(List<String> status, List<String> paymentType, Date date);

    @Modifying
    @Query(value = "update M_ORDER_MAIN set STATUS = ?2 where ORDER_ID = ?1", nativeQuery = true)
    void sendGoods(Integer id, String status);

    @Modifying
    @Query(value = "update M_ORDER_MAIN set STATUS = ?2 where ORDER_ID = ?1", nativeQuery = true)
    void getGoods(String id, String status);

    @Modifying
    @Query(value = "update M_ORDER_MAIN set STATUS = ?2 where ORDER_ID = ?1", nativeQuery = true)
    int updateOrderStatus(String orderId, String status);

    @Query(value = "select count(1) from M_ORDER_MAIN where MEMBER_ID = ?1 and STATUS in ?2", nativeQuery = true)
    Integer getOrderCountByStatusAndMemberId(Long id, List<String> status);

    @Modifying
    @Query(value = "update M_ORDER_MAIN set STATUS = ?2 ,MODIFY_DATE = now() ,MODIFY_ID= ?3 where ORDER_ID in ?1", nativeQuery = true)
    void updateStatus(String[] orderId, String status, Long userId);

    @Query(value = "SELECT distinct m.ORDER_ID,m.STATUS,m.PAYMENT_STATUS,m.PAY_DATE,m.TOTAL_AMOUNT,m.ACTUAL_AMOUNT,m.TOTAL_NUM,m.CREATE_DATE,m.TSN,m.SHIPMENT_TYPE,m.SHIP_AMOUNT FROM M_ORDER_MAIN m , M_ORDER_DETAIL d where m.order_id=d.order_id  and m.member_id=?1 and  (m.order_id=?2 or d.product_name like ?2) and  m.status in (?3) order by m.CREATE_DATE desc  limit ?4,?5", nativeQuery = true)
    List<Object[]> getOrderBySearch(String member, String searchParm, String[] status, int currentPage,
            int currentPageSize);

    @Query(value = "SELECT m.ORDER_ID,m.STATUS,m.PAYMENT_STATUS,m.PAY_DATE,m.TOTAL_AMOUNT,m.ACTUAL_AMOUNT,m.TOTAL_NUM,m.CREATE_DATE,m.TSN,m.SHIPMENT_TYPE ,m.SHIP_AMOUNT FROM M_ORDER_MAIN m where m.member_id=?1 and m.status in (?2) order by m.CREATE_DATE desc  limit ?3,?4", nativeQuery = true)
    List<Object[]> getOrdersByStatus(String memberId, String[] status, int currentPage, int currentPageSize);

    @Query(value = "SELECT * FROM M_ORDER_MAIN m where ORDER_ID=?1", nativeQuery = true)
    MOrderMainEntity getOrdersById(String orderId);

    @Query(value = "SELECT * FROM M_ORDER_MAIN m where ORDER_ID=?1 and MEMBER_ID=?2", nativeQuery = true)
    MOrderMainEntity getOrdersById(String orderId, Long memberId);

    @Query(value = "select distinct o.* from M_ORDER_MAIN o, M_ORDER_DETAIL d where o.order_id = d.order_id and o.member_id = ?1 and (o.order_id = ?2 or d.product_name like ?3) and o.status in ?4 and o.is_delete <> 1 "
            + "order by o.create_date desc limit ?5,?6", nativeQuery = true)
    List<MOrderMainEntity> findOrderByCriteria(Long memberId, String orderIdForSearch, String prodNameForSearch,
            String[] status, int i, int pageSize);

    @Query(value = "select count(*) from M_ORDER_MAIN o, M_ORDER_DETAIL d where o.order_id = d.order_id and o.member_id = ?1 and (o.order_id = ?2 or d.product_name like ?3) and o.status in ?4 ", nativeQuery = true)
    Long orderCount(Long memberId, String orderIdForSearch, String prodNameForSearch, String[] status);

    @Query(value = "select * from M_ORDER_MAIN o where o.member_id = ?1 and o.status in ?2 and o.is_delete <> 1 order by o.create_date desc limit ?3, ?4", nativeQuery = true)
    List<MOrderMainEntity> findOrderByCriteria(Long memberId, String[] status, int i, int pageSize);

    @Query(value = "select count(*) from M_ORDER_MAIN o where o.member_id = ?1 and o.status in ?2 ", nativeQuery = true)
    Long orderCount(Long memberId, String[] status);

    @Query("select count(1) from MOrderMainEntity where status in ?1 and orgId = ?2")
    Integer countOrderByStatusAndOrgId(List<String> orderUncompletStatus, Long orgId);

    @Query(value = "SELECT * FROM M_ORDER_MAIN  where BALANCE_FLAG = 0 and status in('4','5') limit 100 ", nativeQuery = true)
    List<MOrderMainEntity> findAllNoBalance();

    @Modifying
    @Query(value = "update M_ORDER_MAIN set  BALANCE_FLAG = ?2 where order_Id = ?1", nativeQuery = true)
    void updateOrderBalance(String orderId, String balanceFlag);

    @Query(value = "select orderId from MOrderMainEntity where status = ?1 and pressedFlag = ?2 and modifyDate < ?3")
    List<String> findPressTimeoutOrderId(String status, String pressFlag, Date modifyDate);

    @Modifying
    @Query(value = "update M_ORDER_MAIN set ORG_ID = ?1, ORG_NAME = ?2, IF_EXCEPTION = ?3, PRESSED_FLAG = ?4 , RESEND_FLAG = ?5 where ORDER_ID in (?6)", nativeQuery = true)
    void revertTimeoutOrders(Long orgId, String orgName, String ifException, String pressdFlag, String resendFlag,
            List<String> orderIds);

    @Query(value = "select M.CREATE_DATE,M.ORDER_ID,MM.MEMBER_NAME,MM.LOGIN_ID,MA.ADDRESS,PG.GOOD_CODE,PG.GOOD_NAME,PG.SPECS,SUM(MO.NUM*MO.GOOD_NUM) "
            + "from M_ORDER_MAIN  M "
            + "LEFT JOIN M_MEMBER MM ON M.MEMBER_ID =MM.ID "
            + "LEFT JOIN M_ORDER_DETAIL MO ON M.ORDER_ID =MO.ORDER_ID "
            + "LEFT JOIN P_PRODUCT PP ON MO.PRODUCT_ID = PP.PRODUCT_ID "
            + "LEFT JOIN P_GOODS PG ON PP.GOOD_ID =PG.GOODS_ID "
            + "LEFT JOIN M_ADDRESS MA ON MM.ID =MA.MEMBER_ID AND MA.`STATUS` = 1 AND MA.DEF_FLAG =1 "
            + "WHERE M.ORDER_ID in(?1) "
            + "GROUP BY M.CREATE_DATE,M.ORDER_ID,MM.MEMBER_NAME,MM.LOGIN_ID,PG.GOOD_CODE,PG.SPECS,PG.GOOD_NAME,MA.ADDRESS "
            + "ORDER BY M.CREATE_DATE DESC ", nativeQuery = true)
    List<Object[]> findAllOrder(List<String> orders);
    
    /**
     * Description: 根据订单状态查询订单信息
     *
     * @param status
     * @return
     */
    @Query(value = "select * from m_order_main where status = ?1", nativeQuery = true)
    List<MOrderMainEntity> findOrderByStatus(String status);
    
    @Query(value = "select count(1),ifnull(sum(ACTUAL_AMOUNT),0) from m_order_main where member_id = ?1 and status not in ?2", nativeQuery = true)
    List<Object[]> getOrderCountAndAmount(Long memberId, String[] removeOrderStatus);
    
    Integer countByStatusAndMemberId(String status, Long memberId);
}
