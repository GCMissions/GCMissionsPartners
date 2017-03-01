/*
 * Project Name: wrw-common
 * File Name: OrderReturnService.java
 * Class Name: OrderReturnService
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
package com.hengtiansoft.business.service;

import com.hengtiansoft.common.dto.ResultDto;

/**
* Class Name: OrderReturnIntegrationService
* Description: 退货管理
* @author xianghuang
*
*/
public interface OrderReturnIntegrationService {
    
    /**
     * Description: 申请退货
     *
     * @param orderId
     * @return
     */
     ResultDto<String> applyForReturnOrder(String orderId);
     
     /**
      * Description: 申请退款
      *
      * @param orderId
      * @return
      */
      ResultDto<String> applyForReturnAmount(String orderId);
      
     /**
     * Description: 创建退货单
     *
     * @param orderId
     * @param returnOrderStatus
     */
     void createReturnOrder(String orderId,String returnOrderStatus);

}
