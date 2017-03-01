package com.hengtiansoft.wrw.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hengtiansoft.common.domain.BaseEntity;

/**
 * Class Name: ActivityConstranint
 * Description: 活动下单约束
 * @author zhongyidong
 *
 */
@Entity
@Table(name = "act_constranint")
public class ActivityConstranint extends BaseEntity{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "PRODUCT_ID")
    private Long productId;
    
    /**
     * 必填字段
     */
    @Column(name = "REQUIRE_FIELDS")
    private String requireFields;
    
    /**
     * 必填字段是否必填：0-选填，1-必填
     */
    @Column(name = "REQUIRE_MASK")
    private String requireMask;
    
    /**
     * 限购数量
     */
    @Column(name = "LIMIT_NUM")
    private Integer limitNum;
    
    /**
     * 开售类型：0-立即开售；1-定时开售
     */
    @Column(name = "SALE_TYPE")
    private String saleType;
    
    /**
     * 开售开始时间
     */
    @Column(name = "START_TIME")
    private Date startTime;
    
    /**
     * 开售结束时间
     */
    @Column(name = "END_TIME")
    private Date endTime;

    /**
     * 截止下单时间（提前天数）
     */
    @Column(name = "BUY_DEADLINE")
    private Integer buyDeadline;
    
    /**
     * 活动参与人数
     */
    @Column(name = "PARTAKE_INFO")
    private String partakeInfo;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getRequireFields() {
        return requireFields;
    }

    public void setRequireFields(String requireFields) {
        this.requireFields = requireFields;
    }

    public String getRequireMask() {
        return requireMask;
    }

    public void setRequireMask(String requireMask) {
        this.requireMask = requireMask;
    }

    public Integer getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(Integer limitNum) {
        this.limitNum = limitNum;
    }

    public String getSaleType() {
        return saleType;
    }

    public void setSaleType(String saleType) {
        this.saleType = saleType;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getBuyDeadline() {
        return buyDeadline;
    }

    public void setBuyDeadline(Integer buyDeadline) {
        this.buyDeadline = buyDeadline;
    }

    public String getPartakeInfo() {
        return partakeInfo;
    }

    public void setPartakeInfo(String partakeInfo) {
        this.partakeInfo = partakeInfo;
    }
    
}
