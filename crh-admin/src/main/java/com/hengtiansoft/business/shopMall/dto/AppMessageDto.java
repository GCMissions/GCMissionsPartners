package com.hengtiansoft.business.shopMall.dto;

import java.io.Serializable;
import java.util.Date;

public class AppMessageDto implements Serializable {

    private static final long serialVersionUID = 47815083527308296L;

    private Long              messageId;

    private Long              memberID;

    private String            type;

    private String            title;

    private String            image;

    private String            content;

    private String            param;

    private Date              createDate;

    private Long              createID;

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Long getMemberID() {
        return memberID;
    }

    public void setMemberID(Long memberID) {
        this.memberID = memberID;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getCreateID() {
        return createID;
    }

    public void setCreateID(Long createID) {
        this.createID = createID;
    }

}
