/*
 * Project Name: wrw-job
 * File Name: OrderPressTimeoutCheckServiceImpl.java
 * Class Name: OrderPressTimeoutCheckServiceImpl
 *
 * Copyright 2014 Hengtian Software Inc
 *
 * Licensed under the Hengtiansoft
 *
 * http://www.hengtiansoft.com
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hengtiansoft.task.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.task.service.OrderPressTimeoutCheckService;
import com.hengtiansoft.wrw.dao.MOrderDetailDao;
import com.hengtiansoft.wrw.dao.MOrderMainDao;
import com.hengtiansoft.wrw.dao.MSmsDao;
import com.hengtiansoft.wrw.dao.SBasicParaDao;
import com.hengtiansoft.wrw.dao.SMessageDao;
import com.hengtiansoft.wrw.dao.SMessageOrgDao;
import com.hengtiansoft.wrw.dao.SOrderRewardDao;
import com.hengtiansoft.wrw.dao.SOrgDao;
import com.hengtiansoft.wrw.dao.SStockDao;
import com.hengtiansoft.wrw.dao.SStockRecordDao;
import com.hengtiansoft.wrw.entity.MOrderDetailEntity;
import com.hengtiansoft.wrw.entity.MOrderMainEntity;
import com.hengtiansoft.wrw.entity.MSmsEntity;
import com.hengtiansoft.wrw.entity.SBasicParaEntity;
import com.hengtiansoft.wrw.entity.SOrderRewardEntity;
import com.hengtiansoft.wrw.entity.SOrgEntity;
import com.hengtiansoft.wrw.entity.SStockEntity;
import com.hengtiansoft.wrw.entity.SStockRecordEntity;
import com.hengtiansoft.wrw.enums.MSmsTypeEnum;
import com.hengtiansoft.wrw.enums.MessageModelEnum;
import com.hengtiansoft.wrw.enums.OrderExceptionEnum;
import com.hengtiansoft.wrw.enums.OrderPressedStatus;
import com.hengtiansoft.wrw.enums.OrderResendFlagEnum;
import com.hengtiansoft.wrw.enums.OrderStatus;
import com.hengtiansoft.wrw.enums.ParaSettingEnum;
import com.hengtiansoft.wrw.enums.RewardOrderTypeEnum;
import com.hengtiansoft.wrw.enums.SmsStatus;
import com.hengtiansoft.wrw.enums.StockRecordTypeEmum;

/**
 * Class Name: OrderPressTimeoutCheckServiceImpl
 * 
 * Description: 订单催单超时提醒
 * 
 * @author huizhuang
 */
@Service
public class OrderPressTimeoutCheckServiceImpl implements OrderPressTimeoutCheckService {

    @Autowired
    private MSmsDao            mSmsDao;

    @Autowired
    private MOrderMainDao      mOrderMainDao;
    
    @Autowired
    private MOrderDetailDao    mOrderDetailDao;
    
    @Autowired
    private SOrderRewardDao    sOrderRewardDao;

    @Autowired
    private SOrgDao            sOrgDao;
    
    @Autowired
    private SStockDao          sStockDao;
    
    @Autowired
    private SStockRecordDao    sStockRecordDao;

    @Autowired
    private SMessageDao        sMessageDao;

    @Autowired
    private SMessageOrgDao     sMessageOrgDao;

    @Autowired
    private SBasicParaDao      sBasicParaDao;

    private static final Long  DEFAULT_ORG_ID = -1L;

    /**
     * 订单超时检查
     */
    @Override
    @Transactional(value = "jpaTransactionManager")
    public void check() {
        Integer timeoutValue = getOrderPressTimeoutValue();
        Long punishValue = getOrderPressTimeoutPunishValue();

        // 查询超时订单
        List<String> timeoutOrderIds = mOrderMainDao.findPressTimeoutOrderId(OrderStatus.WAIT_PAY.getKey(),
                OrderPressedStatus.PRESSED.getKey(), getOrderPreeTimeEnd(timeoutValue));
        if (timeoutOrderIds.isEmpty()) {
            return;
        }
        
        // 查询超时订单信息
        List<MOrderMainEntity> timeoutOrders = mOrderMainDao.findAll(timeoutOrderIds);

        // 重置超时订单
        revertTimeoutOrder(timeoutOrderIds);

        // 生成惩罚订单
        generatePunishiOrders(timeoutOrders, punishValue);
        
        // 生成配送商手机号map
        Set<Long> orgIds = new HashSet<>();
        for (MOrderMainEntity timeoutOrder : timeoutOrders) {
            orgIds.add(timeoutOrder.getOrgId());
        }
        Map<Long, String> orgPhoneMap = new HashMap<>();
        for (SOrgEntity org : sOrgDao.findAll(orgIds)) {
            orgPhoneMap.put(org.getOrgId(), org.getPhone());
        }
        
        // 生成短信提醒
        generateSmsRemind(timeoutOrders, orgPhoneMap);
        
        // 配送商还原库存
        revertStock(timeoutOrderIds, timeoutOrders);
    }

    /**
     * 重置超时订单
     */
    @Transactional(value = "jpaTransactionManager")
    private void revertTimeoutOrder(List<String> orderIds) {
        mOrderMainDao.revertTimeoutOrders(DEFAULT_ORG_ID, null, OrderExceptionEnum.ABNORMAL.getKey(),
                OrderPressedStatus.UNPRESSED.getKey(), OrderResendFlagEnum.RESEND.getKey(), orderIds);
    }
    
