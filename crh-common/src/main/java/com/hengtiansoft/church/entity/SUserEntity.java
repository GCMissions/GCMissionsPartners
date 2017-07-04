package com.hengtiansoft.church.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import com.hengtiansoft.common.authority.domain.UserInfo;
/**
 * 
* Class Name: SUserEntity
* Description:User information table
* @author chengchaoyin
*
 */
@Entity
@Table(name = "USER")
public class SUserEntity extends UserInfo {

    private static final long serialVersionUID = -2241064570931700134L;

    @Column(name = "ORG_ID")
    private Long              orgId;

    @Column(name = "USER_NAME")
    private String            userName;

    @Column(name = "WEB_TOKEN")
    private String            webToken;

    @Column(name = "PHONE")
    private String            phone;

    @Column(name = "EMAIL")
    private String            email;

    @Column(name = "STATUS")
    private String            status;
    
    @Column(name = "PWD_ERROR_TIMES")
    private Integer            pwdErrorTimes;

    @Column(name = "REMARK")
    private String            remark;

    @Column(name = "CREATE_DATE")
    private Date              createDate;

    @Column(name = "CREATE_ID")
    private Long              createId;

    public String getWebToken() {
        return webToken;
    }

    public void setWebToken(String webToken) {
        this.webToken = webToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Integer getPwdErrorTimes() {
        return pwdErrorTimes;
    }

    public void setPwdErrorTimes(Integer pwdErrorTimes) {
        this.pwdErrorTimes = pwdErrorTimes;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

}
