package com.hengtiansoft.business.message.dto;

import java.io.Serializable;

public class PMessageDto implements Serializable{

	private static final long serialVersionUID = 693854638095673614L;
	
	private Long messageId;
	
	private String title;
	
	private String createDate;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}
	
	
}
