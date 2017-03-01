package com.hengtiansoft.task.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.task.repository.IMOrderMainRepository;

@Component
public class PastActivitiesUpdateTask {

    @Autowired
    private IMOrderMainRepository orderMainRepository;
    
    
    /**
    * Description: 定时更新过期订单状态 每天陵城00:01分跑定时任务
    *
    */
    @Transactional
    @Scheduled(cron = "0 1 0 * * ?")
    public void productShare() {
        orderMainRepository.updatePastActivities();
    }
    
}
