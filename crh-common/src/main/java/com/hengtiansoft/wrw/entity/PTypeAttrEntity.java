package com.hengtiansoft.wrw.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "P_TYPE_ATTR")
public class PTypeAttrEntity implements Serializable {

    private static final long serialVersionUID = 7739965986734920474L;

    @EmbeddedId
    private PTypeAttrPK       id;

    public PTypeAttrPK getId() {
        return id;
    }

    public void setId(PTypeAttrPK id) {
        this.id = id;
    }

}
