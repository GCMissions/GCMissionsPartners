package com.hengtiansoft.wrw.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import javax.persistence.Table;

@Entity
@Table(name = "P_CATE_BRAND")
public class PCateBrandEntity implements Serializable {

    private static final long serialVersionUID = -410184303091236658L;

    @EmbeddedId
    private PCateBrandPK  id;

    public PCateBrandPK getId() {
        return id;
    }

    public void setId(PCateBrandPK id) {
        this.id = id;
    }

}
