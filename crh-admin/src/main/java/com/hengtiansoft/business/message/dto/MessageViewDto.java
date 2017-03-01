package com.hengtiansoft.business.message.dto;

import java.io.Serializable;
import java.util.List;

import com.hengtiansoft.wrw.entity.SOrgEntity;

/**
 * Class Name : MessageViewDto
 * 
 * DESC : 站内信详情DTO
 * 
 * @author zhisongliu
 *
 */
public class MessageViewDto implements Serializable {

    private static final long serialVersionUID = -344002038649354270L;

    private String            title;

    private Long              messageId;

    private String            content;

    private String            type;

    private List<SOrgEntity>  orglist;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<SOrgEntity> getOrglist() {
        return orglist;
    }

    public void setOrglist(List<SOrgEntity> orglist) {
        this.orglist = orglist;
    }

}
