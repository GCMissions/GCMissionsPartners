package com.hengtiansoft.business.order.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.order.dto.DistributionOrdeDto;
import com.hengtiansoft.business.order.dto.DistributionOrdeSearchDto;
import com.hengtiansoft.business.order.servicer.DistributionOrderService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.poi.PoiUtil;
import com.hengtiansoft.common.poi.bean.SimpleExcelBean;
import com.hengtiansoft.wrw.enums.DistributionOrderStatus;

@Controller
@RequestMapping("distributionOrder")
public class DistributionOrderController {

    private Logger           log = LoggerFactory.getLogger(DistributionOrderController.class);

    @Autowired
    DistributionOrderService distributionOrderService;

    @RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView list(ModelMap model) throws Exception {
        Integer newOrder = distributionOrderService.getNewOrder();
        model.addAttribute("newOrder", newOrder);
        model.addAttribute("orderStatus", DistributionOrderStatus.values());
        return new ModelAndView("distributionOrder/distributionOrder_list");
    }

    @RequestMapping(value = { "/", "/listNewOrder" }, method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView listNewOrder(ModelMap model) throws Exception {
        Integer newOrder = distributionOrderService.getNewOrder();
        model.addAttribute("newOrder", newOrder);
        model.addAttribute("newOrderFlag", "T");
        model.addAttribute("orderStatus", DistributionOrderStatus.values());
        return new ModelAndView("distributionOrder/distributionOrder_list");
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public DistributionOrdeSearchDto search(@RequestBody DistributionOrdeSearchDto infoDto) {
        distributionOrderService.searchDistributionOrde(infoDto);
        return infoDto;
    }

    /**
     * 详情
     * 
     * @param id
     */
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(ModelMap model, @PathVariable(value = "id") String id) {
        DistributionOrdeDto distributionOrderDto = distributionOrderService.findDistributionOrder(id);
        model.addAttribute("distributionOrderDto", distributionOrderDto);
        return "distributionOrder/distributionOrder_detail";
    }

    /**
     * MPOS详情
     * 
     * @param id
     */
    @RequestMapping(value = "/detail/mpos/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<DistributionOrdeDto> detailForMpos(ModelMap model, @PathVariable(value = "id") String id) {
        return ResultDtoFactory.toAck(null, distributionOrderService.findDistributionOrder(id));
    }

    /**
     * @Description: 接单
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/getGoods/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<String> getGoods(@PathVariable(value = "id") String id) {
        distributionOrderService.getGoods(id);
        return ResultDtoFactory.toAck("接单成功!");
    }

    /**
     * @Description: 发货
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/sendGoods/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<String> sendGoods(@PathVariable(value = "id") String id) {
        distributionOrderService.sendGoods(id);
        return ResultDtoFactory.toAck("发货成功!");
    }

    // 新订单提醒
    @RequestMapping(value = "/getNewOrderRemind")
    @ResponseBody
    public ResultDto<?> getNewOrderRemind() {
        try {
            Long num = distributionOrderService.getNewOrderRemind();
            if (num == null) {
                num = 0L;
            }
            return ResultDtoFactory.toAck("", num);
        } catch (Exception e) {
            log.error("error", e);
            return ResultDtoFactory.toAck("", 0);
        }
    }

    /**
     * Description: 导出发货单
     *
     * @param request
     * @param response
     * @param orderId
     */

    @RequestMapping(value = "/toExcelOrder", method = RequestMethod.GET)
    public void ExcelShipment(HttpServletRequest request, HttpServletResponse response, String orderId) {
        List<List<String>> content = distributionOrderService.findAllOrder(orderId);
        SimpleExcelBean bean = new SimpleExcelBean();
        bean.setTitle("Z出货详情表");
        String[] colNames = { "时间", "订单号", "消费者姓名", "联系方式", "收货地址", "商品名称", "产品编号", "规格", "数量" };
        bean.setColNames(colNames);
        bean.setContent(content);
        try {
            PoiUtil.toExcel(bean, response, request);
        } catch (IOException e) {
            log.error("msg", e);
        }

    }

}
