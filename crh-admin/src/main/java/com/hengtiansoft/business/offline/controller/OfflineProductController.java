/*
 * Project Name: wrw-admin
 * File Name: OfflinePurchaseController.java
 * Class Name: OfflinePurchaseController
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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.offline.dto.OfflineQueryProductReqDto;
import com.hengtiansoft.business.offline.dto.OfflineQueryProductRespDto;
import com.hengtiansoft.business.offline.service.OfflineProductService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * Class Name: OfflinePurchaseController
 * Description: TODO
 * @author xiaoluzhou
 *
 */
@Api(value = "OfflineProductController", description = "用户线下购买商品")
@Controller
@RequestMapping("/offline/product")
public class OfflineProductController {

    @Autowired
    private OfflineProductService productService;
    
    @ApiOperation(value = "页面跳转", httpMethod = "GET")
    @RequestMapping(value="/", method=RequestMethod.GET)
    public ModelAndView index(){
        return new ModelAndView("offline/index");
    }
    
    @ApiOperation(value = "根据二维码查询商品", httpMethod="POST")
    @RequestMapping(value = "/query", method=RequestMethod.POST)
    @ResponseBody
    public ResultDto<List<OfflineQueryProductRespDto>> queryProduct(@RequestBody OfflineQueryProductReqDto reqDto){
        return ResultDtoFactory.toAck(null, productService.queryProduct(reqDto));
    }
    
}
