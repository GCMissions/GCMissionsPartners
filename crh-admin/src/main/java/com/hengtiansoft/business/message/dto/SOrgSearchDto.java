package com.hengtiansoft.business.message.dto;

import com.hengtiansoft.common.dto.PagingDto;

public class SOrgSearchDto extends PagingDto<SOrgDto> {

    private static final long serialVersionUID = -2935390608181678583L;

    private String            orgType;

    private String            orgCode;

    private String            orgName;

    private String            contact;

    private String            phone;

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
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

}