    /**
     * Description: 重置库存
     *
     * @param orders
     */
    @Transactional(value = "jpaTransactionManager")
    private void revertStock(List<String> orderIds, List<MOrderMainEntity> orders) {
        // 获取订单 - 配送商，映射信息
        Map<String, Long> orderOrgMap = new HashMap<>();
        for (MOrderMainEntity order : orders) {
            orderOrgMap.put(order.getOrderId(), order.getOrgId());
        }
        
        // 获取订单 - 订单详情，映射信息
        Map<String, List<MOrderDetailEntity>> orderOrderDetailMap = new HashMap<>();
        for (MOrderDetailEntity orderDetail : mOrderDetailDao.findByOrderIdIn(orderIds)) {
            String orderId = orderDetail.getOrderId();
            
            if (!orderOrderDetailMap.containsKey(orderId)) {
                orderOrderDetailMap.put(orderId, new ArrayList<MOrderDetailEntity>());
            }
            orderOrderDetailMap.get(orderId).add(orderDetail);
        }
        
        // 逐单恢复库存
        Date now = new Date();
        List<SStockEntity> stocks = new ArrayList<>();
        List<SStockRecordEntity> stockRecords = new ArrayList<>();
        for (Map.Entry<String, List<MOrderDetailEntity>> entry : orderOrderDetailMap.entrySet()) {
            Long orgId = orderOrgMap.get(entry.getKey());
            
            // 获取物料总数，map
            Map<Long, Long> goodTotalNumMap = new HashMap<>();
            for (MOrderDetailEntity detail : entry.getValue()) {
                Long goodId = detail.getGoodId();
                
                if (!goodTotalNumMap.containsKey(goodId)) {
                    goodTotalNumMap.put(goodId, detail.getNum() * detail.getGoodNum());
                } else {
                    goodTotalNumMap.put(goodId, goodTotalNumMap.get(goodId) + detail.getNum() * detail.getGoodNum());
                }
            }
            
            // 库存操作记录
            for (SStockEntity stock : sStockDao.findByOrgIdAndGoodsIdIn(orgId, goodTotalNumMap.keySet())) {
                Long goodNum = goodTotalNumMap.get(stock.getGoodsId());
                stock.setStockNum(stock.getStockNum() + goodNum);
                stocks.add(stock);
                
                SStockRecordEntity stockRecord = new SStockRecordEntity();
                stockRecord.setStockId(stock.getStockId());
                stockRecord.setChangeNum(goodNum);
                stockRecord.setOperId(0L);
                stockRecord.setOperDate(now);
                stockRecord.setOperType(StockRecordTypeEmum.AUTOADD.getCode());
                stockRecords.add(stockRecord);
            }
        }
        
        sStockDao.save(stocks);
        sStockRecordDao.save(stockRecords);
    }

    /**
     * Description: 生成惩罚订单
     *
     * @param orders
     */
    @Transactional(value = "jpaTransactionManager")
    private void generatePunishiOrders(List<MOrderMainEntity> orders, Long punishValue) {
        List<SOrderRewardEntity> punishOrders = new ArrayList<>();
        
        Date now = new Date();
        for (MOrderMainEntity order : orders) {
            SOrderRewardEntity punishOrder = new SOrderRewardEntity();
            punishOrder.setOrderId(order.getOrderId());
            punishOrder.setOrgId(order.getOrgId());
            punishOrder.setType(RewardOrderTypeEnum.TAKE_TIMEOUT.getKey());
            punishOrder.setAmount(-1 * punishValue);
            punishOrder.setCreateDate(now);
            punishOrder.setRewardDate(now);
            punishOrder.setAssignDate(order.getAssignDate());
            punishOrders.add(punishOrder);
        }
        sOrderRewardDao.save(punishOrders);
    }

    /**
     * Description: 生成短信提醒 
     *
     * @param orderPhoneMap
     */
    @Transactional(value = "jpaTransactionManager")
    private void generateSmsRemind(List<MOrderMainEntity> orders, Map<Long, String> orgPhoneMap) {
        List<MSmsEntity> smses = new ArrayList<>();
        
        SBasicParaEntity para = sBasicParaDao.findByParaName(MessageModelEnum.sms_C_sendOrderANotify.getValue());
        String tmplate = para.getParaValue1();
        
        for (MOrderMainEntity order : orders) {
            Long orgId = order.getOrgId();
            if (orgPhoneMap.containsKey(orgId)) {
                MSmsEntity sms = new MSmsEntity();
                sms.setType(MSmsTypeEnum.ORDER_TRANSFER.getKey());
                sms.setContent(tmplate.replace("${orderId}", order.getOrderId()));
                sms.setPhone(orgPhoneMap.get(orgId));
                sms.setCreateDate(new Date());
                sms.setStatus(SmsStatus.UNSEND.getKey());
                smses.add(sms);
            }
        }
        mSmsDao.save(smses);
    }
    
    /**
     * Description: 获取催单超时，结束时间
     *
     * @return
     */
    private Date getOrderPreeTimeEnd(int minuteGap) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -1 * minuteGap);
        return calendar.getTime();
    }

    /**
     * Description: 获取催单后，超时时间(分)
     *
     * @return
     */
    private Integer getOrderPressTimeoutValue() {
        SBasicParaEntity para = sBasicParaDao.findByParaName(ParaSettingEnum.overTimeOrderTwiceInterval.getValue());
        return Integer.valueOf(para.getParaValue1());
    }

    /**
     * Description: 获取催单后，惩罚金额
     *
     * @return
     */
    private Long getOrderPressTimeoutPunishValue() {
        SBasicParaEntity para = sBasicParaDao.findByParaName(ParaSettingEnum.overTimePunishAmount.getValue());

        return yuan2Cent(para.getParaValue1());
    }

    /**
     * 元转分
     */
    private Long yuan2Cent(String yuan) {
        if (null == yuan) {
            return 0L;
        }
        return new BigDecimal(yuan).multiply(new BigDecimal(100)).longValue();
    }
}
