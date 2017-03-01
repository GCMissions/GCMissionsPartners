package com.hengtiansoft.business.customer.dto;

import java.io.Serializable;

/**
* Class Name: MemberDetailDto
* Description: TODO
* @author xianghuang
*
*/
public class MAddressDto implements Serializable {
    private static final long serialVersionUID = 2715077531931097661L;

    // 省份             市                区（县）   小区名称    详细收货地址               邮编             收货人          联系电话
    private String              province;

    private String              city;

    private String            area;
    
    private String            areaName;

    private String            address;

    private Long            zipcode;

    private String            addressContact;

    private String              phone;
    
    //默认地址，0 非默认，1默认
    private String             defFlag;
    
    private Integer regionId;

   
    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
    
    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getZipcode() {
        return zipcode;
    }

    public void setZipcode(Long zipcode) {
        this.zipcode = zipcode;
    }

    public String getAddressContact() {
        return addressContact;
    }

    public void setAddressContact(String addressContact) {
        this.addressContact = addressContact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDefFlag() {
        return defFlag;
    }

    public void setDefFlag(String defFlag) {
        this.defFlag = defFlag;
    }
   
}
