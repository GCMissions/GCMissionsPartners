/*
 * Project Name: wrw-job
 * File Name: KdCharityServiceImpl.java
 * Class Name: KdCharityServiceImpl
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
package com.hengtiansoft.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.task.service.KdCharityService;
import com.hengtiansoft.wrw.dao.KdCharityDao;
import com.hengtiansoft.wrw.enums.KdCharityStatusEnum;

/**
 * Class Name: KdCharityServiceImpl
 * Description: 酷袋公益活动
 * @author zhongyidong
 *
 */
@Service
public class KdCharityServiceImpl implements KdCharityService {

    @Autowired
    private KdCharityDao kdCharityDao;
    
    @Override
    @Transactional
    public void updateStatus() {
        // 活动开启
        kdCharityDao.startActivity(KdCharityStatusEnum.NOTSTART.getKey(), KdCharityStatusEnum.ONGOING.getKey());
        // 活动结束
        kdCharityDao.endActivity(KdCharityStatusEnum.ONGOING.getKey(), KdCharityStatusEnum.FINISHED.getKey());
       
    }

}
