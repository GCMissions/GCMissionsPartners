package com.hengtiansoft.task.service;

/**
 * 
 * Class Name: OrderNoticeService Description: 商家时候有新订单的提醒
 * 
 * @author yigesong
 *
 */
public interface PayNoticeService {
    /**
     * 
     * Description: 商家判断当前是否有新订单接口
     *
     * @param orgId
     * @param orderId
     */
    void newOrderNoticeByOrg(Long orgId, String orderId);

    /**
     * 
     * Description: 商城和app下单时，更新缓存
     *
     * @param orgId
     * @return
     */
    boolean getOrderNotice(Long orgId);
}
