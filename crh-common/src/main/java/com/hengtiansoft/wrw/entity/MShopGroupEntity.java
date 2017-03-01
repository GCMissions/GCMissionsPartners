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
 * The persistent class for the M_SHOP_GROUP database table.
 */
@Entity
@Table(name = "M_SHOP_GROUP")
public class MShopGroupEntity implements Serializable {

    private static final long serialVersionUID = 8645618371998155797L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GROUP_ID")
    private Long              groupId;

    @Column(name = "OPINION")
    private String            opinion;

    @Column(name = "ADDRESS")
    private String            address;

    @Column(name = "COMPANY")
    private String            company;

    @Column(name = "ADDRESS_CONTACT")
    private String            addressContact;

    @Column(name = "ADDRESS_PHONE")
    private String            addressPhone;

    @Column(name = "CREATE_DATE")
    private Date              createDate;

    @Column(name = "CREATE_ID")
    private Long              createId;

    @Column(name = "MEMBER_ID")
    private Long              memberId;

    @Column(name = "MODIFY_DATE")
    private Date              modifyDate;

    @Column(name = "MODIFY_ID")
    private Long              modifyId;

    @Column(name = "NEEDS")
    private String            needs;

    @Column(name = "STATUS")
    private String            status;

    public MShopGroupEntity() {}

    public Long getGroupId() {
        return this.groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressContact() {
        return this.addressContact;
    }

    public void setAddressContact(String addressContact) {
        this.addressContact = addressContact;
    }

    public String getAddressPhone() {
        return this.addressPhone;
    }

    public void setAddressPhone(String addressPhone) {
        this.addressPhone = addressPhone;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getCreateId() {
        return this.createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Long getMemberId() {
        return this.memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Date getModifyDate() {
        return this.modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Long getModifyId() {
        return this.modifyId;
    }

    public void setModifyId(Long modifyId) {
        this.modifyId = modifyId;
    }

    public String getNeeds() {
        return this.needs;
    }

    public void setNeeds(String needs) {
        this.needs = needs;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
