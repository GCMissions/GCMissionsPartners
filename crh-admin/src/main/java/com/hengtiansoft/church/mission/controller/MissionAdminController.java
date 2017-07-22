package com.hengtiansoft.church.mission.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hengtiansoft.church.entity.MissionEntity;
import com.hengtiansoft.church.entity.ModelEntity;
import com.hengtiansoft.church.mission.dto.MissionSaveDto;
import com.hengtiansoft.church.mission.dto.MissionSearchDto;
import com.hengtiansoft.church.mission.services.MissionAdminService;
import com.hengtiansoft.church.model.service.ModelAdminService;
import com.hengtiansoft.common.dto.ResultDto;

@Controller
@RequestMapping(value = "/mission")
public class MissionAdminController {
    
    @Autowired
    private MissionAdminService missionService;
    @Autowired
    private ModelAdminService modelService;
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        List<ModelEntity> list = modelService.getAllModels().getData();
        model.addAttribute("model", list);
        return "mission/mission_list";
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public MissionSearchDto searchPartners(final MissionSearchDto dto) {
        return missionService.getAllMission(dto);
    }
    
//    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
//    public String view(@PathVariable Long id, Model model) {
//        model.addAttribute("mission", missionService.view(id));
//        return "mission/mission_detail";
//    }
//    
    
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    @ResponseBody
    public MissionEntity view(@PathVariable(value="id") Long id) {
        return missionService.view(id);
    }
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<?> savePartner(@RequestBody MissionSaveDto dto) {
        return missionService.save(dto);
    }

}
