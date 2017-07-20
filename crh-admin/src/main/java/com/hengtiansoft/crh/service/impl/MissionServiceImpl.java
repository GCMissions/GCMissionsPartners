package com.hengtiansoft.crh.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.code.yanf4j.core.Session;
import com.hengtiansoft.church.dao.MissionDao;
import com.hengtiansoft.church.dao.ModelDao;
import com.hengtiansoft.church.entity.MissionEntity;
import com.hengtiansoft.church.entity.ModelEntity;
import com.hengtiansoft.church.enums.StatusEnum;
import com.hengtiansoft.church.model.dto.ModelAndDataDto;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.crh.service.MissionService;

@Service
public class MissionServiceImpl implements MissionService {

    @Autowired
    private MissionDao missionDao;
    @Autowired
    private ModelDao modelDao;

    @Override
    public ResultDto<List<MissionEntity>> getMissions() {
        return ResultDtoFactory.toAck("", missionDao.findByDelFlag(StatusEnum.NORMAL.getCode()));
    }

    @Transactional
    @Override
    public ModelAndDataDto getData() {
        ModelAndDataDto data = new ModelAndDataDto();
        ModelEntity model = modelDao.getCurrentModel();
        //暂定
        if(model == null){
            modelDao.chooseModel(1L);
            data.setModelId(1L);
        }else{
            data.setModelId(model.getId());
        }
        
        //特殊情況
        if(data.getModelId() == 2L){
            
        }else{
            List<MissionEntity> list = missionDao.findAll();
            data.setMissions(list);
        }
        return data;
    }
}
