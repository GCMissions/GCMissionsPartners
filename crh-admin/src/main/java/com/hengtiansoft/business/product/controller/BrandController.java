package com.hengtiansoft.business.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.product.dto.ProductBrandDto;
import com.hengtiansoft.business.product.dto.ProductBrandEditDto;
import com.hengtiansoft.business.product.dto.ProductBrandPageDto;
import com.hengtiansoft.business.product.service.BrandService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;

/**
 * 
 * @author jiekaihu
 *
 */
@Controller
@RequestMapping(value = "/brand")
public class BrandController {

	@Autowired
	private BrandService brandService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView list() {

	    return new ModelAndView("brand/brand_list");
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ProductBrandPageDto brandList(@RequestBody ProductBrandPageDto dto) {

		brandService.getBrandList(dto);
		return dto;
	}

	/**
	 * Description: 添加品牌界面
	 * 
	 */
	@RequestMapping(value = "/addPage", method = RequestMethod.GET)
	public ModelAndView add() {

	    return new ModelAndView("brand/brand_add");
	}

	/**
	 * Description:添加单个品牌
	 * 
	 * @param dto
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto<String> addBrand(@RequestBody ProductBrandDto dto) {

		return brandService.saveBrand(dto);
	}

	/**
	 * Description: 删除选定的品牌（1个以上）
	 * 
	 * @param brandId
	 * @return
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResultDto<StringBuilder> deleteById(
			@PathVariable(value = "id") Long brandId) {
		return brandService.deleteById(brandId);
	}

	/**
	 * Description:进入编辑界面
	 * 
	 */
	@RequestMapping(value = "/editPage", method = RequestMethod.GET)
	public ModelAndView edit() {

	    return new ModelAndView("brand/brand_edit");
	}

	/**
	 * Description:编辑品牌数据dto
	 * 
	 * @param brandId
	 * @return
	 */
	@RequestMapping(value = "/detail/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto<ProductBrandEditDto> detailBrand(
			@PathVariable(value = "id") Long brandId) {
		ProductBrandEditDto dto = new ProductBrandEditDto();
		dto.setBrandDto(brandService.findOne(brandId));
		return ResultDtoFactory.toAck("编辑品牌数据dto", dto);
	}

	/**
	 * Description:编辑保存
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto<String> editBrand(@RequestBody ProductBrandDto dto) {

		return brandService.editBrand(dto);
	}

}
