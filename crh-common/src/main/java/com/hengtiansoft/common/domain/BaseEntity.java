package com.hengtiansoft.common.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Class Name: BaseEntity Description: basic Entity
 * 
 * @author taochen
 */
@MappedSuperclass
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = -708401431169678136L;

    @Column(name = "CREATE_DATE", insertable = false, updatable = false)
    private Date createDate;

    @Column(name = "CREATE_ID", nullable = false)
    private Long createId;

    @Column(name = "MODIFY_DATE", insertable = false)
    private Date modifyDate;

    @Column(name = "MODIFY_ID", insertable = false)
    private Long modifyId;

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
        result = prime * result + ((createDate == null) ? 0 : createDate.hashCode());
        result = prime * result + ((createId == null) ? 0 : createId.hashCode());
        result = prime * result + ((modifyDate == null) ? 0 : modifyDate.hashCode());
        result = prime * result + ((modifyId == null) ? 0 : modifyId.hashCode());
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
        BaseEntity other = (BaseEntity) obj;
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
        if (modifyDate == null) {
            if (other.modifyDate != null)
                return false;
        } else if (!modifyDate.equals(other.modifyDate))
            return false;
        if (modifyId == null) {
            if (other.modifyId != null)
                return false;
        } else if (!modifyId.equals(other.modifyId))
            return false;
        return true;
    }

}
