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
@Table(name = "M_FEEDBACK")
public class MFeedbackEntity implements Serializable {

    private static final long serialVersionUID = -3443408644002769169L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "FEEDBACK_ID")
    private Long              feedbackId;

    @Column(name = "STATUS")
    private String            status;

    @Column(name = "MEMBER_ID")
    private Long              memberId;

    @Column(name = "FEED_INFO")
    private String            feedInfo;

    @Column(name = "CREATE_DATE")
    private Date              createDate;

    
    public Long getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Long feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getFeedInfo() {
        return feedInfo;
    }

    public void setFeedInfo(String feedInfo) {
        this.feedInfo = feedInfo;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "MFeedbackEntity [feedbackId=" + feedbackId + ", status=" + status + ", memberId=" + memberId + ", feedInfo=" + feedInfo
                + ", createDate=" + createDate + "]";
    }
}
