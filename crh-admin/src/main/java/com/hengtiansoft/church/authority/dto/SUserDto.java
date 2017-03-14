package com.hengtiansoft.church.authority.dto;

import java.io.Serializable;

/**
 * Class Name: SUserDto
 * Description: 账户信息DTO
 * 
 * @author zhisongliu
 */
public class SUserDto implements Serializable {

    /**
    * Variables Name: serialVersionUID
    * Description: TODO
    * Value Description: TODO
    */
    private static final long serialVersionUID = -8500343522458962523L;

    private Long              id;

    private String            loginId;

    private String            orgId;

    private String            userName;

    private String            phone;
    
    private String            email;

    private String            status;

    private String            createTime;
    
    private String            role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
