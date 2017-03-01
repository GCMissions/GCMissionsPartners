package com.hengtiansoft.task.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hengtiansoft.task.service.AppProductSortService;

@Component
public class AppProductSortTask {

    @Autowired
    private AppProductSortService appProductSortService;
    
    @Scheduled(cron = "0 0/30 * * * ?")
    public void cancelOrder() {
        appProductSortService.productSortValue();
    }
}
