package com.hengtiansoft.church.authority.dto;

import java.io.Serializable;
import java.util.List;

import com.hengtiansoft.church.authority.constant.TreeNodeBean;

/**
 * 
* Class Name: SRoleEditDto
* Description: ROLE EDIT DTO
* @author taochen
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
