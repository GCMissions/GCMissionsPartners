package com.hengtiansoft.task.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "M_SMS")
public class SmsMessage implements Serializable {

    private static final long serialVersionUID = 6988809171317971035L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MSN_ID")
    private Long              smsId;

    @Column(name = "MEMBER_ID")
    private Long              memberId;

    @Column(name = "PHONE")
    private String            phone;

    @Column(name = "STATUS")
    private String            status;

    @Column(name = "TYPE")
    private String            type;

    @Column(name = "MESSAGE")
    private String            message;

    @Column(name = "CONTENT")
    private String            content;

    @Column(name = "CREATE_DATE")
    private Date              createDate;
    
    @Column(name = "TIMES")
    private Integer           times;
    
    @Column(name = "SEND_TS")
    private Date sendTs;

    public Long getSmsId() {
        return smsId;
    }

    public void setSmsId(Long smsId) {
        this.smsId = smsId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public Date getSendTs() {
        return sendTs;
    }

    public void setSendTs(Date sendTs) {
        this.sendTs = sendTs;
    }

    @Override
    public String toString() {
        return "SmsMessage [smsId=" + smsId + ", memberId=" + memberId + ", phone=" + phone + ", status=" + status + ", type=" + type + ", message="
                + message + ", content=" + content + ", createDate=" + createDate + ", sendTime=" + times + "]";
    }
}
