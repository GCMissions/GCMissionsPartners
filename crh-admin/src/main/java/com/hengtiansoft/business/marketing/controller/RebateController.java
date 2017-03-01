package com.hengtiansoft.business.marketing.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.marketing.dto.ProductRebatePageDto;
import com.hengtiansoft.business.marketing.dto.RebateProvinceDto;
import com.hengtiansoft.business.marketing.dto.RefereeRebateDto;
import com.hengtiansoft.business.marketing.service.FreightRebateService;
import com.hengtiansoft.business.marketing.service.ProductRebateService;
import com.hengtiansoft.business.product.service.BrandService;
import com.hengtiansoft.business.product.service.CategoryService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.enumeration.EErrorCode;

/**
 * Class Name: RebateController Description: 平台-营销管理-返点比例设置
 * 
 * @author chenghongtu
 */
@Controller
@RequestMapping("rebate")
public class RebateController {

    @Autowired
    private ProductRebateService productRebateService;

    @Autowired
    private FreightRebateService freightRebateService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = { "/" }, method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("marketing/rebate_list");
        mav.addObject("brand", brandService.findAll());
        mav.addObject("category", categoryService.findAll());
        return mav;
    }

    // 上架商品列表
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public ProductRebatePageDto search(@RequestBody ProductRebatePageDto dto) {
        productRebateService.search(dto);
        return dto;
    }

    // 商品返点比例查询
    @RequestMapping(value = "/detail/{productId}", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<List<RebateProvinceDto>> findOne(@PathVariable Long productId) {
        List<RebateProvinceDto> data = productRebateService.findOne(productId);
        return ResultDtoFactory.toAck("success", data);
    }

    // 商品返点比例保存
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> save(@RequestBody RebateProvinceDto rebateProvinceDto) {
        productRebateService.save(rebateProvinceDto);
        return ResultDtoFactory.toAck(EErrorCode.SUCCESS);
    }

    // 城市返点比例查询
    @RequestMapping(value = "/cityDetail", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<List<RebateProvinceDto>> findFreightRebate() {
        List<RebateProvinceDto> data = freightRebateService.findFreightRebate();
        return ResultDtoFactory.toAck("success", data);
    }

    // 城市返点比例保存
    @RequestMapping(value = "/saveFreightRebate", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> saveFreightRebate(@RequestBody RebateProvinceDto rebateProvinceDto) {
        freightRebateService.saveFreightRebate(rebateProvinceDto);
        return ResultDtoFactory.toAck(EErrorCode.SUCCESS);
    }

    @RequestMapping(value = "/refereeRebate", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Double> findRefereeRebate() {
        return freightRebateService.findRefereeRebate();
    }

    @RequestMapping(value = "/saveRefereeRebate", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> saveRefereeRebate(@RequestBody RefereeRebateDto dto) {
        return freightRebateService.saveRefereeRebate(dto);
    }
}
