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
 * Class Name: SUserController Description: 账户控制器
 * 
 * @author zhisongliu
 */
@Controller
@RequestMapping(value = "/user")
public class SUserController {

    @Autowired
    private SUserService sUserService;

    /**
     * Description:进入首页
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
     * Description:首页数据
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
     * Description:进入到添加页面
     * 
     * @return
     */
    @RequestMapping(value = "/addPage", method = RequestMethod.GET)
    public ModelAndView toaddPage() {
        return new ModelAndView("/user/user_add");
    }

    /**
     * Description :获取添加页面角色数据
     * 
     * @return
     */
    @RequestMapping(value = "/addData", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<List<SRoleInfoEntity>> addPageData() {
        List<SRoleInfoEntity> list = sUserService.findRoles();
        return ResultDtoFactory.toAck("角色Dto", list);

    }

    /**
     * Description : 新增保存
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
     * Description:进入到编辑页面
     * 
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView toeditPage() {
        return new ModelAndView("/user/user_edit");
    }

    /**
     * Description : 编辑页面数据
     */
    @RequestMapping(value = "/editData/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<SUserEditDto> editData(@PathVariable("id") Long id) {
        SUserEditDto editDto = new SUserEditDto();
        List<SRoleInfoEntity> roles = sUserService.findRoles();
        SUserSaveAndUpdateDto dto = sUserService.findById(id);
        editDto.setDto(dto);
        editDto.setList(roles);
        return ResultDtoFactory.toAck("编辑DTO", editDto);
    }
    
    /**
     * Description:编辑保存
     * 
     * @param dto
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> edit(@RequestBody SUserUpdateDto dto) {
        /** 2016-8-18 yigesong 编辑用户时状态不设置 WELY-175，专门开一个解锁用户按钮 **/
        return sUserService.update(dto);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<String> delete(@PathVariable("id") Long id) {

        return sUserService.detele(id);
    }

    /**
     * Description: 密码重置
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
     * Description:进入到个人资料编辑页面
     * 
     * @return
     */
    @RequestMapping(value = "/selfEditPage", method = RequestMethod.GET)
    public ModelAndView selfEditPage() {
        return new ModelAndView("/user/user_selfEdit");
    }

    /**
     * Description:个人资料编辑页面数据
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

        return ResultDtoFactory.toAck("个人资料编辑DTO", editDto);
    }

    /**
     * Description:编辑保存
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
