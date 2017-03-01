package com.hengtiansoft.business.shopStock.regionalPlatform.dto;

import java.io.Serializable;

/**
 * Class Name: TerminalDetailDto
 * Description: 终端配送商信息详情dto
 * 
 * @author fengpan
 */
public class TerminalDetailDto implements Serializable {

    /**
     * Variables Name: serialVersionUID
     * Description: TODO
     * Value Description: TODO
     */
    private static final long serialVersionUID = 151908943825910795L;

    private String            orgCode;

    private String            orgName;

    private String            loginName;

    private String            contactName;

    private String            phone;

    private String            region;

    private String            address;

    private String            longitude;

    private String            latitude;

    /**
     * TerminalDetailDto Constructor
     */
    public TerminalDetailDto() {

    }

    /**
     * TerminalDetailDto Constructor
     *
     * @param orgCode
     * @param orgName
     * @param loginName
     * @param contactName
     * @param phone
     * @param region
     * @param address
     * @param longitude
     * @param latitude
     */
    public TerminalDetailDto(String orgCode, String orgName, String loginName, String contactName, String phone, String region, String address,
            String longitude, String latitude) {
        this.orgCode = orgCode;
        this.orgName = orgName;
        this.loginName = loginName;
        this.contactName = contactName;
        this.phone = phone;
        this.region = region;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
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

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

}
