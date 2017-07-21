package com.hengtiansoft.church.mission.services;
import com.hengtiansoft.church.entity.MissionEntity;
import com.hengtiansoft.church.mission.dto.MissionSaveDto;
import com.hengtiansoft.church.mission.dto.MissionSearchDto;
import com.hengtiansoft.common.dto.ResultDto;

public interface MissionAdminService {

    MissionSearchDto getAllMission(MissionSearchDto dto);

    ResultDto<?> save(MissionSaveDto dto);
    
    MissionEntity view(Long id);

}
