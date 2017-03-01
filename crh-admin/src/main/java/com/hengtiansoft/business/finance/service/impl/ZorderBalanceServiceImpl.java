package com.hengtiansoft.business.finance.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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

import com.hengtiansoft.business.common.util.QueryUtil;
import com.hengtiansoft.business.finance.dto.SOrderBalanceDetailDto;
import com.hengtiansoft.business.finance.dto.SOrderBalanceDto;
import com.hengtiansoft.business.finance.dto.SOrderBalanceMailDto;
import com.hengtiansoft.business.finance.dto.SOrderBalancePageDto;
import com.hengtiansoft.business.finance.service.ZorderBalanceService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.converter.ConverterService;
import com.hengtiansoft.common.util.DateTimeUtil;
import com.hengtiansoft.common.util.ExcleUtil;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.wrw.dao.SOrderBalanceDao;
import com.hengtiansoft.wrw.dao.SOrderRewardDao;
import com.hengtiansoft.wrw.dao.SOrgDao;
import com.hengtiansoft.wrw.dao.SUserDao;
import com.hengtiansoft.wrw.entity.SOrderBalanceEntity;
import com.hengtiansoft.wrw.entity.SUserEntity;
import com.hengtiansoft.wrw.enums.CouponState;
import com.hengtiansoft.wrw.enums.OrgTypeEnum;
import com.hengtiansoft.wrw.enums.RewardOrderTypeEnum;

/**
 * Class Name: SOrderBalanceServiceImpl Description: 财务报表
 * 
 * @author chenghongtu
 */
@Service
public class ZorderBalanceServiceImpl implements ZorderBalanceService {

    private static final Date BEGIN_TIME = DateTimeUtil.getDateBegin("2010-01-01");

    private static final Date END_TIME   = DateTimeUtil.getDateEnd("2099-01-01");

    @Autowired
    private SUserDao          sUserDao;

    @Autowired
    private SOrderBalanceDao  sOrderBalanceDao;

    @Autowired
    private SOrderRewardDao   sOrderRewardDao;

    @PersistenceContext
    private EntityManager     entityManager;

    @Autowired
    private SOrgDao           sOrgDao;

