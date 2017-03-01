package com.hengtiansoft.business.finance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.finance.dto.ProductPageDto;
import com.hengtiansoft.business.finance.service.CostPriceManageService;
import com.hengtiansoft.business.product.service.BrandService;
import com.hengtiansoft.business.product.service.CategoryService;
import com.hengtiansoft.common.dto.ResultDto;

/**
 * Class Name: ProductPriceController
 * Description: TODO
 * 
 * @author chenghongtu
 */
@Controller
@RequestMapping("costPrice")
public class CostPriceController {

    @Autowired
    private CostPriceManageService costPriceManageService;

    @Autowired
    private BrandService           brandService;

    @Autowired
    private CategoryService        categoryService;

    @RequestMapping(value = { "/" }, method = RequestMethod.GET)
    public ModelAndView list() throws Exception {
        ModelAndView mav = new ModelAndView("financialManage/costPrice_list");
        mav.addObject("brand", brandService.findAll());
        mav.addObject("category", categoryService.findAll());
        return mav;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public ProductPageDto search(@RequestBody ProductPageDto productPageDto) {
        costPriceManageService.searchProduct(productPageDto);
        return productPageDto;
    }

    @RequestMapping(value = "/updateCostPrice", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> updateCostPrice(@RequestBody ProductPageDto productPageDto) {

        return costPriceManageService.updateCostPrice(productPageDto.getList());
    }
}
