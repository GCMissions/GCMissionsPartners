package com.hengtiansoft.business.activity.dto;

import java.io.Serializable;

/**
 * 
 * Class Name: ActivityDetailSaveDto Description: 商品详情保存
 * 
 * @author chenghongtu
 *
 */
public class ActivityDetailSaveDto implements Serializable {

    private static final long serialVersionUID = -2004049706551789297L;
    
    private Long productId;
    
    private String description;
    
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescripition(String description) {
        this.description = description;
    }

}
