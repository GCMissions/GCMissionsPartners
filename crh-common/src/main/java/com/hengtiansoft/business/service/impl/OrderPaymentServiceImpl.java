package com.hengtiansoft.business.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.business.service.OrderPaymentService;
import com.hengtiansoft.common.exception.BizServiceException;
import com.hengtiansoft.common.exception.WRWException;
import com.hengtiansoft.common.lbs.LocationUtil;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.wrw.dao.MAccountDao;
import com.hengtiansoft.wrw.dao.MAcctRecordDao;
import com.hengtiansoft.wrw.dao.MAddressDao;
import com.hengtiansoft.wrw.dao.MMemberDao;
import com.hengtiansoft.wrw.dao.MOrderBillingDao;
import com.hengtiansoft.wrw.dao.MOrderDetailDao;
import com.hengtiansoft.wrw.dao.MOrderMainDao;
import com.hengtiansoft.wrw.dao.MOrderOperDao;
import com.hengtiansoft.wrw.dao.MPointDetailDao;
import com.hengtiansoft.wrw.dao.SBasicParaDao;
import com.hengtiansoft.wrw.dao.SOrgDao;
import com.hengtiansoft.wrw.dao.SRegionDao;
import com.hengtiansoft.wrw.dao.SStockDao;
import com.hengtiansoft.wrw.dao.SStockRecordDao;
import com.hengtiansoft.wrw.entity.MAccountEnity;
import com.hengtiansoft.wrw.entity.MAcctRecordEntity;
import com.hengtiansoft.wrw.entity.MAddressEntity;
import com.hengtiansoft.wrw.entity.MMemberEntity;
import com.hengtiansoft.wrw.entity.MOrderBillingEntity;
import com.hengtiansoft.wrw.entity.MOrderDetailEntity;
import com.hengtiansoft.wrw.entity.MOrderMainEntity;
import com.hengtiansoft.wrw.entity.MOrderOperEntity;
import com.hengtiansoft.wrw.entity.MPointDetailEntity;
import com.hengtiansoft.wrw.entity.SBasicParaEntity;
import com.hengtiansoft.wrw.entity.SOrgEntity;
import com.hengtiansoft.wrw.entity.SRegionEntity;
import com.hengtiansoft.wrw.entity.SStockEntity;
import com.hengtiansoft.wrw.entity.SStockRecordEntity;
import com.hengtiansoft.wrw.enums.AcctRecordType;
import com.hengtiansoft.wrw.enums.BasicTypeEnum;
import com.hengtiansoft.wrw.enums.BillStatus;
import com.hengtiansoft.wrw.enums.BillType;
import com.hengtiansoft.wrw.enums.OrderExceptionEnum;
import com.hengtiansoft.wrw.enums.OrderSourceEnum;
import com.hengtiansoft.wrw.enums.OrderStatus;
import com.hengtiansoft.wrw.enums.OrgTypeEnum;
import com.hengtiansoft.wrw.enums.PaymentStatus;
import com.hengtiansoft.wrw.enums.PaymentType;
import com.hengtiansoft.wrw.enums.PointsTypeEnum;
import com.hengtiansoft.wrw.enums.SOrgStatusEnum;
import com.hengtiansoft.wrw.enums.ShipmentType;
import com.hengtiansoft.wrw.enums.StockRecordTypeEmum;

@Service
public class OrderPaymentServiceImpl implements OrderPaymentService {

    @Autowired
    private MAddressDao        mAddressDao;

    @Autowired
    private MMemberDao         mMemberDao;

    @Autowired
    private MAccountDao        mAccountDao;

    @Autowired
    private MAcctRecordDao     mAcctRecordDao;

    @Autowired
    private MPointDetailDao    mPointDetailDao;

    @Autowired
    private MOrderMainDao      mOrderMainDao;
    
    @Autowired
    private MOrderOperDao      mOrderOperDao;

    @Autowired
    private MOrderDetailDao    mOrderDetailDao;

    @Autowired
    private MOrderBillingDao   mOrderBillingDao;
    
    @Autowired
    private SOrgDao            sOrgDao;

    @Autowired
    private SStockDao          sStockDao;

    @Autowired
    private SStockRecordDao    sStockRecordDao;

    @Autowired
    private SRegionDao         sRegionDao;

    @Autowired
    private SBasicParaDao      sBasicParaDao;

