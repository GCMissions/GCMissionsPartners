package com.hengtiansoft.wrw.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "M_MESSAGE")
public class MMessageEntity implements Serializable {

    private static final long serialVersionUID = 880603190753605718L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MESSAGE_ID")
    private Long              messageId;

    @Column(name = "MEMBER_ID")
    private Long              memberId;

    @Column(name = "TYPE")
    private String            type;

    @Column(name = "TITLE")
    private String            title;
    
    @Column(name = "IMAGE")
    private String            image;
    
    @Column(name = "CONTENT")
    private String            content;

    @Column(name = "PARAM")
    private String            param;

    @Column(name = "READ_STATUS")
    private String            readStatus;

    @Column(name = "CREATE_DATE")
    private Date              createDate;

    @Column(name = "CREATE_ID")
    private Long              createId;

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    @Override
	public String toString() {
		return "MMessageEntity [messageId=" + messageId + ", memberId="
				+ memberId + ", type=" + type + ", title=" + title + ", image="
				+ image + ", content=" + content + ", param=" + param
				+ ", readStatus=" + readStatus + ", createDate=" + createDate
				+ ", createId=" + createId + "]";
	}
}
