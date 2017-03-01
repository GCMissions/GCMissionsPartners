/*
 * Project Name: wrw-job
 * File Name: KdCharityTask.java
 * Class Name: KdCharityTask
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hengtiansoft.task.service.KdCharityService;

/**
 * Class Name: KdCharityTask
 * Description: 酷袋公益活动
 * @author zhongyidong
 *
 */
@Component
public class KdCharityTask {

    @Autowired
    private KdCharityService kdCharityService;
    
    /**
     * Description: 酷袋公益活动状态变更（每分钟执行一次）
     *
     */
    @Scheduled(cron = "2 0/1 * * * ?")
    public void updateStatus(){
        kdCharityService.updateStatus();
    }
}
