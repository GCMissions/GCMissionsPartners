package com.hengtiansoft.business.wrkd.advertise.dto;

import java.io.Serializable;

public class KdAdvertiseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long advertiseId;
    
    private String advertiseName;
    
    // 跳转类型
    private String skipType;
    
    // 广告模式
    private String type;
    
    private String skipUrl;
    
    private String actType;

    public Long getAdvertiseId() {
        return advertiseId;
    }

    public void setAdvertiseId(Long advertiseId) {
        this.advertiseId = advertiseId;
    }

    public String getAdvertiseName() {
        return advertiseName;
    }

    public void setAdvertiseName(String advertiseName) {
        this.advertiseName = advertiseName;
    }

    public String getSkipType() {
        return skipType;
    }

    public void setSkipType(String skipType) {
        this.skipType = skipType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSkipUrl() {
        return skipUrl;
    }

    public void setSkipUrl(String skipUrl) {
        this.skipUrl = skipUrl;
    }

    public String getActType() {
        return actType;
    }

    public void setActType(String actType) {
        this.actType = actType;
    }
}