    @Override
    public SOrderBalancePageDto findAll(final SOrderBalancePageDto dto) {

        Long useId = AuthorityContext.getCurrentUser().getUserId();
        SUserEntity user = sUserDao.findOne(useId);
        dto.setOrgId(user.getOrgId().toString());
        StringBuilder sqlCountDataBuilder = new StringBuilder(); // 统计总金额
        final StringBuffer conditionBuffer = new StringBuffer(); // 公用条件condition
        final Map<String, Object> paramMap = new HashMap<String, Object>();// 参数
        paramMap.put("orgId", dto.getOrgId());
        final List<Long> orgIdList = new ArrayList<Long>();
        sqlCountDataBuilder.append("SELECT SUM(O.TOTAL_AMOUNT),SUM(O.ACTUAL_AMOUNT),SUM(O.COUPON_AMOUNT),"
                + "SUM(O.P_SHIP_PROFIT),SUM(O.P_PROD_PROFIT),SUM(O.Z_SHIP_PROFIT)," + "SUM(O.Z_PROD_PROFIT),SUM(O.SHIP_PROFIT),SUM(O.PROD_PROFIT) "
                + "FROM S_ORDER_BALANCE O " + "WHERE 1=1 ");
        if (OrgTypeEnum.P.getKey().equals(dto.getOrgType())) {
            List<Long> idlist = sOrgDao.getOrgIdByPId(Long.valueOf(dto.getOrgId().trim()));
            for (Long id : idlist) {
                orgIdList.add(id);
            }
            sqlCountDataBuilder.append(" AND O.ORG_ID IN (SELECT ORG_ID FROM S_ORG WHERE PARENT_ID = :orgId) ");
        }
        PageRequest pageable = new PageRequest(dto.getCurrentPage() - 1, dto.getPageSize(), new Sort(Direction.DESC, "createDate"));
        Page<SOrderBalanceEntity> page = sOrderBalanceDao.findAll(new Specification<SOrderBalanceEntity>() {

            @Override
            public Predicate toPredicate(Root<SOrderBalanceEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();

                if (OrgTypeEnum.P.getKey().equals(dto.getOrgType())) {
                    predicates.add(cb.or(cb.equal(root.<String> get("orgId"), dto.getOrgId()), root.<Long> get("orgId").in(orgIdList)));
                    conditionBuffer.append(" AND O.ORG_ID in :orgIdList ");
                    paramMap.put("orgIdList", orgIdList);
                } else {
                    predicates.add(cb.equal(root.<String> get("orgId"), dto.getOrgId()));
                    conditionBuffer.append(" AND O.ORG_ID = :orgId ");
                    paramMap.put("orgId", dto.getOrgId());
                }

                if (!WRWUtil.isEmpty(dto.getOrderId())) {
                    predicates.add(cb.equal(root.<String> get("orderId"), dto.getOrderId()));
                    conditionBuffer.append(" AND O.ORDER_ID = :orderId ");
                    paramMap.put("orderId", dto.getOrderId());
                }
                //
                // if (!WLYUtil.isEmpty(dto.getBalanceType())) {
                // List<String> shipmentTypes = new ArrayList<String>();
                // if (dto.getBalanceType().equals(BalanceTypeEnum.mail.getKey())) {
                // shipmentTypes.add(ShipmentType.express.getKey());
                // } else {
                // shipmentTypes.add(ShipmentType.delivery.getKey());
                // shipmentTypes.add(ShipmentType.oneself.getKey());
                // }
                // predicates.add(root.<String> get("shipmentType").in(shipmentTypes));
                // conditionBuffer.append(" AND O.SHIPMENT_TYPE IN (:shipmentType) ");
                // paramMap.put("shipmentType", shipmentTypes);
                // }

                if (WRWUtil.isEmpty(dto.getsDate())) {
                    if (!WRWUtil.isEmpty(dto.geteDate())) {
                        Predicate p1 = cb.lessThanOrEqualTo(root.<Date> get("createDate"), DateTimeUtil.getDateEnd(dto.geteDate()));
                        predicates.add(p1);
                        conditionBuffer.append(" AND O.CREATE_DATE <= :createDate ");
                        paramMap.put("createDate", DateTimeUtil.getDateEnd(dto.geteDate()));
                    }

                }

                if (!WRWUtil.isEmpty(dto.getsDate())) {
                    if (WRWUtil.isEmpty(dto.geteDate())) {
                        Predicate p2 = cb.greaterThanOrEqualTo(root.<Date> get("createDate"), DateTimeUtil.getDateBegin(dto.getsDate()));
                        predicates.add(p2);
                        conditionBuffer.append(" AND O.CREATE_DATE >= :createDate ");
                        paramMap.put("createDate", DateTimeUtil.getDateBegin(dto.getsDate()));
                    } else {
                        Predicate p3 = cb.between(root.<Date> get("createDate"), DateTimeUtil.getDateBegin(dto.getsDate()),
                                DateTimeUtil.getDateEnd(dto.geteDate()));
                        predicates.add(p3);
                        conditionBuffer.append(" AND O.CREATE_DATE BETWEEN :sDate AND :eDate ");
                        paramMap.put("sDate", DateTimeUtil.getDateBegin(dto.getsDate()));
                        paramMap.put("eDate", DateTimeUtil.getDateEnd(dto.geteDate()));
                    }
                }

                Predicate predicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                query.where(predicate);
                return query.getRestriction();
            }
        }, pageable);
        dto.setTotalRecord(page.getTotalElements());
        dto.setTotalPages(page.getTotalPages());
        List<SOrderBalanceMailDto> content = null;
        if (null != page.getContent()) {
            content = new ArrayList<SOrderBalanceMailDto>();
            for (SOrderBalanceEntity entity : page.getContent()) {
                SOrderBalanceMailDto mailDto = new SOrderBalanceMailDto();
                Long prodTotalProfit = entity.getpProdProfit() + entity.getzProdProfit() + entity.getProdProfit();
                mailDto = ConverterService.convert(entity, SOrderBalanceMailDto.class);
                mailDto.setProdTotalProfit(prodTotalProfit);
                content.add(mailDto);
            }
        }
        dto.setList(content);
        //
        Query query = entityManager.createNativeQuery(sqlCountDataBuilder.append(conditionBuffer).toString());
        QueryUtil.processParamForQuery(query, paramMap);
        Object[] countData = (Object[]) query.getSingleResult();
        if (null != countData && countData.length > 0) {
            if (null == countData[0]) {
                dto.setTotalAmount(0L);
            } else if (null != countData[0]) {
                dto.setTotalAmount(Long.valueOf(countData[0].toString()));
            }
            if (null == countData[1]) {
                dto.setActualAmount(0L);
            } else if (null != countData[1]) {
                dto.setActualAmount(Long.valueOf(countData[1].toString()));
            }
            if (null == countData[2]) {
                dto.setCouponAmount(0L);
            } else if (null != countData[2]) {
                dto.setCouponAmount(Long.valueOf(countData[2].toString()));
            }
            if (OrgTypeEnum.P.getKey().equals(dto.getOrgType())) {
                Long pTotalSProfit = 0L;
                Long pTotalPProfit = 0L;
                if (null != countData[3]) {
                    pTotalSProfit = Long.valueOf(countData[3].toString());
                }
                if (null != countData[4]) {
                    pTotalPProfit = Long.valueOf(countData[4].toString());
                }
                dto.setpTotalProProfit(pTotalPProfit);
                dto.setpTotalShipProfit(pTotalSProfit);
                dto.setpTotalSPProfit(pTotalPProfit + pTotalSProfit);
            }

            Long zTotalSProfit = 0L;
            Long zTotalPProfit = 0L;

            if (null != countData[5]) {
                zTotalSProfit = Long.valueOf(countData[5].toString());
            }
            if (null != countData[6]) {
                zTotalPProfit = Long.valueOf(countData[6].toString());
            }

            dto.setzTotalShipProfit(zTotalSProfit);
            dto.setzTotalProProfit(zTotalPProfit);
            dto.setzTotalSPProfit(zTotalSProfit + zTotalPProfit);

        }
        // 统计奖罚总量
        dto.setRewardTotal(sOrderRewardDao.sumAmountByOrgIdAndType(user.getOrgId(), BEGIN_TIME, END_TIME, RewardOrderTypeEnum.REWARD.getKey()));
        dto.setFineTotal(sOrderRewardDao.sumAmountByOrgIdAndType(user.getOrgId(), BEGIN_TIME, END_TIME, RewardOrderTypeEnum.TAKE_TIMEOUT.getKey()));

        return dto;
    }

