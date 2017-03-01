package com.hengtiansoft.business.authority.dto;

import java.io.Serializable;
import java.util.List;

import com.hengtiansoft.business.authority.constant.TreeNodeBean;

/**
 * 
* Class Name: SRoleEditDto
* Description: 角色编辑DTO
* @author zhisongliu
*
 */
public class SRoleEditDto implements Serializable{

	private static final long serialVersionUID = -645449392360766220L;
	
	private List<TreeNodeBean> list;
	
	private SRoleInfoSaveAndUpdateDto dto;

	public List<TreeNodeBean> getList() {
		return list;
	}

	public void setList(List<TreeNodeBean> list) {
		this.list = list;
	}

	public SRoleInfoSaveAndUpdateDto getDto() {
		return dto;
	}

	public void setDto(SRoleInfoSaveAndUpdateDto dto) {
		this.dto = dto;
	}
	
	
}
