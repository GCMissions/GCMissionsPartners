package com.hengtiansoft.wrw.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.SShipmentOrderEntity;

public interface SShipmentOrderDao extends JpaRepository<SShipmentOrderEntity, String>, JpaSpecificationExecutor<SShipmentOrderEntity> {
    @Query(value = "select  count(ORDER_ID) from S_SHIPMENT_ORDER where STATUS in (0) and ((SHIPMENT_ID = ?1) or (receiving_id in (select receiving_id from S_SHIPMENT_ORDER r where r.SHIPMENT_ID = '0' and receiving_id in (select org_id from S_ORG where ORG_TYPE= 'Z' and  PARENT_ID = ?1)) ))", nativeQuery = true)
    Integer getPShipmentCount(Long shipmentId);

    @Query(value = "select  count(ORDER_ID) from S_SHIPMENT_ORDER where STATUS in (1) and RECEIVING_ID = ?1 and SHIPMENT_ID = ?2", nativeQuery = true)
    Integer getSomebodyReceivingCount(Long receivingId, Long shipmentId);

    @Query(value = "select s.order_id,s.total_num,o.org_code,o.org_name receiving_name,s.create_date,s.STATUS,o.contact,o.phone,o.address  from S_SHIPMENT_ORDER s  left join S_ORG o on receiving_id= org_id where  order_id = ?1", nativeQuery = true)
    List<Object[]> getOnePShipment(String id);

    // @Modifying
    // @Query(value = "update S_SHIPMENT_ORDER set STATUS = ?2 where ORDER_ID = ?1", nativeQuery = true)
    // void sendGoods(String id,String status);

    // @Modifying
    // @Query(value = "update S_SHIPMENT_ORDER set STATUS = 3 where ORDER_ID = ?1", nativeQuery = true)
    // void getGoods(String id);

    @Query(value = " SELECT O.EXPRESS_NUM,ORG.ORG_NAME,ORG.ORG_TYPE,SUM(D.NUM),O.STATUS,O.ORDER_ID,O.CREATE_DATE,ORG.ORG_CODE "
            + " FROM S_SHIPMENT_ORDER O inner join S_SHIPMENT_DETAIL D on O.ORDER_ID=D.ORDER_ID inner join S_ORG ORG on ORG.ORG_ID=O.RECEIVING_ID  "
            + " GROUP BY O.EXPRESS_NUM,ORG.ORG_NAME,ORG.ORG_TYPE,O.STATUS,O.ORDER_ID,O.CREATE_DATE,ORG.ORG_CODE ORDER BY O.ORDER_ID DESC", nativeQuery = true)
    List<Object[]> search();

    @Query(value = "SELECT O.EXPRESS_NUM,ORG.ORG_NAME,ORG.ORG_TYPE,SUM(D.NUM),O.STATUS,O.ORDER_ID,O.CREATE_DATE "
            + " FROM S_SHIPMENT_ORDER O inner join S_SHIPMENT_DETAIL D on O.ORDER_ID=D.ORDER_ID inner join S_ORG ORG on ORG.ORG_ID=O.RECEIVING_ID AND O.ORDER_ID=?1"
            + " GROUP BY O.EXPRESS_NUM,ORG.ORG_NAME,ORG.ORG_TYPE,O.STATUS,O.ORDER_ID,O.CREATE_DATE", nativeQuery = true)
    List<Object[]> get(String id);

    @Modifying
    @Query(value = "update S_SHIPMENT_ORDER set STATUS = ?2,RECEIVING_DATE = now(), MODIFY_DATE=now(),MODIFY_ID= ?3 where ORDER_ID = ?1", nativeQuery = true)
    void receivingOrder(String orderId, String status, Long userId);

    @Modifying
    @Query(value = "update S_SHIPMENT_ORDER set STATUS = ?2,SHIPMENT_DATE = now(), MODIFY_DATE=now(),MODIFY_ID= ?3 where ORDER_ID = ?1", nativeQuery = true)
    void shipmentOrder(String orderId, String status, Long userId);

