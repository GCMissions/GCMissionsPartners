/*
 * Project Name: wrw-admin
 * File Name: OrderReturnController.java
 * Class Name: OrderReturnController
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
package com.hengtiansoft.business.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.order.dto.OrderReturnDto;
import com.hengtiansoft.business.order.servicer.OrderReturnService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;

/**
 * 
* Class Name: OrderReturnController
* Description: 订单退款
* @author chengchaoyin
*
 */
@Controller
@RequestMapping(value="/orderReturn")
public class OrderReturnController {
    
    @Autowired
    private OrderReturnService orderReturnService;
    
    @RequestMapping(value="/{orderId}/{type}", method = RequestMethod.GET)
    public ModelAndView index(@PathVariable("orderId") String orderId,@PathVariable("type") String type){
        ModelAndView mv=new ModelAndView("orderManager/order_view");
        OrderReturnDto dto = orderReturnService.getOrderDetail(orderId);
        dto.setReturnType(type);
        mv.addObject("order", dto);
        mv.addObject("remarks", orderReturnService.getRemarks(orderId));
        return mv;
    }
    

    @RequestMapping(value = "/changeActualAmount", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<?> changeActualAmount(@RequestBody OrderReturnDto dto) {
        return ResultDtoFactory.toAck(orderReturnService.changeActualAmount(dto));
    }

    @RequestMapping(value = "/changeReturnOrderAmount", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<?> save(@RequestBody OrderReturnDto dto) {
        return ResultDtoFactory.toAck(orderReturnService.changeReturnOrderAmount(dto));
    }

    @RequestMapping(value = "/cancelOrder", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<?> cancelOrder(@RequestBody OrderReturnDto dto) {
        return ResultDtoFactory.toAck(orderReturnService.cancelOrder(dto));
    }

    @RequestMapping(value = "/addRemark", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<?> addRemark(@RequestBody OrderReturnDto dto) {
        return ResultDtoFactory.toAck(orderReturnService.addRemark(dto));
    }
    
}
