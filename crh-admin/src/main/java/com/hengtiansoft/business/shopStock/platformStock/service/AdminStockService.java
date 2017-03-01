package com.hengtiansoft.business.shopStock.platformStock.service;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import com.hengtiansoft.business.shopStock.platformStock.dto.AdminSearchDto;
import com.hengtiansoft.common.dto.ResultDto;

public interface AdminStockService {

    void search(AdminSearchDto searchDto);

    List<List<String>> findAllOrder(Date startTime, Date endTime);

    ResultDto<String> importExcel(InputStream inputStream);

    List<List<String>> getErrorCode();

}
