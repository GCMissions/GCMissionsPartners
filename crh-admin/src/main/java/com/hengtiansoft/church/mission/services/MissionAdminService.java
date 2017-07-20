package com.hengtiansoft.church.mission.services;

import java.util.List;

import com.hengtiansoft.church.entity.MissionEntity;
import com.hengtiansoft.church.mission.dto.MissionSaveDto;
import com.hengtiansoft.common.dto.ResultDto;

public interface MissionAdminService {

    ResultDto<List<MissionEntity>> getAllMission();

    ResultDto<?> save(MissionSaveDto dto);
    
    MissionEntity view(Long id);

}
