package com.hengtiansoft.business.marketing.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.business.marketing.dto.RebateDto;
import com.hengtiansoft.business.marketing.dto.RebateProvinceDto;
import com.hengtiansoft.business.marketing.dto.RefereeRebateDto;
import com.hengtiansoft.business.marketing.service.FreightRebateService;
import com.hengtiansoft.business.region.dto.ProvAndCityDto;
import com.hengtiansoft.business.region.dto.RegionDto;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.converter.ConverterService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.enumeration.EErrorCode;
import com.hengtiansoft.wrw.dao.SBasicParaDao;
import com.hengtiansoft.wrw.dao.SFreightRebateDao;
import com.hengtiansoft.wrw.dao.SRegionDao;
import com.hengtiansoft.wrw.entity.SBasicParaEntity;
import com.hengtiansoft.wrw.entity.SFreightRebateEntity;
import com.hengtiansoft.wrw.entity.SRegionEntity;
import com.hengtiansoft.wrw.enums.BasicTypeEnum;
import com.hengtiansoft.wrw.enums.RegionLevelType;

@Service
public class FreightRebateServiceImpl implements FreightRebateService {

    @Autowired
    private SFreightRebateDao sFreightRebateDao;

    @Autowired
    private SRegionDao        sRegionDao;

    @Autowired
    private SBasicParaDao     sBasicParaDao;

    @Override
    public List<RebateProvinceDto> findFreightRebate() {
        List<SFreightRebateEntity> sfr = sFreightRebateDao.findAll();
        List<SRegionEntity> provEntities = sRegionDao.findByLevelTypeOrderByPinyinAsc(RegionLevelType.PROVINCE.getKey());
        LinkedHashMap<Integer, ProvAndCityDto> provMap = new LinkedHashMap<Integer, ProvAndCityDto>();
        for (SRegionEntity entity : provEntities) {
            ProvAndCityDto dto = new ProvAndCityDto();
            dto.setFirstLetter(entity.getPinyin().substring(1, 2));
            dto.setProvId(entity.getId());
            dto.setProvName(entity.getName());
            provMap.put(dto.getProvId(), dto);
        }
        List<SRegionEntity> cityEntities = sRegionDao.findByLevelType(RegionLevelType.CITY.getKey());
        for (SRegionEntity entity : cityEntities) {
            provMap.get(entity.getParentId()).setCityDto(ConverterService.convert(entity, RegionDto.class));
        }
        List<ProvAndCityDto> proCity = new ArrayList<ProvAndCityDto>(provMap.values());

        List<RebateProvinceDto> rps = new ArrayList<RebateProvinceDto>();

        for (int j = 0; j < proCity.size(); j++) {
            List<RebateDto> rebates = new ArrayList<RebateDto>();
            for (int i = 0; i < sfr.size(); i++) {
                for (int k = 0; k < proCity.get(j).getCityDtos().size(); k++) {
                    if (sfr.get(i).getRegionId().equals(proCity.get(j).getCityDtos().get(k).getId())) {
                        RebateDto detail = new RebateDto();
                        detail.setCityId(proCity.get(j).getCityDtos().get(k).getId());
                        detail.setCityName(proCity.get(j).getCityDtos().get(k).getName());
                        detail.setpRebate(sfr.get(i).getpRebate());
                        detail.setzRebate(sfr.get(i).getzRebate());
                        rebates.add(detail);
                    }

                }

            }

            if (null != rebates && rebates.size() != 0) {
                RebateProvinceDto rp = new RebateProvinceDto();
                rp.setRegionId(Long.valueOf(proCity.get(j).getProvId()));
                rp.setRegionName(proCity.get(j).getProvName());
                rp.setDetail(rebates);
                rps.add(rp);
            }
        }
        return rps;
    }

    @Override
    @Transactional
    public void saveFreightRebate(RebateProvinceDto rebateProvinceDto) {
        if (null != rebateProvinceDto.getDetail()) {
            for (int i = 0; i < rebateProvinceDto.getDetail().size(); i++) {
                Integer regionId = rebateProvinceDto.getDetail().get(i).getCityId();
                SFreightRebateEntity entity = sFreightRebateDao.findByRegionId(regionId);
                entity.setpRebate(rebateProvinceDto.getDetail().get(i).getpRebate());
                entity.setzRebate(rebateProvinceDto.getDetail().get(i).getzRebate());
                entity.setModifyDate(new Date());
                entity.setModifyId(AuthorityContext.getCurrentUser().getUserId());
                sFreightRebateDao.save(entity);
            }
        }

    }

    @Override
    public ResultDto<Double> findRefereeRebate() {
        List<SBasicParaEntity> entities = sBasicParaDao.findByTypeId(BasicTypeEnum.REFEREE_REBATE.getKey());
        return ResultDtoFactory.toAck("scuess", new Double(entities.get(0).getParaValue1() == null ? "0" : entities.get(0).getParaValue1()));
    }

    @Override
    @Transactional
    public ResultDto<String> saveRefereeRebate(RefereeRebateDto dto) {
        List<SBasicParaEntity> entities = sBasicParaDao.findByTypeId(BasicTypeEnum.REFEREE_REBATE.getKey());
        if (entities.size() == 0) {
            SBasicParaEntity entity = new SBasicParaEntity();
            entity.setTypeId(BasicTypeEnum.REFEREE_REBATE.getKey());
            entity.setParaValue1(dto.getRefereeRebate().toString());
            entity.setParaName(BasicTypeEnum.REFEREE_REBATE.getValue());
            entities.add(entity);
        }
        entities.get(0).setParaValue1(dto.getRefereeRebate().toString());
        sBasicParaDao.save(entities);
        return ResultDtoFactory.toAck(EErrorCode.SUCCESS);
    }
}
