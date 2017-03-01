package com.hengtiansoft.business.product.dto;

import com.hengtiansoft.common.dto.PagingDto;

public class ProductShiefSearchDto extends PagingDto<ProductShiefDto> {

    private static final long serialVersionUID = -6109254441381723098L;
    //商品一级品类
    private String firstCateId;
    
  //商品二级品类
    private String secondCateId;
    
    //服务商
    private String orgId;
    
    private String            productCode;

    private String            productName;

    private String            saleStatus;

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

    public String getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(String saleStatus) {
        this.saleStatus = saleStatus;
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

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

}
