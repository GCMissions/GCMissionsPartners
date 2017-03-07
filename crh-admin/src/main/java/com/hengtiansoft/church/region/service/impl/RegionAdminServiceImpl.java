package com.hengtiansoft.church.region.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.church.common.constants.OprationTypeConstants;
import com.hengtiansoft.church.common.util.QueryUtil;
import com.hengtiansoft.church.dao.CountryDao;
import com.hengtiansoft.church.dao.CountryRegionRefDao;
import com.hengtiansoft.church.dao.RegionDao;
import com.hengtiansoft.church.entity.CountryEntity;
import com.hengtiansoft.church.entity.CountryRegionRefEntity;
import com.hengtiansoft.church.entity.RegionEntity;
import com.hengtiansoft.church.enums.StatusEnum;
import com.hengtiansoft.church.region.dto.RegionDetailDto;
import com.hengtiansoft.church.region.dto.RegionListDto;
import com.hengtiansoft.church.region.dto.RegionSaveDto;
import com.hengtiansoft.church.region.dto.RegionSearchDto;
import com.hengtiansoft.church.region.service.RegionAdminService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.authority.domain.UserInfo;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.util.BasicUtil;
import com.hengtiansoft.common.util.DateTimeUtil;

@Service
public class RegionAdminServiceImpl implements RegionAdminService {

    @Autowired
    private RegionDao regionDao;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private CountryDao countryDao;
    @Autowired
    private CountryRegionRefDao countryRegionRefDao;
    
    @SuppressWarnings("unchecked")
    @Override
    public void searchRegion(final RegionSearchDto dto) {
        Map<String, Object> param = new HashMap<String, Object>();
        StringBuilder conditionSql = new StringBuilder("");
        StringBuilder countSql = new StringBuilder("");
        StringBuilder sql = new StringBuilder(
                " select id,region_name,create_date from region where 1=1 ");
        countSql.append(" select count(1) from ( ").append(sql);
        conditionSql.append(" and del_flag = '1' order by create_date desc,id desc ");
        Query query = entityManager.createNativeQuery(sql.append(conditionSql).toString());
        QueryUtil.processParamForQuery(query, param);
        Query countQuery = entityManager.createNativeQuery(countSql.append(conditionSql).append(" ) a").toString());
        QueryUtil.processParamForQuery(countQuery, param);
        BigInteger totalRecord = (BigInteger) countQuery.getSingleResult();
        query.setFirstResult(dto.getPageSize() * (dto.getCurrentPage() - 1));
        query.setMaxResults(dto.getPageSize());
        dto.setTotalRecord(totalRecord.longValue());
        dto.setTotalPages(totalRecord.intValue() % dto.getPageSize() == 0 ? totalRecord.intValue() / dto.getPageSize()
                : ((totalRecord.intValue() / dto.getPageSize()) + 1));
        dto.setList(buildRegionList(query.getResultList()));
    }

    private List<RegionListDto> buildRegionList(List<Object[]> list) {
        List<RegionListDto> regionList = new ArrayList<RegionListDto>();
        for (Object[] obj : list) {
            RegionListDto dto = new RegionListDto();
            dto.setId(BasicUtil.objToLong(obj[0]));
            dto.setRegionName(BasicUtil.objToString(obj[1]));
            dto.setCreateTime(DateTimeUtil.parseDateToString((Date) obj[2], DateTimeUtil.FOREIGN_FORMAT));
            regionList.add(dto);
        }
        return regionList;
    }

    @Override
    @Transactional
    public ResultDto<?> deleteRegion(Long id) {
        UserInfo userInfo = AuthorityContext.getCurrentUser();
        Long userId = 0L;
        if (userInfo != null) {
            userId = userInfo.getUserId();
        }
        RegionEntity region = regionDao.findOne(id);
        region.setDelFlag(StatusEnum.DELETE.getCode());
        region.setModifyId(userId);
        region.setModifyDate(new Date());
        regionDao.save(region);
        return ResultDtoFactory.toAck("Delete Success!", null);
    }

    @Override
    public RegionDetailDto regionDetail(Long id) {
        RegionDetailDto dto = new RegionDetailDto();
        RegionEntity region = regionDao.findOne(id);
        dto.setId(id);
        dto.setRegionName(region.getRegionName());
        dto.setColor(region.getColor());
        List<CountryEntity> haveCountryList = countryDao.findAllCountryByGroupId(id);
        dto.setHaveCountryList(haveCountryList);
        List<CountryEntity> notHaveCountryList = countryDao.findAll();
        notHaveCountryList.remove(haveCountryList);
        dto.setNotHaveCountryList(notHaveCountryList);
        return dto;
    }

    @Override
    @Transactional
    public ResultDto<?> saveRegion(RegionSaveDto dto) {
        UserInfo userInfo = AuthorityContext.getCurrentUser();
        Long userId = 0L;
        if (userInfo != null) {
            userId = userInfo.getUserId();
        }
        RegionEntity region = null;
        Date date = new Date();
        if (OprationTypeConstants.SAVE_OPRATION.equals(dto.getType())) {
            region = new RegionEntity();
            region.setRegionName(dto.getRegionName());
            region.setColor(dto.getColor());
            region.setDelFlag(StatusEnum.NORMAL.getCode());
            region.setCreateDate(date);
            region.setCreateId(userId);
            Long regionId = regionDao.save(region).getId();
            List<CountryRegionRefEntity> saveRfEntities = new ArrayList<CountryRegionRefEntity>();
            for (Long countryId : dto.getCountryIdList()) {
                CountryRegionRefEntity crEntity = new CountryRegionRefEntity();
                crEntity.setCountyId(countryId);
                crEntity.setRegionId(regionId);
                crEntity.setDelFlag(StatusEnum.NORMAL.getCode());
                saveRfEntities.add(crEntity);
            }
            countryRegionRefDao.save(saveRfEntities);
        } else {
            Long regionId = dto.getId();
            region = regionDao.findOne(regionId);
            region.setRegionName(dto.getRegionName());
            region.setColor(dto.getColor());
            region.setModifyDate(date);
            region.setModifyId(userId);
            regionDao.save(region);
            countryRegionRefDao.initCountryRegionRef(regionId);
            List<CountryRegionRefEntity> saveRfEntities = new ArrayList<CountryRegionRefEntity>();
            for (Long countryId : dto.getCountryIdList()) {
                CountryRegionRefEntity crEntity = new CountryRegionRefEntity();
                crEntity.setCountyId(countryId);
                crEntity.setRegionId(regionId);
                crEntity.setDelFlag(StatusEnum.NORMAL.getCode());
                saveRfEntities.add(crEntity);
            }
            countryRegionRefDao.save(saveRfEntities);
        }
        return ResultDtoFactory.toAck("Save Success!", null);
    }

    @Override
    public List<CountryEntity> findNoRegionCountries() {
        return countryDao.findNoRegionCountries();
    }
}
