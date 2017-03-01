package com.hengtiansoft.business.product.dto;

import java.util.Date;

import com.hengtiansoft.common.dto.PagingDto;

public class ProductSearchDto extends PagingDto<ProductDto> {

    private static final long serialVersionUID = -174566935961392305L;

    private String productName;

    private String productCode;

    private String cateId;

    private String brandId;

    private Date startDate;

    private Date endDate;

    private Long relateProductId;

    private Long goodsId;

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCateId() {
        return cateId;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Long getRelateProductId() {
        return relateProductId;
    }

    public void setRelateProductId(Long relateProductId) {
        this.relateProductId = relateProductId;
    }

}
