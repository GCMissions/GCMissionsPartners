package com.hengtiansoft.business.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.product.dto.AttributeDto;
import com.hengtiansoft.business.product.dto.AttributeSearchDto;
import com.hengtiansoft.business.product.service.AttributeService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;

/**
 * Class Name: AttributeController
 * Description: 属性管理
 * 
 * @author zhisongliu
 */
@Controller
@RequestMapping(value = "/attribute")
public class AttributeController {

    @Autowired
    private AttributeService attributeService;

    /**
     * Description:属性管理首页
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView attributeIndex() {
        return new ModelAndView("/attribute/attribute_list");
    }

    /**
     * Description:搜索
     * 
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public AttributeSearchDto searchAttributes(@RequestBody AttributeSearchDto dto) {
        attributeService.search(dto);
        return dto;
    }

    /**
     * Description:新增页面
     */
    @RequestMapping(value = "/addPage", method = RequestMethod.GET)
    public ModelAndView toAddAttribute() {
        return new ModelAndView("/attribute_add");
    }

    /**
     * Description:保存
     * 
     * @param dto
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> addAttribute(@RequestBody AttributeDto dto) {

        return attributeService.save(dto);
    }

    /**
     * Description:编辑页面
     * 
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/edit/{attrId}", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<AttributeDto> toUpdate(@PathVariable(value = "attrId") Long attrId) {
        return ResultDtoFactory.toAck("详情DTO", attributeService.findById(attrId));
    }

    /**
     * Description:更新操作
     * 
     * @param dto
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> update(@RequestBody AttributeDto dto) {

        return attributeService.update(dto);
    }

    @RequestMapping(value = "/delete/{attrId}", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<String> delete(@PathVariable(value = "attrId") Long attrId) {

        return attributeService.delete(attrId);
    }
}
