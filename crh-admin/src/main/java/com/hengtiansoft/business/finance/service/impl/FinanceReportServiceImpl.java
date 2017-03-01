/*
 * Project Name: wrw-admin
 * File Name: FinanceReportServiceImpl.java
 * Class Name: FinanceReportServiceImpl
 * Copyright 2014 Hengtian Software Inc
 * Licensed under the Hengtiansoft
 * http://www.hengtiansoft.com
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hengtiansoft.business.finance.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hengtiansoft.business.finance.dto.ReportDetailDto;
import com.hengtiansoft.business.finance.dto.ReportDetailSearchDto;
import com.hengtiansoft.business.finance.dto.ReportResutlDto;
import com.hengtiansoft.business.finance.dto.ReportSearchDto;
import com.hengtiansoft.business.finance.service.FinanceReportService;
import com.hengtiansoft.common.converter.ConverterService;
import com.hengtiansoft.common.util.DateTimeUtil;
import com.hengtiansoft.common.util.ExcleUtil;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.wrw.dao.SOrderBalanceDao;
import com.hengtiansoft.wrw.dao.SOrderRewardDao;
import com.hengtiansoft.wrw.dao.SOrgDao;
import com.hengtiansoft.wrw.entity.SOrderBalanceEntity;
import com.hengtiansoft.wrw.entity.SOrgEntity;
import com.hengtiansoft.wrw.enums.OrgTypeEnum;
import com.hengtiansoft.wrw.enums.RewardOrderTypeEnum;

/**
 * Class Name: FinanceReportServiceImpl
 * Description: TODO
 * 
 * @author chengminmiao
 */
@Service
public class FinanceReportServiceImpl implements FinanceReportService {

    private static final DecimalFormat DF = new DecimalFormat("##0.00");

    @Autowired
    private SOrgDao                    sOrgDao;

    @Autowired
    private SOrderBalanceDao           sOrderBalanceDao;

    @Autowired
    private SOrderRewardDao            sOrderRewardDao;

    /**
     * z报表
     */
    @Override
    public void getZFinanceReport(final ReportSearchDto dto) {
        // 取ORG数据
        List<Long> orgIds = new ArrayList<Long>();
        PageRequest pageable = new PageRequest(dto.getCurrentPage() - 1, dto.getPageSize(), new Sort(Direction.ASC, "orgId"));
        Page<SOrgEntity> page = sOrgDao.findAll(new Specification<SOrgEntity>() {
            @Override
            public Predicate toPredicate(Root<SOrgEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                if (!WRWUtil.isEmpty(dto.getOrgType())) {
                    predicates.add(cb.equal(root.<String> get("orgType"), dto.getOrgType()));
                }
                if (!WRWUtil.isEmpty(dto.getOrgName())) {
                    predicates.add(cb.like(root.<String> get("orgName"), "%" + dto.getOrgName() + "%"));
                }
                Predicate predicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                query.where(predicate);
                return query.getRestriction();
            }
        }, pageable);
        dto.setTotalRecord(page.getTotalElements());
        dto.setTotalPages(page.getTotalPages());
        for (SOrgEntity org : page.getContent()) {
            orgIds.add(org.getOrgId());
        }

        Date beginTime = (null == dto.getBeginDate() ? DateTimeUtil.getDateBegin("2016-01-01") : DateTimeUtil.getDayBegin(dto.getBeginDate()));
        Date endTime = (null == dto.getEndDate() ? new Date() : DateTimeUtil.getDayEnd(dto.getEndDate()));

        // 根据取出的数据统计报表
        List<ReportResutlDto> dtos = buildReportResutlDto(sOrderBalanceDao.getZFinReport(orgIds, beginTime, endTime));
        // 取奖罚数据
        buildReportResutlDto(dtos, RewardOrderTypeEnum.INVITE,
                sOrderRewardDao.sumAmountByOrgIdAndType(orgIds, beginTime, endTime, RewardOrderTypeEnum.INVITE.getKey()));
        buildReportResutlDto(dtos, RewardOrderTypeEnum.REWARD,
                sOrderRewardDao.sumAmountByOrgIdAndType(orgIds, beginTime, endTime, RewardOrderTypeEnum.REWARD.getKey()));
        buildReportResutlDto(dtos, RewardOrderTypeEnum.TAKE_TIMEOUT,
                sOrderRewardDao.sumAmountByOrgIdAndType(orgIds, beginTime, endTime, RewardOrderTypeEnum.TAKE_TIMEOUT.getKey()));

        dto.setList(dtos);

        if (WRWUtil.isEmpty(dto.getOrgName())) {
            orgIds = sOrgDao.findOrgIdByType(dto.getOrgType());
        } else {
            orgIds = sOrgDao.findOrgIdByTypeLikeName(dto.getOrgType(), "%" + dto.getOrgName() + "%");
        }
        // 统计总计
        buildSumProfit(dto, orgIds, beginTime, endTime);

        buildSumReward(dto, orgIds, beginTime, endTime);
    }

