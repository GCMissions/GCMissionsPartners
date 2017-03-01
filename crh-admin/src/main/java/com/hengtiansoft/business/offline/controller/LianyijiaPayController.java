/*
 * Project Name: wrw-admin
 * File Name: MposLianyijiaController.java
 * Class Name: MposLianyijiaController
 * Copyright 2014 Hengtian Software Inc
 * Licensed under the Hengtiansoft
 * http://www.hengtiansoft.com
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hengtiansoft.business.offline.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hengtiansoft.business.offline.dto.LianyijiaBalanceQueryReqDto;
import com.hengtiansoft.business.offline.dto.LianyijiaBalanceQueryRespData;
import com.hengtiansoft.business.offline.dto.LianyijiaCancelReqDto;
import com.hengtiansoft.business.offline.dto.LianyijiaCancelRespData;
import com.hengtiansoft.business.offline.dto.LianyijiaConsumeReqDto;
import com.hengtiansoft.business.offline.dto.LianyijiaConsumeRespData;
import com.hengtiansoft.business.offline.dto.LianyijiaReverseReqDto;
import com.hengtiansoft.business.offline.dto.LianyijiaReverseRespData;
import com.hengtiansoft.business.offline.service.LianyijiaPayService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * Class Name: MposLianyijiaController
 * Description: 连亿家支付相关
 * 
 * @author xiaoluzhou
 */
@Api(value = "Lianyijia", description = "连亿家支付")
@RequestMapping("/lianyijia")
@Controller
public class LianyijiaPayController {

    @Autowired
    private LianyijiaPayService payService;

    @ApiOperation(value = "消费接口", httpMethod = "POST")
    @RequestMapping(value = "/consume", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<LianyijiaConsumeRespData> consume(@RequestBody LianyijiaConsumeReqDto reqDto) {
        return ResultDtoFactory.toAck(null, payService.consume(reqDto));
    }

    @ApiOperation(value = "撤销接口", httpMethod = "POST")
    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<LianyijiaCancelRespData> cancel(@RequestBody LianyijiaCancelReqDto reqDto) {
        reqDto.setPaysEnum("OUTER_PAY");
        return ResultDtoFactory.toAck(null, payService.cancel(reqDto));
    }

    @ApiOperation(value = "冲正接口", httpMethod = "POST")
    @RequestMapping(value = "/reverse", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<LianyijiaReverseRespData> reverse(@RequestBody LianyijiaReverseReqDto reqDto) {
        reqDto.setOrderNo(reqDto.getOrigOrderNo() + new Random().nextInt(10) + "rm");
        return ResultDtoFactory.toAck(null, payService.reverse(reqDto));
    }

    @ApiOperation(value = "余额查询接口", httpMethod = "POST")
    @RequestMapping(value = "/query/balance", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<LianyijiaBalanceQueryRespData> queryBalance(@RequestBody LianyijiaBalanceQueryReqDto reqDto) {
        return ResultDtoFactory.toAck(null, payService.queryBalance(reqDto));
    }
}
