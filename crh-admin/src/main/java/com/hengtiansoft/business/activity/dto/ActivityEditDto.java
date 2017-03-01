package com.hengtiansoft.business.activity.dto;
/**
 * 
* Class Name: ActivityEditDto
* Description: 商品编辑对象
* @author chenghongtu
*
 */
public class ActivityEditDto extends ActivitySaveDto {

    private static final long serialVersionUID = 1665061197168948513L;

    private Long productId;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    
}
