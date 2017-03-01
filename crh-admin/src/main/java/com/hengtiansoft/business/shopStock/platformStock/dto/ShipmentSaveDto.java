/*
 * Project Name: wrw-admin
 * File Name: ShipmentSaveDto.java
 * Class Name: ShipmentSaveDto
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
package com.hengtiansoft.business.shopStock.platformStock.dto;

import java.util.List;

/**
* Class Name: ShipmentSaveDto
* Description: 创建发货单模型
* @author xianghuang
*
*/
public class ShipmentSaveDto {
    private Long orgId;
    
    private String type;//发货单类型，0普通发货单，1预警发货单
    
    List<GoodsStockDto> shipmentDetails;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public List<GoodsStockDto> getShipmentDetails() {
        return shipmentDetails;
    }

    public void setShipmentDetails(List<GoodsStockDto> shipmentDetails) {
        this.shipmentDetails = shipmentDetails;
    }
    
}
