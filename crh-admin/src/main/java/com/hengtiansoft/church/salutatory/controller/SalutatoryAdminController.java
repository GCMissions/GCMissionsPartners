package com.hengtiansoft.church.salutatory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hengtiansoft.church.entity.SalutatoryEntity;
import com.hengtiansoft.church.salutatory.dto.SalutatorySaveDto;
import com.hengtiansoft.church.salutatory.service.SalutatoryAdminService;
import com.hengtiansoft.common.dto.ResultDto;

@Controller
@RequestMapping(value = "/salutatory")
public class SalutatoryAdminController {
    @Autowired
    private SalutatoryAdminService salutatoryService;
    
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String index(){
        return "salutatory/salutatory_list";
    }
    
    @RequestMapping(value = "/get",method = RequestMethod.POST)
    @ResponseBody
    public SalutatoryEntity get(){
        return salutatoryService.getSalutatory();
    }
    
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<?> save(@RequestBody SalutatorySaveDto dto){
        return salutatoryService.saveSalutatory(dto);
    }

}
