/*
 * Project Name: wrw-admin
 * File Name: MposWechatMicropayController.java
 * Class Name: MposWechatMicropayController
 *
 * Copyright 2014 Hengtian Software Inc
 *
 * Licensed under the Hengtiansoft
 *
 * http://www.hengtiansoft.com
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hengtiansoft.business.offline.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hengtiansoft.business.offline.dto.WechatMicropayReqDto;
import com.hengtiansoft.business.offline.dto.WechatMicropayRespDto;
import com.hengtiansoft.business.offline.dto.WechatOrderQueryReqDto;
import com.hengtiansoft.business.offline.service.WechatMicropayService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.tencent.protocol.pay_query_protocol.ScanPayQueryResData;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * Class Name: MposWechatMicropayController
 * Description: TODO
 * @author xiaoluzhou
 *
 */
@Api(value = "WechatMicropay", description = "微信刷卡支付")
@Controller
@RequestMapping("/wechat")
public class WechatMicropayController {
    
    @Autowired
    private WechatMicropayService wechatMicropayService;
    
    @ApiOperation(value = "微信刷卡支付", httpMethod = "POST")
    @RequestMapping(value = "/micropay", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<WechatMicropayRespDto> wechatMicropay(@RequestBody WechatMicropayReqDto reqDto){
        return ResultDtoFactory.toAck(null, wechatMicropayService.wechatMicropay(reqDto));
    }
    
    @ApiOperation(value = "微信支付查询", httpMethod = "POST")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<ScanPayQueryResData> wechatMicropayQuery(@RequestBody WechatOrderQueryReqDto reqDto){
        return ResultDtoFactory.toAck(null, wechatMicropayService.scanPayQuery(reqDto));
    }
}
