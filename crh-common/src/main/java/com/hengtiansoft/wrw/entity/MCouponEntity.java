package com.hengtiansoft.wrw.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 
* Class Name: MCouponEntity
* Description: 会员优惠券记录表
* @author chengchaoyin
*
 */
@Entity
@Table(name = "M_COUPONS")
public class MCouponEntity implements Serializable {

    /**
    * Variables Name: serialVersionUID
    * Description: TODO
    * Value Description: TODO
    */
    private static final long serialVersionUID = -4420880088003865382L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COUPON_ID")
    private Long              couponId;

    /**
     * 优惠券配置ID
     */
    @Column(name = "COUP_CON_ID")
    private Long              coupConId;

    /**
     * 会员ID
     */
    @Column(name = "MEMBER_ID")
    private Long              memberId;

    /**
     * 优惠券获取的来源订单ID
     */
    @Column(name = "SOURCE_ORDER_ID")
    private String            sourceOrderId;
    
    /**
     * 被哪个订单使用
     */
    @Column(name = "USED_ORDER_ID")
    private String            usedOrderId;

    /**
     * 状态 0未生效 1生效中 2已使用 3已过期
     */
    @Column(name = "STATUS")
    private String            status;

    /**
     * 优惠券名称
     */
    @Column(name = "COUPON_NAME")
    private String            couponName;

    /**
     * 备注
     */
    @Column(name = "REMARK")
    private String            remark;

    /**
     * 优惠券类型
     */
    @Column(name = "TYPE")
    private String            type;

    /**
     * 面额
     */
    @Column(name = "VALUE")
    private Long              value;

    /**
     * 优惠券使用最低金额
     */
    @Column(name = "LIMIT_VALUE")
    private Long              limitValue;

    /**
     * 生效时间
     */
    @Column(name = "EFFECT_DATE")
    private Date              effectDate;

    /**
     * 失效时间
     */
    @Column(name = "INVALID_DATE")
    private Date              invalidDate;

    /**
     * 使用时间
     */
    @Column(name = "USED_DATE")
    private Date              usedDate;

    /**
     * 优惠码
     */
    @Column(name = "COUP_CODE")
    private String            coupCode;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_DATE")
    private Date              createDate;

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public Long getCoupConId() {
        return coupConId;
    }

    public void setCoupConId(Long coupConId) {
        this.coupConId = coupConId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getSourceOrderId() {
        return sourceOrderId;
    }

    public void setSourceOrderId(String sourceOrderId) {
        this.sourceOrderId = sourceOrderId;
    }

    public String getUsedOrderId() {
        return usedOrderId;
    }

    public void setUsedOrderId(String usedOrderId) {
        this.usedOrderId = usedOrderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Long getLimitValue() {
        return limitValue;
    }

    public void setLimitValue(Long limitValue) {
        this.limitValue = limitValue;
    }

    public Date getEffectDate() {
        return effectDate;
    }

    public void setEffectDate(Date effectDate) {
        this.effectDate = effectDate;
    }

    public Date getInvalidDate() {
        return invalidDate;
    }

    public void setInvalidDate(Date invalidDate) {
        this.invalidDate = invalidDate;
    }

    public Date getUsedDate() {
        return usedDate;
    }

    public void setUsedDate(Date usedDate) {
        this.usedDate = usedDate;
    }

    public String getCoupCode() {
        return coupCode;
    }

    public void setCoupCode(String coupCode) {
        this.coupCode = coupCode;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}
