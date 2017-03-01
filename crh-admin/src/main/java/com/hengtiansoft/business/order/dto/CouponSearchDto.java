package com.hengtiansoft.business.order.dto;

import java.io.Serializable;

public class CouponSearchDto implements Serializable {

    private static final long serialVersionUID = 4563987028542525913L;

    private String orderId;

    private Integer currentPage = 1;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

}
