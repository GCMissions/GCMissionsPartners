package com.hengtiansoft.business.order.dto;

import java.io.Serializable;

/**
* Class Name: RegisterRequestDto
* Description: 绑定手机请求DTO
* @author changchen
*
*/
public class RegisterRequestDto implements Serializable{

    private static final long serialVersionUID = 1L;

    
    private String mobile;
    
    private String regCode;
    
    private String openId;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRegCode() {
        return regCode;
    }

    public void setRegCode(String regCode) {
        this.regCode = regCode;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    @Override
    public String toString() {
        return "RegisterRequestDto [mobile=" + mobile + ", code=" + regCode + ", openId=" + openId + "]";
    }

    public RegisterRequestDto(String mobile, String regCode, String openId) {
        super();
        this.mobile = mobile;
        this.regCode = regCode;
        this.openId = openId;
    }
 
}
