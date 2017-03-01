package com.hengtiansoft.business.marketing.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.marketing.dto.BargainChartPageDto;
import com.hengtiansoft.business.marketing.dto.BargainPageDto;
import com.hengtiansoft.business.marketing.dto.BargainSaveAndEditDto;
import com.hengtiansoft.business.marketing.service.BargainProductService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.exception.WRWException;
import com.hengtiansoft.wrw.enums.BargainStatusEnum;

/**
 * 
* Class Name: BargainProductController
* Description: 砍价
* @author chenghongtu
*
 */
@Controller
@RequestMapping("bargain")
public class BargainProductController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(BargainProductController.class);

    @Autowired
    private BargainProductService bargainProductService;

    private final String ISREVIEW = "1";
    /**
     * Description :商品列表首页
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("/bargain/bargain_list");
        mav.addObject("status", BargainStatusEnum.values());
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
    public BargainPageDto list(@RequestBody BargainPageDto dto) {
        bargainProductService.search(dto);
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
    public ModelAndView addPage(HttpServletRequest request,
            HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("bargain/bargain_save");
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
        ModelAndView mav = new ModelAndView("bargain/bargain_save");
        mav.addObject("bargainDto", bargainProductService.findById(id));
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
        ModelAndView mav = new ModelAndView("bargain/bargain_save");
        mav.addObject("bargainDto", bargainProductService.findById(id));
        mav.addObject("isReview", ISREVIEW);
        return mav;
    }
    
    /**
     * 
    * Description:进入到统计界面
    *
    * @param id
    * @param request
    * @param response
    * @return
     */
    @RequestMapping(value = "/chart/{id}", method = RequestMethod.GET)
    public ModelAndView chartPage(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("bargain/bargain_chart");
        mav.addObject("bargainDto", bargainProductService.findById(id));
        return mav;
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
    public ResultDto<String> add(@RequestBody BargainSaveAndEditDto dto) {
        try {
            bargainProductService.save(dto);
        } catch (WRWException w) {
            LOGGER.info("商品基本信息添加失败:",w);
            return ResultDtoFactory.toNack(w.getMessage());
        } catch (Exception e) {
            LOGGER.error("商品基本信息添加失败:",e);
            return ResultDtoFactory.toNack("保存失败!");
        }
        return ResultDtoFactory.toAck("保存成功!");
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
    public ResultDto<String> update(@RequestBody BargainSaveAndEditDto dto) {
        try {
            bargainProductService.update(dto);
        } catch (WRWException w) {
            LOGGER.info("更新商品:",w);
            return ResultDtoFactory.toNack(w.getMessage());
        } catch (Exception e) {
            LOGGER.error("更新商品:",e);
            return ResultDtoFactory.toNack("保存失败!");
        }
        return ResultDtoFactory.toAck("保存成功!");
    }
    /**
     * 商品删除
     * 
     * @param String ids
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<String> delete(@PathVariable("id")Long id){
        try {
            bargainProductService.delete(id);
        } catch (WRWException w) {
            LOGGER.info("删除商品失败:",w);
            return ResultDtoFactory.toNack(w.getMessage());
        }catch (Exception e) {
            LOGGER.error("删除商品失败:",e);
            return ResultDtoFactory.toNack("删除失败!");
        }
        return ResultDtoFactory.toAck("删除成功!");

    }
    /**
     * 
    * Description: 砍价活动详情
    *
    * @param dto
    * @return
     */
    @RequestMapping(value = "/chart", method = RequestMethod.POST)
    @ResponseBody
    public BargainChartPageDto chart(@RequestBody BargainChartPageDto dto) {
        bargainProductService.searchChart(dto);
        return dto;
    }
    /**
     * 
    * Description: 砍价活动详情信息导出
    *
    * @param request
    * @param response
    * @param pageDto
    * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void toExcle(HttpServletRequest request, HttpServletResponse response, BargainChartPageDto pageDto)
            throws UnsupportedEncodingException {
        String excelName = "活动【" + bargainProductService.findById(pageDto.getBargainId()).getProductName() + "】信息统计表.xls";
        HSSFWorkbook wb = bargainProductService.toExcle(pageDto);
        try {
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-disposition", "attachment;filename="
                    + new String(excelName.getBytes("GB2312"), "ISO8859-1"));
            ServletOutputStream outputStream = response.getOutputStream();
            wb.write(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            LOGGER.error("error", e);
        }

    }
}
