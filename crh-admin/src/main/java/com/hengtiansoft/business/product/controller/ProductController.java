package com.hengtiansoft.business.product.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.product.dto.PCateProductDto;
import com.hengtiansoft.business.product.dto.PGoodsSearchDto;
import com.hengtiansoft.business.product.dto.ProductSaveDto;
import com.hengtiansoft.business.product.dto.ProductSearchDto;
import com.hengtiansoft.business.product.service.BrandService;
import com.hengtiansoft.business.product.service.CategoryService;
import com.hengtiansoft.business.product.service.GoodsService;
import com.hengtiansoft.business.product.service.ProductService;
import com.hengtiansoft.common.dto.ResultDto;

/**
 * 
 * Class Name: ProductController Description: 商品管理
 * 
 * @author zhisongliu
 *
 */
@Controller
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private GoodsService goodsService;

    private final String ISREVIEW = "1";

    /**
     * Description:商品管理首页
     * 
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("/product/product_list");
        mav.addObject("listBrands", brandService.findAllBrands());
        mav.addObject("listCates", categoryService.findAllCates());
        return mav;
    }

    /**
     * Description :商品首页数据
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public ProductSearchDto list(@RequestBody ProductSearchDto dto) {
        productService.search(dto);
        return dto;
    }

    /**
     * Description :选择添加商品的分类
     * 
     * @return
     */
    @RequestMapping(value = "/addPage", method = RequestMethod.GET)
    public ModelAndView beforeAdd(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("product/product_add_chooseCat");
        mav.addObject("msg", request.getAttribute("msg"));
        return mav;
    }

    /**
     * 
     * Description : 进入到添加页面
     * 
     * @param
     * @return
     * @throws IOException
     * @throws ServletException
     * @throws Exception
     */

    @RequestMapping(value = "/addPage/{cateId}", method = RequestMethod.GET)
    public ModelAndView addPage(@PathVariable("cateId") Long cateId, HttpServletRequest request,
            HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("product/product_add");
        Object object = productService.findCateByCateId(cateId, request, response);
        if (object.getClass().equals(String.class)) {
            return new ModelAndView(object.toString());
        }
        mav.addObject("cateInfo", (PCateProductDto) object);
        return mav;
    }

    /**
     * 
     * Description: 商品添加
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<ProductSaveDto> add(@RequestBody ProductSaveDto dto) {
        return productService.save(dto);
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
     * Description : 进入到编辑页面
     * 
     * @param id
     * @return
     * @throws IOException
     * @throws ServletException
     */
    @RequestMapping(value = "/editPage/{id}", method = RequestMethod.GET)
    public ModelAndView editPage(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("product/product_add");
        Object object = productService.findCateById(id, request, response);
        mav.addObject("cateInfo", (PCateProductDto) object);
        mav.addObject("productDto", productService.findById(id));
        return mav;
    }

    /**
     * Description : 进入到预览页面
     * 
     * @param id
     * @return
     * @throws IOException
     * @throws ServletException
     */
    @RequestMapping(value = "/viewPage/{id}", method = RequestMethod.GET)
    public ModelAndView viewPage(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("product/product_add");
        Object object = productService.findCateById(id, request, response);
        mav.addObject("cateInfo", (PCateProductDto) object);
        mav.addObject("productDto", productService.findById(id));
        mav.addObject("isReview", ISREVIEW);
        return mav;
    }

    /**
     * Description:编辑
     * 
     * @param dto
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> edit(@RequestBody ProductSaveDto dto) {

        return productService.update(dto);
    }

    /**
     * 删除
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> delete(@PathVariable("id") Long id) {

        return productService.delete(id);

    }

}
