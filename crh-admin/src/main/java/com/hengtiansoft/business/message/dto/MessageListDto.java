package com.hengtiansoft.business.message.dto;

import java.io.Serializable;

/**
 * Class Name: MessageListDto
 * Description: TODO
 * 
 * @author chenghongtu
 */
public class MessageListDto implements Serializable {

    /**
     * Variables Name: serialVersionUID
     * Description: TODO
     * Value Description: TODO
     */
    private static final long serialVersionUID = 6005715913548828357L;

    private String            title;

    private Long              messageId;

    private String            content;

    private String            createDate;

    private String            readStatus;

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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }

}
