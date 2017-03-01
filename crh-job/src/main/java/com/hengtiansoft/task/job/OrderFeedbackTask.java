package com.hengtiansoft.task.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hengtiansoft.task.service.OrderFeedbackService;

@Component
public class OrderFeedbackTask {

    @Autowired
    private OrderFeedbackService orderFeedbackService;
    
    /**
     * 每天凌晨1点进行一次对超过10天的未评价订单添加系统评价
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void autoFeedback() {
        orderFeedbackService.addFeedback();
    }
}
