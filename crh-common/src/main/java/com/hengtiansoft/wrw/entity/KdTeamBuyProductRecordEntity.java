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
@Table(name = "kd_team_buy_product_record")
public class KdTeamBuyProductRecordEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RECORD_ID")
    private Long recordId;
    
    @Column(name = "TEAM_ID")
    private Long teamId;
    
    @Column(name = "CREATE_TEAM_ID")
    private Long createTeamId;
    
    @Column(name = "STATUS")
    private String status;
    
    @Column(name = "IS_RETURN")
    private String isReturn;
    
    @Column(name = "CREATE_DATE")
    private Date createDate;
    
    @Column(name = "RECORD_STATUS")
    private String recordStatus;

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Long getCreateTeamId() {
        return createTeamId;
    }

    public void setCreateTeamId(Long createTeamId) {
        this.createTeamId = createTeamId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsReturn() {
        return isReturn;
    }

    public void setIsReturn(String isReturn) {
        this.isReturn = isReturn;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }
}
