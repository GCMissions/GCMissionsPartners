package com.hengtiansoft.task.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.common.sequence.SequenceGenerator;
import com.hengtiansoft.common.sequence.SequenceType;
import com.hengtiansoft.common.util.DateTimeUtil;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.task.pay.dto.PaymentParamDto;
import com.hengtiansoft.task.pay.plugin.UnionPayPlugin;
import com.hengtiansoft.task.service.KdTeamBuyProductService;
import com.hengtiansoft.task.service.SMSService;
import com.hengtiansoft.task.service.WechatRefundService;
import com.hengtiansoft.wrw.dao.KdOrderDetailDao;
import com.hengtiansoft.wrw.dao.KdOrderMainDao;
import com.hengtiansoft.wrw.dao.KdOrderOperDao;
import com.hengtiansoft.wrw.dao.KdOrderReturnDao;
import com.hengtiansoft.wrw.dao.KdProductStockDao;
import com.hengtiansoft.wrw.dao.KdProductStockDetailDao;
import com.hengtiansoft.wrw.dao.KdTeamBuyProductDao;
import com.hengtiansoft.wrw.dao.KdTeamBuyProductRecordCycleDao;
import com.hengtiansoft.wrw.dao.KdTeamBuyProductRecordDao;
import com.hengtiansoft.wrw.dao.KdTwentyFourHoursDao;
import com.hengtiansoft.wrw.entity.KdOrderDetailEntity;
import com.hengtiansoft.wrw.entity.KdOrderMainEntity;
import com.hengtiansoft.wrw.entity.KdOrderOperEntity;
import com.hengtiansoft.wrw.entity.KdOrderReturnEntity;
import com.hengtiansoft.wrw.entity.KdProductStockDetailEntity;
import com.hengtiansoft.wrw.entity.KdProductStockEntity;
import com.hengtiansoft.wrw.entity.KdTeamBuyProductRecordCycleEntity;
import com.hengtiansoft.wrw.entity.KdTeamBuyProductRecordEntity;
import com.hengtiansoft.wrw.enums.KdOrderOperTypeEnum;
import com.hengtiansoft.wrw.enums.KdOrderStatusEnum;
import com.hengtiansoft.wrw.enums.KdOrderTypeEnum;
import com.hengtiansoft.wrw.enums.KdPImageEnum;
import com.hengtiansoft.wrw.enums.KdPayTypeEnum;
import com.hengtiansoft.wrw.enums.MessageModelEnum;
import com.hengtiansoft.wrw.enums.StatusEnum;

@Service
public class KdTeamBuyProductServiceImpl implements KdTeamBuyProductService {

    @Autowired
    private KdTeamBuyProductDao kdTeamBuyProductDao;
    @Autowired
    private KdTeamBuyProductRecordDao kdTeamBuyProductRecordDao;
    @Autowired
    private KdTeamBuyProductRecordCycleDao kdTeamBuyProductRecordCycleDao;
    @Autowired
    private KdOrderMainDao kdOrderMainDao;
    @Autowired
    private KdOrderReturnDao kdOrderReturnDao;
    @Autowired
    private SequenceGenerator sequenceGenerator;
    @Autowired
    private KdOrderDetailDao kdOrderDetailDao;
    @Autowired
    private WechatRefundService wechatRefundService;
    @Autowired
    private KdOrderOperDao kdOrderOperDao;
    @Autowired
    private KdTwentyFourHoursDao kdTwentyFourHoursDao;
    @Autowired
    private KdProductStockDao kdProductStockDao;
    @Autowired
    private KdProductStockDetailDao kdProductStockDetailDao;
    @Autowired
    private SMSService sMSService;
    @Autowired
    private UnionPayPlugin unionPayPlugin;
    
    @Override
    @Transactional(value = "jpaTransactionManager")
    public void updateProductStatus() {
        kdTeamBuyProductDao.updateStartStatus();
        kdTeamBuyProductDao.updateEndStatus();
    }

