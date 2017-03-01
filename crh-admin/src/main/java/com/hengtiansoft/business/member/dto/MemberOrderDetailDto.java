package com.hengtiansoft.business.member.dto;

import java.io.Serializable;

public class MemberOrderDetailDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 有效订单数
     */
    private String effectiveOrderCount;
    
    /**
     * 退款次数
     */
    private String refundCount;
    
    /**
     * 有效消费金额
     */
    private String effectivePrice;
    
    /**
     * 取消订单数
     */
    private String cancelOrderCount;

    public String getEffectiveOrderCount() {
        return effectiveOrderCount;
    }

    public void setEffectiveOrderCount(String effectiveOrderCount) {
        this.effectiveOrderCount = effectiveOrderCount;
    }

    public String getRefundCount() {
        return refundCount;
    }

    public void setRefundCount(String refundCount) {
        this.refundCount = refundCount;
    }

    public String getEffectivePrice() {
        return effectivePrice;
    }

    public void setEffectivePrice(String effectivePrice) {
        this.effectivePrice = effectivePrice;
    }

    public String getCancelOrderCount() {
        return cancelOrderCount;
    }

    public void setCancelOrderCount(String cancelOrderCount) {
        this.cancelOrderCount = cancelOrderCount;
    }
}