    @Override
    public HSSFWorkbook toExcle(SOrderBalancePageDto dto) {
        ArrayList<String> fieldName = new ArrayList<String>();
        ArrayList<String> sheetName = new ArrayList<String>();
        ArrayList<String> fieldStyle = new ArrayList<String>();
        List<List<Object>> fieldData = new ArrayList<List<Object>>();
        fieldName.add(0, "序号");
        fieldName.add(1, "订单编号");
        fieldName.add(2, "下单时间");
        fieldName.add(3, "订单金额(元)");
        fieldName.add(4, "配送费分利(元)");
        fieldName.add(5, "商品分利(元)");
        for (int j = 0; j <= fieldName.size(); j++) {
            fieldStyle.add(j, "6000");
        }
        dto.setPageSize(65536);
        SOrderBalancePageDto infoDto = findAll(dto);
        infoDto.getList();
        if (infoDto.getList().size() > 0) {
            for (int i = 0; i < infoDto.getList().size(); i++) {
                SOrderBalanceMailDto entity = infoDto.getList().get(i);
                List<Object> rowData = new ArrayList<Object>();
                rowData.add(0, i + 1);
                rowData.add(1, entity.getOrderId());
                rowData.add(2, entity.getCreateDate());
                rowData.add(3, "￥" + formatNumber2Str(entity.getTotalAmount()));
                if (OrgTypeEnum.P.getKey().equals(dto.getOrgType())) {
                    rowData.add(4, "￥" + formatNumber2Str(entity.getpShipProfit()));
                    rowData.add(5, "￥" + formatNumber2Str(entity.getpProdProfit()));
                } else {
                    rowData.add(4, "￥" + formatNumber2Str(entity.getzShipProfit()));
                    rowData.add(5, "￥" + formatNumber2Str(entity.getzProdProfit()));
                }
                fieldData.add(rowData);
            }
        } else {
            List<Object> rowData = new ArrayList<Object>();
            fieldData.add(rowData);
        }
        List<Object> totalData = new ArrayList<Object>();
        totalData.add(0, "订单总额:￥" + formatNumber2Str(infoDto.getTotalAmount()) + "元");
        if (OrgTypeEnum.P.getKey().equals(dto.getOrgType())) {
            totalData.add(1, "配送费总分利:￥" + formatNumber2Str(infoDto.getpTotalShipProfit()) + "元");
            totalData.add(2, "商品总分利:￥" + formatNumber2Str(infoDto.getpTotalProProfit()) + "元");
            totalData.add(3, "合计分利:￥" + formatNumber2Str(infoDto.getpTotalSPProfit()) + "元");
            sheetName.add(0, "区域平台商报表");
        } else {
            totalData.add(1, "配送费总分利:￥" + formatNumber2Str(infoDto.getzTotalShipProfit()) + "元");
            totalData.add(2, "商品总分利:￥" + formatNumber2Str(infoDto.getzTotalProProfit()) + "元");
            totalData.add(3, "合计分利:￥" + formatNumber2Str(infoDto.getzTotalSPProfit()) + "元");
            sheetName.add(0, "终端配送商报表");
        }
        fieldData.add(totalData);

        ExcleUtil ex = new ExcleUtil(fieldName, sheetName, fieldData, fieldStyle);
        return ex.createWorkbook();
    }

