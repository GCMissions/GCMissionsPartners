package com.hengtiansoft.business.region.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.business.region.dto.RegionAddDto;
import com.hengtiansoft.business.region.dto.RegionCityDto;
import com.hengtiansoft.business.region.dto.RegionDto;
import com.hengtiansoft.business.region.service.RegionCityService;
import com.hengtiansoft.wrw.dao.SRegionDao;
import com.hengtiansoft.wrw.entity.SRegionEntity;
import com.hengtiansoft.wrw.enums.RegionLevelType;

@Service
public class RegionCityServiceImpl implements RegionCityService {

    @Autowired
    private SRegionDao sRegionDao;

    @Override
    public List<RegionCityDto> findRegionByLevelType() {
        List<SRegionEntity> listP = sRegionDao.findByLevelType(RegionLevelType.PROVINCE.getKey());
        List<SRegionEntity> listC = sRegionDao.findByLevelType(RegionLevelType.CITY.getKey());
        List<RegionCityDto> resultDto = new ArrayList<RegionCityDto>();
        for (SRegionEntity sp : listP) {
            RegionCityDto dto = new RegionCityDto();
            dto.setRegionId(sp.getId());
            dto.setRegionName(sp.getName());
            dto.setParentId(sp.getParentId());
            for (SRegionEntity sc : listC) {
                if (sc.getParentId().equals(sp.getId())) {
                    dto.setCityNum(dto.getCityNum() + 1);
                    if ("1".equals(sc.getOpenFlag())) {
                        dto.setOpenNum(dto.getOpenNum() + 1);
                    }
                }
            }
            resultDto.add(dto);
        }
        return resultDto;
    }

    @Override
    @Transactional(value = "jpaTransactionManager")
    public void update(RegionAddDto dto) {
        List<RegionDto> list = dto.getList();
        List<Integer> ids = new ArrayList<Integer>();
        Map<Integer, RegionDto> map = new HashMap<Integer, RegionDto>();
        for (RegionDto regionDto : list) {
            ids.add(regionDto.getId());
            map.put(regionDto.getId(), regionDto);
        }

        List<SRegionEntity> listEntity = sRegionDao.findAll(ids);
        List<SRegionEntity> listE = new ArrayList<SRegionEntity>();
        for (SRegionEntity sRegionEntity : listEntity) {
            RegionDto rdto = map.get(sRegionEntity.getId());
            sRegionEntity.setOpenFlag(rdto.getOpenFlag());
            listE.add(sRegionEntity);
        }
        sRegionDao.save(listE);
    }

}
