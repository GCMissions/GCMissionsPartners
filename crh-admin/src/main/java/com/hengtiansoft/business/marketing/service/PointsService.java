package com.hengtiansoft.business.marketing.service;

import com.hengtiansoft.business.marketing.dto.PointsDto;
import com.hengtiansoft.common.dto.ResultDto;

public interface PointsService {

    /**
     * Description: 查询
     *
     * @return
     */
    PointsDto getPointConfig();

    /**
     * Description: 保存
     *
     * @param pointsDto
     * @return
     */
    ResultDto<String> savePointsConfig(PointsDto pointsDto);

}
