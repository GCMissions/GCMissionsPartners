package com.hengtiansoft.business.shopStock.service;

import com.hengtiansoft.business.shopStock.zStock.dto.StockRecordDto;


public interface SStockRecordService {

    StockRecordDto findByStockId(StockRecordDto dto);
}