    /**
     * P报表
     */
    @Override
    public void getPFinanceReport(final ReportSearchDto dto) {
        // 取ORG数据
        List<Long> orgIds = new ArrayList<Long>();
        PageRequest pageable = new PageRequest(dto.getCurrentPage() - 1, dto.getPageSize(), new Sort(Direction.ASC, "orgId"));
        Page<SOrgEntity> page = sOrgDao.findAll(new Specification<SOrgEntity>() {
            @Override
            public Predicate toPredicate(Root<SOrgEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                if (!WRWUtil.isEmpty(dto.getOrgType())) {
                    predicates.add(cb.equal(root.<String> get("orgType"), dto.getOrgType()));
                }
                if (!WRWUtil.isEmpty(dto.getOrgName())) {
                    predicates.add(cb.like(root.<String> get("orgName"), "%" + dto.getOrgName() + "%"));
                }
                Predicate predicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                query.where(predicate);
                return query.getRestriction();
            }
        }, pageable);
        dto.setTotalRecord(page.getTotalElements());
        dto.setTotalPages(page.getTotalPages());
        for (SOrgEntity org : page.getContent()) {
            orgIds.add(org.getOrgId());
        }

        Date beginTime = (null == dto.getBeginDate() ? DateTimeUtil.getDateBegin("2016-01-01") : DateTimeUtil.getDayBegin(dto.getBeginDate()));
        Date endTime = (null == dto.getEndDate() ? new Date() : DateTimeUtil.getDayEnd(dto.getEndDate()));

        // 根据取出的数据统计报表
        List<ReportResutlDto> dtos = buildReportResutlDto(sOrderBalanceDao.getPFinReport(orgIds, beginTime, endTime));
        // 取奖罚数据
        buildReportResutlDto(dtos, RewardOrderTypeEnum.INVITE,
                sOrderRewardDao.sumAmountByOrgIdAndType(orgIds, beginTime, endTime, RewardOrderTypeEnum.INVITE.getKey()));
        dto.setList(dtos);

        // 统计总计
        if (WRWUtil.isEmpty(dto.getOrgName())) {
            orgIds = sOrgDao.findOrgIdByType(dto.getOrgType());
        } else {
            orgIds = sOrgDao.findOrgIdByTypeLikeName(dto.getOrgType(), "%" + dto.getOrgName() + "%");
        }
        orgIds = sOrgDao.findOrgIdByParentId(orgIds);

        buildSumProfit(dto, orgIds, beginTime, endTime);

        buildSumReward(dto, orgIds, beginTime, endTime);
    }

    private List<ReportResutlDto> buildReportResutlDto(List<Object[]> finReport) {
        List<ReportResutlDto> dtos = new ArrayList<ReportResutlDto>();
        for (Object[] obj : finReport) {
            ReportResutlDto dto = new ReportResutlDto();
            dto.setOrgId(new Long(obj[0].toString()));
            dto.setOrgName(obj[1].toString());
            dto.setShipProfit(new Long(obj[2].toString()));
            dto.setProdProfit(new Long(obj[3].toString()));
            dtos.add(dto);
        }
        return dtos;
    }

    private void buildReportResutlDto(List<ReportResutlDto> dtos, RewardOrderTypeEnum reward, List<BigDecimal> list) {
        if (reward.equals(RewardOrderTypeEnum.REWARD)) {
            for (int i = 0; i < dtos.size(); i++) {
                dtos.get(i).setRewardTotal(list.get(i).longValue());
                dtos.get(i).setOrgType(OrgTypeEnum.Z.getValue());
            }
        } else if (reward.equals(RewardOrderTypeEnum.TAKE_TIMEOUT)) {
            for (int i = 0; i < dtos.size(); i++) {
                dtos.get(i).setFineTotal(list.get(i).longValue());
            }
        } else if (reward.equals(RewardOrderTypeEnum.INVITE)) {
            for (int i = 0; i < dtos.size(); i++) {
                dtos.get(i).setRefereeTotal(list.get(i).longValue());
                dtos.get(i).setOrgType(OrgTypeEnum.P.getValue());
            }
        }
    }

