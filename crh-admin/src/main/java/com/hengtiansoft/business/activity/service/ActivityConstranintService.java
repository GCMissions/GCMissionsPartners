/*
 * Project Name: wrw-admin
 * File Name: ActivityConstranintService.java
 * Class Name: ActivityConstranintService
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
package com.hengtiansoft.business.activity.service;

import java.util.List;

import com.hengtiansoft.business.activity.dto.ActivityConstranintDto;
import com.hengtiansoft.business.activity.dto.ActivityPartakeDto;
import com.hengtiansoft.wrw.entity.ActivityConstranint;

/**
 * Class Name: ActivityConstranintService
 * Description: 活动约束表
 * @author zhongyidong
 *
 */
public interface ActivityConstranintService {
    
    /**
     * Description: 查询某个活动的约束信息
     *
     * @param productId
     * @return
     */
    ActivityConstranintDto findByActId(Long productId);
    
    /**
     * Description: 查询参与人信息
     *
     * @param productId
     * @return
     */
    List<ActivityPartakeDto> findPartakeInfo(Long productId);
    
    /**
     * Description: 保存某个活动的约束信息
     *
     * @param actConstranintDto
     * @return
     */
    ActivityConstranint addContranint(ActivityConstranintDto actConstranintDto);
    
    /**
     * Description: 更新某个活动的约束信息
     *
     * @param actConstranintDto
     * @return
     */
    ActivityConstranint updateContranint(ActivityConstranintDto actConstranintDto);
}
