/*
 * Project Name: wrw-admin
 * File Name: KdCharityService.java
 * Class Name: KdCharityService
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
package com.hengtiansoft.business.wrkd.activity.service;

import java.util.List;

import com.hengtiansoft.business.wrkd.activity.dto.KdActivityDetailRequestDto;
import com.hengtiansoft.business.wrkd.activity.dto.KdCharityDetailResponseDto;
import com.hengtiansoft.business.wrkd.activity.dto.KdCharityDto;
import com.hengtiansoft.business.wrkd.activity.dto.KdCharitySearchDto;
import com.hengtiansoft.common.dto.ResultDto;

/**
 * Class Name: KdCharityService
 * Description: 酷袋公益活动
 * @author zhongyidong
 *
 */
public interface KdCharityService {
    
    /**
     * Description: 查询公益活动
     *
     * @param charityId
     * @return
     */
    KdCharityDto findCharity(Long charityId);
    
    /**
     * Description: 条件查询公益活动
     * 
     * @paramsearchDto
     * @return
     */
    void searchCharity(KdCharitySearchDto searchDto);

    /**
     * Description: 保存公益活动
     *
     * @param charityDto
     * @return
     */
    ResultDto<Long> saveCharity(KdCharityDto charityDto);
    
    /**
     * Description: 保存公益活动详情
     *
     * @param charityId
     * @param detail
     * @return
     */
    ResultDto<String> saveDetail(Long charityId, String detail);
    
    /**
     * Description: 保存公益活动反馈详情
     *
     * @param charityId
     * @param feedback
     * @return
     */
    int saveFeedback(Long charityId, String feedback);
    
    /**
     * Description: 删除公益活动
     *
     * @param charityIds
     * @return
     */
    ResultDto<String> deleteCharity(List<Long> charityIds);
    
    /**
     * Description: 检查专辑下面是否有进行中的公益活动
     *
     * @param featureId
     * @return
     */
    boolean checkCharityByFeature(Long featureId);
    
    /**
     * Description: 更新公益活动状态
     *
     * @param featureId
     * @param status
     * @param userId
     * @return
     */
    boolean updateCharityStatus(Long featureId, String status, Long userId);
    
    /**
     * 
    * Description: 获取详情
    *
    * @param requestDto
    * @return
     */
    ResultDto<KdCharityDetailResponseDto> getDetail(KdActivityDetailRequestDto requestDto);
}
