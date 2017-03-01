package com.hengtiansoft.business.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.business.service.RechargePaymentService;
import com.hengtiansoft.common.converter.ConverterService;
import com.hengtiansoft.common.exception.WRWException;
import com.hengtiansoft.wrw.dao.MAccountDao;
import com.hengtiansoft.wrw.dao.MAcctRechargeDao;
import com.hengtiansoft.wrw.dao.MAcctRecordDao;
import com.hengtiansoft.wrw.dao.MCouponDao;
import com.hengtiansoft.wrw.dao.SCouponConfigDao;
import com.hengtiansoft.wrw.dao.SRechargeCouponDao;
import com.hengtiansoft.wrw.entity.MAccountEnity;
import com.hengtiansoft.wrw.entity.MAcctRechargeEntity;
import com.hengtiansoft.wrw.entity.MAcctRecordEntity;
import com.hengtiansoft.wrw.entity.MCouponEntity;
import com.hengtiansoft.wrw.entity.SCouponConfigEntity;
import com.hengtiansoft.wrw.entity.SRechargeCouponEntity;
import com.hengtiansoft.wrw.enums.AcctRechargePayStatus;
import com.hengtiansoft.wrw.enums.AcctRecordType;
import com.hengtiansoft.wrw.enums.CouponState;
import com.hengtiansoft.wrw.enums.StatusEnum;

@Service
public class RechargePaymentServiceImpl implements RechargePaymentService {

    @Autowired
    private MAccountDao        mAccountDao;

    @Autowired
    private MAcctRecordDao     mAcctRecordDao;

    @Autowired
    private MAcctRechargeDao   mAcctRechargeDao;

    @Autowired
    private MCouponDao         mCouponDao;

    @Autowired
    private SCouponConfigDao   sCouponConfigDao;

    @Autowired
    private SRechargeCouponDao sRechargeCouponDao;

    /**
     * 充值确认
     */
    @Transactional("jpaTransactionManager")
    @Override
    public boolean rechargeConfirm(String paymentType, String orderId, String tsn, Long amount) {
        // 充值单校验
        MAcctRechargeEntity recharge = mAcctRechargeDao.findOne(orderId);
        if (null == recharge) {
            return false;
        }
        if (AcctRechargePayStatus.PAID.getKey().equals(recharge.getPayStatus())) {
            return true;
        }
        // 金额验证
        if (recharge.getAmount().longValue() != amount.longValue()) {
            return false;
        }
        
        // 账户更新
        Long memberId = recharge.getMemberId();
        MAccountEnity account = mAccountDao.findByMemberIdAndStatus(memberId, StatusEnum.NORMAL.getCode());
        if (null == account) {
            return false;
        }
        account.setBalance(account.getBalance() + amount);
        mAccountDao.save(account);

        // 记录账户流水
        MAcctRecordEntity record = new MAcctRecordEntity();
        record.setAcctId(account.getAcctId());
        record.setOrderId(orderId);
        record.setType(AcctRecordType.RECHARGE.getKey());
        record.setChangeValue(amount);
        record.setRemark("账户充值");
        record.setCreateId(memberId);
        record.setCreateDate(new Date());
        mAcctRecordDao.save(record);

        // 优惠券处理
        List<MCouponEntity> coupons = new ArrayList<>();
        
        Map<Long, Integer> couponNumMap = new HashMap<>();
        List<SRechargeCouponEntity> rechargeCoupons = sRechargeCouponDao.findByConfigId(recharge.getConfigId());
        for (SRechargeCouponEntity rechargeCoupon : rechargeCoupons) {
            couponNumMap.put(rechargeCoupon.getId().getCouponId(), rechargeCoupon.getNum());
        }
        
        List<SCouponConfigEntity> couponConfigs = sCouponConfigDao.findAll(couponNumMap.keySet());
        for (SCouponConfigEntity couponConfig : couponConfigs) {
            MCouponEntity coupon = ConverterService.convert(couponConfig, MCouponEntity.class);
            coupon.setCouponId(null);
            coupon.setCoupConId(couponConfig.getCouponId());
            coupon.setMemberId(memberId);
            coupon.setCreateDate(new Date());
            // 设置优惠券状态
            if (new Date().after(couponConfig.getInvalidDate())) {
                coupon.setStatus(CouponState.EXPIRED.getKey());
            } else if (new Date().before(couponConfig.getEffectDate())) {
                coupon.setStatus(CouponState.INVALID.getKey());
            } else {
                coupon.setStatus(CouponState.VALID.getKey());
            }
            
            for (int i = 0; i < couponNumMap.get(couponConfig.getCouponId()); i++) {
                MCouponEntity newCoupon = new MCouponEntity();
                coupons.add(ConverterService.convert(coupon, newCoupon));
            }
        }
        mCouponDao.save(coupons);
        
        // 防止上次回调未处理完，生成新数据
        recharge = mAcctRechargeDao.findOne(orderId);
        if (AcctRechargePayStatus.PAID.getKey().equals(recharge.getPayStatus())) {
            throw new WRWException("充值订单已支付");
        }
        
        // 充值单更新
        recharge.setPaymentType(paymentType);
        recharge.setTsn(tsn);
        recharge.setPayStatus(AcctRechargePayStatus.PAID.getKey());
        recharge.setPayDate(new Date());
        mAcctRechargeDao.save(recharge);
        
        return true;
    }
}
