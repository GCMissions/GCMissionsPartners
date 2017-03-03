package com.hengtiansoft.crh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengtiansoft.church.dao.GroupDao;
import com.hengtiansoft.church.dao.PartnerDao;
import com.hengtiansoft.church.entity.GroupEntity;
import com.hengtiansoft.church.enums.StatusEnum;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.crh.dto.GroupDto;
import com.hengtiansoft.crh.service.GroupService;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private PartnerDao partnerDao;
    @Autowired
    private GroupDao groupDao;
    
    @Override
    public ResultDto<List<GroupDto>> getAllGroups() {
        List<GroupEntity> groupList = groupDao.findByDelFlagAndSort(StatusEnum.NORMAL.getCode());
        if (groupList.isEmpty()) {
            return ResultDtoFactory.toAck("No Data!", null);
        }
        for (GroupEntity group : groupList) {
            List<Object[]> partnerObj = partnerDao.getPartnersByGroupId(group.getId());
        }
        return null;
    }
}
