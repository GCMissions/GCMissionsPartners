package com.hengtiansoft.wrw.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "P_TYPE_SPEC")
public class PTypeSpecEntity implements Serializable {

    private static final long serialVersionUID = 7420070419384603918L;

    @EmbeddedId
    private PTypeSpecPK       id;

    public PTypeSpecPK getId() {
        return id;
    }

    public void setId(PTypeSpecPK id) {
        this.id = id;
    }

}
