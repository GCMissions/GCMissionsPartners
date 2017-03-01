package com.hengtiansoft.business.product.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.hengtiansoft.common.dto.RequestDto;
import com.hengtiansoft.wrw.entity.PProductEntity;

public class ProductSaveDto extends RequestDto {

    private static final long serialVersionUID = 1531732127195143994L;

    private Long productId;

    private Long brandId;

    private Long cateId;

    private String brandName;

    private String cateName;

    private String productCode;

    private String productName;

    private Long sort;

    private String unitName;

    private BigDecimal price;

    private Long costPrice;

    private Long weight;

    private String image;

    private String note;

    private String desc;

    private String url;

    private Integer specNum;

    private String promotion;

    private Date productionDate;

    private List<ProductAttrDto> listAttr;

    private List<Long> relationShipIds;

    private List<ProductImageDto> listImages;

    private List<PProductEntity> listProducts;

    private Long goodId;

    private String goodName;

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Long getCateId() {
        return cateId;
    }

    public void setCateId(Long cateId) {
        this.cateId = cateId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
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

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Long costPrice) {
        this.costPrice = costPrice;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public List<ProductAttrDto> getListAttr() {
        return listAttr;
    }

    public void setListAttr(List<ProductAttrDto> listAttr) {
        this.listAttr = listAttr;
    }

    public List<Long> getRelationShipIds() {
        return relationShipIds;
    }

    public void setRelationShipIds(List<Long> relationShipIds) {
        this.relationShipIds = relationShipIds;
    }

    public List<ProductImageDto> getListImages() {
        return listImages;
    }

    public void setListImages(List<ProductImageDto> listImages) {
        this.listImages = listImages;
    }

    public List<PProductEntity> getListProducts() {
        return listProducts;
    }

    public void setListProducts(List<PProductEntity> listProducts) {
        this.listProducts = listProducts;
    }

    public Integer getSpecNum() {
        return specNum;
    }

    public void setSpecNum(Integer specNum) {
        this.specNum = specNum;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

}
