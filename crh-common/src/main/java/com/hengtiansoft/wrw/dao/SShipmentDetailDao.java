package com.hengtiansoft.wrw.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.SShipmentDetailEntity;

public interface SShipmentDetailDao extends JpaRepository<SShipmentDetailEntity, String>,JpaSpecificationExecutor<SShipmentDetailEntity>{
    @Query(value = " SELECT G.GOODS_ID,G.GOOD_CODE,G.GOOD_NAME,G.PRICE,G.CREATE_DATE,S.NUM from S_SHIPMENT_DETAIL S ,P_GOODS G where S.GOOD_ID=G.GOODS_ID and ORDER_ID= ?1", nativeQuery = true)
    List<Object[]> getOrderDetail(String id);
    
    List<SShipmentDetailEntity> findByOrderId(String orderId);
    
    @Query(value = "select m.goodId,sum(m.num),ps.goodName ,so.receivingId,so.shipmentId from SShipmentDetailEntity m ,PGoodsEntity ps,SShipmentOrderEntity so where so.orderId =m.orderId and  m.goodId = ps.goodsId and m.orderId  in (?1) group by m.goodId,ps.goodName ,so.receivingId,so.shipmentId")
    List<Object[]> findNumByOrderId(List<String> orderIds);

}
