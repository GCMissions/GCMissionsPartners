/*
 * Project Name: wrw-job
 * File Name: OrderPressTask.java
 * Class Name: OrderPressTask
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
package com.hengtiansoft.task.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hengtiansoft.task.service.OrderPressTimeoutCheckService;

/**
* Class Name: OrderPressTask
* 
* Description: 催单超时惩罚任务
* 
* @author huizhuang
*/
@Component
public class OrderPressTimeoutTask {
    
    @Autowired
    private OrderPressTimeoutCheckService orderPressTimeoutCheckService;

    // 每5秒检查一次催单超时
//    @Scheduled(cron = "0/5 * *  * * ?")
//    public void orderPressTimeoutCheck() {
//        orderPressTimeoutCheckService.check();
//    }
}
