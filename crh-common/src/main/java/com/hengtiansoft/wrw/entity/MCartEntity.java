package com.hengtiansoft.wrw.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "M_CART")
public class MCartEntity implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 2738177874910042101L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CART_ID")
    private Long              id;

    @Column(name = "MEMBER_ID")
    private Long              memberId;

    @Column(name = "GOOD_ID")
    private Long              goodId;

    @Column(name = "PRODUCT_ID")
    private Long              productId;

    @Column(name = "NUM")
    private Integer           num;

    @Column(name = "STATUS")
    private String            status;

    @Column(name = "CREATE_DATE")
    private Date              createDate;

    @Column(name = "SELECT_FLAG")
    private String            isSelected;
    
    @Column(name = "SPEC_INFO")
    private String            specInfo;
    
    
    @Column(name = "FLAG")
    private String            flag;

    @Column(name = "ACT_DATE")
    private Date            actDate;
    
    @Column(name = "PRICE")
    private Integer            price;
    
    @Column(name = "REQUIRE_FIELDS")
    private String            requireFields;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
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

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected;
    }

    public String getSpecInfo() {
        return specInfo;
    }

    public void setSpecInfo(String specInfo) {
        this.specInfo = specInfo;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Date getActDate() {
        return actDate;
    }

    public void setActDate(Date actDate) {
        this.actDate = actDate;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getRequireFields() {
        return requireFields;
    }

    public void setRequireFields(String requireFields) {
        this.requireFields = requireFields;
    }

    
}
