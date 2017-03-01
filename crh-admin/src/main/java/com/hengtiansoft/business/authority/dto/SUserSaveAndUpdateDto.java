package com.hengtiansoft.business.authority.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 
* Class Name: SUserSaveAndUpdateDto
* Description: 账户新增或编辑DTO
* @author zhisongliu
*
 */
public class SUserSaveAndUpdateDto implements Serializable{

	private static final long serialVersionUID = 3331612490216121385L;
	
	private Long id;
	
	private String userName;
	
	private String loginId;
	
	private Long orgId;
	
	private String phone;
	
	private String password;
	
	private String status;
	
	private List<Long> roleIds;
	
	private String remark;
	
	private String email;

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

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Long> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<Long> roleIds) {
		this.roleIds = roleIds;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	
}
