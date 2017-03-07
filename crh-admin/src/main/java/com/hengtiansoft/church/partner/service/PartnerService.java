package com.hengtiansoft.church.partner.service;

import com.hengtiansoft.church.partner.dto.PartnerSaveDto;
import com.hengtiansoft.church.partner.dto.PartnerSearchDto;
import com.hengtiansoft.common.dto.ResultDto;

public interface PartnerService {

    void searchPartner(PartnerSearchDto dto);
    
    PartnerSaveDto view(Long id);
    
    ResultDto<?> deletePartner(Long id);
    
    ResultDto<?> savePartner(PartnerSaveDto dto);
}
