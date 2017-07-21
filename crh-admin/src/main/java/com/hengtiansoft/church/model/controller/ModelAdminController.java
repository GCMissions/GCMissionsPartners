package com.hengtiansoft.church.model.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hengtiansoft.church.entity.ModelEntity;
import com.hengtiansoft.church.model.service.ModelAdminService;
import com.hengtiansoft.common.dto.ResultDto;

@Controller
@RequestMapping(value = "/model")
public class ModelAdminController {

    @Autowired
    private ModelAdminService modelService;
    
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
   public ResultDto<?> getAllModel(){
       return modelService.getAllModels();
   }
    
    @RequestMapping(value = "/change", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<?> changeModel(Long id ) {
        return modelService.changeModel(id);
    }

    @RequestMapping(value = "/current",method = RequestMethod.GET)
    @ResponseBody
    public ModelEntity getCurentModel(){
        return modelService.getCurrentModle();
        
    }
}