    @Modifying
    @Query(value = "update S_SHIPMENT_ORDER set STATUS = ?2 where ORDER_ID = ?1", nativeQuery = true)
    void changeStatus(String orderId, String status);

    @Query(value = "select  count(1) from S_SHIPMENT_ORDER s " + "where s.STATUS in ?2 and s.RECEIVING_ID = ?1", nativeQuery = true)
    Integer getNoShipmentCount(Long useId, List<String> status);

    @Modifying
    @Query(value = "update S_SHIPMENT_ORDER set STATUS = ?2,RECEIVING_DATE=now() where ORDER_ID = ?1", nativeQuery = true)
    void confirmGetGoods(String id, String status);

    @Query("select count(1) from SShipmentOrderEntity where status in ?1 and shipmentId =?2 ")
    Integer countOrderByStatusAndShipmentId(List<String> shipmentUncompletStatus, Long orgId);

    @Query("select count(1) from SShipmentOrderEntity where status in ?1 and receivingId =?2 ")
    Integer countOrderByStatusAndReceivingId(List<String> shipmentUncompletStatus, Long orgId);
    
    @Query(value ="select s.CREATE_DATE, so.ORG_NAME ,p.GOOD_NAME,p.SPECS,p.GOOD_CODE, sum(ss.NUM) "+
                " from S_SHIPMENT_ORDER s "+
                " LEFT JOIN S_SHIPMENT_DETAIL ss on s.ORDER_ID =ss.ORDER_ID "+
                " LEFT JOIN P_GOODS p on ss.GOOD_ID = p.GOODS_ID "+
                " LEFT JOIN S_ORG so on s.RECEIVING_ID = so.ORG_ID "+
                " where s.ORDER_ID in (?1) group by s.ORDER_ID,s.CREATE_DATE, so.ORG_NAME ,p.GOOD_NAME,p.SPECS,p.GOOD_CODE "   , nativeQuery = true)
    List<Object[]> findAllOrder(List<String> orders);
    
    @Query(value ="select s.CREATE_DATE, so.ORG_NAME ,p.GOOD_NAME,p.SPECS,p.GOOD_CODE, sum(ss.NUM) "+
            " from S_SHIPMENT_ORDER s "+
            " LEFT JOIN S_SHIPMENT_DETAIL ss on s.ORDER_ID =ss.ORDER_ID "+
            " LEFT JOIN P_GOODS p on ss.GOOD_ID = p.GOODS_ID "+
            " LEFT JOIN S_ORG so on s.RECEIVING_ID = so.ORG_ID "+
            " where s.RECEIVING_ID = ?3 and s.CREATE_DATE BETWEEN ?1 AND ?2  group by s.ORDER_ID,s.CREATE_DATE, so.ORG_NAME ,p.GOOD_NAME,p.SPECS,p.GOOD_CODE "   , nativeQuery = true)
    List<Object[]> findAllByTimeAndOrgId(Date startTime, Date endTime, Long platformUserOrgId);
    
    @Query(value ="select s.CREATE_DATE, so.ORG_NAME ,p.GOOD_NAME,p.SPECS,p.GOOD_CODE, sum(ss.NUM) "+
            " from S_SHIPMENT_ORDER s "+
            " LEFT JOIN S_SHIPMENT_DETAIL ss on s.ORDER_ID =ss.ORDER_ID "+
            " LEFT JOIN P_GOODS p on ss.GOOD_ID = p.GOODS_ID "+
            " LEFT JOIN S_ORG so on s.RECEIVING_ID = so.ORG_ID "+
            " where s.RECEIVING_ID = ?1  group by s.ORDER_ID,s.CREATE_DATE, so.ORG_NAME ,p.GOOD_NAME,p.SPECS,p.GOOD_CODE "   , nativeQuery = true)
    List<Object[]> findAllByOrgIdNotTime(Long platformUserOrgId);

}
