package com.hengtiansoft.church.partner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hengtiansoft.church.partner.dto.PartnerSaveDto;
import com.hengtiansoft.church.partner.dto.PartnerSearchDto;
import com.hengtiansoft.church.partner.service.PartnerAdminService;
import com.hengtiansoft.common.dto.ResultDto;

@RequestMapping(value = "/partner")
@Controller
public class PartnerAdminController {
    
    @Autowired
    private PartnerAdminService partnerService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "partner/partner_list";
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public PartnerSearchDto searchPartners(PartnerSearchDto dto) {
        partnerService.searchPartner(dto);
        return dto;
    }
    
    @RequestMapping(value = "/view/{id}/{showType}", method = RequestMethod.GET)
    public String view(@PathVariable Long id, @PathVariable String showType, Model model) {
        model.addAttribute("showType", showType);
        model.addAttribute("partner", partnerService.view(id));
        return "partner/partner_view";
    }
    
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<?> deletePartner(@PathVariable Long id) {
        return partnerService.deletePartner(id);
    }
    
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<?> savePartner(PartnerSaveDto dto) {
        return partnerService.savePartner(dto);
    }
}
