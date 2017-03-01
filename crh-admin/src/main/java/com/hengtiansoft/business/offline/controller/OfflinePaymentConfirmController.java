/*
 * Project Name: wrw-admin
 * File Name: OfflineOrderConfirmController.java
 * Class Name: OfflineOrderConfirmController
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

import com.hengtiansoft.business.offline.dto.OfflinePaymentConfirmReqDto;
import com.hengtiansoft.business.offline.dto.OfflinePaymentConfirmRespDto;
import com.hengtiansoft.business.offline.service.OfflinePaymentConfirmService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * Class Name: OfflineOrderConfirmController
 * Description: 
 * @author xiaoluzhou
 *
 */
@Api(value = "OfflineOrderConfirmController", description="支付结果未知，同步支付结果")
@Controller
@RequestMapping("/offline/orderconfirm")
public class OfflinePaymentConfirmController {

    @Autowired
    private OfflinePaymentConfirmService paymentConfirmService;
    
    
    @ApiOperation(value = "同步方法", httpMethod = "POST")
    @RequestMapping(value = "/sync", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<OfflinePaymentConfirmRespDto> syncPaymentResult(@RequestBody OfflinePaymentConfirmReqDto reqDto){
        return ResultDtoFactory.toAck(null, paymentConfirmService.confirm(reqDto));
    }
}
