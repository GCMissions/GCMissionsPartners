package com.hengtiansoft.business.shopStock.zStock.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Class Name: PunishmentOrderDto
 * 
 * Description: 惩罚订单Dto
 * 
 * @author zhishenghong
 *
 */
public class OrderRewardDto implements Serializable {

    private static final long serialVersionUID = -8738717856537205623L;

    private Long rewardId;

    private String orderId;

    private Date assignDate;// 派送时间

    private Date rewardDate;// 惩罚时间
    
    private String rewardType;

    private Long orgId;

    private String orgName;

    private Date createDate;

    private String type;// 惩罚类型 2-超时接单 3-超时派送

    private Double amount;


    public Date getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(Date assignDate) {
        this.assignDate = assignDate;
    }

    public Date getRewardDate() {
        return rewardDate;
    }

    public void setRewardDate(Date rewardDate) {
        this.rewardDate = rewardDate;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Long getRewardId() {
        return rewardId;
    }

    public void setRewardId(Long rewardId) {
        this.rewardId = rewardId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getRewardType() {
        return rewardType;
    }

    public void setRewardType(String rewardType) {
        this.rewardType = rewardType;
    }
}
