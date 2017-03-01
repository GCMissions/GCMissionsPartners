package com.hengtiansoft.business.wrkd.advertise.dto;

import com.hengtiansoft.common.dto.PagingDto;

public class FeatureSearchDto extends PagingDto<FeatureListDto> {

    private static final long serialVersionUID = 1L;

    private String featureName;
    
    private Long tagId;

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }
}
