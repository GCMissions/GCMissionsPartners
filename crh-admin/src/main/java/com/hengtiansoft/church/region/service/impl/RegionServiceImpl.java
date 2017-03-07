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

import com.hengtiansoft.church.common.util.QueryUtil;
import com.hengtiansoft.church.dao.CountryDao;
import com.hengtiansoft.church.dao.RegionDao;
import com.hengtiansoft.church.entity.RegionEntity;
import com.hengtiansoft.church.enums.StatusEnum;
import com.hengtiansoft.church.region.dto.RegionListDto;
import com.hengtiansoft.church.region.dto.RegionSaveDto;
import com.hengtiansoft.church.region.dto.RegionSearchDto;
import com.hengtiansoft.church.region.service.RegionService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.authority.domain.UserInfo;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.util.BasicUtil;
import com.hengtiansoft.common.util.DateTimeUtil;

@Service
public class RegionServiceImpl implements RegionService {

    @Autowired
    private RegionDao regionDao;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private CountryDao countryDao;
    
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
    public RegionSaveDto regionDetail(Long id) {
        RegionSaveDto dto = new RegionSaveDto();
        RegionEntity region = regionDao.findOne(id);
        dto.setId(region.getId());
        dto.setRegionName(region.getRegionName());
        dto.setColor(region.getColor());
        
        return null;
    }

    @Override
    @Transactional
    public ResultDto<?> saveRegion(RegionSaveDto dto) {
        return null;
    }
}