    /**
     * 支付宝支付确认
     */
    @Transactional("jpaTransactionManager")
    @Override
    public boolean aliPayPaidConfirm(String orderId, String tsn, Long amount) {
//        return orderPaidConfirm(PaymentType.ALIPAY.getKey(), orderId, tsn, amount);
        return true;
    }

    /**
     * 微信支付确认
     */
    @Transactional("jpaTransactionManager")
    @Override
    public boolean wechatPaidConfirm(String orderId, String tsn, Long amount) {
        return orderPaidConfirm(PaymentType.WECHAT.getKey(), orderId, tsn, amount);
    }
    
    /**
     * 连亿家支付完成后操作
     */
    @Transactional("jpaTransactionManager")
    @Override
    public boolean lianyijiaPaidConfirm(String orderId, String tsn, Long amount) {
//        return orderPaidConfirm(PaymentType.LIANYIJIA.getKey(), orderId, tsn, amount);
        return true;
    }
    
    /**
     * 兴业银行支付确认
     */
    @Transactional("jpaTransactionManager")
    @Override
    public boolean ibPayPaidConfirm(String orderId, String tsn, Long amount) {
//        return orderPaidConfirm(PaymentType.IBPAY.getKey(), orderId, tsn, amount);
        return true;
    }
    
    /**
     * 订单支付确认
     */
    @Transactional("jpaTransactionManager")
    private boolean orderPaidConfirm(String paymentType, String orderId, String tsn, Long amount) {
        // 订单验证
        MOrderMainEntity order = mOrderMainDao.findOne(orderId);
        if (null == order || !order.getActualAmount().equals(amount.longValue())) {
            return false;
        }
        if (OrderStatus.WAIT_START.getKey().equals(order.getStatus())) {
            return true;
        }

        // 库存处理
        processStock(order);

        // 积分处理
        if(!OrderSourceEnum.offlineOrderSource().contains(order.getSource())){
            addPoint(order.getMemberId(), orderId, order.getActualAmount());
        }

        String status = null;
        // 订单状态变更记录
        if(OrderSourceEnum.offlineOrderSource().contains(order.getSource())){
        	status = OrderStatus.COMPLETE.getKey();
        }else{
        	status = OrderStatus.WAIT_START.getKey();
        }
        updateOrderStatus(orderId, status, order.getMemberId());

        // 账单处理
        processOrderBills(order, mapPayType2BillType(paymentType));
        
        // 防止上次回调未处理完，生成新数据
        order = mOrderMainDao.findOne(orderId);
        if (OrderStatus.WAIT_START.getKey().equals(order.getStatus())) {
            throw new WRWException("订单已支付");
        }
        
        // 订单信息更新
        order.setStatus(status);
        order.setTsn(tsn);
        order.setPaymentType(paymentType);
        order.setPayDate(new Date());
        order.setPaymentStatus(PaymentStatus.paid.getKey());
        mOrderMainDao.save(order);
        
        // 缓存处理
//        orderNoticeService.newOrderNoticeByOrg(order.getOrgId(), orderId);
        
        return true;
    }
    
    /**
     * Description: 生成账单
     *
     * @param order
     *            订单信息
     */
    @Transactional("jpaTransactionManager")
    private void processOrderBills(MOrderMainEntity order, String billType) {
        List<MOrderBillingEntity> billings = new ArrayList<>();
        
        Long memberId = order.getMemberId();
        String orderId = order.getOrderId();
        Date now = new Date();
        
        // 优惠券账单
        MOrderBillingEntity couponBilling = mOrderBillingDao.findByOrderIdAndBillType(orderId, BillType.COUPON.getKey());
        if( null != couponBilling ){
            couponBilling.setPayTime(new Date());
            couponBilling.setStatus(BillStatus.PAID.getKey());
            couponBilling.setModifyDate(now);
            couponBilling.setModifyId(memberId);
            billings.add(couponBilling);
        }
        
        // 运费账单
        MOrderBillingEntity shipBilling = mOrderBillingDao.findByOrderIdAndBillType(orderId, BillType.SHIP_AMOUNT.getKey());
        if( null != shipBilling ){
            shipBilling.setPayTime(new Date());
            shipBilling.setStatus(BillStatus.PAID.getKey());
            shipBilling.setModifyDate(now);
            shipBilling.setModifyId(memberId);
            billings.add(shipBilling);
        }
       
        // 在线支付账单
        billings.add(generateBilling(memberId, orderId, billType, order.getActualAmount(), order.getTsn()));
        
        mOrderBillingDao.save(billings);
    }
    
