package com.hengtiansoft.business.marketing.service;

import java.util.List;

import com.hengtiansoft.business.marketing.dto.RebateProvinceDto;
import com.hengtiansoft.business.marketing.dto.RefereeRebateDto;
import com.hengtiansoft.common.dto.ResultDto;

public interface FreightRebateService {
    /**
     * Description: 市配送费返点设置
     *
     * @return
     */
    List<RebateProvinceDto> findFreightRebate();

    void saveFreightRebate(RebateProvinceDto rebateProvinceDto);

    ResultDto<Double> findRefereeRebate();

    ResultDto<String> saveRefereeRebate(RefereeRebateDto dto);
}
