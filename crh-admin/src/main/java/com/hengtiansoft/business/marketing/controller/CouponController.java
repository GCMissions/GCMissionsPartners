package com.hengtiansoft.business.marketing.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.marketing.dto.CouponDto;
import com.hengtiansoft.business.marketing.dto.CouponPageDto;
import com.hengtiansoft.business.marketing.dto.CouponRecordPageDto;
import com.hengtiansoft.business.marketing.dto.SaveCouponDto;
import com.hengtiansoft.business.marketing.service.CouponService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.wordnik.swagger.annotations.Api;

/**
 * 
* Class Name: CouponController
* Description: 平台优惠券管理（优惠券模板）
* @author chengchaoyin
*
 */
@Api(value = "CouponController", description = "平台优惠券管理")
@Controller
@RequestMapping("/coupon")
public class CouponController {
    
    private static final Logger logger = LoggerFactory.getLogger(CouponController.class);

    @Autowired
    private CouponService couponService;

    @Autowired
    /**
     * Description: 默认页
     *
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView list() {
        return new ModelAndView("coupon/coupon_list");
    }

    /**
     * Description: 查询列表
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public CouponPageDto couponList(@RequestBody CouponPageDto dto) {
        couponService.getCouponList(dto);
        return dto;
    }

    /**
     * Description: 添加页面
     *
     * @return
     */
    @RequestMapping(value = "/toAddCoupon", method = RequestMethod.GET)
    public ModelAndView add() throws Exception {
        return new ModelAndView("coupon/coupon_add");
    }

    /**
     * Description: 查看详情
     *
     * @return
     */
    @RequestMapping(value = "/detail/{couponId}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView detail(@PathVariable(value = "couponId") Long couponId) {
        ModelAndView mav = new ModelAndView("coupon/coupon_detail");
        mav.addObject("coupon", couponService.getCoupon(couponId));
        return mav;
    }

    /**
     * Description: 保存优惠券
     *
     * @param couponDto
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/saveCoupon", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> saveCoupon(@RequestBody SaveCouponDto couponDto) {
        return couponService.saveCoupon(couponDto);
    }

    /**
     * Description: 删除
     *
     * @return
     */
    @RequestMapping(value = "/delete/{couponId}", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<String> delete(@PathVariable(value = "couponId") Long couponId) {
        return couponService.delete(couponId);
    }

    /**
     * Description: 优惠券领取记录页面
     *
     * @param couponId
     * @return
     */
    @RequestMapping(value = "/toRecordList", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView toRecordList() {
        return new ModelAndView("coupon/recordList");
    }

    /**
     * Description: 优惠券领取记录
     *
     * @param couponId
     * @return
     */
    @RequestMapping(value = "/recordList", method = RequestMethod.POST)
    @ResponseBody
    public CouponRecordPageDto recordList(@RequestBody CouponRecordPageDto couponDto) {
        couponService.recordList(couponDto);
        return couponDto;
    }

    /**
     * Description: 查询合作券的CODE
     *
     * @param couponId
     * @return
     */
    @RequestMapping(value = "/couponCodeList/{couponId}", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<List<String>> couponCodeList(@PathVariable(value = "couponId") Long couponId) {
        return couponService.couponCodeList(couponId);
    }
    
    /**
     * 
    * Description: 获取优惠券详情
    *
    * @return
    * @author chengchaoyin
     */
    @RequestMapping(value = "/getCoupon", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<CouponDto> getCoupon(@RequestBody Long couponId) {
        if(couponId != null){
            return ResultDtoFactory.toAck("success!", couponService.getCoupon(couponId));
        }
        return null;
    }
    
    @RequestMapping(value = "/sendCoupon", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Boolean> sendCoupon(@RequestBody SaveCouponDto couponDto) {
        return couponService.sendCoupon(couponDto);
    }
    
    @RequestMapping(value = "/updtaeCoupon", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Boolean> updtaeCoupon(@RequestBody SaveCouponDto couponDto) {
        return couponService.updateCouponStatus(couponDto);
    }

    /**
     * 
    * Description: 解析导入的excel，excel表本身不存入服务器
    *              为避免后期需求可能需要导入的数据进行一次用户确认再导入，所以预留的接口将excel解析的list也传给前台
    * @param request
    * @param response
    * @param multipartFile
    * @return
    * @author chengchaoyin
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Map<String,Object>> importExcel(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value = "postExcel") MultipartFile multipartFile) {

        String fileName = multipartFile.getName();
        // 原始文件名
        String sourceName = multipartFile.getOriginalFilename(); 
        String fileType = sourceName.substring(sourceName.lastIndexOf("."));

        if (null == fileName || "".equals(fileName)) {
            return ResultDtoFactory.toNack("请传入文件名！");
        }

        if (!".xlsx".equals(fileType) && !".xls".equals(fileType)) {
            return ResultDtoFactory.toNack("文件格式错误！");
        }

        InputStream inputStream = null;
        try {
            inputStream = multipartFile.getInputStream();
        } catch (IOException e) {
            return ResultDtoFactory.toNack(e.toString());
        }
        SaveCouponDto couponDto = new SaveCouponDto();
        couponDto.setUserIds(request.getParameter("userIds"));
        couponDto.setSendType("1");
        couponDto.setCouponIds(request.getParameter("couponIds"));
        return couponService.importExcel(inputStream,couponDto);
    }
    
    /**
     * 
    * Description: 导入模板
    *
    * @param request
    * @param response
    * @author chengchaoyin
     */
    @RequestMapping(value = "toTmlExcel", method = RequestMethod.GET)
    public void toTemplateExcel(HttpServletRequest request, HttpServletResponse response) {
        HSSFWorkbook wb = couponService.toExcel();
        String excelName = "批量用户导入模板.xls";
        try {
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-disposition", "attachment;filename="
                    + new String(excelName.getBytes("GB2312"), "ISO8859-1"));
            ServletOutputStream outputStream = response.getOutputStream();
            wb.write(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            logger.error("error", e);
        }
    }

}
