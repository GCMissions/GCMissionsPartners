package com.hengtiansoft.business.wrkd.activity.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hengtiansoft.common.serialize.CustomDateTimeSerializer;

/**
 * 
* Class Name: BargainDto
* Description: 24小时活动
* @author chengchaoyin
*
 */
public class KdBargainDto {
    
    private Long id;
    
    /**
     * 活动名称
     */
    private String name;
    
    /**
     * 关联商品id
     */
    private Long productId;
    
    /**
     * 关联商品id
     */
    private String productCode;
    
    /**
     * 关联商品名
     */
    private String productName;
    
    /**
     * 创建时间
     */
    private Date createDate;
    
    /**
     * 生效时间
     */
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    private Date effectiveStartDate;
    
    /**
     * 失效时间
     */
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    private Date effectiveEndDate;
    
    /**
     * 状态（KdBargainStatusEnum）
     */
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getEffectiveStartDate() {
        return effectiveStartDate;
    }

    public void setEffectiveStartDate(Date effectiveStartDate) {
        this.effectiveStartDate = effectiveStartDate;
    }

    public Date getEffectiveEndDate() {
        return effectiveEndDate;
    }

    public void setEffectiveEndDate(Date effectiveEndDate) {
        this.effectiveEndDate = effectiveEndDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
