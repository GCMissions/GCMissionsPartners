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
 * The persistent class for the M_ORDER_FEEDBACK database table.
 * 
 */
@Entity
@Table(name="M_ORDER_FEEDBACK")
public class MOrderFeedbackEntity implements Serializable {

    private static final long serialVersionUID = 1079568323237679367L;

    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="FEEDBACK_ID")
	private Long feedbackId;

	@Column(name="ATTITUDE_LEVEL")
	private Integer attitudeLevel;

	@Column(name="CREATE_DATE")
	private Date createDate;

	@Column(name="FEED_INFO")
	private String feedInfo;

	@Column(name="MEMBER_ID")
	private Long memberId;

	@Column(name="ORDER_ID")
	private String orderId;
	
    @Column(name = "ORDER_DETAIL_ID")
    private Long orderDetailId;

    @Column(name = "PRODUCT_ID")
    private Long productId;
    
    /**
     * 图片地址
     */
    @Column(name = "IMG_URLS")
    private String imgUrls;
    
    /**
     * 是否匿名
     */
    @Column(name = "ANONYMOUS")
    private String anonymous;
    
    /**
     * 星级
     */
    @Column(name = "STAR")
    private Integer star;

	@Column(name="SERVICE_LEVEL")
	private Integer serviceLevel;

	@Column(name="SHIPMENT_LEVLE")
	private Integer shipmentLevel;

	public MOrderFeedbackEntity() {
	}

	public Long getFeedbackId() {
		return this.feedbackId;
	}

	public void setFeedbackId(Long feedbackId) {
		this.feedbackId = feedbackId;
	}

	public Integer getAttitudeLevel() {
		return this.attitudeLevel;
	}

	public void setAttitudeLevel(Integer attitudeLevel) {
		this.attitudeLevel = attitudeLevel;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

    public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getFeedInfo() {
		return this.feedInfo;
	}

	public void setFeedInfo(String feedInfo) {
		this.feedInfo = feedInfo;
	}

	public Long getMemberId() {
		return this.memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getServiceLevel() {
		return this.serviceLevel;
	}

	public void setServiceLevel(Integer serviceLevel) {
		this.serviceLevel = serviceLevel;
	}

	public Integer getShipmentLevel() {
		return this.shipmentLevel;
	}

	public void setShipmentLevel(Integer shipmentLevel) {
		this.shipmentLevel = shipmentLevel;
	}
	
	public Long getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Long orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(String imgUrls) {
        this.imgUrls = imgUrls;
    }

    public String getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(String anonymous) {
        this.anonymous = anonymous;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }
}