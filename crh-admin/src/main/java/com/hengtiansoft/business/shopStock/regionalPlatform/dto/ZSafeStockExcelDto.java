package com.hengtiansoft.business.shopStock.regionalPlatform.dto;

import java.io.Serializable;

public class ZSafeStockExcelDto implements Serializable {

    private static final long serialVersionUID = -5227989844667595710L;

    private String createDate;

    private String orgName;

    private String goodName;

    private String specs;

    private Integer stockNum;

    private Integer num;

    private Long orgId;

    private String orgNameP;

    private Long parentId;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getOrgNameP() {
        return orgNameP;
    }

    public void setOrgNameP(String orgNameP) {
        this.orgNameP = orgNameP;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getStockNum() {
        return stockNum;
    }

    public void setStockNum(Integer stockNum) {
        this.stockNum = stockNum;
    }

}
