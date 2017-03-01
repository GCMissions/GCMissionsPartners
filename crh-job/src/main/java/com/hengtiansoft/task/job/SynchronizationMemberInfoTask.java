package com.hengtiansoft.task.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hengtiansoft.task.service.SynchronizationMemberInfoService;

@Component
public class SynchronizationMemberInfoTask {

    @Autowired
    private SynchronizationMemberInfoService synchronizationMemberInfoService;
    
    
    /**
    * Description: 每天固定时间执行 每天凌晨1点及上午11点
    *
    */
    @Scheduled(cron ="0 0 01,11 * * ?")
    public void execute(){
        synchronizationMemberInfoService.synchronizationMember();
    }
    
}
