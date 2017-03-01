/*
 * Project Name: wrw-job
 * File Name: OrderBalanceTask.java
 * Class Name: OrderBalanceTask
 * Copyright 2014 Hengtian Software Inc
 * Licensed under the Hengtiansoft
 * http://www.hengtiansoft.com
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hengtiansoft.task.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hengtiansoft.task.service.OrderBalanceService;
import com.hengtiansoft.wrw.entity.MOrderMainEntity;
import com.hengtiansoft.wrw.entity.MOrderReturnEntity;

/**
 * Class Name: OrderBalanceTask
 * Description: 返利计算
 * 
 * @author chengminmiao
 */
@Component
public class OrderBalanceTask {

    @Autowired
    private OrderBalanceService orderBalanceService;

    /**
     * Description: 返利
     */
//    @Scheduled(cron = "0/5 * *  * * ?")
//    public void orderBalance() {
//        System.out.println("Job begin： orderBalance");
//        List<MOrderMainEntity> orderlist = orderBalanceService.getOrder();
//        for (MOrderMainEntity entity : orderlist) {
//            orderBalanceService.orderBalance(entity);
//
//        }
//    }

    /**
     * Description: 退货
     */
//    @Scheduled(cron = "0/5 * *  * * ?")
//    public void orderReturn() {
//        System.out.println("Job begin：orderReturn ");
//        List<MOrderReturnEntity> orderlist = orderBalanceService.getOrderReturn();
//        for (MOrderReturnEntity entity : orderlist) {
//            orderBalanceService.orderReturn(entity);
//
//        }
//    }

}
