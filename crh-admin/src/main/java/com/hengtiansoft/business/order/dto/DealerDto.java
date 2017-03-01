package com.hengtiansoft.business.order.dto;

public class DealerDto {
    private Long orgId;

    private String orgCode;

    private String orgName;

    private String phone;

    private String contact;

    private String address;

    public DealerDto() {
    }

    public DealerDto(Long orgId, String orgCode, String orgName, String phone, String contact, String address) {
        this.orgId = orgId;

        this.orgCode = orgCode;

        this.orgName = orgName;

        this.phone = phone;

        this.contact = contact;

        this.address = address;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
