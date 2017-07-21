package com.hengtiansoft.church.partner.service;

import java.util.List;

import com.hengtiansoft.church.entity.CountryEntity;
import com.hengtiansoft.church.partner.dto.PartnerSaveDto;
import com.hengtiansoft.church.partner.dto.PartnerSearchDto;
import com.hengtiansoft.church.partner.dto.RegionAndCountryDto;
import com.hengtiansoft.common.dto.ResultDto;

public interface PartnerAdminService {

    void searchPartner(PartnerSearchDto dto);
    
    void getAllOtherPartner(PartnerSearchDto dto);
    
    PartnerSaveDto view(Long id);
    
    ResultDto<?> deletePartner(Long id);
    
    ResultDto<?> savePartner(PartnerSaveDto dto);
    
    RegionAndCountryDto getAllRegion();
    
    ResultDto<List<CountryEntity>> getCountry(Long regionId);
    
}
