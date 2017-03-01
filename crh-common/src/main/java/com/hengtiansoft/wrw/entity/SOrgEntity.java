package com.hengtiansoft.wrw.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hengtiansoft.common.domain.BaseEntity;

@Entity
@Table(name = "S_ORG")
public class SOrgEntity extends BaseEntity {

    private static final long serialVersionUID = -4638616628609252102L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORG_ID")
    private Long              orgId;

    @Column(name = "ORG_CODE", insertable = true, updatable = false)
    private String            orgCode;

    @Column(name = "ORG_NAME")
    private String            orgName;

    @Column(name = "ORG_TYPE", insertable = true, updatable = false)
    private String            orgType;

    @Column(name = "STATUS")
    private String            status;

    @Column(name = "PARENT_ID")
    private Long              parentId;

    @Column(name = "ADDRESS")
    private String            address;

    @Column(name = "LONGITUDE")
    private String            lng;

    @Column(name = "LATITUDE")
    private String            lat;

    @Column(name = "PHONE")
    private String            phone;
    
    @Column(name = "SERVICE_PHONE")
    private String            servicePhone;

    @Column(name = "CONTACT")
    private String            contact;
    
    @Column(name = "BUSINESSER")
    private String            businesser;
    
    @Column(name = "REGISTRATION_LICENSE")
    private String            registrationLicense;
    
    @Column(name = "INTRODUCE")
    private String            introduce;

    @Column(name = "REGION")
    private Integer           region;

    @Column(name = "BANK_NAME")
    private String            bankName;

    @Column(name = "BARCODE_FLAG")
    private String            barcodeFlag;

    @Column(name = "BRANCH_NAME")
    private String            branchName;

    @Column(name = "BANK_ACCT")
    private String            bankAcct;

    @Column(name = "BANK_CONTACT")
    private String            bankContact;

    @Column(name = "BUSINESS_NUMBER")
    private String            businessNumber;

    @Column(name = "TERMINAL_NUMBER")
    private String            terminalNumber;

    @Column(name = "DEVICE_NUMBER")
    private String            deviceNumber;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Integer getRegion() {
        return region;
    }

    public void setRegion(Integer region) {
        this.region = region;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBarcodeFlag() {
        return barcodeFlag;
    }

    public void setBarcodeFlag(String barcodeFlag) {
        this.barcodeFlag = barcodeFlag;
    }

    public String getBankAcct() {
        return bankAcct;
    }

    public void setBankAcct(String bankAcct) {
        this.bankAcct = bankAcct;
    }

    public String getBankContact() {
        return bankContact;
    }

    public void setBankContact(String bankContact) {
        this.bankContact = bankContact;
    }

    public String getBusinessNumber() {
        return businessNumber;
    }

    public void setBusinessNumber(String businessNumber) {
        this.businessNumber = businessNumber;
    }

    public String getTerminalNumber() {
        return terminalNumber;
    }

    public void setTerminalNumber(String terminalNumber) {
        this.terminalNumber = terminalNumber;
    }

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public String getBusinesser() {
        return businesser;
    }

    public void setBusinesser(String businesser) {
        this.businesser = businesser;
    }

    public String getRegistrationLicense() {
        return registrationLicense;
    }

    public void setRegistrationLicense(String registrationLicense) {
        this.registrationLicense = registrationLicense;
    }

    public String getServicePhone() {
        return servicePhone;
    }

    public void setServicePhone(String servicePhone) {
        this.servicePhone = servicePhone;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

}
