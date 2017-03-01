/*
 * Project Name: wrw-admin
 * File Name: ShipmentService.java
 * Class Name: ShipmentService
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
package com.hengtiansoft.business.shopStock.platformStock.service;

import java.util.List;

import com.hengtiansoft.business.shopStock.platformStock.dto.ShipmentDetailDto;
import com.hengtiansoft.business.shopStock.platformStock.dto.ShipmentSaveDto;
import com.hengtiansoft.business.shopStock.platformStock.dto.ShipmentSearchDto;
import com.hengtiansoft.common.dto.ResultDto;

/**
 * Class Name: ShipmentService Description: TODO
 * 
 * @author xianghuang
 *
 */
public interface PlatformShipmentService {

    /**
     * Description: 查询发货单
     *
     * @param dto
     * @return
     */
    ShipmentSearchDto search(ShipmentSearchDto dto);

    /**
     * Description: 获取发货单详情
     *
     * @param shimentId
     * @return
     */
    ShipmentDetailDto get(String shipmentId);

    /**
     * Description: 创建发货单
     *
     * @param dto
     * @return
     */
    ResultDto<String> save(ShipmentSaveDto dto);

    /**
     * Description: 发货给P
     *
     * @param orderId
     *            发货单Id
     * @return
     */
    ResultDto<String> deliverGoods(String orderId);

    List<List<String>> findAllOrder(String orderId);

    List<List<String>> toExcel(String orgType);

}
