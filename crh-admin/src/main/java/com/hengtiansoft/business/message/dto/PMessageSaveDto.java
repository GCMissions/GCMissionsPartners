package com.hengtiansoft.business.message.dto;

import java.io.Serializable;
import java.util.List;

public class PMessageSaveDto implements Serializable{

	private static final long serialVersionUID = -1731235812404036348L;
	
	private String title;
	
	private String content;
	
	private List<Long> orgIds;
	
	private String orgType;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<Long> getOrgIds() {
		return orgIds;
	}

	public void setOrgIds(List<Long> orgIds) {
		this.orgIds = orgIds;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
	
	
	
}
