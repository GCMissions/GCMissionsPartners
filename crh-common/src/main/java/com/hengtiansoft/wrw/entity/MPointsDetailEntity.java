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
public class MPointsDetailEntity implements Serializable{

    /**
    * Variables Name: serialVersionUID
    * Description: TODO
    * Value Description: TODO
    */
    private static final long serialVersionUID = -4993817227939644253L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="DETAIL_ID")
    private Long detailId;
    
    @Column(name="MEMBER_ID")
    private Long memberId;
    
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
    
    @Column(name="ORDER_ID")
    private String orderId;

    
    public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((changeValue == null) ? 0 : changeValue.hashCode());
        result = prime * result + ((createDate == null) ? 0 : createDate.hashCode());
        result = prime * result + ((createId == null) ? 0 : createId.hashCode());
        result = prime * result + ((detailId == null) ? 0 : detailId.hashCode());
        result = prime * result + ((memberId == null) ? 0 : memberId.hashCode());
        result = prime * result + ((remark == null) ? 0 : remark.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MPointsDetailEntity other = (MPointsDetailEntity) obj;
        if (changeValue == null) {
            if (other.changeValue != null)
                return false;
        } else if (!changeValue.equals(other.changeValue))
            return false;
        if (createDate == null) {
            if (other.createDate != null)
                return false;
        } else if (!createDate.equals(other.createDate))
            return false;
        if (createId == null) {
            if (other.createId != null)
                return false;
        } else if (!createId.equals(other.createId))
            return false;
        if (detailId == null) {
            if (other.detailId != null)
                return false;
        } else if (!detailId.equals(other.detailId))
            return false;
        if (memberId == null) {
            if (other.memberId != null)
                return false;
        } else if (!memberId.equals(other.memberId))
            return false;
        if (remark == null) {
            if (other.remark != null)
                return false;
        } else if (!remark.equals(other.remark))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }
    
}