    /**
     * Description: 保存订单操作记录
     *
     * @param status
     * @param orderIds
     */
    @Transactional(value = "jpaTransactionManager")
    private void updateOrderStatus(String orderId, String operType, Long memberId) {
       MOrderOperEntity orderOper = mOrderOperDao.findByOrderIdAndOpertype(orderId, operType);
       if (null == orderOper) {
           orderOper = new MOrderOperEntity();
           orderOper.setOrderId(orderId);
           orderOper.setOpertype(operType);
       }
       orderOper.setComment("订单状态更新:" + OrderStatus.getValue(operType));
       orderOper.setUserId(memberId);
       orderOper.setOperDate(new Date());
       mOrderOperDao.save(orderOper);
    }
    
    /**
     * Description: 生成账单
     *
     * @return
     */
    private MOrderBillingEntity generateBilling(Long memberId, String orderId, String billType, Long amount, String tsn) {
        MOrderBillingEntity billing = generateBilling(memberId, orderId, billType, amount);
        billing.setTsn(tsn);
        return billing;
    }
    
    /**
     * Description: 生成账单
     *
     * @return
     */
    private MOrderBillingEntity generateBilling(Long memberId, String orderId, String billType, Long amount) {
        Date now = new Date();
        
        MOrderBillingEntity billing = mOrderBillingDao.findByOrderIdAndBillType(orderId, billType);
        if (null == billing) {
            billing = new MOrderBillingEntity();
            billing.setOrderId(orderId);
            billing.setBillType(billType);
            billing.setCreateDate(new Date());
            billing.setCreateId(memberId);
        }
        billing.setStatus(BillStatus.PAID.getKey());
        billing.setAmount(amount);
        billing.setActualAmount(amount);
        billing.setPayTime(now);
        return billing;
    }

    /**
     * Description: 生成余额流水
     *
     * @param order
     *            订单信息
     * @param account
     *            账户信息
     */
    @Transactional("jpaTransactionManager")
    private void saveAcctRecord(MOrderMainEntity order, MAccountEnity account) {
        MAcctRecordEntity record = new MAcctRecordEntity();

        record.setAcctId(account.getAcctId());
        record.setChangeValue(order.getActualAmount());
        record.setCreateDate(new Date());
        record.setCreateId(account.getMemberId());
        record.setOrderId(order.getOrderId());
        record.setType(AcctRecordType.SPEND.getKey());
        record.setRemark("订单支付:" + order.getOrderId());

        mAcctRecordDao.save(record);
    }

    /**
     * Description: 库存处理
     *
     * @param order
     *            订单信息
     */
    @Transactional("jpaTransactionManager")
    private void processStock(MOrderMainEntity order) {
        Long orgId = order.getOrgId();
        
        // 【配送方式】路由配送商
        if (ShipmentType.delivery.getKey().equals(order.getShipmentType())
                || OrderSourceEnum.offlineOrderSource().contains(order.getSource())) {
            Map<Long, Long> goodNumMap = getGoodNumFromOrderDetail(order.getOrderId());
            
            if(!OrderSourceEnum.offlineOrderSource().contains(order.getSource())){
                orgId = getDealer(order, goodNumMap);
            }
            if (null == orgId) {
                order.setIfException(OrderExceptionEnum.ABNORMAL.getKey());
            } else {
                decStock(goodNumMap, orgId, order.getMemberId());
            }
        }

        // 订单信息更新
        if (ShipmentType.express.getKey().equals(order.getShipmentType())) {
            order.setOrgId(0L);
            order.setOrgName(null);
        }
        else if (OrderExceptionEnum.ABNORMAL.getKey().equals(order.getIfException())) {
            order.setOrgId(-1L);
            order.setOrgName(null);
        } 
        else {
            SOrgEntity org = sOrgDao.findOne(orgId);
            order.setOrgId(orgId);
            order.setOrgName(org.getOrgName());
            order.setAssignDate(new Date());
        }
    }
    
    /**
     * Description: 获取订单，物料数量map
     *
     * @param details
     * @return
     */
    private Map<Long, Long> getGoodNumFromOrderDetail(String orderId) {
        Map<Long, Long> goodNumMap = new HashMap<>();
        
        for (MOrderDetailEntity detail : mOrderDetailDao.findByOrderId(orderId)) {
            Long goodId = detail.getGoodId();
            
            if (goodNumMap.containsKey(goodId)) {
                goodNumMap.put(goodId, goodNumMap.get(goodId) + detail.getNum() * detail.getGoodNum());
            } else {
                goodNumMap.put(goodId, detail.getNum() * detail.getGoodNum());
            }
        }
        
        return goodNumMap;
    }

