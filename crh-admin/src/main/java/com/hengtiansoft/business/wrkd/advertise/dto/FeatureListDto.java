package com.hengtiansoft.business.wrkd.advertise.dto;

import java.io.Serializable;

public class FeatureListDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer index;
    
    private Long featureId;
    
    private String featureName;
    
    private String tagName;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Long getFeatureId() {
        return featureId;
    }

    public void setFeatureId(Long featureId) {
        this.featureId = featureId;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
