package com.hengtiansoft.business.barcode.dto;

import java.io.Serializable;

public class BarcodeCodeDto implements Serializable{

    private static final long serialVersionUID = 7062457932546189261L;
    
    private String prefixCode;

    public String getPrefixCode() {
        return prefixCode;
    }

    public void setPrefixCode(String prefixCode) {
        this.prefixCode = prefixCode;
    }
    
    
}
