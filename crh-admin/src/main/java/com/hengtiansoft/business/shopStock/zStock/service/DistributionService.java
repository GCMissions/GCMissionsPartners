package com.hengtiansoft.business.shopStock.zStock.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hengtiansoft.business.shopStock.zStock.dto.StockDto;
import com.hengtiansoft.business.shopStock.zStock.dto.StockRecordDto;
import com.hengtiansoft.business.shopStock.zStock.dto.StockSearchDto;

public interface DistributionService {

    void searchStock(StockSearchDto infoDto);

    StockDto findStock(Long id);

    StockRecordDto findStockRecord(StockRecordDto dto);

    Integer getWarnStockCount(String orgType);

    HSSFWorkbook toExcel(StockSearchDto searchDto);
}
