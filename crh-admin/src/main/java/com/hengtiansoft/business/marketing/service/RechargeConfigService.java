/*
 * Project Name: wrw-admin
 * File Name: RechargeConfigService.java
 * Class Name: RechargeConfigService
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
package com.hengtiansoft.business.marketing.service;

import com.hengtiansoft.business.marketing.dto.CouponPageDto;
import com.hengtiansoft.business.marketing.dto.RechargeConfDto;
import com.hengtiansoft.business.marketing.dto.RechargeConfPageDto;
import com.hengtiansoft.common.dto.ResultDto;

/**
 * Class Name: RechargeConfigService
 * Description: TODO
 * 
 * @author chengminmiao
 */
public interface RechargeConfigService {

    /**
     * Description: 查询充值配置列表
     * 
     * @param dto
     * @return
     */
    void getList(RechargeConfPageDto dto);

    /**
     * Description: 添加充值配置
     *
     * @param dto
     * @return
     */
    ResultDto<String> save(RechargeConfDto dto);

    /**
     * Description: 查找充值配送
     *
     * @param configId
     * @return
     */
    RechargeConfDto find(Long configId);

    /**
     * Description: 查找可用优惠券
     * 
     * @param dto
     * @return
     */
    void getCoupon(CouponPageDto dto);

    /**
     * Description: 删除
     *
     * @param configId
     * @return
     */
    ResultDto<String> delete(Long configId);

}
