package com.hengtiansoft.business.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.product.dto.PGoodsDto;
import com.hengtiansoft.business.product.dto.PGoodsSearchDto;
import com.hengtiansoft.business.product.dto.ProductSearchDto;
import com.hengtiansoft.business.product.service.GoodsService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;

/**
 * 
 * Class Name: GoodsController
 * 
 * Description: 物料管理Controller
 * 
 * @author zhishenghong
 *
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * Description: 物料管理首页
     *
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("/goods/index");
    }

    /**
     * Description: 物料新增保存
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> save(@RequestBody PGoodsDto dto) {
        return goodsService.saveGoods(dto);
    }

    /**
     * Description: 更新物料信息
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> update(@RequestBody PGoodsDto dto) {
        return goodsService.updateGoods(dto);
    }

    /**
     * Description: 搜索物料
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public PGoodsSearchDto search(@RequestBody PGoodsSearchDto dto) {
        goodsService.searchGoods(dto);
        return dto;
    }

    /**
     * Description: 跟吴物料ID查出与此物料关联的商品数据
     *
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/productList", method = RequestMethod.GET)
    public ModelAndView detail(@RequestParam Long goodsId) {
        ModelAndView mav = new ModelAndView("/goods/product_list");
        PGoodsDto dto = goodsService.findByGoodsId(goodsId);
        mav.addObject("goodsId", goodsId);
        mav.addObject("goodsDto", dto);
        return mav;
    }

    /**
     * Description: 跟吴物料ID查出与此物料关联的商品数据
     *
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/searchProducts", method = RequestMethod.POST)
    @ResponseBody
    public ProductSearchDto searchProducts(@RequestBody ProductSearchDto dto) {
        goodsService.findProducts(dto);
        return dto;
    }

    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<PGoodsDto> findGoods(@RequestBody PGoodsDto dto) {
        return ResultDtoFactory.toAck("success", goodsService.findByGoodsId(dto.getGoodsId()));
    }
}
