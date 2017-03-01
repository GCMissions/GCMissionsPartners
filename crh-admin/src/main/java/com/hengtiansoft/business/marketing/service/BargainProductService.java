package com.hengtiansoft.business.marketing.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hengtiansoft.business.marketing.dto.BargainChartPageDto;
import com.hengtiansoft.business.marketing.dto.BargainPageDto;
import com.hengtiansoft.business.marketing.dto.BargainSaveAndEditDto;
/**
 * 
* Class Name: BargainProductService
* Description: 
* @author chenghongtu
*
 */
public interface BargainProductService {

    void search(BargainPageDto dto);

    BargainSaveAndEditDto findById(Long id);

    void delete(Long id);

    void update(BargainSaveAndEditDto dto);

    void save(BargainSaveAndEditDto dto);

    void searchChart(BargainChartPageDto dto);

    HSSFWorkbook toExcle(BargainChartPageDto pageDto);

}
