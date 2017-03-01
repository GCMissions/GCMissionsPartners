package com.hengtiansoft.task.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hengtiansoft.task.service.AppStatisticsService;

/**
 * 
* Class Name: AppStatisticsTask
* Description: App相关统计定时任务
* @author yiminli
*
 */
@Component
public class AppStatisticsTask {

    @Autowired
    private AppStatisticsService appStatisticsService;
    
    /**
     * 
    * Description: 每半小时统计商品分享次数、销售量、点击数
    *
    * @author yiminli
     */
    @Scheduled(cron = "0 0/30 * * * ?")
    public void productShare() {
        appStatisticsService.statisticsDeal();
    }
}
