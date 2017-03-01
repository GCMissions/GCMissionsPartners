package com.hengtiansoft.business.shopStock.zStock.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.hengtiansoft.business.shopStock.zStock.dto.ShipmentDetailDto;
import com.hengtiansoft.business.shopStock.zStock.dto.ShipmentDto;
import com.hengtiansoft.business.shopStock.zStock.dto.ShipmentSearchDto;
import com.hengtiansoft.business.shopStock.zStock.service.ShipmentService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.exception.WRWException;
import com.hengtiansoft.common.util.DateTimeUtil;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.wrw.dao.SShipmentOrderDao;
import com.hengtiansoft.wrw.dao.SStockDao;
import com.hengtiansoft.wrw.dao.SStockRecordDao;
import com.hengtiansoft.wrw.dao.SUserDao;
import com.hengtiansoft.wrw.entity.SStockEntity;
import com.hengtiansoft.wrw.entity.SStockRecordEntity;
import com.hengtiansoft.wrw.entity.SUserEntity;
import com.hengtiansoft.wrw.enums.ShipmentState;
import com.hengtiansoft.wrw.enums.StockRecordTypeEmum;

/**
 * Class Name: ShipmentServiceImpl
 * Description: TODO
 * 
 * @author chengminmiao
 */
@Service
public class ShipmentServiceImpl implements ShipmentService {

    @Autowired
    SShipmentOrderDao     sshipmentOrderDao;

    @Autowired
    private SUserDao      sUserDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private SStockDao     stockDao;

    @Autowired
    SStockRecordDao       stockRecordDao;

    @SuppressWarnings("unchecked")
    @Override
    public void searchShipment(ShipmentSearchDto infoDto) {
        Long useId = AuthorityContext.getCurrentUser().getUserId();
        SUserEntity user = sUserDao.findOne(useId);
        StringBuilder sqlDataBuilder = new StringBuilder();
        StringBuilder countDataBuilder = new StringBuilder();

        sqlDataBuilder.append("select ORDER_ID,TOTAL_NUM,CREATE_DATE,STATUS from S_SHIPMENT_ORDER where  RECEIVING_ID = ").append(user.getOrgId());

        countDataBuilder.append("select count(1) from (").append(sqlDataBuilder);

        StringBuffer conditionBuffer = new StringBuffer();
        // 未收货列表
        if ("F".equals(infoDto.getIsReceipt())) {
            conditionBuffer.append(" and STATUS in (").append(ShipmentState.SHIPMENT.getKey()).append(")");
        }

        // 发货单号
        if (!StringUtils.isEmpty(infoDto.getOrderCode())) {
            conditionBuffer.append(" and ORDER_ID = '").append(infoDto.getOrderCode()).append("'");
        }
        // 发货数量low
        if (!StringUtils.isEmpty(infoDto.getNumLow())) {
            conditionBuffer.append(" and TOTAL_NUM>= ").append(infoDto.getNumLow());
        }
        // 发货数量hign
        if (!StringUtils.isEmpty(infoDto.getNumHign())) {
            conditionBuffer.append(" and TOTAL_NUM<= ").append(infoDto.getNumHign());
        }
        // 发货状态 -已创建
        if ("1".equals(infoDto.getStatus())) {
            conditionBuffer.append(" and STATUS = ").append(ShipmentState.CREATE.getKey());
        }
        // 发货状态 -已收货
        if ("3".equals(infoDto.getStatus())) {
            conditionBuffer.append(" and STATUS = ").append(ShipmentState.DELIVERY.getKey());
        }
        // 发货状态 -已发货
        if ("2".equals(infoDto.getStatus())) {
            conditionBuffer.append(" and STATUS = ").append(ShipmentState.SHIPMENT.getKey());
        }
        // 创建时间
        if (!StringUtils.isEmpty(infoDto.getStartDate())) {
            conditionBuffer.append(" and CREATE_DATE >='").append(infoDto.getStartDate()).append(" 00:00:00'");
        }
        if (!StringUtils.isEmpty(infoDto.getEndDate())) {
            conditionBuffer.append(" and CREATE_DATE <='").append(infoDto.getEndDate()).append(" 23:59:59'");
        }

        Query query = entityManager.createNativeQuery(sqlDataBuilder.append(conditionBuffer).append(" order by CREATE_DATE desc").toString());
        Query countQuery = entityManager.createNativeQuery(countDataBuilder.append(conditionBuffer).append(" order by CREATE_DATE desc) A")
                .toString());
        BigInteger totalRecord = (BigInteger) countQuery.getSingleResult();
        query.setFirstResult(infoDto.getPageSize() * (infoDto.getCurrentPage() - 1));
        query.setMaxResults(infoDto.getPageSize());
        infoDto.setTotalRecord(totalRecord.longValue());
        infoDto.setTotalPages(totalRecord.intValue() % infoDto.getPageSize() == 0 ? totalRecord.intValue() / infoDto.getPageSize() : ((totalRecord
                .intValue() / infoDto.getPageSize()) + 1));
        infoDto.setList(buildShipmentTotalDtos(query.getResultList()));

    }

