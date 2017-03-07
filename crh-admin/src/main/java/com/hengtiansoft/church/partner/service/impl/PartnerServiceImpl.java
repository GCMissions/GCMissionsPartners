package com.hengtiansoft.church.partner.service.impl;

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
import com.hengtiansoft.church.dao.PartnerDao;
import com.hengtiansoft.church.dao.RegionDao;
import com.hengtiansoft.church.entity.CountryRegionRefEntity;
import com.hengtiansoft.church.entity.PartnersEntity;
import com.hengtiansoft.church.enums.StatusEnum;
import com.hengtiansoft.church.partner.dto.PartnerListDto;
import com.hengtiansoft.church.partner.dto.PartnerSaveDto;
import com.hengtiansoft.church.partner.dto.PartnerSearchDto;
import com.hengtiansoft.church.partner.service.PartnerService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.authority.domain.UserInfo;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.util.BasicUtil;

@Service
public class PartnerServiceImpl implements PartnerService {
    
    @Autowired
    private PartnerDao partnerDao;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private CountryRegionRefDao countryRegionRefDao;
    @Autowired
    private CountryDao countryDao;
    @Autowired
    private RegionDao regionDao;

    @SuppressWarnings("unchecked")
    @Override
    public void searchPartner(final PartnerSearchDto dto) {
        Map<String, Object> param = new HashMap<String, Object>();
        StringBuilder conditionSql = new StringBuilder("");
        StringBuilder countSql = new StringBuilder("");
        StringBuilder sql = new StringBuilder(
                " select id,partner_name,mission,c_r_ref_id from partner where 1=1 ");
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
        dto.setList(buildPartnerList(query.getResultList()));
    }

    private List<PartnerListDto> buildPartnerList(List<Object[]> list) {
        List<PartnerListDto> partnerList = new ArrayList<PartnerListDto>();
        for (Object[] obj : list) {
            PartnerListDto dto = new PartnerListDto();
            dto.setId(BasicUtil.objToLong(obj[0]));
            dto.setPartnerName(BasicUtil.objToString(obj[1]));
            dto.setMission(BasicUtil.objToString(obj[2]));
            Long cRRefId = BasicUtil.objToLong(obj[3]);
            CountryRegionRefEntity entity = countryRegionRefDao.findOne(cRRefId);
            dto.setCountryName(countryDao.findOne(entity.getCountyId()).getCountryName());
            dto.setRegionName(regionDao.findOne(entity.getRegionId()).getRegionName());
            partnerList.add(dto);
        }
        return partnerList;
    }

    @Override
    public PartnerSaveDto view(Long id) {
        PartnerSaveDto dto = new PartnerSaveDto();
        PartnersEntity partner = partnerDao.findOne(id);
        dto.setId(partner.getId());
        dto.setPartnerName(partner.getPartnerName());
        dto.setMission(partner.getMission());
        Long cRRefId = partner.getcRRefId();
        CountryRegionRefEntity entity = countryRegionRefDao.findOne(cRRefId);
        dto.setCountryName(countryDao.findOne(entity.getCountyId()).getCountryName());
        dto.setRegionName(regionDao.findOne(entity.getRegionId()).getRegionName());
        dto.setAddress(partner.getAddress());
        dto.setImage(partner.getImage());
        dto.setIntroduce(partner.getIntroduce());
        return dto;
    }

    @Override
    @Transactional
    public ResultDto<?> deletePartner(Long id) {
        UserInfo userInfo = AuthorityContext.getCurrentUser();
        Long userId = 0L;
        if (userInfo != null) {
            userId = userInfo.getUserId();
        }
        PartnersEntity partner = partnerDao.findOne(id);
        partner.setDelFlag(StatusEnum.DELETE.getCode());
        partner.setModifyId(userId);
        partner.setModifyDate(new Date());
        partnerDao.save(partner);
        return ResultDtoFactory.toAck("Delete Success!", null);
    }

    @Override
    @Transactional
    public ResultDto<?> savePartner(PartnerSaveDto dto) {
        UserInfo userInfo = AuthorityContext.getCurrentUser();
        Long userId = 0L;
        if (userInfo != null) {
            userId = userInfo.getUserId();
        }
        Date date = new Date();
        PartnersEntity partner = null;
        if (OprationTypeConstants.SAVE_OPRATION.equals(dto.getType())) {
            partner = new PartnersEntity();
            partner.setDelFlag(StatusEnum.NORMAL.getCode());
            partner.setCreateDate(date);
            partner.setCreateId(userId);
        } else {
            partner = partnerDao.findOne(dto.getId());
            partner.setModifyDate(date);
            partner.setModifyId(userId);
        }
        partner.setPartnerName(dto.getPartnerName());
        partner.setImage(dto.getImage());
        partner.setMission(dto.getMission());
        partner.setAddress(dto.getAddress());
        partner.setIntroduce(dto.getIntroduce());
        CountryRegionRefEntity entity = countryRegionRefDao.findByCountyIdAndRegionId(dto.getCountryId(), dto.getRegionId());
        partner.setcRRefId(entity.getId());
        partnerDao.save(partner);
        return ResultDtoFactory.toAck("Save Success!", null);
    }
}
