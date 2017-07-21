package com.hengtiansoft.church.mission.services.impl;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hengtiansoft.church.dao.MissionDao;
import com.hengtiansoft.church.entity.MissionEntity;
import com.hengtiansoft.church.mission.dto.MissionSaveDto;
import com.hengtiansoft.church.mission.dto.MissionSearchDto;
import com.hengtiansoft.church.mission.services.MissionAdminService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;

@Service
public class MissionAdminServeiceImpl implements MissionAdminService{

    @Autowired
    private MissionDao  missionDao;
    @Override
    public MissionSearchDto getAllMission(final MissionSearchDto dto) {
       dto.setList(missionDao.findAll());
       return dto;
    }

    @Transactional
    @Override
    public ResultDto<?> save(MissionSaveDto dto) {
        MissionEntity mission = null;
        if(dto!= null && dto.getId() != 0L) {
            mission = missionDao.findOne(dto.getId());
            mission.setTitle(dto.getTitle());
            mission.setContent(dto.getContent());
        }
        missionDao.save(mission);
        return ResultDtoFactory.toAck("Save Success!", null);
    }

    @Override
    public MissionEntity view(Long id) {
        return missionDao.findOne(id);
    }
}
