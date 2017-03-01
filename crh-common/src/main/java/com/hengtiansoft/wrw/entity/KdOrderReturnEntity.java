package com.hengtiansoft.wrw.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hengtiansoft.common.domain.BaseEntity;

@Entity
@Table(name = "kd_order_return")
public class KdOrderReturnEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

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

    @Column(name = "RETURN_SHIP_AMOUNT")
    private Long              returnShipAmount;                        // 运费退款金额

    @Column(name = "RETURN_STATUS")
    private Integer           returnStatus;                            // 退货单状态

    @Column(name = "PAY_ACCOUNT")
    private String            payAccount;                              // 退款账号

    @Column(name = "BALANCE_FLAG")
    private String            balanceFlag;
    
    @Column(name = "PRODUCT_ID")
    private Long productId;
    
    @Column(name = "RETURN_COUNT")
    private Long returnCount;
    
    @Column(name = "TYPE")
    private String type;
    
    @Column(name = "SPEC_INFO")
    private String specInfo;

    public String getReturnId() {
        return returnId;
    }

    public void setReturnId(String returnId) {
        this.returnId = returnId;
    }

    public String getOrderMainId() {
        return orderMainId;
    }

    public void setOrderMainId(String orderMainId) {
        this.orderMainId = orderMainId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(Long returnAmount) {
        this.returnAmount = returnAmount;
    }

    public Integer getReturnType() {
        return returnType;
    }

    public void setReturnType(Integer returnType) {
        this.returnType = returnType;
    }

    public Long getReturnShipAmount() {
        return returnShipAmount;
    }

    public void setReturnShipAmount(Long returnShipAmount) {
        this.returnShipAmount = returnShipAmount;
    }

    public Integer getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(Integer returnStatus) {
        this.returnStatus = returnStatus;
    }

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
    }

    public String getBalanceFlag() {
        return balanceFlag;
    }

    public void setBalanceFlag(String balanceFlag) {
        this.balanceFlag = balanceFlag;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getReturnCount() {
        return returnCount;
    }

    public void setReturnCount(Long returnCount) {
        this.returnCount = returnCount;
    }

    public String getSpecInfo() {
        return specInfo;
    }

    public void setSpecInfo(String specInfo) {
        this.specInfo = specInfo;
    }
}
