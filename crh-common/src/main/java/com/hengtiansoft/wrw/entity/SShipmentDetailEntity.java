package com.hengtiansoft.wrw.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the S_SHIPMENT_DETAIL database table.
 */
@Entity
@Table(name = "S_SHIPMENT_DETAIL")
public class SShipmentDetailEntity implements Serializable {

    private static final long serialVersionUID = -7226174137508322561L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DETAIL_ID")
    private Long            detailId;
    
    @Column(name = "ORDER_ID")
    private String            orderId;
    
    @Column(name = "GOOD_ID")
    private Long            goodId;
    
    @Column(name = "PRODUCT_ID")
    private Long            productId;
    
    @Column(name = "PRODUCT_NAME")
    private String            productName;
    
    @Column(name = "UNIT")
    private String            unit;
    
    @Column(name = "NUM")
    private Long            num;

    public Long getDetailId() {
        return detailId;
    }

    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    

}
