package com.hengtiansoft.business.wrkd.activity.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.wrkd.activity.dto.KdBargainAllSpecInfo;
import com.hengtiansoft.business.wrkd.activity.dto.KdBargainPageDto;
import com.hengtiansoft.business.wrkd.activity.dto.KdBargainSaveAndEditDto;
import com.hengtiansoft.business.wrkd.activity.dto.TwentyfourInfo;
import com.hengtiansoft.business.wrkd.activity.exception.TwentyfourException;
import com.hengtiansoft.business.wrkd.activity.service.KdTwentyFourHoursService;
import com.hengtiansoft.business.wrkd.order.controller.KdOrderManagerController;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.exception.WRWException;
import com.hengtiansoft.wrw.dao.KdProductSaleDao;
import com.hengtiansoft.wrw.entity.KdProductSaleEntity;
import com.hengtiansoft.wrw.enums.KdBargainStatusEnum;

/**
 * 
 * Class Name: KdTwentyFourHoursController Description: 酷袋专享管理-24小时管理
 * 
 * @author chengchaoyin
 *
 */
@Controller
@RequestMapping("/kdBargain")
public class KdTwentyFourHoursController {

    private Logger logger = LoggerFactory.getLogger(KdOrderManagerController.class);

    private final String ISREVIEW = "1";

    @Autowired
    private KdTwentyFourHoursService kdTwentyFourHoursService;

    @Autowired
    private KdProductSaleDao kdProductSaleDao;

    /**
     * Description :24小时活动列表首页
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("/wrkd/bargain/bargain_list");
        mav.addObject("status", KdBargainStatusEnum.values());
        return mav;
    }

    /**
     * Description :24小时活动首页数据
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public KdBargainPageDto list(@RequestBody KdBargainPageDto dto) {
        kdTwentyFourHoursService.search(dto);
        return dto;
    }

    /**
     * 活动逻辑删除
     * 
     * @param String
     *            ids
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> delete(@RequestParam("ids") String ids) {
        return kdTwentyFourHoursService.delete(ids);
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
        ModelAndView mav = new ModelAndView("/wrkd/bargain/bargain_save");
        return mav;
    }

    /**
     * 
     * Description: 24th活动保存
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> add(@RequestBody KdBargainSaveAndEditDto dto) {
        try {
            kdTwentyFourHoursService.save(dto);
        } catch (WRWException w) {
            logger.info("24th活动保存添加失败:", w);
            return ResultDtoFactory.toNack(w.getMessage());
        } catch (Exception e) {
            logger.error("24th活动保存添加失败:", e);
            return ResultDtoFactory.toNack("保存失败!");
        }
        return ResultDtoFactory.toAck("保存成功!");
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
        ModelAndView mav = new ModelAndView("/wrkd/bargain/bargain_save");
        KdBargainSaveAndEditDto bargainDto = kdTwentyFourHoursService.findById(id);
        mav.addObject("bargainDto", bargainDto);
        KdProductSaleEntity saleEntity = kdProductSaleDao.findByProductId(bargainDto.getProductId());
        mav.addObject("saleEntity", saleEntity);
        mav.addObject("isReview", ISREVIEW);
        return mav;
    }

    /**
     * 
     * Description: 通过商品id查询出活动商品已选的规格信息
     *
     * @param productId
     * @return
     * @author chengchaoyin
     */
    @RequestMapping(value = "/findSpecInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<KdBargainAllSpecInfo> findSpecInfo(@RequestParam(value = "tfId") Long tfId,
            @RequestParam(value = "isReview") String isReview) {
        KdBargainAllSpecInfo kdBargainAllSpecInfo = kdTwentyFourHoursService.findSpecInfo(tfId);
        if (kdBargainAllSpecInfo != null) {
            kdBargainAllSpecInfo.setIsReview(isReview);
            return ResultDtoFactory.toAck("查询成功！", kdBargainAllSpecInfo);
        } else {
            return new ResultDto<KdBargainAllSpecInfo>();
        }
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
        ModelAndView mav = new ModelAndView("/wrkd/bargain/bargain_save");
        KdBargainSaveAndEditDto bargainDto = kdTwentyFourHoursService.findById(id);
        mav.addObject("bargainDto", bargainDto);
        KdProductSaleEntity saleEntity = kdProductSaleDao.findByProductId(bargainDto.getProductId());
        mav.addObject("saleEntity", saleEntity);
        mav.addObject("isEdit", "1");
        return mav;
    }

    /**
     * 
     * Description: 预览
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/toPreview/{id}", method = RequestMethod.GET)
    public String toPreview(@PathVariable Long id, Model model) {
        model.addAttribute("twentyFourId", id);
        return "wrkd/bargain/bargain_preview";
    }

    /**
     * Description: 获取活动信息
     *
     * @param actId
     * @return
     */
    @RequestMapping(value = "/preview", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<TwentyfourInfo> getInfo(@RequestParam(required = true) Long actId, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            TwentyfourInfo info = kdTwentyFourHoursService.getTwentyfourInfo(actId, request, response);
            return ResultDtoFactory.toAck("SUCCESS", info);
        } catch (TwentyfourException e) {
            logger.error(e.getMessage());
            return ResultDtoFactory.toNack(e.getMessage(), null);
        }
    }

    /**
     * 
     * Description: 通过商品id以及上下架时间查询是否有活动
     *
     * @param productId
     * @return
     * @author chengchaoyin
     */
    @RequestMapping(value = "/getTfByProductIdAndTime", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Integer> getTfByProductIdAndTime(@RequestBody KdBargainPageDto dto) {
        Integer count = kdTwentyFourHoursService.getTfByProductIdAndTime(dto.getProductId(), dto.getOnTime(),
                dto.getOffTime(),dto.getId());
        return ResultDtoFactory.toAck("SUCCESS", count == null ? 0 : count);
    }
    
    @RequestMapping(value = "/findSaleStatusByProductId", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> findSaleStatusByProductId(@RequestParam(value = "productId") Long productId) {
        KdProductSaleEntity saleEntity = kdProductSaleDao.findByProductId(productId);
        if(saleEntity != null){
            return ResultDtoFactory.toAck("查询成功！", saleEntity.getSaleStatus());
        }
        return ResultDtoFactory.toNack("查询无数据！");
    }

}
