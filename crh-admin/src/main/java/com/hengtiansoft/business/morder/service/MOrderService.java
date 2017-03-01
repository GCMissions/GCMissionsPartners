package com.hengtiansoft.business.morder.service;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hengtiansoft.business.morder.dto.MOrderSearchDto;
import com.hengtiansoft.wrw.entity.SOrgEntity;

public interface MOrderService {
    
    List<SOrgEntity> findOrgs();
    

    void search(MOrderSearchDto dto);


    HSSFWorkbook toExcle(MOrderSearchDto pageDto);
    
}
