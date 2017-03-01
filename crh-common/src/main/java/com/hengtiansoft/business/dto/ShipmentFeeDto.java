package com.hengtiansoft.business.dto;

import java.io.Serializable;

public class ShipmentFeeDto implements Serializable {

    private static final long serialVersionUID = 7318958999783163835L;

    private Long              totalAmount      = 0L;

    private Long              actualAmount     = 0L;

    private Long              discountAmount   = 0L;

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

    public Long getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Long discountAmount) {
        this.discountAmount = discountAmount;
    }

    @Override
    public String toString() {
        return "ShipmentAmountDto [totalAmount=" + totalAmount + ", actualAmount=" + actualAmount + ", discountAmount=" + discountAmount + "]";
    }
}
