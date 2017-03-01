package com.hengtiansoft.task.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.task.service.KdOrderCancelService;
import com.hengtiansoft.wrw.dao.KdOrderDetailDao;
import com.hengtiansoft.wrw.dao.KdOrderMainDao;
import com.hengtiansoft.wrw.dao.KdOrderOperDao;
import com.hengtiansoft.wrw.dao.KdProductStockDao;
import com.hengtiansoft.wrw.dao.KdProductStockDetailDao;
import com.hengtiansoft.wrw.dao.SBasicParaDao;
import com.hengtiansoft.wrw.entity.KdOrderDetailEntity;
import com.hengtiansoft.wrw.entity.KdOrderMainEntity;
import com.hengtiansoft.wrw.entity.KdOrderOperEntity;
import com.hengtiansoft.wrw.entity.KdProductStockDetailEntity;
import com.hengtiansoft.wrw.entity.KdProductStockEntity;
import com.hengtiansoft.wrw.enums.KdOrderOperTypeEnum;
import com.hengtiansoft.wrw.enums.KdOrderStatusEnum;
import com.hengtiansoft.wrw.enums.KdOrderTypeEnum;

@Service
public class KdOrderCancelServiceImpl implements KdOrderCancelService {
    
    private static final String PARA_TYPE_NAME = "超时订单取消";
    
    private static final String NORMAL = "正常订单";
    
    private static final String GROUP_BUY = "团购订单";
    
    private static final String TWENTY_FOUR = "24小时订单";
    
    private static final String SPEC = "0";
    
    private static final String STOCK = "1";
    
    private static final String AUTO_RECEIVE_PARA_TYPE_NAME = "自动确认收货时间";
    
    private static final String AUTO_RECEIVE = "AUTO_RECEIVE";
    
    @Autowired
    private SBasicParaDao sBasicParaDao;
    @Autowired
    private KdOrderMainDao kdOrderMainDao;
    @Autowired
    private KdOrderOperDao kdOrderOperDao;
    @Autowired
    private KdOrderDetailDao kdOrderDetailDao;
    @Autowired
    private KdProductStockDao kdProductStockDao;
    @Autowired
    private KdProductStockDetailDao kdProductStockDetailDao;

    @Override
    @Transactional("jpaTransactionManager")
    public void cancelKdOrder() {
        List<KdOrderMainEntity> orderToCancel = new ArrayList<>();
            
        Date now = new Date();
        List<KdOrderMainEntity> orders = kdOrderMainDao.findByStatus(KdOrderStatusEnum.WAIT_PAY.getCode());
        List<KdOrderOperEntity> orderOpers = new ArrayList<KdOrderOperEntity>();
        for (KdOrderMainEntity order : orders) {
            List<KdOrderDetailEntity> detailList = kdOrderDetailDao.findByOrderId(order.getOrderId());
            String time = "";
            if (KdOrderTypeEnum.NORMAL.getCode().equals(detailList.get(0).getType())) {
                time = sBasicParaDao.findParaValue1ByTypeName(PARA_TYPE_NAME, NORMAL);
            } else if (KdOrderTypeEnum.TEAM_BUY.getCode().equals(detailList.get(0).getType())) {
                time = sBasicParaDao.findParaValue1ByTypeName(PARA_TYPE_NAME, GROUP_BUY);
            } else {
                time = sBasicParaDao.findParaValue1ByTypeName(PARA_TYPE_NAME, TWENTY_FOUR);
            }
            Integer mins = Integer.parseInt(time);
            if (getLeftPayTime(order.getCreateDate(), mins).longValue() <= 0L) {
                order.setStatus(KdOrderStatusEnum.CANCELED.getCode());
                order.setModifyDate(now);
                orderToCancel.add(order);
                orderOpers.add(genarateOrderOper(order));
                // 库存处理
                productStockDeal(detailList);
            }
        }
        kdOrderMainDao.save(orderToCancel);
        kdOrderOperDao.save(orderOpers);
    }
    
    @Transactional("jpaTransactionManager")
    private void productStockDeal(List<KdOrderDetailEntity> detailList) {
        for (KdOrderDetailEntity entity : detailList) {
            addStockCount(entity.getProductId(), entity.getProductCount(), -1L, buildStockSpec(entity.getSpecInfo()));
        }
    }
    
