package com.hengtiansoft.church.model.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengtiansoft.church.dao.ModelDao;
import com.hengtiansoft.church.entity.ModelEntity;
import com.hengtiansoft.church.model.dto.ModelSaveDto;
import com.hengtiansoft.church.model.service.ModelAdminService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
@Service
public class ModelAdminServicesImpl implements ModelAdminService{

    @Autowired
    private ModelDao modelDao;
    @Override
    public ResultDto<List<ModelEntity>> getAllModels(){
        return ResultDtoFactory.toAck("",modelDao.getAllModel());
    }

    @Override
    public ModelEntity getCurrentModle() {
        return modelDao.getCurrentModel();
    }

    @Transactional
    @Override
    public ResultDto<?> changeModel(Long id) {
        String message = "";
        ModelEntity model = modelDao.getCurrentModel();
        if(id == 0L || id == model.getId()){
            message = "Error operation!";
        }else{
            modelDao.chooseModel(id); 
            modelDao.unchooseModel(model.getId());
            message = "Change Model Successfully!";
        }
        return ResultDtoFactory.toAck(message,null);
    }

   

}
