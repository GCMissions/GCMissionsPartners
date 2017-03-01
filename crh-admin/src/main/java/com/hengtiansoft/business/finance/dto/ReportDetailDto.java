/*
 * Project Name: wrw-admin
 * File Name: ReportDetailDto.java
 * Class Name: ReportDetailDto
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
package com.hengtiansoft.business.finance.dto;

import java.io.Serializable;

/**
 * Class Name: ReportDetailDto
 * Description: TODO
 * 
 * @author chengminmiao
 */
public class ReportDetailDto implements Serializable {

    private static final long serialVersionUID = -1229755019693817082L;

    private String            orderId;

    private String            shipmentType;

    private Long              totalAmount;

    private Long              actualAmount;

    private Long              couponAmount;

    private Long              acctAmount;

    private Long              shipAmount;

    private Long              pShipProfit;

    private Long              pProdProfit;

    private Long              zShipProfit;

    private Long              zProdProfit;

    private Long              shipProfit;

    private Long              prodProfit;

    private Integer           cityId;

    private Integer           provId;

    private Long              orgId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getShipmentType() {
        return shipmentType;
    }

    public void setShipmentType(String shipmentType) {
        this.shipmentType = shipmentType;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(Long actualAmount) {
        this.actualAmount = actualAmount;
    }

    public Long getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(Long couponAmount) {
        this.couponAmount = couponAmount;
    }

    public Long getAcctAmount() {
        return acctAmount;
    }

    public void setAcctAmount(Long acctAmount) {
        this.acctAmount = acctAmount;
    }

    public Long getShipAmount() {
        return shipAmount;
    }

    public void setShipAmount(Long shipAmount) {
        this.shipAmount = shipAmount;
    }

    public Long getpShipProfit() {
        return pShipProfit;
    }

    public void setpShipProfit(Long pShipProfit) {
        this.pShipProfit = pShipProfit;
    }

    public Long getpProdProfit() {
        return pProdProfit;
    }

    public void setpProdProfit(Long pProdProfit) {
        this.pProdProfit = pProdProfit;
    }

    public Long getzShipProfit() {
        return zShipProfit;
    }

    public void setzShipProfit(Long zShipProfit) {
        this.zShipProfit = zShipProfit;
    }

    public Long getzProdProfit() {
        return zProdProfit;
    }

    public void setzProdProfit(Long zProdProfit) {
        this.zProdProfit = zProdProfit;
    }

    public Long getShipProfit() {
        return shipProfit;
    }

    public void setShipProfit(Long shipProfit) {
        this.shipProfit = shipProfit;
    }

    public Long getProdProfit() {
        return prodProfit;
    }

    public void setProdProfit(Long prodProfit) {
        this.prodProfit = prodProfit;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getProvId() {
        return provId;
    }

    public void setProvId(Integer provId) {
        this.provId = provId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

}
