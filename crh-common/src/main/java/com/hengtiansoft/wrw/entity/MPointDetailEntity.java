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
@Table(name="M_POINTS_DETAIL")
public class MPointDetailEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4751413050023169613L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="DETAIL_ID")
	private Long detailId;
	
	@Column(name="MEMBER_ID")
	private Long memberId;
	
	@Column(name="ORDER_ID")
    private String orderId;

    @Column(name="TYPE")
	private String type;
	
	@Column(name="CHANGE_VALUE")
	private Long changeValue;
	
	@Column(name="REMARK")
	private String remark;
	
	@Column(name="CREATE_DATE")
	private Date createDate;
	
	@Column(name="CREATE_ID")
	private Long createId;

	public Long getDetailId() {
		return detailId;
	}

	public void setDetailId(Long detailId) {
		this.detailId = detailId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getChangeValue() {
		return changeValue;
	}

	public void setChangeValue(Long changeValue) {
		this.changeValue = changeValue;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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
