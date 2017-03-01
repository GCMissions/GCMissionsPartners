package com.hengtiansoft.business.shopMall.dto;

import java.util.Date;

import com.hengtiansoft.common.dto.PagingDto;

public class AppMessagePageDto extends PagingDto<AppMessageDto> {

	private static final long serialVersionUID = -293094629452770093L;

	private String title;

	private String type;

	private Date startDate;

	private Date endDate;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
