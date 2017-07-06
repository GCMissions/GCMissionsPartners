package com.hengtiansoft.church.authority.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 
* Class Name: SUserSaveAndUpdateDto
* Description: Add or edit roles DTO
* @author tao chen
*
 */
public class SUserUpdateDto implements Serializable {

    private static final long serialVersionUID = 3331612490216121385L;

    private Long              id;

    private String            userName;

    private String            phone;

    private String            password;

    private List<Long>        roleIds;

    private String            email;

    private String            lockUser;

    private String            unlock;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLockUser() {
        return lockUser;
    }

    public void setLockUser(String lockUser) {
        this.lockUser = lockUser;
    }

    public String getUnlock() {
        return unlock;
    }

    public void setUnlock(String unlock) {
        this.unlock = unlock;
    }
    
    

}