    /**
     * Description: 获取有效配送商Id
     *
     * @param address
     *            收货地址
     * @param goodNumMap
     *            产品购买量Map
     * @return
     */
    private Long getDealer(MOrderMainEntity order, Map<Long, Long> goodNumMap) {
        MAddressEntity address = mAddressDao.findOne(order.getAddressId());
        
        // 获取市级下所有地区ID
        SRegionEntity region = sRegionDao.findOne(address.getRegionId());
        List<Integer> regionIds = sRegionDao.findIdByParentId(region.getParentId());
        regionIds.add(region.getParentId());

        // 获取市内的配送商
        List<Long> dealerIds = sOrgDao.findOrgIdByRegionInAndOrgType(regionIds, OrgTypeEnum.Z.getKey());
        if (dealerIds.isEmpty()) {
            return null;
        }

        // 获取配送商库存信息
        List<SStockEntity> stocks = sStockDao.findByOrgIdInAndGoodsIdIn(dealerIds, goodNumMap.keySet());
        Map<Long, List<SStockEntity>> stockMap = divideStock(stocks);

        // 获取最近的配送商
        List<Long> validDealerIds = stockMatch(stockMap, goodNumMap);
        Long dealerId = getNearestDealer(sOrgDao.findAll(validDealerIds), address.getLat(), address.getLng());

        return dealerId;
    }

    /**
     * Description: 获取最近配送商
     *
     * @param dealers
     *            配送商
     * @param lat
     *            纬度
     * @param lng
     *            经度
     * @return
     */
    private Long getNearestDealer(List<SOrgEntity> dealers, String lat, String lng) {
        if (WRWUtil.isEmpty(lat) || WRWUtil.isEmpty(lng)) {
            return null;
        }
        Long dealerId = null;
        
        double minDistance = Double.MAX_VALUE;
        for (SOrgEntity dealer : dealers) {
            if (!SOrgStatusEnum.NORMAL.getCode().equals(dealer.getStatus())) {
                continue;
            }
            if (WRWUtil.isEmpty(dealer.getLat()) || WRWUtil.isEmpty(dealer.getLng())) {
                continue;
            }

            double distance = LocationUtil.getDistance_M(Double.valueOf(dealer.getLat()), Double.valueOf(dealer.getLng()), Double.valueOf(lat),
                    Double.valueOf(lng));
            if (distance < minDistance) {
                minDistance = distance;
                dealerId = dealer.getOrgId();
            }
        }

        // 仅返回7km范围内的配送商
        if (minDistance > 7000) {
            return null;
        }

        return dealerId;
    }

    /**
     * Description: 配送商库存匹配
     *
     * @param stockMap
     * @param goodNumMap
     * @return
     */
    private List<Long> stockMatch(Map<Long, List<SStockEntity>> stockMap, Map<Long, Long> goodNumMap) {
        List<Long> dealerIds = new ArrayList<>();

        for (Map.Entry<Long, List<SStockEntity>> entry : stockMap.entrySet()) {
            boolean valid = true;

            int matchSize = 0;
            for (SStockEntity stock : entry.getValue()) {
                if (stock.getStockNum() < goodNumMap.get(stock.getGoodsId())) {
                    valid = false;
                    break;
                }
                matchSize++;
            }

            if (valid && matchSize == goodNumMap.size()) {
                dealerIds.add(entry.getKey());
            }
        }
        return dealerIds;
    }

    /**
     * Description: 减库存
     *
     * @param productNumMap
     *            产品购买数量map
     * @param stocks
     *            配送商库存
     */
    @Transactional("jpaTransactionManager")
    private void decStock(Map<Long, Long> goodNumMap, Long orgId, Long memberId) {
        List<SStockEntity> stocks = sStockDao.findByOrgIdAndGoodsIdIn(orgId, goodNumMap.keySet());
        Date now = new Date();
        
        // 库存操作记录
        List<SStockRecordEntity> stockRecords = new ArrayList<>();
        for (SStockEntity stock : stocks) {
            Long goodNum = goodNumMap.get(stock.getGoodsId());
            stock.setStockNum(stock.getStockNum() - goodNum);

            SStockRecordEntity stockRecord = new SStockRecordEntity();
            stockRecord.setStockId(stock.getStockId());
            stockRecord.setChangeNum(goodNum);
            stockRecord.setOperId(memberId);
            stockRecord.setOperDate(now);
            stockRecord.setOperType(StockRecordTypeEmum.AUTOREDUCE.getCode());
            stockRecords.add(stockRecord);
        }

        sStockDao.save(stocks);
        sStockRecordDao.save(stockRecords);
    }

