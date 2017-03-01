package com.hengtiansoft.business.wrkd.order.dto;

import java.io.Serializable;

public class KdOrderRemarkDto implements Serializable{

    private static final long serialVersionUID = 1L;

    private Integer index;
    
    private String name;
    
    private String remarkDate;
    
    private String remark;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemarkDate() {
        return remarkDate;
    }

    public void setRemarkDate(String remarkDate) {
        this.remarkDate = remarkDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
