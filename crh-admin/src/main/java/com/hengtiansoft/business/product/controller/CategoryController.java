package com.hengtiansoft.business.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.product.dto.ProductCategoryAddDto;
import com.hengtiansoft.business.product.dto.ProductCategoryDto;
import com.hengtiansoft.business.product.dto.ProductCategoryEditDto;
import com.hengtiansoft.business.product.service.CategoryService;
import com.hengtiansoft.business.product.service.PTypeService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;

/**
 * 
 * @author chenghongtu
 *
 */
@Controller
@RequestMapping(value = "/category")
public class CategoryController {

	@Autowired
	private CategoryService cateService;

	@Autowired
	private PTypeService typeService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView list() {

	    return new ModelAndView("category/cate_list");
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto<List<ProductCategoryDto>> categoryFatherList() {

		return ResultDtoFactory.toAck("获取所有品类成功", cateService.findAll());
	}

	/**
	 * Description: 获取单个父节点下的分类列表
	 * 
	 * @param cateId
	 */
	@RequestMapping(value = "/list/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto<List<ProductCategoryDto>> getListById(
			@PathVariable(value = "id") Long cateId) {

		return ResultDtoFactory.toAck("获取子品类成功",
				cateService.getListById(cateId));
	}

	/**
	 * Description: 添加分类界面
	 * 
	 */
	@RequestMapping(value = "/addPage", method = RequestMethod.GET)
	public ModelAndView add() {

	    return new ModelAndView("category/cate_add");
	}

	/**
	 * Description: 添加分类数据dto
	 * 
	 * @return
	 */
	@RequestMapping(value = "/addData", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto<ProductCategoryAddDto> addData() {
		ProductCategoryAddDto addDto = new ProductCategoryAddDto();
		addDto.setTypeList(typeService.findAll());

		return ResultDtoFactory.toAck("添加品类数据dto", addDto);

	}

	/**
	 * Description:添加单个分类
	 * 
	 * @param dto
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto<String> addCategory(@RequestBody ProductCategoryDto dto) {

		return cateService.saveCategory(dto);
	}

	/**
	 * Description: 删除分类
	 * 
	 * @param cateId
	 * @return
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResultDto<String> deleteById(@PathVariable(value = "id") Long cateId) {

		return cateService.deleteById(cateId);
	}

	/**
	 * Description:进入编辑界面
	 * 
	 */
	@RequestMapping(value = "/editPage", method = RequestMethod.GET)
	public ModelAndView edit() {

	    return new ModelAndView("category/category_edit");
	}

	/**
	 * Description: 编辑分类数据dto
	 * 
	 * @param cateId
	 * @return
	 */
	@RequestMapping(value = "/editData/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto<ProductCategoryEditDto> editData(
			@PathVariable(value = "id") Long cateId) {
		ProductCategoryEditDto editDto = new ProductCategoryEditDto();
		ProductCategoryDto cateDto = cateService.getCategory(cateId);
		editDto.setTypeList(typeService.findAll());
		editDto.setCateDto(cateDto);

		// 设置当前分类的父类名
		Long parentId = cateDto.getParentId();
		if (parentId == 0L) {
			editDto.setParentName("顶级品类");
		} else {
			editDto.setParentName(cateService.getCategory(parentId)
					.getCateName());
			//父类同级list
			editDto.setCateList(cateService.getParentListById(cateId));
		}

		return ResultDtoFactory.toAck("编辑品类数据dto", editDto);
	}

	/**
	 * Description:编辑保存
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto<String> editCategory(@RequestBody ProductCategoryDto dto) {

		return cateService.editCategory(dto);
	}
}
