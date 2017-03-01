package com.hengtiansoft.wrw.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "P_SPEC_VALUE")
public class PSpecValueEntity implements Serializable {

    private static final long serialVersionUID = -2183510370930136416L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SPEC_VALUE_ID")
    private Long              specValueId;

    @Column(name = "SPEC_ID")
    private Long              specId;

    @Column(name = "VALUE_INFO")
    private String            valueInfo;

    @Column(name = "STATUS")
    private String            status;

    @Column(name = "IMAGE")
    private String            image;

    public Long getSpecValueId() {
        return specValueId;
    }

    public void setSpecValueId(Long specValueId) {
        this.specValueId = specValueId;
    }

    public Long getSpecId() {
        return specId;
    }

    public void setSpecId(Long specId) {
        this.specId = specId;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((image == null) ? 0 : image.hashCode());
        result = prime * result + ((specId == null) ? 0 : specId.hashCode());
        result = prime * result + ((specValueId == null) ? 0 : specValueId.hashCode());
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
        PSpecValueEntity other = (PSpecValueEntity) obj;
        if (image == null) {
            if (other.image != null)
                return false;
        } else if (!image.equals(other.image))
            return false;
        if (specId == null) {
            if (other.specId != null)
                return false;
        } else if (!specId.equals(other.specId))
            return false;
        if (specValueId == null) {
            if (other.specValueId != null)
                return false;
        } else if (!specValueId.equals(other.specValueId))
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
