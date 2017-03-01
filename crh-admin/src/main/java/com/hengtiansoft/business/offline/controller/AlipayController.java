package com.hengtiansoft.business.offline.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.response.AlipayTradeQueryResponse;
import com.hengtiansoft.business.offline.dto.AliTradeQueryReqDto;
import com.hengtiansoft.business.offline.dto.AlipayReqDto;
import com.hengtiansoft.business.offline.dto.AlipayRespDto;
import com.hengtiansoft.business.offline.service.AlipayService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * Class Name: MposAlipayController
 * Description: 
 * @author xiaoluzhou
 *
 */
@Api(value = "Alipay", description = "阿里扫码支付")
@Controller
@RequestMapping("/alipay")
public class AlipayController {
    @Autowired
    private AlipayService mposAlipayService;
    
    @ApiOperation(value = "阿里扫码支付", httpMethod = "POST")
    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<AlipayRespDto> aliTradePay(@RequestBody AlipayReqDto reqDto){
        return ResultDtoFactory.toAck(null, mposAlipayService.alipay(reqDto));
    }
    
    @ApiOperation(value = "阿里扫描支付结果查询", httpMethod = "POST")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<AlipayTradeQueryResponse> aliTradeQuery(@RequestBody AliTradeQueryReqDto reqDto){
        return ResultDtoFactory.toAck(null, mposAlipayService.tradeQuery(reqDto));
    }
}