    private List<ShipmentDto> buildShipmentTotalDtos(List<Object[]> list) {
        List<ShipmentDto> detailDtos = new ArrayList<ShipmentDto>();
        for (Object[] array : list) {
            ShipmentDto dto = new ShipmentDto();
            dto.setId(array[0].toString());
            dto.setOrderCode(null != array[0] ? array[0].toString() : "");
            dto.setTotalNum(null != array[1] ? array[1].toString() : "");
            dto.setShipmentDate(null != array[2] ? array[2].toString().substring(0, array[2].toString().length() - 2) : "");
            if (null != array[3]) {
                dto.setStatus(ShipmentState.getValue(array[3].toString()));
            }
            detailDtos.add(dto);
        }
        return detailDtos;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ShipmentDto findShipment(String id) {
        StringBuilder sqlDataBuilder = new StringBuilder();
        sqlDataBuilder
                .append("select o.ORDER_ID,o.SHIPMENT_DATE,o.STATUS,o.TOTAL_NUM,g.GOOD_NAME,g.GOOD_CODE,g.PRICE,d.NUM from S_SHIPMENT_ORDER o "
                        + "left join S_SHIPMENT_DETAIL d on o.ORDER_ID = d.ORDER_ID left join P_GOODS g on g.GOODS_ID = d.GOOD_ID "
                        + "where o.ORDER_ID = '").append(id).append("'");
        Query query = entityManager.createNativeQuery(sqlDataBuilder.toString());
        List<Object[]> list = query.getResultList();
        ShipmentDto dto = new ShipmentDto();
        List<ShipmentDetailDto> shipmentDetailDtos = new ArrayList<ShipmentDetailDto>();
        String orderCode = "";
        String shipmentDate = "";
        String status = "";
        int totalNum = 0;
        for (Object[] array : list) {
            ShipmentDetailDto shipmentDetaildto = new ShipmentDetailDto();
            shipmentDetaildto.setGoodCode(null != array[5] ? array[5].toString() : "");
            shipmentDetaildto.setGoodName(null != array[4] ? array[4].toString() : "");
            shipmentDetaildto.setPrice(null != array[6] ? array[6].toString() : "");
            shipmentDetaildto.setNum(null != array[7] ? array[7].toString() : "");
            shipmentDetailDtos.add(shipmentDetaildto);
            orderCode = null != array[0] ? array[0].toString() : "";
            shipmentDate = null != array[1] ? array[1].toString() : "";
            status = array[2].toString();
            if (null != array[7]) {
                totalNum = Integer.valueOf(array[7].toString()) + totalNum;
            }
            shipmentDetaildto.setPriceYuan(WRWUtil.transFenToYuan(null != array[6] ? Long.parseLong(array[6].toString()) : 0L).toString());

            dto.setShipmentDetailDtos(shipmentDetailDtos);
        }
        dto.setOrderCode(orderCode);
        dto.setShipmentDate(shipmentDate);
        dto.setStatus(ShipmentState.getValue(status));
        dto.setTotalNum(String.valueOf(totalNum));
        return dto;
    }

    // @Override
    // @Transactional(value = "jpaTransactionManager")
    // public void sendGoods(String id) {
    // sshipmentOrderDao.sendGoods(id,ShipmentState.delivery.getKey());
    // }

    // @Override
    // @Transactional(value = "jpaTransactionManager")
    // public void getGoods(String id) {
    // sshipmentOrderDao.getGoods(id);
    // }

    @Override
    public Integer getNoShipmentCount() {
        Long useId = AuthorityContext.getCurrentUser().getUserId();
        SUserEntity user = sUserDao.findOne(useId);
        List<String> status = new ArrayList<String>();
        status.add(ShipmentState.SHIPMENT.getKey());
        return sshipmentOrderDao.getNoShipmentCount(user.getOrgId(), status);
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(value = "jpaTransactionManager")
    public void confirmGetGoods(String id) {
        Long useId = AuthorityContext.getCurrentUser().getUserId();
        SUserEntity user = sUserDao.findOne(useId);
        Long orgId = user.getOrgId();
        sshipmentOrderDao.confirmGetGoods(id, ShipmentState.DELIVERY.getKey());
        // 订单中的商品数量
        StringBuilder orderDataBuilder = new StringBuilder();
        orderDataBuilder
                .append("select d.GOOD_ID,d.NUM from S_SHIPMENT_ORDER o left join S_SHIPMENT_DETAIL d on o.ORDER_ID = d.ORDER_ID  where o.ORDER_ID = '")
                .append(id).append("'");
        Query orderQuery = entityManager.createNativeQuery(orderDataBuilder.toString());
        List<Object[]> orderList = orderQuery.getResultList();
        if (null != orderList && orderList.size() > 0) {
            for (Object[] o : orderList) {
                List<SStockEntity> stockList = stockDao.findByOrgIdAndGoodsId(orgId, Long.valueOf(o[0].toString()));
                // 原先库存中有此商品
                if (null != stockList && stockList.size() > 0) {
                    Long newStockNum = Long.valueOf(o[1].toString()) + stockList.get(0).getStockNum();
                    stockList.get(0).setStockNum(newStockNum);
                    setStock(user, o, stockList.get(0));
                } else {
                    SStockEntity stock = new SStockEntity();
                    stock.setStockNum(Long.valueOf(o[1].toString()));
                    setStock(user, o, stock);
                }
            }
        }
    }

    private void setStock(SUserEntity user, Object[] o, SStockEntity stock) {
        stock.setOrgId(user.getOrgId());
        stock.setOrgName(user.getUserName());
        stock.setGoodsId(Long.valueOf(o[0].toString()));
        stock.setSafeNum(0L);
        stock.setStandardNum(0L);
        stockDao.save(stock);
        // 插入库存变更记录
        SStockRecordEntity sd = new SStockRecordEntity();
        sd.setStockId(stock.getStockId());
        sd.setOperType(StockRecordTypeEmum.AUTOADD.getCode());
        sd.setChangeNum(Long.valueOf(o[1].toString()));
        sd.setOperId(user.getOrgId());
        sd.setOperDate(new Date());
        stockRecordDao.save(sd);
    }

    @Override
    public List<List<String>> findAllOrder(String orderId) {
        List<List<String>> listStr = new ArrayList<List<String>>();
        if (WRWUtil.isEmpty(orderId)) {
            throw new WRWException("订单不能为空!");
        }
        String[] orderIds = orderId.split(",");
        List<String> orders = new ArrayList<String>();
        for (String str : orderIds) {
            orders.add(str);
        }
        List<Object[]> list = sshipmentOrderDao.findAllOrder(orders);

        Integer numCount = 0;
        for (int i = 0; i < list.size(); i++) {
            List<String> strList = new ArrayList<String>();
            strList.add(String.valueOf(i + 1));
            strList.add(DateTimeUtil.parseDateToString((Date) list.get(i)[0], DateTimeUtil.CHINA_YMD));
            strList.add(list.get(i)[2].toString());
            strList.add(list.get(i)[3].toString());
            strList.add(list.get(i)[4].toString());
            strList.add(list.get(i)[5].toString());
            listStr.add(strList);
            numCount += Integer.valueOf(list.get(i)[5].toString());
        }
        List<String> hzList = new ArrayList<String>();
        hzList.add("汇总");
        hzList.add("");
        hzList.add("");
        hzList.add("");
        hzList.add("");
        hzList.add(numCount.toString());
        listStr.add(hzList);
        return listStr;
    }

}
