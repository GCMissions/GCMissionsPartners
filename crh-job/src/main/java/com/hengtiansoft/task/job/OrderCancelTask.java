package com.hengtiansoft.task.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hengtiansoft.task.service.OrderCancelService;

@Component
public class OrderCancelTask {
    
    @Autowired
    private OrderCancelService orderCancelService;
    
    /**
     * 每隔5分钟进行一次待付款订单超时取消
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    public void cancelOrder() {
        orderCancelService.cancelOrder();
    }
}
