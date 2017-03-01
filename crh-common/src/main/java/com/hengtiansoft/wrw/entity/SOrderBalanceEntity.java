package com.hengtiansoft.wrw.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class Name: SOrderBalanceEntity
 * Description: 财务报表
 * 
 * @author chenghongtu
 */
@Entity
@Table(name = "S_ORDER_BALANCE")
public class SOrderBalanceEntity implements Serializable {

    private static final long serialVersionUID = -4359845889699672477L;

    @Id
    @Column(name = "ORDER_ID")
    private String            orderId;

    @Column(name = "SHIPMENT_TYPE")
    private String            shipmentType;

    @Column(name = "TOTAL_AMOUNT")
    private Long              totalAmount;

    @Column(name = "ACTUAL_AMOUNT")
    private Long              actualAmount;

    @Column(name = "COUPON_AMOUNT")
    private Long              couponAmount;

    @Column(name = "ACCT_AMOUNT")
    private Long              acctAmount;

    @Column(name = "SHIP_AMOUNT")
    private Long              shipAmount;

    @Column(name = "CREATE_DATE")
    private Date              createDate;

    @Column(name = "FINISH_DATE")
    private Date              finishDate;

    @Column(name = "PAY_DATE")
    private Date              payDate;

    @Column(name = "P_SHIP_PROFIT")
    private Long              pShipProfit;

    @Column(name = "P_PROD_PROFIT")
    private Long              pProdProfit;

    @Column(name = "Z_SHIP_PROFIT")
    private Long              zShipProfit;

    @Column(name = "Z_PROD_PROFIT")
    private Long              zProdProfit;

    @Column(name = "SHIP_PROFIT")
    private Long              shipProfit;

    @Column(name = "PROD_PROFIT")
    private Long              prodProfit;

    @Column(name = "CITY_ID")
    private Integer           cityId;

    @Column(name = "PROV_ID")
    private Integer           provId;

    @Column(name = "ORG_ID")
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
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
