package com.hengtiansoft.task.dto;

import java.io.Serializable;

/**
* Class Name: SimpleMemberInfoDto
* Description: 用户手机号及OPEN_ID
* @author changchen
*
*/
public class SimpleMemberInfoDto implements Serializable{

    private static final long serialVersionUID = 1L;
    
    /**
     * 原始数据ID
     */
    private Long id;

    /**
     * 手机号
     */
    private String phone;
    
    /**  
     * 微信OPEN_ID
     */
    private String openId;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SimpleMemberInfoDto [phone=" + phone + ", openId=" + openId + "]";
    }

    public SimpleMemberInfoDto() {
        super();
    }

    public SimpleMemberInfoDto(String phone, String openId) {
        super();
        this.phone = phone;
        this.openId = openId;
    }

    public SimpleMemberInfoDto(Long id, String phone, String openId) {
        super();
        this.id = id;
        this.phone = phone;
        this.openId = openId;
    }

    
}
