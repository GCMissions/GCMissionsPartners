package com.hengtiansoft.wrw.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "S_COUPON_PRODUCT")
public class SCouponProductEntity implements Serializable {

    private static final long serialVersionUID = 5422438780317986467L;

    @EmbeddedId
    private SCouponProductPK  id;

    public SCouponProductPK getId() {
        return id;
    }

    public void setId(SCouponProductPK id) {
        this.id = id;
    }

}
