package com.hengtiansoft.task.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hengtiansoft.task.service.SheifService;

@Component
public class SheifTask {

    @Autowired
    private SheifService sheifService;
    
    @Scheduled(cron = "0 0 0 *  * ?")
    public void updateSheif() {
        sheifService.updateSheif();
    }
}
