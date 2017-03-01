package com.hengtiansoft.business.order.dto;

import java.io.Serializable;

public class OrderProductSearchDto implements Serializable{
    
    private static final long serialVersionUID = -5032628150903017818L;

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
