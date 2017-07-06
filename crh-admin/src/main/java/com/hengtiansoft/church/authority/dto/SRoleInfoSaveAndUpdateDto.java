package com.hengtiansoft.church.authority.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * Class Name: SRoleInfoSaveAndUpdateDto Description: Add or edit roles DTO
 * 
 * @author tao chen
 *
 */
public class SRoleInfoSaveAndUpdateDto implements Serializable {
    private static final long serialVersionUID = 2137718744388076756L;

    private Long roleId;

    private String role;

    private String description;

    private List<Long> functionIds;

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

    public List<Long> getFunctionIds() {
        return functionIds;
    }

    public void setFunctionIds(List<Long> functionIds) {
        this.functionIds = functionIds;
    }

}