    private void buildSumReward(ReportSearchDto dto, List<Long> orgIds, Date beginTime, Date endTime) {
        Object[] objects = sOrderBalanceDao.sumProfit(orgIds, beginTime, endTime);
        Object[] object = (Object[]) objects[0];
        dto.setTotalAmount(WRWUtil.objToLong(object[0]));
        dto.setActualAmount(WRWUtil.objToLong(object[1]));
        dto.setCouponAmount(WRWUtil.objToLong(object[2]));
        dto.setTotal1Profit(WRWUtil.objToLong(object[3]));
        dto.setTotalPProfit(WRWUtil.objToLong(object[4]));
        dto.setTotalZProfit(WRWUtil.objToLong(object[5]));

        dto.setTotalProfit(dto.getTotal1Profit() + dto.getTotalPProfit() + dto.getTotalZProfit());
    }

    private void buildSumProfit(ReportSearchDto dto, List<Long> orgIds, Date beginTime, Date endTime) {
        dto.setTotalReferee(0L);
        dto.setTotalReward(0L);
        dto.setTotalFine(0L);
        for (Object[] object : sOrderRewardDao.sumReward(orgIds, beginTime, endTime)) {

            if (object[1].toString().equalsIgnoreCase(RewardOrderTypeEnum.INVITE.getKey())) {
                dto.setTotalReferee(WRWUtil.objToLong(object[0]));
            } else if (object[1].toString().equalsIgnoreCase(RewardOrderTypeEnum.REWARD.getKey())) {
                dto.setTotalReward(new Long(WRWUtil.objToLong(object[0])));

            } else if (object[1].toString().equalsIgnoreCase(RewardOrderTypeEnum.TAKE_TIMEOUT.getKey())) {
                dto.setTotalFine(new Long(WRWUtil.objToLong(object[0])));
            }
        }
    }

    @Override
    public HSSFWorkbook toExcle(ReportSearchDto dto) {
        ArrayList<String> fieldName = new ArrayList<String>();
        ArrayList<String> sheetName = new ArrayList<String>();
        ArrayList<String> fieldStyle = new ArrayList<String>();
        List<List<Object>> fieldData = new ArrayList<List<Object>>();
        fieldName.add(0, "序号");
        fieldName.add(1, "商家ID");
        fieldName.add(2, "商家名称");
        fieldName.add(3, "商家类型");
        fieldName.add(4, "配送分利");
        fieldName.add(5, "商品分利");
        fieldName.add(6, "罚款总额");
        fieldName.add(7, "奖励总额");
        fieldName.add(8, "推广总额");

        for (int j = 0; j <= fieldName.size(); j++) {
            fieldStyle.add(j, "6000");
        }
        dto.setPageSize(65536);

        if (OrgTypeEnum.P.getKey().equalsIgnoreCase(dto.getOrgType())) {
            getPFinanceReport(dto);

        } else if (OrgTypeEnum.Z.getKey().equalsIgnoreCase(dto.getOrgType())) {
            getZFinanceReport(dto);
        }
        if (dto.getList().size() > 0) {
            for (int i = 0; i < dto.getList().size(); i++) {
                ReportResutlDto entity = dto.getList().get(i);
                List<Object> rowData = new ArrayList<Object>();
                rowData.add(0, i + 1);
                rowData.add(1, entity.getOrgId());
                rowData.add(2, entity.getOrgName());
                rowData.add(3, entity.getOrgType());
                rowData.add(4, formatNumberToString(entity.getShipProfit()));
                rowData.add(5, formatNumberToString(entity.getProdProfit()));
                rowData.add(6, formatNumberToString(entity.getFineTotal()));
                rowData.add(7, formatNumberToString(entity.getRewardTotal()));
                rowData.add(8, formatNumberToString(entity.getRefereeTotal()));
                fieldData.add(rowData);
            }
        } else {
            List<Object> rowData = new ArrayList<Object>();
            fieldData.add(rowData);
        }
        List<Object> totalData = new ArrayList<Object>();
        totalData.add(0, "汇总");
        totalData.add(1, "订单总额:" + formatNumberToString(dto.getTotalAmount()));
        totalData.add(2, "实付总额:" + formatNumberToString(dto.getActualAmount()));
        totalData.add(3, "订单总毛利:" + formatNumberToString(dto.getTotalProfit()));
        totalData.add(4, "优惠券总额:" + formatNumberToString(dto.getCouponAmount()));
        if (OrgTypeEnum.P.getKey().equalsIgnoreCase(dto.getOrgType())) {
            totalData.add(5, "P端分利:" + formatNumberToString(dto.getTotalZProfit()));

        } else if (OrgTypeEnum.Z.getKey().equalsIgnoreCase(dto.getOrgType())) {
            totalData.add(5, "Z端分利:" + formatNumberToString(dto.getTotalPProfit()));
        }
        totalData.add(6, "罚款总额:" + formatNumberToString(dto.getTotalFine()));
        totalData.add(7, "奖励总额:" + formatNumberToString(dto.getTotalReward()));
        totalData.add(8, "推广总额:" + formatNumberToString(dto.getTotalReferee()));

        fieldData.add(totalData);

        sheetName.add(0, "财务报表");

        ExcleUtil ex = new ExcleUtil(fieldName, sheetName, fieldData, fieldStyle);
        return ex.createWorkbook();
    }

