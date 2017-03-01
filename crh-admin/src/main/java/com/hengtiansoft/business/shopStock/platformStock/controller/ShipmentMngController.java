/*
 * Project Name: wrw-admin
 * File Name: ShipmentMngController.java
 * Class Name: ShipmentMngController
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
package com.hengtiansoft.business.shopStock.platformStock.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.provider.dto.SearchOrgDto;
import com.hengtiansoft.business.provider.service.OrgService;
import com.hengtiansoft.business.shopStock.platformStock.dto.GoodsStockSearchDto;
import com.hengtiansoft.business.shopStock.platformStock.dto.OrgStockDetailDto;
import com.hengtiansoft.business.shopStock.platformStock.dto.OrgStockSearchDto;
import com.hengtiansoft.business.shopStock.platformStock.dto.ShipmentDetailDto;
import com.hengtiansoft.business.shopStock.platformStock.dto.ShipmentSaveDto;
import com.hengtiansoft.business.shopStock.platformStock.dto.ShipmentSearchDto;
import com.hengtiansoft.business.shopStock.platformStock.service.OrgStockService;
import com.hengtiansoft.business.shopStock.platformStock.service.PlatformShipmentService;
import com.hengtiansoft.business.shopStock.platformStock.service.ProductStockService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.poi.PoiUtil;
import com.hengtiansoft.common.poi.bean.SimpleExcelBean;
import com.hengtiansoft.wrw.enums.OrgTypeEnum;
import com.hengtiansoft.wrw.enums.StatusEnum;
import com.hengtiansoft.wrw.enums.StockState;
import com.wordnik.swagger.annotations.Api;

/**
 * Class Name: ShipmentMngController Description: 发货管理
 * 
 * @author xianghuang
 *
 */
@Api(value = "ShipmentMngController")
@Controller
@RequestMapping("platformShipment")
public class ShipmentMngController {
    private static final Logger log= LoggerFactory.getLogger(ShipmentMngController.class);
    @Autowired
    private OrgService orgServicers;

    @Autowired
    private PlatformShipmentService shipmentService;

    @Autowired
    private ProductStockService productStockService;

    @Autowired
    private OrgStockService orgStockService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("plateformStock/shipment_list");
        Integer warnNum = orgStockService.getOrgNum(StockState.warning);
        mav.addObject("warnNum", warnNum);
        return mav;
    }

    @RequestMapping(value = "/creatShipment", method = RequestMethod.GET)
    public ModelAndView creatShipment() {
        return new ModelAndView("plateformStock/creatShipment");
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public ShipmentSearchDto list(@RequestBody ShipmentSearchDto searchDto) {
        return shipmentService.search(searchDto);
    }

    @RequestMapping(value = { "/detail/{orderId}" }, method = RequestMethod.GET)
    public ModelAndView searchOne(@PathVariable(value = "orderId") String orderId) {
        ShipmentDetailDto dto = shipmentService.get(orderId);
        ModelAndView mav = new ModelAndView("plateformStock/shipment_detail");
        mav.addObject("shipmentDetailDto", dto);
        return mav;
    }

    /**
     * Description: 选择商品
     *
     * @param searchDto
     * @return
     */
    @RequestMapping(value = "/select", method = RequestMethod.POST)
    @ResponseBody
    public GoodsStockSearchDto select(@RequestBody GoodsStockSearchDto searchDto) {
        return productStockService.searchProductStock(searchDto);
    }

    /**
     * Description: 匹配商家
     *
     * @param partName
     * @return
     */
    @RequestMapping(value = "/matchingOrg", method = RequestMethod.POST)
    @ResponseBody
    public SearchOrgDto matchingOrg(@RequestParam String keyword, @RequestParam String type) {
        SearchOrgDto dto = new SearchOrgDto();
        dto.setOrgName(keyword);
        dto.setPageSize(100);
        dto.setStatus(StatusEnum.NORMAL.getCode());
        orgServicers.findOrg(dto, type);
        return dto;
    }

    /**
     * Description: 创建发货单
     *
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> save(@RequestBody ShipmentSaveDto dto) {
        return shipmentService.save(dto);
    }

    /**
     * Description: 查看预警状态商家
     *
     * @return
     */
    @RequestMapping(value = "/list/stockWarn", method = RequestMethod.POST)
    @ResponseBody
    public OrgStockSearchDto stockWarn(OrgStockSearchDto dto) {
        orgStockService.searchOrg(dto, StockState.warning);
        return dto;
    }

    /**
     * Description: 查看商家下预警状态商品
     *
     * @param orgId
     * @return
     */
    @RequestMapping(value = { "/list/productWarn/{orgId}" }, method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<OrgStockDetailDto> warn(@PathVariable(value = "orgId") Long orgId) {
        return ResultDtoFactory.toAck("ok", orgStockService.getWarnList(orgId));
    }

    /**
     * Description: 发货改变发货单状态
     *
     * @return
     */
    @RequestMapping(value = "/deliverGoods/{orderId}", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> changeState(@PathVariable(value = "orderId") String orderId) {
        return shipmentService.deliverGoods(orderId);
    }

    /**
     * 
     * Description: 导出发货单
     *
     * @param request
     * @param response
     * @param orderId
     */

    @RequestMapping(value = "/toExcelShipment", method = RequestMethod.GET)
    public void ExcelShipment(HttpServletRequest request, HttpServletResponse response, String orderId) {
        List<List<String>> content = shipmentService.findAllOrder(orderId);
        SimpleExcelBean bean = new SimpleExcelBean();
        bean.setTitle("“1”给P出库详情表");
        String[] colNames = { "序号", "时间", "区域代理商名称", "商品名称", "规格", "产品编号", "数量", "应配数量", "实配数量", "出库人", "核查人" };
        bean.setColNames(colNames);
        bean.setContent(content);
        try {
            PoiUtil.toExcel(bean, response, request);
        } catch (IOException e) {
            log.error("msg",e);
        }

    }

    /**
     * 
     * Description: 导出库存预警单
     *
     * @param request
     * @param response
     * @param orderId
     */

    @RequestMapping(value = "/toExcel", method = RequestMethod.GET)
    public void toExcel(HttpServletRequest request, HttpServletResponse response, String orgType) {
        List<List<String>> content = shipmentService.toExcel(orgType);
        SimpleExcelBean bean = new SimpleExcelBean();
        if (orgType.equals(OrgTypeEnum.P.getKey())) {
            bean.setTitle("P安全库存预警表");
            String[] colNames = { "序号", "时间", "区域代理商名称", "单瓶商品名称", "规格", "当前库存数量", "补货数量" };
            bean.setColNames(colNames);
        } else {
            bean.setTitle("Z安全库存预警表");
            String[] colNames = { "序号", "时间", "区域代理商名称", "终端配送商名称", "单瓶商品名称", "规格", "当前库存数量", "补货数量" };
            bean.setColNames(colNames);
        }
        bean.setContent(content);
        try {
            PoiUtil.toExcel(bean, response, request);
        } catch (IOException e) {
            log.error("msg",e);
        }
    }
}
