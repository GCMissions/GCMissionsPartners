package com.hengtiansoft.wrw.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 退货表
 * 
 * @desc 使用代码生成器生成.
 * @date 2016/07/11
 */
@Entity
@Table(name = "M_ORDER_RETURN")
public class MOrderReturnEntity implements Serializable {

    private static final long serialVersionUID = -2446098591862571754L;

    @Id
    @Column(name = "RETURN_ID")
    private String            returnId;                                // 退货单ID

    @Column(name = "ORDER_MAIN_ID")
    private String            orderMainId;                             // 订单ID
    
    @Column(name = "ORDER_STATUS")
    private String           orderStatus;                              // 申请退货时订单状态

    @Column(name = "RETURN_AMOUNT")
    private Long              returnAmount;                            // 退款金额

    @Column(name = "RETURN_TYPE")
    private Integer           returnType;                              // 退货类型

    @Column(name = "RETURN_COUPON_AMOUNT")
    private Long              returnCouponAmount;                      // 优惠券退款金额

    @Column(name = "RETURN_SHIP_AMOUNT")
    private Long              returnShipAmount;                        // 运费退款金额

    @Column(name = "RETURN_STATUS")
    private Integer           returnStatus;                            // 退货单状态

    @Column(name = "RETURN_POINT")
    private String            returnPoint;                             // 退还积分

    @Column(name = "PAY_ACCOUNT")
    private String            payAccount;                              // 退款账号

    @Column(name = "CREATE_ID")
    private Long              createId;

    @Column(name = "CREATE_DATE")
    private Date              createDate;

    @Column(name = "UPDATE_ID")
    private Long              updateId;

    @Column(name = "UPDATE_DATE")
    private Date              updateDate;

    public void setReturnId(String returnId) {
        this.returnId = returnId;
    }

    public String getReturnId() {
        return this.returnId;
    }

    public void setOrderMainId(String orderMainId) {
        this.orderMainId = orderMainId;
    }

    public String getOrderMainId() {
        return this.orderMainId;
    }

    public void setReturnAmount(Long returnAmount) {
        this.returnAmount = returnAmount;
    }

    public Long getReturnAmount() {
        return this.returnAmount;
    }

    public void setReturnCouponAmount(Long returnCouponAmount) {
        this.returnCouponAmount = returnCouponAmount;
    }

    public Long getReturnCouponAmount() {
        return this.returnCouponAmount;
    }

    public void setReturnShipAmount(Long returnShipAmount) {
        this.returnShipAmount = returnShipAmount;
    }

    public Long getReturnShipAmount() {
        return this.returnShipAmount;
    }

    public void setReturnStatus(Integer returnStatus) {
        this.returnStatus = returnStatus;
    }

    public Integer getReturnStatus() {
        return this.returnStatus;
    }

    public void setReturnPoint(String returnPoint) {
        this.returnPoint = returnPoint;
    }

    public String getReturnPoint() {
        return this.returnPoint;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Long getCreateId() {
        return this.createId;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }

    public Long getUpdateId() {
        return this.updateId;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getUpdateDate() {
        return this.updateDate;
    }

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
    }

    public Integer getReturnType() {
        return returnType;
    }

    public void setReturnType(Integer returnType) {
        this.returnType = returnType;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

}
