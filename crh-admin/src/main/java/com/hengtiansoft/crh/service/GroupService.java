package com.hengtiansoft.crh.service;

import java.util.List;

import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.crh.dto.RegionDto;

public interface GroupService {

    ResultDto<List<RegionDto>> getAllGroups();
}
