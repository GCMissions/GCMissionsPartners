/*
 * Project Name: wrw-admin
 * File Name: ShipmentSearchDto.java
 * Class Name: ShipmentSearchDto
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
package com.hengtiansoft.business.shopStock.platformStock.dto;

import com.hengtiansoft.common.dto.PagingDto;

/**
 * Class Name: ShipmentSearchDto
 * Description:
 * 
 * @author xianghuang
 */
public class ShipmentSearchDto extends PagingDto<ShipmentSearchRecordDto> {

    private static final long serialVersionUID = 1624961855823328872L;

    // 发货单
    private String            shipmentConde;

    private String            orgCode;

    private String            orgName;

    private String            orgCate;

    private String            createDateStart;

    private String            createDateEnd;

    // 发货状态
    private String            shipmentState;

    // 库存状态
    private String            stockState;

    public String getShipmentConde() {
        return shipmentConde;
    }

    public void setShipmentConde(String shipmentConde) {
        this.shipmentConde = shipmentConde;
    }

    public String getOrgName() {
        return orgName;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgCate() {
        return orgCate;
    }

    public String getCreateDateStart() {
        return createDateStart;
    }

    public void setCreateDateStart(String createDateStart) {
        this.createDateStart = createDateStart;
    }

    public String getCreateDateEnd() {
        return createDateEnd;
    }

    public void setCreateDateEnd(String createDateEnd) {
        this.createDateEnd = createDateEnd;
    }

    public void setOrgCate(String orgCate) {
        this.orgCate = orgCate;
    }

    public String getShipmentState() {
        return shipmentState;
    }

    public void setShipmentState(String shipmentState) {
        this.shipmentState = shipmentState;
    }

    public String getStockState() {
        return stockState;
    }

    public void setStockState(String stockState) {
        this.stockState = stockState;
    }

}
