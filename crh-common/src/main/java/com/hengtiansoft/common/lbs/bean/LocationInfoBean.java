package com.hengtiansoft.common.lbs.bean;

import java.util.ArrayList;
import java.util.List;

public class LocationInfoBean {

    private String status;

    private Integer size;

    private Integer total;

    private List<StoreLocationDetailBean> contents = new ArrayList<StoreLocationDetailBean>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<StoreLocationDetailBean> getContents() {
        return contents;
    }

    public void setContents(List<StoreLocationDetailBean> contents) {
        this.contents = contents;
    }

}
