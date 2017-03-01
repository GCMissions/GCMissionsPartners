package com.hengtiansoft.wrw.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the S_SHIPMENT_ORDER database table.
 */
@Entity
@Table(name = "S_SHIPMENT_ORDER")
public class SShipmentOrderEntity implements Serializable {

    private static final long serialVersionUID = 5910910716833696681L;

    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    private String            orderId;

    private String            address;

    @Column(name = "CREATE_DATE")
    private Date              createDate;

    @Column(name = "CREATE_ID")
    private Long              createId;

    @Column(name = "EXPRESS_NAME")
    private String            expressName;

    @Column(name = "EXPRESS_NUM")
    private String            expressNum;

    @Column(name = "MODIFY_DATE")
    private Date              modifyDate;

    @Column(name = "MODIFY_ID")
    private Long              modifyId;

    private String            phone;

    @Column(name = "RECEIVING_DATE")
    private Date              receivingDate;

    @Column(name = "RECEIVING_ID")
    private Long              receivingId;

    @Column(name = "REGION_ID")
    private Integer           regionId;

    private String            remark;

    @Column(name = "SHIPMENT_DATE")
    private Date              shipmentDate;

    @Column(name = "SHIPMENT_ID")
    private Long              shipmentId;
    
    @Column(name = "TYPE")
    private String              type;

   

    private String            status;

    @Column(name = "TOTAL_NUM")
    private Long              totalNum;

    public SShipmentOrderEntity() {}

    public String getOrderId() {
        return this.orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getCreateId() {
        return this.createId;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public String getExpressName() {
        return this.expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    public String getExpressNum() {
        return this.expressNum;
    }

    public void setExpressNum(String expressNum) {
        this.expressNum = expressNum;
    }

    public Date getModifyDate() {
        return this.modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Long getModifyId() {
        return this.modifyId;
    }

    public void setModifyId(Long modifyId) {
        this.modifyId = modifyId;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getReceivingDate() {
        return this.receivingDate;
    }

    public void setReceivingDate(Date receivingDate) {
        this.receivingDate = receivingDate;
    }

    public Long getReceivingId() {
        return this.receivingId;
    }

    public void setReceivingId(Long receivingId) {
        this.receivingId = receivingId;
    }

    public Integer getRegionId() {
        return this.regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getShipmentDate() {
        return this.shipmentDate;
    }

    public void setShipmentDate(Date shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public Long getShipmentId() {
        return this.shipmentId;
    }

    public void setShipmentId(Long shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getTotalNum() {
        return this.totalNum;
    }

    public void setTotalNum(Long totalNum) {
        this.totalNum = totalNum;
    }

}
