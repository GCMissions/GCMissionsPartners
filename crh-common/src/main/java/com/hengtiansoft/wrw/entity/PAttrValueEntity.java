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
@Table(name = "P_ATTR_VALUE")
public class PAttrValueEntity implements Serializable {

    private static final long serialVersionUID = -1622416171496490833L;

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ATTR_VALUE_ID")
	private Long attrValueId;
	
	@Column(name = "ATTR_ID")
	private Long attrId;
	
	@Column(name = "VALUE_INFO")
	private String valueInfo;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "IMAGE")
	private String image;
	
	@Column(name = "SORT")
	private Long sort;
	
	@Column(name = "CREATE_DATE")
    private Date createDate;

	@Column(name = "CREATE_ID")
	private Long createId;
	
    @Column(name = "MODIFY_DATE")
    private Date modifyDate;

    @Column(name = "MODIFY_ID")
	private Long modifyId;

    public Long getAttrValueId() {
        return attrValueId;
    }

    public void setAttrValueId(Long attrValueId) {
        this.attrValueId = attrValueId;
    }

    public Long getAttrId() {
        return attrId;
    }

    public void setAttrId(Long attrId) {
        this.attrId = attrId;
    }

    public String getValueInfo() {
        return valueInfo;
    }

    public void setValueInfo(String valueInfo) {
        this.valueInfo = valueInfo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
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

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Long getModifyId() {
		return modifyId;
	}

	public void setModifyId(Long modifyId) {
		this.modifyId = modifyId;
	}

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((attrId == null) ? 0 : attrId.hashCode());
        result = prime * result + ((attrValueId == null) ? 0 : attrValueId.hashCode());
        result = prime * result + ((image == null) ? 0 : image.hashCode());
        result = prime * result + ((sort == null) ? 0 : sort.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((valueInfo == null) ? 0 : valueInfo.hashCode());
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
        PAttrValueEntity other = (PAttrValueEntity) obj;
        if (attrId == null) {
            if (other.attrId != null)
                return false;
        } else if (!attrId.equals(other.attrId))
            return false;
        if (attrValueId == null) {
            if (other.attrValueId != null)
                return false;
        } else if (!attrValueId.equals(other.attrValueId))
            return false;
        if (image == null) {
            if (other.image != null)
                return false;
        } else if (!image.equals(other.image))
            return false;
        if (sort == null) {
            if (other.sort != null)
                return false;
        } else if (!sort.equals(other.sort))
            return false;
        if (status == null) {
            if (other.status != null)
                return false;
        } else if (!status.equals(other.status))
            return false;
        if (valueInfo == null) {
            if (other.valueInfo != null)
                return false;
        } else if (!valueInfo.equals(other.valueInfo))
            return false;
        return true;
    }

}
