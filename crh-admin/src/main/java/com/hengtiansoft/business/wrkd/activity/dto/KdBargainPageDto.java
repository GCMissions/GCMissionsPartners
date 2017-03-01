package com.hengtiansoft.business.wrkd.activity.dto;

import com.hengtiansoft.common.dto.PagingDto;

/**
 * 
* Class Name: KdBargainPageDto
* Description: 24小时活动Dto
* @author chengchaoyin
*
 */
public class KdBargainPageDto extends PagingDto<KdBargainDto>{
    
    /**
    * Variables Name: serialVersionUID
    * Description: TODO
    * Value Description: TODO
    */
    private static final long serialVersionUID = 5387382027208849105L;

    /**
     * 开始时间
     */
    private String startDate;
    
    /**
     * 结束时间
     */
    private String endDate;
    
    /**
     * 上架时间
     */
    private String onTime;
    
    /**
     * 下架时间
     */
    private String offTime;
    
    /**
     * 状态（KdBargainStatusEnum）
     */
    private String status;
    
    /**
     * 活动名称
     */
    private String name;
    
    /**
     * 关联商品id
     */
    private Long productId;
    
    /**
     * 商品名称
     */
    private String productName;
    
    /**
     * 商品id
     */
    private String productCode;
    
    /**
     * 活动id
     */
    private Long id;
    
    
    
    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getOnTime() {
        return onTime;
    }

    public void setOnTime(String onTime) {
        this.onTime = onTime;
    }

    public String getOffTime() {
        return offTime;
    }

    public void setOffTime(String offTime) {
        this.offTime = offTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
