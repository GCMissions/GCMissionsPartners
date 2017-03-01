package com.hengtiansoft.business.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.activity.service.ActivityService;
import com.hengtiansoft.business.product.dto.ProductDeliveryShiefDto;
import com.hengtiansoft.business.product.dto.ProductPriceDto;
import com.hengtiansoft.business.product.dto.ProductShiefSearchDto;
import com.hengtiansoft.business.product.service.ProductShiefService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;

/**
 * 
* Class Name: ProductShiefController
* Description: 商品上下架管理
* @author  
*
 */

@Controller
@RequestMapping(value = "/productShief")
public class ProductShiefController {

    @Autowired
    private ProductShiefService productShiefService;

    @Autowired
    private ActivityService activityService;
    /**
     * Desc 进入首页
     * 
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {

        ModelAndView mav = new ModelAndView("/product/product_sale_list");

        mav.addObject("firstCates", activityService.findAllFirstCates());
        mav.addObject("listOrgs", activityService.findOrgs());
        return mav;
    }

    /**
     * 获取列表
     * 
     * @param dto
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public ProductShiefSearchDto search(@RequestBody ProductShiefSearchDto dto) {

        productShiefService.search(dto);
        return dto;
    }


    /**
     * 进入上下架页面
     * 
     * 
     */
    @RequestMapping(value = "/shiefPage/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<ProductPriceDto> shiefPage(@PathVariable("id") Long id) {
        // 获取所有的城市以及上下架状态
        return ResultDtoFactory.toAck("上下架DTO", productShiefService.findShiefByProductId(id));
    }

    
    @RequestMapping(value = "/checkProduct/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<String> checkProduct(@PathVariable("id") Long id) {
        return productShiefService.checkProduct(id);
    }
    /**

     * 
     * 商品上下架操作 
     * 
     * @param dto
     * @return
     */
    @RequestMapping(value = "/productShief", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> downShief(@RequestBody ProductDeliveryShiefDto dto) {
//        ApplicationContextUtil.getBean(ProductShiefService.class).clearHomePageDataCache();
        return productShiefService.productShief(dto);
    }
    

}
