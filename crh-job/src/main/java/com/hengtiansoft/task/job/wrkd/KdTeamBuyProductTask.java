package com.hengtiansoft.task.job.wrkd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hengtiansoft.task.service.KdTeamBuyProductService;

@Component
public class KdTeamBuyProductTask {

    @Autowired
    private KdTeamBuyProductService kdTeamBuyProductService;

    /**
     * 每隔1秒进行一次看见团购状态变更
     */
    @Scheduled(cron = "0/1 * *  * * ?")
    public void updateKdTeamBuyProductStatus() {
        kdTeamBuyProductService.updateProductStatus();
    }
    
    /**
     * 每隔5分钟对已结束的未成团商品进行退款
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    public void returnTeamBuyAmount(){
        kdTeamBuyProductService.returnTeamBuyAmount();
    }
}
