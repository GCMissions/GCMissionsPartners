package com.hengtiansoft.business.advertise.dto;

import java.io.Serializable;

public class PurchaseInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String url;
    
    private String info;
    
    private String operation;
    
    private String skipUrl;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getSkipUrl() {
        return skipUrl;
    }

    public void setSkipUrl(String skipUrl) {
        this.skipUrl = skipUrl;
    }
}
