package com.hengtiansoft.business.order.servicer.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengtiansoft.business.common.util.QueryUtil;
import com.hengtiansoft.business.order.dto.CustomerFeedbackDto;
import com.hengtiansoft.business.order.dto.CustomerFeedbackPageDto;
import com.hengtiansoft.business.order.servicer.CustomerFeedbackService;
import com.hengtiansoft.common.util.DateTimeUtil;
import com.hengtiansoft.wrw.dao.MFeedbackDao;
import com.hengtiansoft.wrw.enums.FeedbackStatus;

@Service
public class CustomerFeedbackServiceImpl implements CustomerFeedbackService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private MFeedbackDao  feedbackDao;

    @SuppressWarnings("unchecked")
    @Override
    public void searchFeedback(CustomerFeedbackPageDto customerFeedbackPageDto) {

        Map<String, Object> param = new HashMap<String, Object>();
        StringBuilder sqlDataBuilder = new StringBuilder();
        sqlDataBuilder.append("select new com.hengtiansoft.business.order.dto.CustomerFeedbackDto (")
                .append(" f.feedbackId, m.memberName, m.loginId, r.mergerName,a.address, a.areaName, f.feedInfo, f.status, f.createDate ) ")
                .append("from MFeedbackEntity f ,MMemberEntity m , MAddressEntity a, SRegionEntity r")
                .append(" where f.memberId= m.userId and a.memberId = m.userId and a.defFlag=1 and r.id= a.regionId");

        StringBuilder conditionBuilder = new StringBuilder(); // 公用条件condition
        if (!StringUtils.isEmpty(customerFeedbackPageDto.getStartDate())) {
            conditionBuilder.append(" and f.createDate >= :startDate");
            param.put("startDate", DateTimeUtil.getDateBegin(customerFeedbackPageDto.getStartDate()));
        }
        if (!StringUtils.isEmpty(customerFeedbackPageDto.getEndDate())) {
            conditionBuilder.append(" and f.createDate <= :endDate");
            param.put("endDate", DateTimeUtil.getDateEnd(customerFeedbackPageDto.getEndDate()));
        }
        if (!StringUtils.isEmpty(customerFeedbackPageDto.getStatus())) {
            conditionBuilder.append(" and f.status= :status");
            param.put("status", customerFeedbackPageDto.getStatus());
        }
        String orderString = " order by f.createDate desc";
        Query query = entityManager.createQuery(sqlDataBuilder.append(conditionBuilder).append(orderString).toString());
        QueryUtil.processParamForQuery(query, param);

        Query countQuery = entityManager.createQuery(sqlDataBuilder.toString());
        QueryUtil.processParamForQuery(countQuery, param);

        query.setFirstResult(customerFeedbackPageDto.getPageSize() * (customerFeedbackPageDto.getCurrentPage() - 1));
        query.setMaxResults(customerFeedbackPageDto.getPageSize());
        List<CustomerFeedbackDto> list = query.getResultList();
        Long totalRecord = Long.valueOf(countQuery.getResultList().size());
        QueryUtil.buildPagingDto(customerFeedbackPageDto, totalRecord, list);
    }

    @Transactional
    @Override
    public String changeStatus(Long id) {
        feedbackDao.updateById(FeedbackStatus.DEALED.getKey(), id);
        return "更新成功!";
    }
}
