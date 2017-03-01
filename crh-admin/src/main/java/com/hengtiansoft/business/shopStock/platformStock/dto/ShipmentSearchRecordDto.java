/*
 * Project Name: wrw-admin
 * File Name: ShipmentSearchDto.java
 * Class Name: ShipmentSearchDto
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

import java.io.Serializable;
import java.util.Date;

import com.hengtiansoft.wrw.enums.OrgTypeEnum;
import com.hengtiansoft.wrw.enums.ShipmentState;

/**
* Class Name: ShipmentSearchDto
* Description: 
* @author xianghuang
*
*/
public class ShipmentSearchRecordDto implements Serializable {
    private static final long serialVersionUID = 7739139859611263751L;

    // 发货单Id
    private String            orderId;

    // 发货单
    private String            expressNum;

    private String            orgName;

    private String            orgCate;

    // 发货数量
    private Long            shipmentNum;

    private Date            createDate;

    private String            shipmentState;
    
    public ShipmentSearchRecordDto(){}

    /**
    * ShipmentSearchRecordDto Constructor
    *
    * @param orderId
    * @param expressNum
    * @param orgName
    * @param orgCate
    * @param shipmentNum
    * @param createDate
    * @param shipmentState
    */
    /*o.expressNum,o.orgName,o.orgType,sum(d.num),o.status,o.orderId,o.createDate*/
    public ShipmentSearchRecordDto(String expressNum, String orgName, String orgCate,Long dNum,String shipmentState,String orderId,Date createDate) {
        this.orderId = orderId;
        this.expressNum = expressNum;
        this.orgName = orgName;
        this.orgCate = OrgTypeEnum.getValue(orgCate);
        this.shipmentNum = dNum;
        this.createDate = createDate;
        this.shipmentState = ShipmentState.getValue(shipmentState);
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getExpressNum() {
        return expressNum;
    }

    public void setExpressNum(String expressNum) {
        this.expressNum = expressNum;
    }

    public String getOrgCate() {
        return orgCate;
    }

    public void setOrgCate(String orgCate) {
        this.orgCate = orgCate;
    }

    public Long getShipmentNum() {
        return shipmentNum;
    }

    public void setShipmentNum(Long shipmentNum) {
        this.shipmentNum = shipmentNum;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getShipmentState() {
        return shipmentState;
    }

    public void setShipmentState(String shipmentState) {
        this.shipmentState = shipmentState;
    }

}
