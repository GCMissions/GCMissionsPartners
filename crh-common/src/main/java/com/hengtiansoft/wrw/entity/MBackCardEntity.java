package com.hengtiansoft.wrw.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
* Class Name: MBackCardEntity
* Description: 银行卡管理的实体
* @author yigesong
*
 */
@Entity
@Table(name = "M_BANK_CARD")
public class MBackCardEntity implements Serializable {

    private static final long serialVersionUID = 3144114361904351176L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long              id;

    @Column(name = "MER_CUST_ID")
    private Long              merCustId;

    @Column(name = "NAME")
    private String            name;

    @Column(name = "CARD_TYPE")
    private String            cardType;

    @Column(name = "CARD_NO")
    private String            cardNo;

    @Column(name = "CERT_TYPE")
    private String            certType;

    @Column(name = "CERT_NO")
    private String            certNo;

    @Column(name = "PHONE")
    private Long              phone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMerCustId() {
        return merCustId;
    }

    public void setMerCustId(Long merCustId) {
        this.merCustId = merCustId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

}
