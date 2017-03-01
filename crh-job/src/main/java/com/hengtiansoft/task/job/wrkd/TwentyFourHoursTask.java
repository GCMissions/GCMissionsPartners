package com.hengtiansoft.task.job.wrkd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hengtiansoft.task.service.KdTwentyFourHoursService;

/**
 * 
 * Class Name: TwentyFourHoursTask Description: 24小时活动相关的job
 * 
 * @author chengchaoyin
 *
 */
@Component
public class TwentyFourHoursTask {

    @Autowired
    private KdTwentyFourHoursService kdTwentyFourHoursService;

    /**
     * 每隔1秒进行一次活动状态变更
     */
    @Scheduled(cron = "0/1 * *  * * ?")
    public void updateBargainProductStatus() {
        kdTwentyFourHoursService.updateActStatusByTime();
    }

    /**
     * 用户参加24小时后3天未领钱则全部砍价的金额拿去做公益
     *
     */
    @Scheduled(cron = "0/1 * * * * ?")
    public void updateReturnType() {
        kdTwentyFourHoursService.updateReturnType();
    }

}
