package com.hengtiansoft.business.finance.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hengtiansoft.business.finance.dto.SOrderBalanceDto;
import com.hengtiansoft.business.finance.dto.SOrderBalancePageDto;

public interface SOrderBalanceService {

    SOrderBalancePageDto findAll(SOrderBalancePageDto dto);

    HSSFWorkbook toExcle(SOrderBalancePageDto dto);

    SOrderBalanceDto getOrderDetail(String orderId);


}
