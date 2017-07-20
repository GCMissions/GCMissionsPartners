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
import com.hengtiansoft.church.entity.CountryEntity;
import com.hengtiansoft.church.entity.CountryRegionRefEntity;
import com.hengtiansoft.church.entity.PartnersEntity;
import com.hengtiansoft.church.entity.RegionEntity;
import com.hengtiansoft.church.enums.StatusEnum;
import com.hengtiansoft.church.partner.dto.PartnerListDto;
import com.hengtiansoft.church.partner.dto.PartnerSaveDto;
import com.hengtiansoft.church.partner.dto.PartnerSearchDto;
import com.hengtiansoft.church.partner.dto.RegionAndCountryDto;
import com.hengtiansoft.church.partner.service.PartnerAdminService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.authority.domain.UserInfo;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.util.BasicUtil;

@Service
public class PartnerAdminServiceImpl implements PartnerAdminService {

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
                " select p.id,p.partner_name,p.mission,c.country_name,r.region_name from partners p left join country_region_ref cr on p.c_r_ref_id = cr.id "
                        + " left join country c on cr.country_id = c.id left join region r on cr.region_id = r.id where 1=1 ");
        countSql.append(" select count(1) from ( ").append(sql);
        conditionSql
                .append(" and p.del_flag = '1' and cr.del_flag = '1' and c.del_flag = '1' and r.del_flag = '1' order by p.create_date desc,p.id desc ");
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
    
    /**
     *查找跟国家无关的partner
     */
    @Override
    @SuppressWarnings("unchecked")
    public void getAllOtherPartner(final PartnerSearchDto dto) {
        Map<String, Object> param = new HashMap<String, Object>();
        StringBuilder conditionSql = new StringBuilder("");
        StringBuilder countSql = new StringBuilder("");
        StringBuilder sql = new StringBuilder(
                " select p.id,p.partner_name,p.mission,c.country_name,r.region_name from partners p where p.c_r_ref_id is null");
        countSql.append(" select count(1) from ( ").append(sql);
        conditionSql
                .append(" and p.del_flag = '1' order by p.create_date desc,p.id desc ");
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
            if (!BasicUtil.objToString(obj[2]).isEmpty()) {
                dto.setMission(BasicUtil.objToString(obj[2]));
            }
            if (!BasicUtil.objToString(obj[3]).isEmpty()) {
                dto.setCountryName(BasicUtil.objToString(obj[3]));
            }

            dto.setRegionName(BasicUtil.objToString(obj[4]));
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
        Long cRRefId = 0L;
        if (partner.getcRRefId() != null && partner.getcRRefId() != 0L) {
            cRRefId = partner.getcRRefId();
            CountryRegionRefEntity entity = countryRegionRefDao.findOne(cRRefId);
            CountryEntity country = countryDao.findOne(entity.getCountyId());
            RegionEntity region = regionDao.findOne(entity.getRegionId());
            dto.setCountryName(country.getCountryName());
            dto.setRegionName(region.getRegionName());
            dto.setCountryId(country.getId());
            dto.setRegionId(region.getId());
            dto.setRegionList(regionDao.findAllRegionNotEqId(StatusEnum.NORMAL.getCode(), region.getId()));
            dto.setCountryList(countryDao.findAllCountryByGroupIdAndNotEqId(region.getId(), country.getId()));
        }
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
        //国家和区域非空时，partner是和国家关联的，需要给partner的c_r_ref赋值，否则为空
        if(dto.getCountryId()!= null && dto.getCountryId() != 0L && dto.getRegionId() != null&& dto.getRegionId() != 0L){
            CountryRegionRefEntity entity = countryRegionRefDao.findByCountyIdAndRegionId(dto.getCountryId(),
                    dto.getRegionId());
            partner.setcRRefId(entity.getId());  
        }   
        partnerDao.save(partner);
        return ResultDtoFactory.toAck("Save Success!", null);
    }

    @Override
    public RegionAndCountryDto getAllRegion() {
        RegionAndCountryDto dto = new RegionAndCountryDto();
        List<RegionEntity> regionList = regionDao.findByDelFlagAndSort(StatusEnum.NORMAL.getCode());
        if (!regionList.isEmpty()) {
            dto.setRegionList(regionList);
            dto.setCountryList(countryDao.findAllCountryByGroupId(regionList.get(0).getId()));
        }
        return dto;
    }

    @Override
    public ResultDto<List<CountryEntity>> getCountry(Long regionId) {
        return ResultDtoFactory.toAck("", countryDao.findAllCountryByGroupId(regionId));
    }

   

}
