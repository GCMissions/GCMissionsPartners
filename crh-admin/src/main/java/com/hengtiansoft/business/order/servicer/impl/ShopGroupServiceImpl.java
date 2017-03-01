package com.hengtiansoft.business.order.servicer.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengtiansoft.business.common.util.QueryUtil;
import com.hengtiansoft.business.order.dto.ShopGroupDto;
import com.hengtiansoft.business.order.dto.ShopGroupPageDto;
import com.hengtiansoft.business.order.servicer.ShopGroupService;
import com.hengtiansoft.common.converter.ConverterService;
import com.hengtiansoft.common.util.DateTimeUtil;
import com.hengtiansoft.wrw.dao.MShopGroupDao;
import com.hengtiansoft.wrw.entity.MShopGroupEntity;
import com.hengtiansoft.wrw.enums.ShopGroupStatus;

/**
 * Class Name: ShopGroupServiceImpl
 * Description: 团购订单 service 实现类
 * 
 * @author kangruan
 */
@Service
public class ShopGroupServiceImpl implements ShopGroupService {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private MShopGroupDao shopGroupDao;

    /**
     * Description: 根据条件进行分页查询
     *
     * @param pageDto
     */
    @Override
    public void findPage(ShopGroupPageDto pageDto) {
        StringBuilder sbSql = new StringBuilder();
        StringBuilder countSql = new StringBuilder();
        StringBuilder paramSql = new StringBuilder();

        Map<String, Object> param = new HashMap<String, Object>();

        sbSql.append("from MShopGroupEntity s where 1=1 ");
        countSql.append("select count(1) from MShopGroupEntity s where 1=1 ");

        if (StringUtils.isNotEmpty(pageDto.getStatus())) {
            paramSql.append(" and s.status =:status ");
            param.put("status", pageDto.getStatus());
        }
        
        if (StringUtils.isNotEmpty(pageDto.getAddressPhone())) {
            paramSql.append(" and s.addressPhone =:addressPhone ");
            param.put("addressPhone", pageDto.getAddressPhone().trim());
        }

        if (StringUtils.isNotEmpty(pageDto.getStartDate())) {
            paramSql.append(" and s.createDate >=:startDate ");
            param.put("startDate", DateTimeUtil.getDateBegin(pageDto.getStartDate()));
        }

        if (StringUtils.isNotEmpty(pageDto.getEndDate())) {
            paramSql.append(" and s.createDate <=:endDate ");
            param.put("endDate", DateTimeUtil.getDateEnd(pageDto.getEndDate()));
        }

        sbSql.append(paramSql.toString()).append(" order by s.createDate desc");
        countSql.append(paramSql.toString());

        Query query = entityManager.createQuery(sbSql.toString());
        QueryUtil.processParamForQuery(query, param);
        Query countQuery = entityManager.createQuery(countSql.toString());
        QueryUtil.processParamForQuery(countQuery, param);

        query.setFirstResult(pageDto.getPageSize() * (pageDto.getCurrentPage() - 1));
        query.setMaxResults(pageDto.getPageSize());
        Long totalRecord = (Long) countQuery.getSingleResult();
        QueryUtil.buildPagingDto(pageDto, totalRecord.longValue(), buildOrderManagerDtos(query.getResultList()));

    }

    private List<ShopGroupDto> buildOrderManagerDtos(List<MShopGroupEntity> list) {
        List<ShopGroupDto> resultList = new ArrayList<ShopGroupDto>();
        ShopGroupDto shopGroupDto = null;

        if (CollectionUtils.isNotEmpty(list)) {
            for (MShopGroupEntity entity : list) {
                shopGroupDto = ConverterService.convert(entity, ShopGroupDto.class);
                shopGroupDto.setStatusStr(ShopGroupStatus.getValue(shopGroupDto.getStatus()));
                resultList.add(shopGroupDto);
            }
        }
        return resultList;
    }

    /**
     * Description: 处理团购订单
     *
     * @param groupId
     */
    @Override
    @Transactional
    public void dealOrder(Long groupId) {
        MShopGroupEntity shopGroup = shopGroupDao.findOne(groupId);
        shopGroup.setStatus(ShopGroupStatus.deal.getKey());
        shopGroupDao.save(shopGroup);
    }

}