    @Override
    @Transactional
    public void returnTeamBuyAmount() {
        List<KdTeamBuyProductRecordEntity> recordList = kdTeamBuyProductRecordDao.findOverTimeTeamBuy();
        List<KdTeamBuyProductRecordEntity> returnRecords = new ArrayList<KdTeamBuyProductRecordEntity>();
        List<KdOrderReturnEntity> returnOrderList = new ArrayList<KdOrderReturnEntity>();
        List<KdOrderOperEntity> operList = new ArrayList<KdOrderOperEntity>();
        for (KdTeamBuyProductRecordEntity record : recordList) {
            List<KdTeamBuyProductRecordCycleEntity> cycleList = kdTeamBuyProductRecordCycleDao.findByRecordIdAndStatus(record.getRecordId(), StatusEnum.NORMAL.getCode());
            for (KdTeamBuyProductRecordCycleEntity cycle : cycleList) {
                KdOrderMainEntity order = kdOrderMainDao.findByOrderId(cycle.getOrderId());
                if (order != null && !KdOrderStatusEnum.WAIT_PAY.getCode().equals(order.getStatus()) && !KdOrderStatusEnum.COMPLETE_RETURNMONEY.getCode().equals(order.getStatus())) {
                    String result = "";
                    if (KdPayTypeEnum.WECHAT.getKey().equals(order.getPaymentType())) {
                        result = wechatRefundService.wechatRefund(String.valueOf(order.getActualAmount()),
                                String.valueOf(order.getActualAmount()), order.getOrderId(), order.getTsn());
                    } else if (KdPayTypeEnum.UNION.getKey().equals(order.getPaymentType())) {
                        String orderId = order.getOrderId() + DateTimeUtil.getCurrentTime(DateTimeUtil.SIMPLE_SECONDS);
                        PaymentParamDto paramDto = new PaymentParamDto(orderId, String.valueOf(order.getActualAmount()), order.getTsn());
                        result = unionPayPlugin.refund(paramDto).getCode();
                    }
                    if (result.equalsIgnoreCase("SUCCESS") || result.equalsIgnoreCase("ACK")) {
                        List<KdOrderDetailEntity> details = kdOrderDetailDao.findByOrderId(order.getOrderId());
                        if (!details.isEmpty()) {
                            KdOrderDetailEntity detail = details.get(0);
                            String specGroup = buildStockSpec(detail.getSpecInfo());
                            KdOrderReturnEntity returnEntity = new KdOrderReturnEntity();
                            returnEntity.setOrderMainId(order.getOrderId());
                            returnEntity.setReturnId(sequenceGenerator.getOrderId(SequenceType.M_KD_RETURN));
                            returnEntity.setReturnAmount(order.getActualAmount());
                            returnEntity.setOrderStatus(order.getStatus());
                            returnEntity.setProductId(record.getTeamId());
                            returnEntity.setCreateDate(new Date());
                            returnEntity.setCreateId(-1L);
                            returnEntity.setType(KdOrderTypeEnum.TEAM_BUY.getCode());
                            returnEntity.setReturnCount(detail.getProductCount().longValue());
                            returnEntity.setSpecInfo(detail.getSpecInfo());
                            returnOrderList.add(returnEntity);
                            
                            order.setStatus(KdOrderStatusEnum.COMPLETE_RETURNMONEY.getCode());
                            kdOrderMainDao.save(order);
                            
                            addStockCount(detail.getProductId(), detail.getProductCount(), -1L, specGroup, KdOrderTypeEnum.TEAM_BUY.getCode());
                            
                            KdOrderOperEntity operEntity = new KdOrderOperEntity();
                            operEntity.setOrderId(order.getOrderId());
                            operEntity.setOperType(KdOrderOperTypeEnum.REFUND.getCode());
                            operEntity.setComment("订单状态：" + KdOrderStatusEnum.getText(order.getStatus()) + "未成团订单自动退款 ");
                            operEntity.setCreateDate(new Date());
                            operEntity.setCreateId(-1L);
                            operList.add(operEntity);
                            
                            // 发短信 
                            HashMap<String, String> dataMap = new HashMap<String, String>();
                            dataMap.put("amount", WRWUtil.transFenToYuan2String(order.getActualAmount()));
                            dataMap.put("day", "5");
                            dataMap.put("actType", KdPImageEnum.TEAMBUY.getDesc());
                            dataMap.put("productName", kdTeamBuyProductDao.findOne(detail.getProductId()).getName() + " : " + specGroup);
                            sMSService.sendSMS(order.getPhone(), MessageModelEnum.sms_C_KdActRefundNotify, dataMap);
                        }
                    } 
                }
            }
            record.setIsReturn("1");
            returnRecords.add(record);
        }
        kdOrderReturnDao.save(returnOrderList);
        kdOrderOperDao.save(operList);
        kdTeamBuyProductRecordDao.save(returnRecords);
    }

    private void addStockCount(Long productId, Integer productCount, Long userId, String specGroup, String orderType) {
        Long kdProductId = 0L;
        if (KdOrderTypeEnum.OFFLINE.getCode().equals(orderType) || KdOrderTypeEnum.NORMAL.getCode().equals(orderType)) {
            kdProductId = productId;
        } else if (KdOrderTypeEnum.TEAM_BUY.getCode().equals(orderType)) {
            kdProductId = kdTeamBuyProductDao.findOne(productId).getProductId();
        } else {
            kdProductId = kdTwentyFourHoursDao.findOne(productId).getProductId();
        }
        KdProductStockEntity stock = kdProductStockDao.findByProductId(kdProductId);
        if (stock != null) {
            // 按规格
            if ("0".equals(stock.getStockType())) {
                kdProductStockDao.updateRestAmount(kdProductId, productCount, userId);
                KdProductStockDetailEntity entity = kdProductStockDetailDao.findByStockIdAndSpecGroup(stock.getId(), specGroup);
                if (entity != null) {
                    kdProductStockDetailDao.addRestAmount(productCount, entity.getId(), userId);
                }
            }
            // 按总库存
            if ("1".equals(stock.getStockType())) {
                kdProductStockDao.updateRestAmount(kdProductId, productCount, userId);
            }
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
    
    
}
