package com.hengtiansoft.business.activity.dto;

import com.hengtiansoft.common.dto.PagingDto;

/**
 * 
 * Class Name: ActivitySearchDto 
 * Description: 商品查询DTO
 * 
 * @author jiafengchen
 *
 */
public class ActivitySearchDto extends PagingDto<ActivityDto>{

    private static final long serialVersionUID = 1L;
   
    // 商品一级品类
    private String firstCateId;
    
    // 商品二级品类
    private String secondCateId;
    
    // 服务商
    private String orgId;
    
    private String productName;
    
    private String productCode;
    
    private String lowPrice;
    
    private String highPrice;
    
    // vip标识
    private String vipFlag;
    
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

    public String getOrgId() {
        return orgId;
    }
    
    public String getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(String lowPrice) {
        this.lowPrice = lowPrice;
    }

    public String getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(String highPrice) {
        this.highPrice = highPrice;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getFirstCateId() {
        return firstCateId;
    }

    public void setFirstCateId(String firstCateId) {
        this.firstCateId = firstCateId;
    }

    public String getSecondCateId() {
        return secondCateId;
    }

    public void setSecondCateId(String secondCateId) {
        this.secondCateId = secondCateId;
    }

    public String getVipFlag() {
        return vipFlag;
    }

    public void setVipFlag(String vipFlag) {
        this.vipFlag = vipFlag;
    }
    
}
