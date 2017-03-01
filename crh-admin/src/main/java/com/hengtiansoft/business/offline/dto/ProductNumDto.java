package com.hengtiansoft.business.offline.dto;

import java.io.Serializable;

public class ProductNumDto implements Serializable {

    private static final long serialVersionUID = 2192969651403933138L;

    private Long              productId;

    private Integer           num;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "ProductNumDto [productId=" + productId + ", num=" + num + "]";
    }
}
