package com.hengtiansoft.church.slides.service;

import com.hengtiansoft.church.slides.dto.SlidesListDto;
import com.hengtiansoft.church.slides.dto.SlidesSaveDto;
import com.hengtiansoft.church.slides.dto.SlidesSearchDto;
import com.hengtiansoft.common.dto.ResultDto;

public interface SlidesService {

    void getSlidesList(SlidesSearchDto dto);
    
    ResultDto<?> deleteSlide(Long id);
    
    SlidesListDto slideDetail(Long id);
    
    ResultDto<?> saveSlides(SlidesSaveDto dto);
    
    ResultDto<?> adjustSort(Long id, String type);
}
