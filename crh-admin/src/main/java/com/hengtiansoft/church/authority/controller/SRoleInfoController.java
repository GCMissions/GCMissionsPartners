package com.hengtiansoft.church.authority.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.church.authority.constant.TreeNodeBean;
import com.hengtiansoft.church.authority.dto.SRoleInfoSaveAndUpdateDto;
import com.hengtiansoft.church.authority.dto.SRoleInfoSearchDto;
import com.hengtiansoft.church.authority.service.SRoleInfoService;
import com.hengtiansoft.common.dto.ResultDto;

/**
 * 
* Class Name: SRoleInfoController
* Description: role controller
* @author taochen
*
 */
@Controller
@RequestMapping(value = "/role")
public class SRoleInfoController {

    @Autowired
    private SRoleInfoService sRoleInfoService;

    /**
     * Description:jump to homepage
     * 
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("/role/role_list");
    }

    /**
     * Description:homepage's data
     * 
     * @param dto
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public SRoleInfoSearchDto search(@RequestBody SRoleInfoSearchDto dto) {
        sRoleInfoService.search(dto);
        return dto;
    }

    /**
     * Description:jupm to add page
     * 
     * @return
     */
    @RequestMapping(value = "/addPage", method = RequestMethod.GET)
    public ModelAndView toaddPage() {
        ModelAndView mav = new ModelAndView("/role/role_add");
        mav.addObject("functions", sRoleInfoService.getFunctionTree(null));
        return mav;
    }

    /**
     * Description :add and save
     * 
     * @param dto
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> add(@RequestBody SRoleInfoSaveAndUpdateDto dto) {
        return sRoleInfoService.save(dto);
    }

    /**
     * Description:jump to edit page
     * 
     * @return
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView toeditPage(@PathVariable("id") Long id) {
        ModelAndView mav = new ModelAndView("/role/role_edit");
        mav.addObject("roleInfo", sRoleInfoService.findById(id));
        mav.addObject("functions", sRoleInfoService.getFunctionTree(null));
        return mav;
    }

    /**
     * Description:save edit 
     * 
     * @param dto
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> edit(@RequestBody SRoleInfoSaveAndUpdateDto dto) {
        return sRoleInfoService.update(dto);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<String> delete(@PathVariable("id") Long id) {

        return sRoleInfoService.delete(id);
    }

    /**
     * Description:Go to the view page
     * 
     * @return
     */
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public ModelAndView detailPage(@PathVariable("id") Long id) {
        ModelAndView mav = new ModelAndView("/role/role_detail");
        SRoleInfoSaveAndUpdateDto dto = sRoleInfoService.findById(id);
        List<TreeNodeBean> functions = sRoleInfoService.getFunctionTree(null);
        mav.addObject("roleInfo", dto);
        mav.addObject("functions", functions);
        List<Long> nodeIds = new ArrayList<Long>();
        for (TreeNodeBean bean : functions) {
            for (Long fids : dto.getFunctionIds()) {
                for(TreeNodeBean child : bean.getChildrenList()){
                    if (child.getId().equals(fids)) {
                        nodeIds.add(child.getPid());
                    }
                }
            }
        }
        mav.addObject("nodeIds", nodeIds);
        return mav;
    }
}
