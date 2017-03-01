package com.hengtiansoft.business.order.dto;

import com.hengtiansoft.common.dto.PagingDto;

public class DealerPageDto extends PagingDto<DealerDto> {
    private static final long serialVersionUID = -3584362897156806643L;

    private String orgCode;

    private String orgName;

    private Integer province;

    private Integer city;

    private String phone;

    private String contact;

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

    public Integer getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
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

}
