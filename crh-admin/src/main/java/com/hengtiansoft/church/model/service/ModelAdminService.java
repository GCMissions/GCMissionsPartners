package com.hengtiansoft.church.model.service;

import java.util.List;

import com.hengtiansoft.church.entity.ModelEntity;
import com.hengtiansoft.common.dto.ResultDto;

public interface ModelAdminService {

    ResultDto<List<ModelEntity>> getAllModels();
    
    ModelEntity getCurrentModle();
    
    ResultDto<?> changeModel(Long id);
} 
