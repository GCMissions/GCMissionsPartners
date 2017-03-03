package com.hengtiansoft.crh.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengtiansoft.church.dao.SlidesDao;
import com.hengtiansoft.church.entity.SlidesEntity;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.crh.dto.SlidesDto;
import com.hengtiansoft.crh.service.SlideService;

@Service
public class SlideServiceImpl implements SlideService {
    
    @Autowired
    private SlidesDao slidesDao;

    @Override
    public ResultDto<SlidesDto> getSlides() {
        List<SlidesEntity> slideList = slidesDao.findAllAndSort();
        SlidesDto dto = new SlidesDto();
        List<String> imageList = new ArrayList<String>();
        for (SlidesEntity slide : slideList) {
            imageList.add(slide.getImage());
        }
        dto.setImageList(imageList);
        return ResultDtoFactory.toAck("SUCCESS", dto);
    }
}
