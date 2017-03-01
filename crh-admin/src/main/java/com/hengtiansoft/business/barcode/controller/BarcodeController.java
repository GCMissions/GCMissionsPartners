package com.hengtiansoft.business.barcode.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hengtiansoft.business.barcode.dto.BarcodeCheckDto;
import com.hengtiansoft.business.barcode.dto.BarcodeCodeDto;
import com.hengtiansoft.business.barcode.dto.BarcodeDto;
import com.hengtiansoft.business.barcode.dto.BarcodePageDto;
import com.hengtiansoft.business.barcode.dto.BarcodeStorageDto;
import com.hengtiansoft.business.barcode.service.BarcodeCheckService;
import com.hengtiansoft.business.barcode.service.BarcodeService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.wrw.enums.SBasicCodeCycleStatusEnum;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * Class Name: BarcodeController
 * Description: Z端 扫描条码
 * 
 * @author zhisongliu
 */
@Api(value = "BarcodeController", description = "Z端 扫描条码")
@Controller
@RequestMapping(value = "/barcode")
public class BarcodeController {

    @Autowired
    private BarcodeService barcodeService;
    
    @Autowired
    private BarcodeCheckService checkService;

    /**
     * Description: (单条条码验证)
     *
     * @return
     */
    @ApiOperation(value = "验证URL（出库、入库）", httpMethod = "POST")
    @RequestMapping(value = "/checkUrl", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<BarcodeCheckDto> barcodeCheckUrl(@RequestBody BarcodeDto dto) {
        return checkService.checkUrl(dto);
    }

    /**
     * Description: 扫码页面（出库、入库）
     *
     * @return
     */
    @ApiOperation(value = "扫码页面（出库、入库）", httpMethod = "GET")
    @RequestMapping(value = "/index/{status}/{orderId}", method = RequestMethod.GET)
    public String barcodeIndex(Model model, @PathVariable("orderId") String orderId, @PathVariable("status") String status, HttpServletRequest request) {
        String url = request.getHeader("Referer");
        model.addAttribute("url", url);
        model.addAttribute("orderDetail", barcodeService.findByOrderId(orderId, status));
        model.addAttribute("orderId", orderId);
        model.addAttribute("status", status);
        if (SBasicCodeCycleStatusEnum.C_Z_STOTAGE.getKey().equals(status) || SBasicCodeCycleStatusEnum.C_SALE_STOTAGE.getKey().equals(status)) {
            return "orderReturn/returnIndex";
        } else {
            return "barcode/shipmentIndex";
        }

    }
    
    /**
     * Description: MPOS扫码页面（出库、入库）
     *
     * @return
     */
    @ApiOperation(value = "MPOS扫码页面（出库、入库）", httpMethod = "GET")
    @RequestMapping(value = "/mPosIndex/{status}/{orderId}", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<BarcodePageDto> mPosIndex(@PathVariable("orderId") String orderId, @PathVariable("status") String status) {
         return barcodeService.findByOrderId(orderId, status);
       

    }

    /**
     * Description: 1入库 到 -- z-c出库
     *
     * @return
     */
    @ApiOperation(value = "确认（出库、入库）", httpMethod = "POST")
    @RequestMapping(value = "/shipment", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> barcodeShipment(@RequestBody BarcodeStorageDto sdto) {
        return barcodeService.storageCode(sdto);
    }

    /**
     * Description: 平台扫码入库
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/adminIndex")
    public String barcodeAdminIndex(Model model) {
        model.addAttribute("status", SBasicCodeCycleStatusEnum.ADMIN_STORAGE.getKey());
        return "barcode/adminIndex";
    }

    /**
     * Description: 查询二维码明码是否为母码
     *
     * @param prefixCode
     * @return
     */
    @RequestMapping(value = "/checkCode")
    @ResponseBody
    public ResultDto<Integer> barcodeCheckCode(@RequestBody BarcodeCodeDto dto) {
        return barcodeService.checkCode(dto.getPrefixCode());
    }

}
