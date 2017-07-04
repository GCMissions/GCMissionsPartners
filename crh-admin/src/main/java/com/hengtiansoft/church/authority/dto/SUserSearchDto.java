package com.hengtiansoft.church.authority.dto;

import com.hengtiansoft.common.dto.PagingDto;

/**
 * Class Name: SUserSearchDto
 * Description: ACCOUNT RESRARCHDTO
 * 
 * @author zhisongliu
 */
public class SUserSearchDto extends PagingDto<SUserDto> {

    /**
    * Variables Name: serialVersionUID
    * Description: TODO
    * Value Description: TODO
    */
    private static final long serialVersionUID = 424250826390683519L;

    private String            userName;

    private String            loginId;

    private String            phone;
    
    private String            email;
    
    private Long            roleId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

}
