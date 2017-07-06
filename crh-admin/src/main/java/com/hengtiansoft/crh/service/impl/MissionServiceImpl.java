package com.hengtiansoft.crh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengtiansoft.church.dao.MissionDao;
import com.hengtiansoft.church.entity.MissionEntity;
import com.hengtiansoft.church.enums.StatusEnum;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.crh.service.MissionService;

@Service
public class MissionServiceImpl implements MissionService {

    @Autowired
    private MissionDao missionDao;

    @Override
    public ResultDto<List<MissionEntity>> getMissions() {
        return ResultDtoFactory.toAck("", missionDao.findByDelFlag(StatusEnum.NORMAL.getCode()));
    }
}
