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
@Table(name = "M_SMS")
public class MSmsEntity implements Serializable {

    private static final long serialVersionUID = 3823278301521318481L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MSN_ID")
    private Integer           msnId;

    @Column(name = "MEMBER_ID")
    private Long              memberId;

    @Column(name = "PHONE")
    private String            phone;

    @Column(name = "STATUS")
    private String            status;

    @Column(name = "TYPE")
    private String            type;

    @Column(name = "CONTENT")
    private String            content;

    @Column(name = "MESSAGE")
    private String            message;

    @Column(name = "CREATE_DATE")
    private Date              createDate;

    
    public Integer getMsnId() {
        return msnId;
    }

    public void setMsnId(Integer msnId) {
        this.msnId = msnId;
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

    @Override
    public String toString() {
        return "MSms [msnId=" + msnId + ", memberId=" + memberId + ", phone=" + phone + ", status=" + status + ", type=" + type + ", content="
                + content + ", message=" + message + ", createDate=" + createDate + "]";
    }
}
