package com.hengtiansoft.church.slides.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengtiansoft.church.common.util.QueryUtil;
import com.hengtiansoft.church.slides.dto.SlidesListDto;
import com.hengtiansoft.church.slides.dto.SlidesSearchDto;
import com.hengtiansoft.church.slides.service.SlidesService;
import com.hengtiansoft.common.util.BasicUtil;

@Service
public class SlidesServiceImpl implements SlidesService {
    
    @Autowired
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    @Override
    public void getSlidesList(final SlidesSearchDto dto) {
        Map<String, Object> param = new HashMap<String, Object>();
        StringBuilder conditionSql = new StringBuilder("");
        StringBuilder countSql = new StringBuilder("");
        StringBuilder sql = new StringBuilder(
                " select id,image,description,display,sort from slides where 1=1 ");
        countSql.append(" select count(1) from ( ").append(sql);
        conditionSql.append(" and del_flag = '1' order by sort ");
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
        dto.setList(buildSlidesList(query.getResultList()));
    }

    private List<SlidesListDto> buildSlidesList(List<Object[]> list) {
        List<SlidesListDto> slideList = new ArrayList<SlidesListDto>();
        for (Object[] obj : list) {
            SlidesListDto dto = new SlidesListDto();
            dto.setId(BasicUtil.objToLong(obj[0]));
            dto.setImage(BasicUtil.objToString(obj[1]));
            dto.setDescription(BasicUtil.objToString(obj[2]));
        }
        return slideList;
    }
}
