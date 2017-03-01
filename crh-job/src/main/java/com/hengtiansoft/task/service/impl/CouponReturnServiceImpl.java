package com.hengtiansoft.task.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.task.repository.IMCouponRepository;
import com.hengtiansoft.task.repository.IMOrderMainRepository;
import com.hengtiansoft.task.service.CouponReturnService;
import com.hengtiansoft.wrw.entity.MCouponEntity;
import com.hengtiansoft.wrw.entity.MOrderMainEntity;
import com.hengtiansoft.wrw.enums.CouponState;
import com.hengtiansoft.wrw.enums.OrderStatus;

@Service
public class CouponReturnServiceImpl implements CouponReturnService {

    @Autowired
    private IMOrderMainRepository mOrderMainRepository;

    @Autowired
    private IMCouponRepository    mCoupongRepository;

    /**
     * 返还优惠券
     */
    @Transactional("jpaTransactionManager")
    @Override
    public void returnCoupon() {
        // 获取已取消的订单ID
        List<String> canceledOrderIds = mOrderMainRepository.findOrderIdByStatus(OrderStatus.CANCELED.getKey());
        if (canceledOrderIds.isEmpty()) {
            return;
        }

        // 获取已取消订单，对应的已使用优惠券
        List<MCouponEntity> usedCoupons = mCoupongRepository.findByStatusAndUsedOrderIdIn(CouponState.USED.getKey(), canceledOrderIds);

        // 优惠券按照订单划分
        Map<String, List<MCouponEntity>> orderCouponMap = new HashMap<>();
        for (MCouponEntity usedCoupon : usedCoupons) {
            String orderId = usedCoupon.getUsedOrderId();
            if (!orderCouponMap.containsKey(orderId)) {
                orderCouponMap.put(orderId, new ArrayList<MCouponEntity>());
            }
            orderCouponMap.get(orderId).add(usedCoupon);
        }

        // 返还优惠券
        List<MCouponEntity> couponsToReturn = new ArrayList<>();
        List<MOrderMainEntity> ordersToFix = new ArrayList<>();

        List<MOrderMainEntity> orders = mOrderMainRepository.findAll(orderCouponMap.keySet());
        for (MOrderMainEntity order : orders) {
            Date cancelDate = order.getModifyDate();
            if (null == cancelDate) {
                order.setModifyDate(new Date());
                ordersToFix.add(order);
                continue;
            }

            if (getLeftReturnTime(cancelDate).longValue() <= 0) {
                couponsToReturn.addAll(orderCouponMap.get(order.getOrderId()));
            }
        }
        mOrderMainRepository.save(ordersToFix);

        // 优惠券状态更新
        Date now = new Date();
        for (MCouponEntity couponToReturn : couponsToReturn) {
            if (now.before(couponToReturn.getEffectDate())) {
                couponToReturn.setStatus(CouponState.INVALID.getKey());
            } else if (now.after(couponToReturn.getInvalidDate())) {
                couponToReturn.setStatus(CouponState.EXPIRED.getKey());
            } else {
                couponToReturn.setStatus(CouponState.VALID.getKey());
            }
            couponToReturn.setUsedDate(null);
            couponToReturn.setUsedOrderId(null);
        }
        mCoupongRepository.save(couponsToReturn);

    }

    /**
     * Description: 获取剩余优惠券保留时间（秒）
     *
     * @param cancelDate
     *            订单取消时间
     * @return
     */
    private Long getLeftReturnTime(Date cancelDate) {
        Long gapTime = new Date().getTime() - cancelDate.getTime();
        Long leftPayTime = 5 * 60 * 60 - gapTime / 1000;

        return leftPayTime;
    }

}