    /**
     * Description: 增加积分
     *
     * @param memberId
     *            用户ID
     * @param orderId
     *            订单ID
     * @param amount
     *            订单金额
     */
    @Transactional("jpaTransactionManager")
    private void addPoint(Long memberId, String orderId, Long amount) {
        // 积分处理
        SBasicParaEntity buyEntity = sBasicParaDao.findByTypeId(BasicTypeEnum.POINT_BUY.getKey()).get(0);
        Long pointDivide = Long.valueOf(buyEntity.getParaValue1());
        Long pointReward = Long.valueOf(buyEntity.getParaValue2());
        if (pointDivide <= 0 || pointReward <= 0) {
            return;
        }
        Long points = amount * pointReward / pointDivide + (amount * pointReward % pointDivide > 0 ? 1L : 0L); 
        
        // 用户积分
        MMemberEntity member = mMemberDao.findOne(memberId);
        member.setPoint(member.getPoint() + points);
        mMemberDao.save(member);

        // 积分记录
        MPointDetailEntity pointDetail = new MPointDetailEntity();
        pointDetail.setMemberId(memberId);
        pointDetail.setChangeValue(points);
        pointDetail.setType(PointsTypeEnum.CONSUME.getKey());
        pointDetail.setRemark("消费返积分");
        pointDetail.setOrderId(orderId);
        pointDetail.setCreateDate(new Date());
        pointDetail.setCreateId(memberId);
        mPointDetailDao.save(pointDetail);
    }

    /**
     * Description: 配送商库存划分map
     *
     * @param stocks
     * @return
     */
    private Map<Long, List<SStockEntity>> divideStock(List<SStockEntity> stocks) {
        Map<Long, List<SStockEntity>> stockMap = new HashMap<>();

        for (SStockEntity stock : stocks) {
            Long orgId = stock.getOrgId();
            if (!stockMap.containsKey(orgId)) {
                stockMap.put(orgId, new ArrayList<SStockEntity>());
            }
            stockMap.get(orgId).add(stock);
        }
        return stockMap;
    }

    /**
     * 
     * Description: 根据订单id和终端配送商id 减库存
     *
     * @param orderId
     * @param orgId
     */
    @Override
    @Transactional
    public void decStock(String orderId, Long orgId) {
        SOrgEntity org = sOrgDao.findOne(orgId);
        Map<Long, Long> goodNumMap = getGoodNumFromOrderDetail(orderId);
        List<Long> orgGoodIds = sStockDao.findGoodIdByOrgIdAndGoodsIdIn(orgId, goodNumMap.keySet());
        
        List<SStockEntity> newStocks = new ArrayList<SStockEntity>();
        for (Map.Entry<Long, Long> entry : goodNumMap.entrySet()) {
            if (orgGoodIds.contains(entry.getKey())) {
                continue;
            }
            SStockEntity newStock = new SStockEntity();
            newStock.setOrgId(orgId);
            newStock.setOrgName(org.getOrgName());
            newStock.setGoodsId(entry.getKey());
            newStock.setSafeNum(0L);
            newStock.setStandardNum(0L);
            newStock.setStockNum(0L);
            newStocks.add(newStock);
        }
        sStockDao.save(newStocks);
        
        // 更新库存记录
        decStock(goodNumMap, orgId, mOrderMainDao.findOne(orderId).getMemberId());
    }
    
    /**
     * Description: 支付类型转账单类型
     *
     * @param paymentType
     * @return
     */
    private String mapPayType2BillType(String paymentType) {
//        if (PaymentType.ALIPAY.getKey().equals(paymentType)) {
//            return BillType.ALIPAY.getKey();
//        }
        if (PaymentType.WECHAT.getKey().equals(paymentType)) {
            return BillType.WECHAT.getKey();
        }
//        if(PaymentType.QUICKPAY.getKey().equals(paymentType)){
//            return BillType.QUICK_PAY.getKey();
//        }
//        if(PaymentType.IBPAY.getKey().equals(paymentType)){
//            return BillType.IB_PAY.getKey();
//        }
//        if(PaymentType.LIANYIJIA.getKey().equals(paymentType)){
//            return BillType.LIANYIJIA.getKey();
//        }
        return null;
    }
    
}
