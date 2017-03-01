package com.hengtiansoft.business.activity.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.activity.dto.ActivityCopyPageDto;
import com.hengtiansoft.business.activity.dto.ActivityDetailSaveDto;
import com.hengtiansoft.business.activity.dto.ActivityEditDto;
import com.hengtiansoft.business.activity.dto.ActivitySaveDto;
import com.hengtiansoft.business.activity.dto.ActivitySearchDto;
import com.hengtiansoft.business.activity.dto.ActivityStockDto;
import com.hengtiansoft.business.activity.service.ActivityService;
import com.hengtiansoft.business.activity.service.ActivityStockService;
import com.hengtiansoft.business.product.dto.ProductCategoryDto;
import com.hengtiansoft.business.product.service.CategoryService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.exception.WRWException;
import com.hengtiansoft.wrw.dao.SBasicParaDao;
import com.hengtiansoft.wrw.entity.PProductEntity;
import com.hengtiansoft.wrw.entity.SOrgEntity;
/**
 * 
* Class Name: GoodController
* Description: 商品新增(商品基本信息、商品详情介绍)
* @author chenghongtu
*
 */
@Controller
@RequestMapping(value = "activity")
public class ActivityController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityController.class);

    @Autowired
    private SBasicParaDao basicParaDao;
    
    @Autowired
    private ActivityService activityService;

    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private ActivityStockService activityStockService;
    
    private final static String ISREVIEW = "1";
    private final static String HOST_TYPE = "域名信息";
    private final static String WEW_HOST_PARA = "WEW_HOST";
    
    /**
     * Description :商品列表首页
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("/activity/activity_list");
        mav.addObject("firstCates", activityService.findAllFirstCates());
        mav.addObject("listOrgs", activityService.findOrgs());
        mav.addObject("wechatHost", basicParaDao.findParaValue1ByTypeName(HOST_TYPE, WEW_HOST_PARA));
        return mav;
    }
    
    /**
     * Description :商品首页数据
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/activityList", method = RequestMethod.POST)
    @ResponseBody
    public ActivitySearchDto list(@RequestBody ActivitySearchDto dto) {
        activityService.search(dto);
        return dto;
    }
    
    /**
     * 
     * Description:添加页面
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/addPage", method = RequestMethod.GET)
    public ModelAndView addPage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("activity/activity_save");
        List<ProductCategoryDto> cates = categoryService.getCategoryFatherList();
        List<SOrgEntity> orgs = activityService.findOrgs();
        mav.addObject("cates", cates);
        mav.addObject("orgs", orgs);
        mav.addObject("mainSpecList", activityStockService.queryMainSpecs());
        mav.addObject("reqFieldList", activityStockService.queryRequiredFields());
        
        return mav;
    }
    
    /**
     * 
     * Description: 进入到编辑页面
     *
     * @param id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/editPage/{id}", method = RequestMethod.GET)
    public ModelAndView editPage(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("activity/activity_edit");
        mav.addObject("orgs", activityService.findOrgs());
        mav.addObject("cateInfo", activityService.findCateByProductId(id));
        mav.addObject("productDto", activityService.findById(id));
        mav.addObject("mainSpecList", activityStockService.queryMainSpecs());
        mav.addObject("reqFieldList", activityStockService.queryRequiredFields());
        
        return mav;
    }
    
    /**
     * 
     * Description: 进入到预览页面
     *
     * @param id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/viewPage/{id}", method = RequestMethod.GET)
    public ModelAndView viewPage(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("activity/activity_view");
        mav.addObject("cateInfo", activityService.findCateByProductId(id));
        mav.addObject("productDto", activityService.findById(id));
        mav.addObject("orgs", activityService.findOrgs());
        mav.addObject("productId", id);
        List<ActivityStockDto> actStockList = activityStockService.findAllByActId(id);
        mav.addObject("actStockList", actStockList);
        if (CollectionUtils.isNotEmpty(actStockList)) {
            mav.addObject("showStock", actStockList.get(0).getShowStock());
        }
        mav.addObject("reqFieldList", activityStockService.queryRequiredFields());
        mav.addObject("isReview", ISREVIEW);
        return mav;
    }
    
    /**
     * 
     * Description: 根据一级商品品类id获得二级商品品类
     *
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "/getSubCategoryByParent/{categoryId}", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<List<ProductCategoryDto>> getSubCategoryByParent(@PathVariable("categoryId") Long categoryId){
        List<ProductCategoryDto> cates = new ArrayList<>();
        try {
            cates = categoryService.getListById(categoryId);
        } catch (Exception e) {
            LOGGER.error("查询失败:",e);
            return ResultDtoFactory.toNack("操作失败!");
        }
        return ResultDtoFactory.toAck("二级品类",cates);
    }

    /**
     * 
     * Description: 获取一级商品品类
     *
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "/getParentCate", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<List<ProductCategoryDto>> getParentCate(){
        List<ProductCategoryDto> cates = new ArrayList<>();
        try {
            cates = categoryService.getCategoryFatherList();
        } catch (Exception e) {
            LOGGER.error("查询失败:",e);
            return ResultDtoFactory.toNack("操作失败!");
        }
        return ResultDtoFactory.toAck("一级品类",cates);
    }
    
    /**
     * 
     * Description: 获取父品类
     *
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "/getParentCateBySon/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Long> getParentCateBySon(@PathVariable("id")Long id){
        Long pId = null;
        try {
            pId = activityService.getParentCateBySon(id).getParentId();
        } catch (Exception e) {
            LOGGER.error("查询失败:",e);
            return ResultDtoFactory.toNack("操作失败!");
        }
        return ResultDtoFactory.toAck("一级品类",pId);
    }
    
    /**
     * 
     * Description: 商品基本信息添加
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<PProductEntity> add(@RequestBody ActivitySaveDto dto) {
        PProductEntity product = new PProductEntity();
        try {
            product = activityService.save(dto);
        } catch (WRWException w) {
            LOGGER.info("商品基本信息添加失败:",w);
            return ResultDtoFactory.toNack(w.getMessage());
        } catch (Exception e) {
            LOGGER.error("商品基本信息添加失败:",e);
            return ResultDtoFactory.toNack("保存失败!");
        }
        return ResultDtoFactory.toAck("保存成功!", product);
    }
    
    /**
     * 
     * Description: 商品详情添加
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/saveDetail", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<PProductEntity> saveDetail(@RequestBody ActivityDetailSaveDto dto) {
        PProductEntity product = new PProductEntity();
        try {
            product = activityService.saveDetail(dto);
        } catch (WRWException w) {
            LOGGER.info("商品详情操作失败:",w);
            return ResultDtoFactory.toNack(w.getMessage());
        } catch (Exception e) {
            LOGGER.error("商品详情操作失败:",e);
            return ResultDtoFactory.toNack("保存失败!");
        }
        return ResultDtoFactory.toAck("保存成功!", product);
    }
    
    /**
     * 
     * Description: 商品基本信息更新
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<PProductEntity> update(@RequestBody ActivityEditDto dto) {
        PProductEntity entity = new PProductEntity();
        try {
            entity = activityService.update(dto);
        } catch (WRWException w) {
            LOGGER.info("更新商品:",w);
            return ResultDtoFactory.toNack(w.getMessage());
        } catch (Exception e) {
            LOGGER.error("更新商品:",e);
            return ResultDtoFactory.toNack("保存失败!");
        }
        return ResultDtoFactory.toAck("保存成功!",entity);
    }
    
    /**
     * Description: 商品删除
     * 
     * @param String ids
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> delete(@RequestParam("ids") String ids){
        return activityService.delete(ids);
    }
    
    /**
     * 
     * Description: 获取服务商
     *
     * @return
     */
    @RequestMapping(value = "/getOrgs", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<List<SOrgEntity>> getOrgs(){
        List<SOrgEntity> orgs = new ArrayList<SOrgEntity>();
        try {
            orgs = activityService.findOrgs();
        } catch (Exception e) {
            LOGGER.error("查询失败:",e);
            return ResultDtoFactory.toNack("操作失败!");
        }
        return ResultDtoFactory.toAck("服务商",orgs);
    }
    
    /**
     * 
     * Description: 获得已有活动列表
     *
     * @param activityCopyPageDto
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public ActivityCopyPageDto search(@RequestBody ActivityCopyPageDto activityCopyPageDto){
        activityService.search(activityCopyPageDto);
        return activityCopyPageDto;
    }
    
}
