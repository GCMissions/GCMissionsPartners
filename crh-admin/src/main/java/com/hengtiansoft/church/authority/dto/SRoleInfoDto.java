package com.hengtiansoft.church.authority.dto;

import java.io.Serializable;
/**
 * 
* Class Name: SRoleInfoDto
* Description: The role of the information DTO
* @author tao chen
*
 */
public class SRoleInfoDto implements Serializable{

	private static final long serialVersionUID = 6478591975506559699L;
	
	private Long roleId;
	
	private String role;
	
	private String createDate;
	
	private String status;

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

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
