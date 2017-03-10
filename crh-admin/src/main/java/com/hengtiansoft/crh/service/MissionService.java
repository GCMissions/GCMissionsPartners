package com.hengtiansoft.crh.service;

import java.util.List;

import com.hengtiansoft.church.entity.MissionEntity;
import com.hengtiansoft.common.dto.ResultDto;

public interface MissionService {

    ResultDto<List<MissionEntity>> getMissions();
}
