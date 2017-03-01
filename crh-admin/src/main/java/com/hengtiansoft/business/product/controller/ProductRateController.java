package com.hengtiansoft.business.product.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.product.dto.ProductRatePageDto;
import com.hengtiansoft.business.product.service.ProductRateService;

@Controller
@RequestMapping(value = "/productRate")
public class ProductRateController {

    @Autowired
    private ProductRateService productRateService;
    
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView index(@PathVariable("id") Long id) {

        ModelAndView mav = new ModelAndView("/product/product_rate");
        
        mav.addObject("productName", productRateService.getProductName(id));
        mav.addObject("productNo", id);
        Double avg = productRateService.getAvgStar(id);
        mav.addObject("avgStar", null==avg? 0:new BigDecimal(avg).setScale(0, BigDecimal.ROUND_HALF_UP).intValue());
        
        mav.addObject("allRate", null==avg? 0:avg);
        return mav;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public ProductRatePageDto search(@RequestBody ProductRatePageDto dto) {

        productRateService.search(dto);
        return dto;
    }
}
