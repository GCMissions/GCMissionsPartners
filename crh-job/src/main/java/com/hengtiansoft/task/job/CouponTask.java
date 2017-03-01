package com.hengtiansoft.task.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hengtiansoft.task.service.CouponService;

@Component
public class CouponTask {

    @Autowired
    private CouponService couponService;
    
    /**
     * Description: 每1秒检查过期的优惠券修改成已过期
     */
    @Scheduled(cron = "0/1 * *  * * ?")
    public void returnCoupon() {
        couponService.updateCouponStatusByTime();
    }
}