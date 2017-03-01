package com.hengtiansoft.business.wrkd.advertise.dto;

import com.hengtiansoft.common.dto.PagingDto;

public class KdAdvertiseSearchDto extends PagingDto<KdAdvertiseDto>{

    private static final long serialVersionUID = 1L;

    // 广告模式
    private String type;
    
    private String advertiseName;
    
    private String advertiseIds;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAdvertiseName() {
        return advertiseName;
    }

    public void setAdvertiseName(String advertiseName) {
        this.advertiseName = advertiseName;
    }

    public String getAdvertiseIds() {
        return advertiseIds;
    }

    public void setAdvertiseIds(String advertiseIds) {
        this.advertiseIds = advertiseIds;
    }
}
