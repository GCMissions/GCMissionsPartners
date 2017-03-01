package com.hengtiansoft.business.activity.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.activity.dto.ValidateSearchDto;
import com.hengtiansoft.business.activity.service.ValidateService;
import com.hengtiansoft.business.order.dto.OrderDetailSearchDto;
import com.hengtiansoft.business.order.dto.OrderManagerExportSearchDto;
import com.hengtiansoft.business.order.dto.OrderReturnDto;
import com.hengtiansoft.business.order.servicer.OrderReturnService;
import com.hengtiansoft.business.product.dto.ProductCategoryDto;
import com.hengtiansoft.business.product.service.CategoryService;
import com.hengtiansoft.business.product.service.ProductService;
import com.hengtiansoft.business.provider.service.OrgService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.wrw.dao.PProductDao;
import com.hengtiansoft.wrw.entity.PProductEntity;
import com.hengtiansoft.wrw.enums.ProductTypeEnum;

@Controller
@RequestMapping(value = "/validate")
public class ProductValidateController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductValidateController.class);

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private OrgService orgService;
    @Autowired
    private ValidateService validateService;
    @Autowired
    private OrderReturnService orderReturnService;
    @Autowired
    private PProductDao pProductDao;

    @RequestMapping("/")
    public String index(Model model) {
        // 所有一级品类
        List<ProductCategoryDto> categoryList = categoryService.getCategoryFatherList();
        model.addAttribute("fatherCategorys", categoryList);
        model.addAttribute("pTypes", ProductTypeEnum.values());
        return "orderManager/product_validate";
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public ValidateSearchDto findList(@RequestBody ValidateSearchDto dto) {
        validateService.findList(dto);
        return dto;
    }

    @RequestMapping(value = "/doValidate/{productId}", method = RequestMethod.GET)
    public String validateIndex(Model model, @PathVariable Long productId) {
        model.addAttribute("productId", productId);
        PProductEntity product = pProductDao.findOne(productId);
        model.addAttribute("productName", product == null ? "出错啦！" : product.getProductName());
        return "orderManager/do_validate";
    }

    @RequestMapping(value = "/validateCode", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<?> doValidate(Long productId, String code) {
        return validateService.doValidate(productId, code);
    }

    // 导出
    @RequestMapping(value = "/export", method = RequestMethod.POST)
    public void toExcle(HttpServletRequest request, HttpServletResponse response, OrderManagerExportSearchDto pageDto)
            throws UnsupportedEncodingException {
        String excelName = "";
        HSSFWorkbook wb = null;
        // pageDto.setStatus(status);

        wb = validateService.toExcle(pageDto);
        excelName = "订单列表.xls";
        try {
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-disposition", "attachment;filename="
                    + new String(excelName.getBytes("GB2312"), "ISO8859-1"));
            ServletOutputStream outputStream = response.getOutputStream();
            wb.write(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            LOGGER.error("error", e);
        }
    }
    
    @RequestMapping(value = "/view/{orderId}/{type}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("orderId") String orderId, @PathVariable("type") String type) {
        ModelAndView mv = new ModelAndView("orderManager/org_order_view");
        OrderReturnDto dto = orderReturnService.getOrderDetail(orderId);
        dto.setReturnType(type);
        mv.addObject("order", dto);
        mv.addObject("remarks", orderReturnService.getRemarks(orderId));
        return mv;
    }
    
    @RequestMapping("/findDetails")
    @ResponseBody
    public ResultDto<?> getDetails(@RequestBody OrderDetailSearchDto listDto) {
        return ResultDtoFactory.toAck("", validateService.findOrderDetails(listDto));
    }
}
