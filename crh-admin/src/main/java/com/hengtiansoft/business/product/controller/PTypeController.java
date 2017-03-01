package com.hengtiansoft.business.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.product.dto.PTypeAddDto;
import com.hengtiansoft.business.product.dto.PTypeEditDto;
import com.hengtiansoft.business.product.dto.PTypeSaveDto;
import com.hengtiansoft.business.product.dto.PTypeSearchDto;
import com.hengtiansoft.business.product.service.AttributeService;
import com.hengtiansoft.business.product.service.BrandService;
import com.hengtiansoft.business.product.service.PTypeService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
/**
 * 
* Class Name: PTypeController
* Description: 类型管理
* @author zhisongliu
*
 */
@Controller
@RequestMapping(value = "/ptype")
public class PTypeController {

    @Autowired
    private PTypeService     pTypeService;

    @Autowired
    private AttributeService attributeService;

    @Autowired
    private BrandService     brandService;

    /**
     * Description:进入首页
     * 
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("/ptype/ptype_list");
    }

    /**
     * Description:获取首页数据
     * 
     * @param dto
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public PTypeSearchDto search(@RequestBody PTypeSearchDto dto) {
        pTypeService.search(dto);
        return dto;
    }

    /**
     * 
     * Description : 进入到新增页面
     * 
     * @return
     */
    @RequestMapping(value = "/addPage", method = RequestMethod.GET)
    public ModelAndView addPage() {

        ModelAndView mav = new ModelAndView("/ptype/ptype_add");

        return mav;
    }

    /**
     * Desc :获取新增数据
     * 
     * @return
     */
    @RequestMapping(value = "/addData", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<PTypeAddDto> addData() {
        PTypeAddDto dto = new PTypeAddDto();
        dto.setListBrands(brandService.findAllBrands());
        dto.setListAttrs(attributeService.findAllAttributes());
        return ResultDtoFactory.toAck("", dto);
    }

    /**
     * Description :新增保存
     * 
     * @param dto
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> add(@RequestBody PTypeSaveDto dto) {
        return pTypeService.save(dto);
    }

    /**
     * Description :进入到编辑页面
     * 
     * @return
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editPage(@PathVariable(value = "id") Long id) {

        return new ModelAndView("/ptype/ptype_edit");
    }

    /**
     * DESC 编辑数据
     * 
     * 
     */
    @RequestMapping(value = "/editData/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<PTypeEditDto> editData(@PathVariable("id") Long id) {
        PTypeEditDto editDto = new PTypeEditDto();
        editDto.setDto(pTypeService.findById(id));
        editDto.setListAttrs(attributeService.findAllAttributes());
        editDto.setListBrands(brandService.findAllBrands());
        return ResultDtoFactory.toAck("编辑DTO", editDto);
    }

    /**
     * Description 编辑保存
     * @param dto
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> edit(@RequestBody PTypeSaveDto dto) {

        return pTypeService.update(dto);
    }

    /**
     * Description  删除某个类型
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<String> delete(@PathVariable(value = "id") Long id) {

        return pTypeService.delete(id);
    }
}
