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
@Table(name = "M_ADDRESS")
public class MAddressEntity implements Serializable {

    private static final long serialVersionUID = -96070263839854830L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADDRESS_ID")
    private Long              id;

    @Column(name = "MEMBER_ID")
    private Long              memberId;

    @Column(name = "STATUS")
    private String            status;

    @Column(name = "ADDRESS")
    private String            address;

    @Column(name = "ZIPCODE")
    private Long              zipcode;

    @Column(name = "ADDRESS_PHONE")
    private String            addressPhone;

    @Column(name = "ADDRESS_CONTACT")
    private String            addressContact;

    @Column(name = "REGION_ID")
    private Integer           regionId;

    @Column(name = "LONGITUDE")
    private String            lng;

    @Column(name = "LATITUDE")
    private String            lat;

    @Column(name = "DEF_FLAG")
    private String            defFlag;

    @Column(name = "CREATE_DATE")
    private Date              createDate;

    @Column(name = "CREATE_ID")
    private Long              createId;

    @Column(name = "MODIFY_DATE")
    private Date              modifyDate;

    @Column(name = "MODIFY_ID")
    private Long              modifyId;

    @Column(name = "AREA_NAME")
    private String            areaName;

    @Column(name = "ID_CARD")
    private String            idCard;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressPhone() {
        return addressPhone;
    }

    public void setAddressPhone(String addressPhone) {
        this.addressPhone = addressPhone;
    }

    public String getAddressContact() {
        return addressContact;
    }

    public void setAddressContact(String addressContact) {
        this.addressContact = addressContact;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public String getDefFlag() {
        return defFlag;
    }

    public void setDefFlag(String defFlag) {
        this.defFlag = defFlag;
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

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public Long getZipcode() {
        return zipcode;
    }

    public void setZipcode(Long zipcode) {
        this.zipcode = zipcode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    @Override
    public String toString() {
        return "MAddressEntity [id=" + id + ", memberId=" + memberId + ", status=" + status + ", address=" + address + ", addressPhone="
                + addressPhone + ", addressContact=" + addressContact + ", regionId=" + regionId + ", lng=" + lng + ", lat=" + lat + ", defFlag="
                + defFlag + ", createDate=" + createDate + ", createId=" + createId + ", modifyDate=" + modifyDate + ", modifyId=" + modifyId + "]";
    }
}
