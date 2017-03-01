package com.hengtiansoft.wrw.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hengtiansoft.common.domain.BaseEntity;

@Entity
@Table(name = "kd_team_buy_product_detail")
public class KdTeamBuyProductDetailEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "TEAM_BUY_ID")
    private Long teamBuyId;
    
    @Column(name = "SPEC_INFO")
    private String specInfo;
    
    @Column(name = "PRICE")
    private String price;
    
    @Column(name = "STATUS")
    private String status;
    
    @Column(name = "LIMIT_COUNT")
    private String limitCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTeamBuyId() {
        return teamBuyId;
    }

    public void setTeamBuyId(Long teamBuyId) {
        this.teamBuyId = teamBuyId;
    }

    public String getSpecInfo() {
        return specInfo;
    }

    public void setSpecInfo(String specInfo) {
        this.specInfo = specInfo;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLimitCount() {
        return limitCount;
    }

    public void setLimitCount(String limitCount) {
        this.limitCount = limitCount;
    }
}