    @SuppressWarnings("rawtypes")
    private String buildStockSpec(String specInfo) {
        String specGroup = "";
        JSONObject jsonObject = JSONObject.fromObject(specInfo);
        Iterator iterator = jsonObject.keys();
        List<String> specs = new ArrayList<String>();
        while(iterator.hasNext()){
            specs.add((String) jsonObject.get(String.valueOf(iterator.next())));
        }
        for (int i = 0; i < specs.size(); i++) {
            if (i == specs.size() - 1) {
                specGroup += specs.get(i);
            } else {
                specGroup += specs.get(i) + "-";
            }
        }
        return specGroup;
    }
    
    @Transactional
    public void addStockCount(Long productId, Integer count, Long userId, String specGroup) {
        KdProductStockEntity stock = kdProductStockDao.findByProductId(productId);
        if (stock != null) {
            // 按规格
            if (SPEC.equals(stock.getStockType())) {
                kdProductStockDao.updateRestAmount(productId, count, userId);
                KdProductStockDetailEntity entity = kdProductStockDetailDao.findByStockIdAndSpecGroup(stock.getId(), specGroup);
                if (entity != null) {
                    kdProductStockDetailDao.addRestAmount(count, entity.getId(), userId);
                }
            }
            // 按总库存
            if (STOCK.equals(stock.getStockType())) {
                kdProductStockDao.updateRestAmount(productId, count, userId);
            }
        }
    }

    /**
     * Description: 获取剩余付款时间（秒）
     *
     * @param createDate
     *            订单生成时间
     * @return
     */
    private Long getLeftPayTime(Date createDate, Integer mins) {
        Long gapTime = new Date().getTime() - createDate.getTime();
        Long leftPayTime = mins * 60 - gapTime / 1000;

        return leftPayTime;
    }
    
    /**
     * Description: 保存订单操作记录
     *
     * @param status
     * @param orderIds
     */
    @Transactional(value = "jpaTransactionManager")
    private KdOrderOperEntity genarateOrderOper(KdOrderMainEntity order) {
        String status = order.getStatus();
        KdOrderOperEntity orderOper = new KdOrderOperEntity();
        orderOper.setComment("订单状态更新:" + KdOrderStatusEnum.getText(status) + ",系统自动取消");
        orderOper.setCreateId(-1L);
        orderOper.setCreateDate(new Date());
        orderOper.setOperType(KdOrderOperTypeEnum.CANCEL.getCode());
        orderOper.setOrderId(order.getOrderId());
        return orderOper;
    }

    @Override
    @Transactional(value = "jpaTransactionManager")
    public void productReceive() {
        List<KdOrderMainEntity> orderList = kdOrderMainDao.findByStatus(KdOrderStatusEnum.WAIT_RECEIVE.getCode());
        List<KdOrderMainEntity> receiveOrderList = new ArrayList<KdOrderMainEntity>();
        List<KdOrderOperEntity> recieveOperOrderList = new ArrayList<KdOrderOperEntity>();
        String time = sBasicParaDao.findParaValue1ByTypeName(AUTO_RECEIVE_PARA_TYPE_NAME, AUTO_RECEIVE);
        for (KdOrderMainEntity order : orderList) {
            Date date = new Date();
            List<KdOrderOperEntity> operList = kdOrderOperDao.findByOrderIdAndOperType(order.getOrderId(), KdOrderOperTypeEnum.DELIVERY.getCode());
            if (!operList.isEmpty()) {
                if (getLeftPayTime(operList.get(0).getCreateDate(), Integer.parseInt(time)) <= 0L) {
                    order.setStatus(KdOrderStatusEnum.WATI_RATE.getCode());
                    order.setModifyDate(date);
                    order.setModifyId(0L);
                    receiveOrderList.add(order);
                    
                    KdOrderOperEntity operEntity = new KdOrderOperEntity();
                    operEntity.setOperType(KdOrderOperTypeEnum.AUTO_RECEIVE.getCode());
                    operEntity.setOrderId(order.getOrderId());
                    operEntity.setComment("订单状态更新：" + KdOrderStatusEnum.WATI_RATE.getCode() + ",系统自动确认收货");
                    operEntity.setCreateDate(date);
                    operEntity.setCreateId(0L);
                    recieveOperOrderList.add(operEntity);
                }
            }
        }
        kdOrderMainDao.save(receiveOrderList);
        kdOrderOperDao.save(recieveOperOrderList);
    }
}
