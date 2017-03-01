package com.hengtiansoft.business.shopMall.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.hengtiansoft.wrw.entity.PCategoryEntity;

public class ProductCatogoryDto implements Serializable {

    private static final long     serialVersionUID = 420864962391512882L;

    private Long                  cateId;

    private Long                  typeId;

    private String                cateName;

    private Long                  parentId;

    private String                status;

    private String                path;

    private String                sort;

    private String                image;

    private Date                  createDate;

    private Long                  createId;

    private Date                  modifyDate;

    private Long                  modifyId;

    private List<PCategoryEntity> children;

    public Long getCateId() {
        return cateId;
    }

    public void setCateId(Long cateId) {
        this.cateId = cateId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Long getModifyId() {
        return modifyId;
    }

    public void setModifyId(Long modifyId) {
        this.modifyId = modifyId;
    }

    public List<PCategoryEntity> getChildren() {
        return children;
    }

    public void setChildren(List<PCategoryEntity> children) {
        this.children = children;
    }

}
