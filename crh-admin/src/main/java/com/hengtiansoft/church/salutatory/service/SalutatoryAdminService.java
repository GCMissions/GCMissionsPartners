package com.hengtiansoft.church.salutatory.service;

import com.hengtiansoft.church.entity.SalutatoryEntity;
import com.hengtiansoft.church.salutatory.dto.SalutatorySaveDto;
import com.hengtiansoft.common.dto.ResultDto;

public interface SalutatoryAdminService {
    
    SalutatoryEntity getSalutatory();
    
    ResultDto<?> saveSalutatory(SalutatorySaveDto dto);

}
