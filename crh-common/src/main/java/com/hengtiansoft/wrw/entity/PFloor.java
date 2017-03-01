package com.hengtiansoft.wrw.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class Name: PFloor Description: 商品楼层信息实体
 * 
 * @author yigesong
 */
@Entity
@Table(name = "P_FLOOR")
public class PFloor implements Serializable {

    private static final long serialVersionUID = -7657874825786460975L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FLOOR_ID")
    private Long              floorId;

    @Column(name = "REGION_ID")
    private Integer           regionId;

    @Column(name = "FLOOR_TYPE")
    private String            floorType;

    @Column(name = "PRODUCT_ID")
    private Long              productId;

    @Column(name = "SORT")
    private Long              sort;

    @Column(name = "STATUS")
    private String            status;

    @Column(name = "CREATE_ID")
    private Long              createId;

    @Column(name = "CREATE_DATE")
    private Date              createDate;

    @Column(name = "MODIFY_ID")
    private Long              modifyId;

    @Column(name = "MODIFY_DATE")
    private Date              modifyDate;

    @Column(name = "IMAGE")
    private String            image;

    public Long getFloorId() {
        return floorId;
    }

    public void setFloorId(Long floorId) {
        this.floorId = floorId;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public String getFloorType() {
        return floorType;
    }

    public void setFloorType(String floorType) {
        this.floorType = floorType;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getModifyId() {
        return modifyId;
    }

    public void setModifyId(Long modifyId) {
        this.modifyId = modifyId;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
