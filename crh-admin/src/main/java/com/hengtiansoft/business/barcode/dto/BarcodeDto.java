package com.hengtiansoft.business.barcode.dto;

import java.io.Serializable;

public class BarcodeDto implements Serializable {

    private static final long serialVersionUID = 2284458028675904576L;

    private String            url;                                    // 扫码信息

    private String            orderId;                                // 订单ID
    
    private String            status;                                 //状态

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
    
}
