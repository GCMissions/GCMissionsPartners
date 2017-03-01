/*
 * Project Name: wrw-admin
 * File Name: ShipmentDetailDto.java
 * Class Name: ShipmentDetailDto
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

import java.util.Date;
import java.util.List;

/**
* Class Name: ShipmentDetailDto
* Description: TODO
* @author xianghuang
*
*/
public class ShipmentDetailDto {
    
    private String orderId;
    
    private String orgCode;
    
    private String orgName;
    
    private String orgCate;
    
    

    private String shipmentStatus;
    
    //联系人
    private String createName;
    
    private String phone;
    
    private Date createDate;
    
    private Date receivingDate;
    
    //发货信息
    private List<GoodsStockDto> productList;
    

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgCate() {
        return orgCate;
    }

    public void setOrgCate(String orgCate) {
        this.orgCate = orgCate;
    }

    public String getShipmentStatus() {
        return shipmentStatus;
    }

    public void setShipmentStatus(String shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
    }

  

    public String getCreateName() {
        return createName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getReceivingDate() {
        return receivingDate;
    }

    public void setReceivingDate(Date receivingDate) {
        this.receivingDate = receivingDate;
    }

    public List<GoodsStockDto> getProductList() {
        return productList;
    }

    public void setProductList(List<GoodsStockDto> productList) {
        this.productList = productList;
    }
    
}
