package com.hengtiansoft.business.shopStock.regionalPlatform.service;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hengtiansoft.business.shopStock.regionalPlatform.dto.TerminalDetailDto;
import com.hengtiansoft.business.shopStock.regionalPlatform.dto.TerminalStockSearchDto;

public interface TerminalStockService {

    void searchDto(TerminalStockSearchDto searchDto);

    TerminalDetailDto terminalDetail(String orgCode);

    List<Integer> getWarnZStock(String orgType);

    HSSFWorkbook toExcel();
}