    public String formatNumberToString(Long amount) {
        return DF.format(amount.doubleValue() / 100);
    }

    @Override
    public void getFinanceDetailReport(final ReportDetailSearchDto dto) {
        final List<Long> orgIds = new ArrayList<Long>();
        if (dto.getOrgType().equalsIgnoreCase(OrgTypeEnum.P.getKey())) {
            orgIds.addAll(sOrgDao.getOrgIdByPId(dto.getOrgId()));
        } else {
            orgIds.add(dto.getOrgId());
        }

        PageRequest pageable = new PageRequest(dto.getCurrentPage() - 1, dto.getPageSize(), new Sort(Direction.DESC, "createDate"));
        Page<SOrderBalanceEntity> page = sOrderBalanceDao.findAll(new Specification<SOrderBalanceEntity>() {
            @Override
            public Predicate toPredicate(Root<SOrderBalanceEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(root.<Long> get("orgId").in(orgIds));
                if (null != dto.getBeginDate()) {
                    predicates.add(cb.greaterThanOrEqualTo(root.<Date> get("createDate"), dto.getBeginDate()));
                }
                if (null != dto.getEndDate()) {
                    predicates.add(cb.lessThanOrEqualTo(root.<Date> get("createDate"), dto.getEndDate()));
                }
                Predicate predicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                query.where(predicate);
                return query.getRestriction();
            }
        }, pageable);
        dto.setTotalRecord(page.getTotalElements());
        dto.setTotalPages(page.getTotalPages());
        dto.setList(buildDetailDtos(page.getContent()));

        Date beginTime = (null == dto.getBeginDate() ? DateTimeUtil.getDateBegin("2016-01-01") : DateTimeUtil.getDayBegin(dto.getBeginDate()));
        Date endTime = (null == dto.getEndDate() ? new Date() : DateTimeUtil.getDayEnd(dto.getEndDate()));
        // 统计总计
        buildSumProfit(dto, orgIds, beginTime, endTime);

        buildSumReward(dto, orgIds, beginTime, endTime);
    }

    private void buildSumReward(ReportDetailSearchDto dto, List<Long> orgIds, Date beginTime, Date endTime) {
        dto.setTotalReferee(0L);
        dto.setTotalReward(0L);
        dto.setTotalFine(0L);
        for (Object[] object : sOrderRewardDao.sumReward(orgIds, beginTime, endTime)) {

            if (object[1].toString().equalsIgnoreCase(RewardOrderTypeEnum.INVITE.getKey())) {
                dto.setTotalReferee(WRWUtil.objToLong(object[0]));
            } else if (object[1].toString().equalsIgnoreCase(RewardOrderTypeEnum.REWARD.getKey())) {
                dto.setTotalReward(new Long(WRWUtil.objToLong(object[0])));

            } else if (object[1].toString().equalsIgnoreCase(RewardOrderTypeEnum.TAKE_TIMEOUT.getKey())) {
                dto.setTotalFine(new Long(WRWUtil.objToLong(object[0])));
            }
        }
    }

    private void buildSumProfit(ReportDetailSearchDto dto, List<Long> orgIds, Date beginTime, Date endTime) {
        dto.setTotalReferee(0L);
        dto.setTotalReward(0L);
        dto.setTotalFine(0L);
        for (Object[] object : sOrderRewardDao.sumReward(orgIds, beginTime, endTime)) {

            if (object[1].toString().equalsIgnoreCase(RewardOrderTypeEnum.INVITE.getKey())) {
                dto.setTotalReferee(WRWUtil.objToLong(object[0]));
            } else if (object[1].toString().equalsIgnoreCase(RewardOrderTypeEnum.REWARD.getKey())) {
                dto.setTotalReward(new Long(WRWUtil.objToLong(object[0])));

            } else if (object[1].toString().equalsIgnoreCase(RewardOrderTypeEnum.TAKE_TIMEOUT.getKey())) {
                dto.setTotalFine(new Long(WRWUtil.objToLong(object[0])));
            }
        }
    }

    private List<ReportDetailDto> buildDetailDtos(List<SOrderBalanceEntity> content) {
        List<ReportDetailDto> dtos = new ArrayList<ReportDetailDto>();
        for (SOrderBalanceEntity entity : content) {
            dtos.add(ConverterService.convert(entity, ReportDetailDto.class));
        }
        return dtos;
    }
}
