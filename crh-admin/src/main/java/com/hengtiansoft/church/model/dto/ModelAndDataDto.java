package com.hengtiansoft.church.model.dto;

import java.io.Serializable;
import java.util.List;

import com.hengtiansoft.church.entity.MissionEntity;
import com.hengtiansoft.church.entity.SalutatoryEntity;

public class ModelAndDataDto implements Serializable {

    /**
     * Variables Name: serialVersionUID Description: TODO Value Description: TODO
     */
    private static final long serialVersionUID = 1L;
    private Long modelId;
    private List<MissionEntity> missions;
    private SalutatoryEntity salutatoryEntity;
    public Long getModelId() {
        return modelId;
    }
    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }
    public List<MissionEntity> getMissions() {
        return missions;
    }
    public void setMissions(List<MissionEntity> missions) {
        this.missions = missions;
    }
    public SalutatoryEntity getSalutatoryEntity() {
        return salutatoryEntity;
    }
    public void setSalutatoryEntity(SalutatoryEntity salutatoryEntity) {
        this.salutatoryEntity = salutatoryEntity;
    }


}
