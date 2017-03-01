package com.hengtiansoft.business.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.product.dto.ProductFloorSaveDto;
import com.hengtiansoft.business.product.dto.ProductFloorSearchDto;
import com.hengtiansoft.business.product.dto.ProductPageSearchDto;
import com.hengtiansoft.business.product.service.ProductPFloorService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;

/**
 * 
* Class Name: ProductPFloorController
* Description: 楼层管理
* @author zhisongliu
*
 */
@Controller
@RequestMapping(value = "/productPfloor")
public class ProductPFloorController {

    @Autowired
    private ProductPFloorService floorService;

    /**
     * 
     * 首页
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("/product/product_floor");
    }

    /**
     * 根据regionId和楼层Id来获取当前楼层的所有商品
     * @return
     */
    @RequestMapping(value = "/floorData", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<ProductFloorSaveDto> floorData(@RequestBody ProductFloorSearchDto dto) {

        return ResultDtoFactory.toAck("商品DTO", floorService.findByRegionIdAndfloorType(dto));
    }

    /**
     * 保存楼层设置信息
     * @return
     * 
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> floorSave(@RequestBody ProductFloorSaveDto dto) {
        return floorService.save(dto);
    }

    /**
     * 根据regionId来搜索所有已经上架的商品
     * 
     * 
     */
    @RequestMapping(value = "/searchProduct", method = RequestMethod.POST)
    @ResponseBody
    public ProductPageSearchDto floorProductSearch(@RequestBody ProductPageSearchDto dto) {
        List<Long> productIds = floorService.findByRegionId(dto.getRegionId());
        if (productIds != null && productIds.size() > 0) {
            floorService.search(dto, productIds);
        }
        return dto;
    }
}
