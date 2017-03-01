package com.hengtiansoft.task.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hengtiansoft.task.service.CouponReturnService;

@Component
public class CouponReturnTask {

    @Autowired
    private CouponReturnService couponReturnService;
    
    /**
     * Description: 每隔5分钟进行一次优惠券返还
     */
//    @Scheduled(cron = "0 0/5 * * * ?")
//    public void returnCoupon() {
//        couponReturnService.returnCoupon();
//    }
}