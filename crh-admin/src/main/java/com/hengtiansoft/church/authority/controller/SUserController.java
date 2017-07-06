package com.hengtiansoft.church.authority.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.church.authority.dto.SUserEditDto;
import com.hengtiansoft.church.authority.dto.SUserSaveAndUpdateDto;
import com.hengtiansoft.church.authority.dto.SUserSearchDto;
import com.hengtiansoft.church.authority.dto.SUserUpdateDto;
import com.hengtiansoft.church.authority.service.SUserService;
import com.hengtiansoft.church.entity.SRoleInfoEntity;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.util.AppConfigUtil;

/**
 * Class Name: SUserController Description: account controller
 * 
 * @author taochen
 */
@Controller
@RequestMapping(value = "/user")
public class SUserController {

    @Autowired
    private SUserService sUserService;

    /**
     * Description:go to homepage
     * 
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("/user/user_list");
        mav.addObject("listRoles", sUserService.findRoles());
        return mav;
    }

    /**
     * Description:homepage's data
     * 
     * @param dto
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public SUserSearchDto search(@RequestBody SUserSearchDto dto) {
        sUserService.search(dto);
        return dto;
    }

    /**
     * Description:go to add page
     * 
     * @return
     */
    @RequestMapping(value = "/addPage", method = RequestMethod.GET)
    public ModelAndView toaddPage() {
        return new ModelAndView("/user/user_add");
    }

    /**
     * Description :get add page's role data
     * 
     * @return
     */
    @RequestMapping(value = "/addData", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<List<SRoleInfoEntity>> addPageData() {
        List<SRoleInfoEntity> list = sUserService.findRoles();
        return ResultDtoFactory.toAck("Role Dto", list);

    }

    /**
     * Description : add and save
     * 
     * @param dto
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> add(@RequestBody SUserSaveAndUpdateDto dto) {
        return sUserService.save(dto);
    }

    /**
     * Description:go to edit page
     * 
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView toeditPage() {
        return new ModelAndView("/user/user_edit");
    }

    /**
     * Description : edit page's data
     */
    @RequestMapping(value = "/editData/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<SUserEditDto> editData(@PathVariable("id") Long id) {
        SUserEditDto editDto = new SUserEditDto();
        List<SRoleInfoEntity> roles = sUserService.findRoles();
        SUserSaveAndUpdateDto dto = sUserService.findById(id);
        editDto.setDto(dto);
        editDto.setList(roles);
        return ResultDtoFactory.toAck("Edit DTO", editDto);
    }
    
    /**
     * Description:save edit 
     * 
     * @param dto
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> edit(@RequestBody SUserUpdateDto dto) {
        /** 2016-8-18 yigesong Edit the user when the state does not set WELY-175, specifically open a unlock user button **/
        return sUserService.update(dto);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<String> delete(@PathVariable("id") Long id) {

        return sUserService.detele(id);
    }

    /**
     * Description: Password Reset
     *
     * @param memberId
     * @return
     */
    @RequestMapping(value = { "/resetPwd/{loginId}" }, method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> resetPwd(@PathVariable(value = "loginId") String loginId) {
        return sUserService.resetPwd(loginId);
    }

    /**
     * Description:go to edit personal data
     * 
     * @return
     */
    @RequestMapping(value = "/selfEditPage", method = RequestMethod.GET)
    public ModelAndView selfEditPage() {
        return new ModelAndView("/user/user_selfEdit");
    }

    /**
     * Description:personal data
     */
    @RequestMapping(value = "/selfEditData", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<SUserEditDto> selfEditData() {
        Long id = AuthorityContext.getCurrentUser().getUserId();
        SUserEditDto editDto = new SUserEditDto();
        List<SRoleInfoEntity> roles = sUserService.findRolesById(id);
        SUserSaveAndUpdateDto dto = sUserService.findById(id);
        editDto.setDto(dto);
        editDto.setList(roles);
        editDto.setQrCodeUrl(AppConfigUtil.getConfig("common.qr.server") + AppConfigUtil.getConfig("common.qr.url") + "/?" + dto.getOrgId());

        return ResultDtoFactory.toAck("Personal information editor DTO", editDto);
    }

    /**
     * Description:save edit 
     * 
     * @param dto
     * @return
     */
    @RequestMapping(value = "/selfEdit", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> selfEdit(@RequestBody SUserSaveAndUpdateDto dto) {
        return sUserService.selfUpdate(dto);
    }
}
