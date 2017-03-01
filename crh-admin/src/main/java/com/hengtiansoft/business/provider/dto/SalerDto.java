package com.hengtiansoft.business.provider.dto;

import java.io.Serializable;
import java.util.Date;
/**
 * 
* Class Name: SalerDto
* Description: TODO
* @author yiminli
*
 */
public class SalerDto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long              orgId;                                   // 内部ID

    private String            orgCode;                                 // 服务商编号

    private String            orgName;                                 // 服务商名称

    private String            registrationLicense;                     // 工商执照注册号
    
    private String            businesser;                              // 企业法人姓名
    
    private String            contact;                                 // 联系人姓名
    
    private String            phone;                                   // 联系人手机号
    
    private Date              createDate;                              // 创建日期
    
    private String            servicePhone;                            // 客服电话
    
    private String            introduce;                               // 简介

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

    public String getRegistrationLicense() {
        return registrationLicense;
    }

    public void setRegistrationLicense(String registrationLicense) {
        this.registrationLicense = registrationLicense;
    }

    public String getBusinesser() {
        return businesser;
    }

    public void setBusinesser(String businesser) {
        this.businesser = businesser;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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
