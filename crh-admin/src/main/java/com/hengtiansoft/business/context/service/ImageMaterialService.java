package com.hengtiansoft.business.context.service;

import java.util.List;

import com.hengtiansoft.business.context.dto.ImageMaterialIdDto;
import com.hengtiansoft.common.dto.ResultDto;

public interface ImageMaterialService {

    void findList(ImageMaterialIdDto dto);
    
    ResultDto<?> uploadImage(String url);
    
    ResultDto<?> deleteImages(List<Long> deleteIds);
}
