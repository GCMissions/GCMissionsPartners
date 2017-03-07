package com.hengtiansoft.church.resource.service;

import com.hengtiansoft.church.resource.dto.ResourceListDto;
import com.hengtiansoft.church.resource.dto.ResourceSaveDto;
import com.hengtiansoft.church.resource.dto.ResourceSearchDto;
import com.hengtiansoft.common.dto.ResultDto;

public interface ResourceService {

    void searchResource(ResourceSearchDto dto);

    ResultDto<?> deleteResource(Long id);

    ResourceListDto resourceDetail(Long id);

    ResultDto<?> adjustSort(Long id, String type);

    ResultDto<?> saveResource(ResourceSaveDto dto);
}
