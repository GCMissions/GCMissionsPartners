package com.hengtiansoft.business.activity.service;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hengtiansoft.business.activity.dto.ValidateSearchDto;
import com.hengtiansoft.business.order.dto.OrderDetailDto;
import com.hengtiansoft.business.order.dto.OrderDetailSearchDto;
import com.hengtiansoft.business.order.dto.OrderManagerExportSearchDto;
import com.hengtiansoft.business.order.dto.OrderManagerListSearchDto;
import com.hengtiansoft.common.dto.ResultDto;

public interface ValidateService {
    
    void findList(ValidateSearchDto dto);
    
    ResultDto<?> doValidate(Long productId, String code);
    
    void search(OrderManagerListSearchDto dto);
    
    HSSFWorkbook toExcle(OrderManagerExportSearchDto pageDto);
    
    void exportSearch(OrderManagerExportSearchDto dto);
    
    List<OrderDetailDto> findOrderDetails(OrderDetailSearchDto listDto);
}
