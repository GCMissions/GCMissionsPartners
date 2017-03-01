package com.hengtiansoft.task.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hengtiansoft.task.service.PShareService;

/**
* Class Name: ShareStatisticsTask
* Description: 统计分享次数定时任务
* @author changchen
*
*/
@Component
public class ShareStatisticsTask {

    @Autowired
    private PShareService pShareService;
    
    /**
     * 
    * Description: 每半小时统计商品被分享次数
    *
    * @author chengchaoyin
     */
    @Scheduled(cron = "0 0/30 * * * ?")
    public void productShare() {
        pShareService.pShareDeal();
    }
}
