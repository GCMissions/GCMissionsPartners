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
* Class Name: MQuickpayCycleEntity
* Description: 市民卡支付请求流水表
* @author yigesong
*
 */
@Entity
@Table(name = "M_QUICKPAY_CYCLE")
public class MQuickpayCycleEntity implements Serializable {
    private static final long serialVersionUID = -233079010577278550L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long              id;

    @Column(name = "REQUESTER")
    private String            requester;
    
    
    @Column(name = "RESPONSER")
    private String            reponser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public String getReponser() {
        return reponser;
    }

    public void setReponser(String reponser) {
        this.reponser = reponser;
    }

}