    @SuppressWarnings("unchecked")
    @Override
    public SOrderBalanceDto getOrderDetail(String orderId) {
        SOrderBalanceDto dto = new SOrderBalanceDto();
        dto.setOrderId(orderId);
        StringBuilder sqlDataBuilder = new StringBuilder(); //
        sqlDataBuilder.append("SELECT DISTINCT(O.PRODUCT_ID),P.PRODUCT_CODE,P.PRODUCT_NAME,"
                + "B.BRAND_NAME,T.CATE_NAME,O.NUM,O.PRICE,SUM(C.VALUE),P.SPEC_NUM,O.AMOUNT " + " FROM  M_ORDER_DETAIL O "
                + " LEFT JOIN S_COUPON_PRODUCT CP ON O.PRODUCT_ID = CP.PRODUCT_ID " + " LEFT JOIN P_PRODUCT P ON O.PRODUCT_ID = P.PRODUCT_ID"
                + " LEFT JOIN P_BRAND B ON P.BRAND_ID = B.BRAND_ID" + " LEFT JOIN P_CATEGORY T ON P.CATE_ID = T.CATE_ID "
                + " LEFT JOIN M_COUPONS C ON C.COUP_CON_ID = CP.COUPON_ID  and C.ORDER_ID = O.ORDER_ID AND C.STATUS = :status "
                + " WHERE O.ORDER_ID = :orderId " + " GROUP BY O.PRODUCT_ID,O.PRODUCT_NAME,O.NUM,O.PRICE,"
                + " P.PRODUCT_CODE,B.BRAND_NAME,T.CATE_NAME,P.SPEC_NUM,O.AMOUNT");
        Query query = entityManager.createNativeQuery(sqlDataBuilder.toString());
        query.setParameter("orderId", orderId);
        query.setParameter("status", CouponState.USED.getKey());
        List<Object[]> content = query.getResultList();
        List<SOrderBalanceDetailDto> data = null;
        if (null != content) {
            data = new ArrayList<SOrderBalanceDetailDto>();
            for (Object[] arr : content) {
                SOrderBalanceDetailDto detail = new SOrderBalanceDetailDto();
                if (null != arr[1]) {
                    detail.setProductCode(arr[1].toString());
                }
                if (null != arr[2]) {
                    detail.setProductName(arr[2].toString());
                }
                if (null != arr[3]) {
                    detail.setBrandName(arr[3].toString());
                }
                if (null != arr[4]) {
                    detail.setCateName(arr[4].toString());
                }
                if (null != arr[5]) {
                    detail.setNum(Long.valueOf(arr[5].toString()));
                }
                if (null != arr[6]) {
                    detail.setPrice(Long.valueOf(arr[6].toString()));
                }
                if (null != arr[7]) {
                    detail.setCouponAmount(Long.valueOf(arr[7].toString()));
                }
                if (null != arr[8]) {
                    detail.setBottles((Long.valueOf(arr[5].toString())) * (Long.valueOf(arr[8].toString())));
                }
                if (null != arr[9]) {
                    detail.setValue(Long.valueOf(arr[9].toString()));
                }
                data.add(detail);
            }
        }
        dto.setDetail(data);
        return dto;
    }

    public String formatNumber2Str(Long amount) {
        DecimalFormat decimalFormat = new DecimalFormat("##,##0.00");
        amount = amount == null ? 0 : amount;
        return decimalFormat.format(amount.doubleValue() / 100);
    }

}
