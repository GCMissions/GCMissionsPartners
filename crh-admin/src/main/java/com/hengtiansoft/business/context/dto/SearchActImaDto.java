package com.hengtiansoft.business.context.dto;

import java.util.List;

import com.hengtiansoft.common.dto.PagingDto;

public class SearchActImaDto extends PagingDto<ActImagDto> {

    private static final long serialVersionUID = 1L;

    private Long productId;
    
    private String cateName;
    
    private String startDate;
    
    private String endDate;
    
    private String actName;
    
    private String status;
    
    private String actCode;
    
    private List<String> imageList;

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

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public String getActCode() {
        return actCode;
    }

    public void setActCode(String actCode) {
        this.actCode = actCode;
    }
}
