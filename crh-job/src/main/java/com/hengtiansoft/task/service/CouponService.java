package com.hengtiansoft.task.service;

/**
 * 
* Class Name: CouponService
* Description: 优惠券Service
* @author chengchaoyin
*
 */
public interface CouponService {
    
    /**
     * 
    * Description: 将失效时间过期的优惠券自动变更状态
    *
    * @author chengchaoyin
     */
    void updateCouponStatusByTime();
}
