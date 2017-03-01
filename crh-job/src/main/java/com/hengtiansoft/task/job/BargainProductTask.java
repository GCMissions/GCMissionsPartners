package com.hengtiansoft.task.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hengtiansoft.task.service.BargainProductService;

/**
 * 
 * Class Name: BargainProductTask Description: 看见商品定时job
 * 
 * @author chengchaoyin
 *
 */
@Component
public class BargainProductTask {

    @Autowired
    private BargainProductService bargainProductService;

    /**
     * 每隔1秒进行一次看见商品状态变更
     */
    @Scheduled(cron = "0/1 * *  * * ?")
    public void updateBargainProductStatus() {
        
        bargainProductService.updateBargainProductInStatus();
        
        bargainProductService.updateBargainProductOutStatus();
    }
}
