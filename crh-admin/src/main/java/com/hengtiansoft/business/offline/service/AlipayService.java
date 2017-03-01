package com.hengtiansoft.business.offline.service;

import com.alipay.api.response.AlipayTradeQueryResponse;
import com.hengtiansoft.business.offline.dto.AliTradeQueryReqDto;
import com.hengtiansoft.business.offline.dto.AlipayReqDto;
import com.hengtiansoft.business.offline.dto.AlipayRespDto;

/**
 * Class Name: MposAlipayService
 * Description: Mpos支付宝支付service
 * 
 * @author yigesong
 */
public interface AlipayService {

    AlipayRespDto alipay(AlipayReqDto reqDto);

    AlipayTradeQueryResponse tradeQuery(AliTradeQueryReqDto reqDto);
}
