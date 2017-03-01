/*
 * Project Name: wrw-admin
 * File Name: MposOrderController.java
 * Class Name: MposOrderController
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hengtiansoft.business.offline.dto.OfflineOrderSubmitReqDto;
import com.hengtiansoft.business.offline.dto.OfflineOrderSubmitRespDto;
import com.hengtiansoft.business.offline.service.OfflineOrderService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * Class Name: MposOrderController
 * Description:
 * 
 * @author xiaoluzhou
 */
@Api(value = "OfflineOrder", description = "线下订单相关接口")
@Controller
@RequestMapping("/offline/order")
public class OfflineOrderController {

    @Autowired
    private OfflineOrderService orderService;

    @ApiOperation(value = "Mpos提交订单", httpMethod = "POST")
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<OfflineOrderSubmitRespDto> submitOrder(@RequestBody OfflineOrderSubmitReqDto submitDto) {
        return ResultDtoFactory.toAck(null, orderService.submitOrder(submitDto));
    }

}
