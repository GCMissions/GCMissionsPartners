package com.hengtiansoft.crh.service;

import java.util.List;

import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.crh.dto.ResourceDto;

public interface ResourceService {

    ResultDto<List<ResourceDto>> findResources();
}
