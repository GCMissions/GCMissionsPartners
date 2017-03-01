package com.hengtiansoft.task.job.wrkd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hengtiansoft.task.service.KdOrderCancelService;

@Component
public class KdOrderCancelTask {
    
    @Autowired
    private KdOrderCancelService kdOrderCancelService;

    /**
     * 每隔5分钟进行一次待付款订单超时取消
     */
    @Scheduled(cron = "0 0/5 *  * * ?")
    public void cancelOrder() {
        kdOrderCancelService.cancelKdOrder();
    }
    
    /**
     * 
    * Description: 每隔5分钟的第10秒进行自动确认收货
    *
     */
    @Scheduled(cron = "10 0/5 *  * * ?")
    public void receiveOrder() {
        kdOrderCancelService.productReceive();
    }
}
