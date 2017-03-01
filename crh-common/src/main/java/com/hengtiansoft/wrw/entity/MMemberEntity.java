package com.hengtiansoft.wrw.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hengtiansoft.common.authority.domain.UserInfo;

@Entity
@Table(name = "M_MEMBER")
public class MMemberEntity extends UserInfo {

    private static final long serialVersionUID = -4436929830133312480L;

    @Column(name = "MEMBER_NAME")
    private String            memberName;

    @Column(name = "WEB_TOKEN")
    private String            webToken;

    @Column(name = "WECHAT_TOKEN")
    private String            wechatToken;

    @Column(name = "APP_TOKEN")
    private String            appToken;

    @Column(name = "STATUS")
    private String            status;

    @Column(name = "POINT")
    private Long point = 0L;

    @JsonProperty(value = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_DATE")
    private Date              createDate;

    @JsonProperty(value = "modifyDate")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MODIFY_DATE")
    private Date              modifyDate;

    @Column(name = "SEX")
    private String            gender;

    @Column(name = "CUST_IMAGE")
    private String            custImage;

    @Column(name = "CUST_NAME")
    private String            custName;

    @Column(name = "CREATE_ID")
    private Integer           createId;

    @Column(name = "MODIFY_ID")
    private Integer           modifyId;

    @Column(name = "REAL_NAME")
    private String            realName;

    @Column(name = "ID_CARD")
    private String            idCard;

    @Column(name = "EMAIL")
    private String            email;

    @Column(name = "PWD_ERROR_TIMES")
    private Integer           pwdErrorTimes;

    @Column(name = "REFEREE")
    private Integer           referee;
    
    @Column(name = "OPENID")
    private String openId;

    public Integer getPwdErrorTimes() {
        return pwdErrorTimes;
    }

    public void setPwdErrorTimes(Integer pwdErrorTimes) {
        this.pwdErrorTimes = pwdErrorTimes;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getWebToken() {
        return webToken;
    }

    public void setWebToken(String webToken) {
        this.webToken = webToken;
    }

    public String getWechatToken() {
        return wechatToken;
    }

    public void setWechatToken(String wechatToken) {
        this.wechatToken = wechatToken;
    }

    public String getAppToken() {
        return appToken;
    }

    public void setAppToken(String appToken) {
        this.appToken = appToken;
    }

    public Long getPoint() {
        return point;
    }

    public void setPoint(Long point) {
        this.point = point;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCustImage() {
        return custImage;
    }

    public void setCustImage(String custImage) {
        this.custImage = custImage;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public Integer getModifyId() {
        return modifyId;
    }

    public void setModifyId(Integer modifyId) {
        this.modifyId = modifyId;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getReferee() {
        return referee;
    }

    public void setReferee(Integer referee) {
        this.referee = referee;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

}
