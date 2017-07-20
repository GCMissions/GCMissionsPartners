package com.hengtiansoft.church.mission.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.hengtiansoft.church.mission.dto.MissionSaveDto;
import com.hengtiansoft.church.mission.services.MissionAdminService;
import com.hengtiansoft.church.partner.dto.PartnerSearchDto;
import com.hengtiansoft.common.dto.ResultDto;

@Controller
@RequestMapping(value = "/mission")
public class MissionAdminCotroller {
    
    public MissionAdminCotroller(){
        System.out.println("MissionAdminCotroller init");
    }

    @Autowired
    private MissionAdminService missionService;
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        System.out.println("1231313");
        return "mission/mission_list";
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public PartnerSearchDto searchPartners(@RequestBody PartnerSearchDto dto) {
        missionService.getAllMission();
        return dto;
    }
    
    @RequestMapping(value = "/view/{id}/{showType}", method = RequestMethod.GET)
    public String view(@PathVariable Long id, @PathVariable String showType, Model model) {
        model.addAttribute("showType", showType);
        if (id != 0) {
            model.addAttribute("mission", missionService.view(id));
        } 
        return "mission/mission_detail";
    }
    
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<?> savePartner(@RequestBody MissionSaveDto dto) {
        return missionService.save(dto);
    }

}
