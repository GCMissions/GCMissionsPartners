package com.hengtiansoft.crh.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengtiansoft.church.dao.CountryDao;
import com.hengtiansoft.church.dao.RegionDao;
import com.hengtiansoft.church.dao.PartnerDao;
import com.hengtiansoft.church.entity.CountryEntity;
import com.hengtiansoft.church.entity.RegionEntity;
import com.hengtiansoft.church.entity.PartnersEntity;
import com.hengtiansoft.church.enums.StatusEnum;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.crh.dto.CountryDto;
import com.hengtiansoft.crh.dto.RegionDto;
import com.hengtiansoft.crh.dto.PartnerDto;
import com.hengtiansoft.crh.service.GroupService;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private PartnerDao partnerDao;
    @Autowired
    private RegionDao regionDao;
    @Autowired
    private CountryDao countryDao;

    @Override
    public ResultDto<List<RegionDto>> getAllGroups() {
        List<RegionEntity> regionList = regionDao.findByDelFlagAndSort(StatusEnum.NORMAL.getCode());
        List<RegionDto> regionDtoList = new ArrayList<RegionDto>();
        if (regionList.isEmpty()) {
            return ResultDtoFactory.toAck("No Data!", null);
        }
        for (RegionEntity region : regionList) {
            RegionDto rDto = new RegionDto();
            List<CountryEntity> countryList = countryDao.findAllCountryByGroupId(region.getId());
            List<CountryDto> countryDtoList = new ArrayList<CountryDto>();
            for (CountryEntity country : countryList) {
                CountryDto cDto = new CountryDto();
                List<PartnersEntity> partnerList = partnerDao.getAllPartnersByRegionIdAndCountryId(region.getId(),
                        country.getId());
                if (partnerList.isEmpty()) {
                    continue;
                } else {
                    List<PartnerDto> partnerDtoList = new ArrayList<PartnerDto>();
                    for (PartnersEntity partner : partnerList) {
                        PartnerDto pDto = new PartnerDto();
                        pDto.setName(partner.getPartnerName());
                        pDto.setTitle(partner.getPartnerName());
                        pDto.setImgSrc(partner.getImage());
                        pDto.setContent(partner.getIntroduce());
                        partnerDtoList.add(pDto);
                    }
                    cDto.setName(country.getCountrySimpleName());
                    cDto.setItem(partnerDtoList);
                    countryDtoList.add(cDto);
                }
            }
            rDto.setName(region.getRegionName());
            rDto.setColor(region.getColor());
            rDto.setItem(countryDtoList);
            regionDtoList.add(rDto);
        }
        return ResultDtoFactory.toAck("SUCCESS", regionDtoList);
    }
}
