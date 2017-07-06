package com.hengtiansoft.crh.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengtiansoft.church.dao.ResourceDao;
import com.hengtiansoft.church.entity.ResourceEntity;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.crh.dto.ResourceDto;
import com.hengtiansoft.crh.service.ResourceService;

@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceDao resourceDao;

    @Override
    public ResultDto<List<ResourceDto>> findResources() {
        List<ResourceEntity> resourceList = resourceDao.findAllBySort();
        List<ResourceDto> dtoList = new ArrayList<ResourceDto>();
        for (ResourceEntity resource : resourceList) {
            ResourceDto dto = new ResourceDto();
            dto.setTitle(resource.getTitle());
            dto.setImgSrc(resource.getImage());
            dto.setUrl(resource.getLink());
            dtoList.add(dto);
        }
        return ResultDtoFactory.toAck("SUCCESS", dtoList);
    }

}
