package com.hengtiansoft.business.provider.dto;

import com.hengtiansoft.common.dto.PagingDto;

/**
 * 
* Class Name: SearchSalerDto
* Description: TODO
* @author yiminli
*
 */
public class SearchSalerDto extends PagingDto<SalerDto> {

    private static final long serialVersionUID = 1L;

    private String            orgCode;                                 // 服务商编号
    
    private String            orgName;                                 // 服务商名称
    
    private String            registrationLicense;                     // 工商执照注册号
    
    private String            businesser;                              // 企业法人姓名
    
    private String            contact;                                 // 联系人姓名
    
    private String            phone;                                   // 联系人手机号
    
    private String            status;                                  // 状态

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
