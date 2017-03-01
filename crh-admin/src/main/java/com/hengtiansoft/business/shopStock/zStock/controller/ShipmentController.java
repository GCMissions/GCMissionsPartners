package com.hengtiansoft.business.shopStock.zStock.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.shopStock.zStock.dto.ShipmentDto;
import com.hengtiansoft.business.shopStock.zStock.dto.ShipmentSearchDto;
import com.hengtiansoft.business.shopStock.zStock.service.ShipmentService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.poi.PoiUtil;
import com.hengtiansoft.common.poi.bean.SimpleExcelBean;

@Controller
@RequestMapping("shipment")
public class ShipmentController {
    private static final Logger log= LoggerFactory.getLogger(ShipmentController.class);
    @Autowired
    ShipmentService shipmentService;

    @RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
    public ModelAndView list(Model model) throws Exception {
        Integer noShipmentCount = shipmentService.getNoShipmentCount();
        model.addAttribute("noShipmentCount", noShipmentCount);
        return new ModelAndView("distributionShipment/shipment_list");
    }
    /**
     * 列表
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public ShipmentSearchDto search(@RequestBody ShipmentSearchDto infoDto) {
        shipmentService.searchShipment(infoDto);
        return infoDto;
    }

    /**
     * 详情
     * 
     * @param id
     */
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(ModelMap model, @PathVariable(value = "id") String id) {
        ShipmentDto shipmentDto = shipmentService.findShipment(id);
        model.addAttribute("shipmentDto", shipmentDto);
        return "distributionShipment/shipment_detail";
    }
    
    /**
     * 
    * Description: MPOS收货管理的收货详情接口
    *
    * @param id
    * @return
     */
    @RequestMapping(value = "/detail/mpos/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<ShipmentDto> detailForMPO(@PathVariable(value = "id") String id) {
        return ResultDtoFactory.toAck(null, shipmentService.findShipment(id));
    }

    /**
     * @Description: 确认收货
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/confirmGetGoods/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<String> getGoods(@PathVariable(value = "id") String id) {
        shipmentService.confirmGetGoods(id);
        return ResultDtoFactory.toAck("收货成功!");
    }
    
    /**
     * 
    * Description: 导出收货表
    *
    * @param request
    * @param response
    * @param orderId
     */
    
    @RequestMapping(value ="/toExcelShipment",method=RequestMethod.GET)
    public void ExcelShipment(HttpServletRequest request, HttpServletResponse response,String orderId){
        List<List<String>> content =shipmentService.findAllOrder(orderId);    
        SimpleExcelBean bean = new SimpleExcelBean();
        bean.setTitle("Z入库详情表");
        String[] colNames ={"序号","时间","商品名称","规格","产品编号","数量"};
        bean.setColNames(colNames);
        bean.setContent(content);
        try {
            PoiUtil.toExcel(bean, response, request);
        } catch (IOException e) {
            log.error("msg",e);
        }
        
    }

}
