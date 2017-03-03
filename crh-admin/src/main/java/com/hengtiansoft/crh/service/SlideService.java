package com.hengtiansoft.crh.service;

import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.crh.dto.SlidesDto;

public interface SlideService {

    ResultDto<SlidesDto> getSlides();
}
