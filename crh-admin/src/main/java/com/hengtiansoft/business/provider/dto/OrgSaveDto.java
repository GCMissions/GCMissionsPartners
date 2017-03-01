package com.hengtiansoft.business.provider.dto;

import java.io.Serializable;
/**
 * 
* Class Name: OrgSaveDto
* Description: TODO
* @author yiminli
*
 */
public class OrgSaveDto implements Serializable {

    private static final long serialVersionUID = 1L;

    // 服务商编号
    private String orgCode;
    
    // 服务商名称
    private String orgName;
    
    // 企业法人姓名
    private String businesser;
    
    // 工商执照注册号
    private String registrationLicense;
    
    // 联系人姓名
    private String contact;
    
    // 联系人手机号
    private String phone;
    
    private String orgType;
    
    private String status;
    
    private String servicePhone;
    
    private String introduce;

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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
