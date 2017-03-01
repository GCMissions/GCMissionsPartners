package com.hengtiansoft.common.authority.domain;

import java.io.Serializable;

public class RoleInfo implements Serializable {

    private static final long serialVersionUID = -6701053677524576235L;

    private Long roleId;

    private String role;

    private String description;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
