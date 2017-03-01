/*
 * Project Name: wrw-job
 * File Name: KdProductSaleTask.java
 * Class Name: KdProductSaleTask
 *
 * Copyright 2014 Hengtian Software Inc
 *
 * Licensed under the Hengtiansoft
 *
 * http://www.hengtiansoft.com
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hengtiansoft.task.job.wrkd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hengtiansoft.task.service.KdProductSaleService;

/**
 * Class Name: KdProductSaleTask
 * Description: 酷袋商品上下架
 * @author zhongyidong
 *
 */
@Component
public class KdProductSaleTask {
    
    private static final Logger LOGGER  = LoggerFactory.getLogger(KdProductSaleTask.class);

    @Autowired
    private KdProductSaleService kdProductSaleService;
    
    /**
     * 每隔一分钟的第5秒进行一次商品上下架操作
     */
    @Scheduled(cron = "5 0/1 * * * ?")
    public void saleOnAndOff() {
        // 商品上架
        kdProductSaleService.putOnSale();
        // 商品下架
        kdProductSaleService.putOffSale();
        
        LOGGER.error("酷袋商品上下架job执行成功！");
    }

}
