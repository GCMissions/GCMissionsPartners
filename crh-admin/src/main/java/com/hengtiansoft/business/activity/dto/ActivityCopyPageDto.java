package com.hengtiansoft.business.activity.dto;

import com.hengtiansoft.common.dto.PagingDto;
/**
 * 
* Class Name: ActivityCopyPageDto
* Description: 复制已有活动（已有活动查询条件）
* @author chenghongtu
*
 */
public class ActivityCopyPageDto extends PagingDto<ActivityCopyDto> {

    private static final long serialVersionUID = -4203306060790993218L;
    
    private Long categoryId;
    
    private String productName;
    
    private Long orgId;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

}
