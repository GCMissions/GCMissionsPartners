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
import com.hengtiansoft.business.finance.service.SOrderBalanceService;
import com.hengtiansoft.common.converter.ConverterService;
import com.hengtiansoft.common.util.DateTimeUtil;
import com.hengtiansoft.common.util.ExcleUtil;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.wrw.dao.SOrderBalanceDao;
import com.hengtiansoft.wrw.entity.SOrderBalanceEntity;
import com.hengtiansoft.wrw.enums.BalanceTypeEnum;
import com.hengtiansoft.wrw.enums.CouponState;
import com.hengtiansoft.wrw.enums.ShipmentType;

/**
 * 
 * Class Name: SOrderBalanceServiceImpl Description: 财务报表
 * 
 * @author chenghongtu
 *
 */
@Service
public class SOrderBalanceServiceImpl implements SOrderBalanceService {


    @Autowired
    private SOrderBalanceDao sOrderBalanceDao;

    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public SOrderBalancePageDto findAll(final SOrderBalancePageDto dto) {
        StringBuilder sqlCountDataBuilder = new StringBuilder(); // 统计总金额
        final StringBuffer conditionBuffer = new StringBuffer(); // 公用条件condition
        final Map<String, Object> paramMap = new HashMap<String, Object>();// 参数
        sqlCountDataBuilder.append("SELECT SUM(O.TOTAL_AMOUNT),SUM(O.ACTUAL_AMOUNT),SUM(O.COUPON_AMOUNT),"
                + "SUM(O.P_SHIP_PROFIT),SUM(O.P_PROD_PROFIT),SUM(O.Z_SHIP_PROFIT),"
                + "SUM(O.Z_PROD_PROFIT),SUM(O.SHIP_PROFIT),SUM(O.PROD_PROFIT) " + "FROM S_ORDER_BALANCE O "
                + "WHERE 1=1 ");
        PageRequest pageable = new PageRequest(dto.getCurrentPage() - 1, dto.getPageSize(), new Sort(Direction.DESC,
                "createDate"));
        Page<SOrderBalanceEntity> page = sOrderBalanceDao.findAll(new Specification<SOrderBalanceEntity>() {

            @Override
            public Predicate toPredicate(Root<SOrderBalanceEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                if (!WRWUtil.isEmpty(dto.getOrderId())) {
                    predicates.add(cb.equal(root.<String> get("orderId"), dto.getOrderId()));
                    conditionBuffer.append(" AND O.ORDER_ID = :orderId ");
                    paramMap.put("orderId", dto.getOrderId());
                }
                if (!WRWUtil.isEmpty(dto.getBalanceType())) {
                    List<String> shipmentTypes = new ArrayList<String>();
                    if (dto.getBalanceType().equals(BalanceTypeEnum.mail.getKey())) {
                        shipmentTypes.add(ShipmentType.express.getKey());
                    } else {
                        shipmentTypes.add(ShipmentType.delivery.getKey());
                        shipmentTypes.add(ShipmentType.oneself.getKey());
                    }
                    predicates.add(root.<String> get("shipmentType").in(shipmentTypes));
                    conditionBuffer.append(" AND O.SHIPMENT_TYPE IN(:shipmentType) ");
                    paramMap.put("shipmentType", shipmentTypes);
                }

                if (WRWUtil.isEmpty(dto.getsDate())) {
                    if (!WRWUtil.isEmpty(dto.geteDate())) {
                        Predicate p1 = cb.lessThanOrEqualTo(root.<Date> get("createDate"),
                                DateTimeUtil.getDateEnd(dto.geteDate()));
                        predicates.add(p1);
                        conditionBuffer.append(" AND O.CREATE_DATE <= :createDate ");
                        paramMap.put("createDate", DateTimeUtil.getDateEnd(dto.geteDate()));
                    }

                }
                if (!WRWUtil.isEmpty(dto.getsDate())) {
                    if (WRWUtil.isEmpty(dto.geteDate())) {
                        Predicate p2 = cb.greaterThanOrEqualTo(root.<Date> get("createDate"),
                                DateTimeUtil.getDateBegin(dto.getsDate()));
                        predicates.add(p2);
                        conditionBuffer.append(" AND O.CREATE_DATE >= :createDate ");
                        paramMap.put("createDate", DateTimeUtil.getDateBegin(dto.getsDate()));
                    } else {
                        Predicate p3 = cb.between(root.<Date> get("createDate"),
                                DateTimeUtil.getDateBegin(dto.getsDate()), DateTimeUtil.getDateEnd(dto.geteDate()));
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
            Long pTotalSProfit = 0L;
            Long pTotalPProfit = 0L;
            Long zTotalSProfit = 0L;
            Long zTotalPProfit = 0L;
            Long totalSProfit = 0L;
            Long totalPProfit = 0L;
            if (null != countData[3]) {
                pTotalSProfit = Long.valueOf(countData[3].toString());
            }
            if (null != countData[4]) {
                pTotalPProfit = Long.valueOf(countData[4].toString());
            }
            if (null != countData[5]) {
                zTotalSProfit = Long.valueOf(countData[5].toString());
            }
            if (null != countData[6]) {
                zTotalPProfit = Long.valueOf(countData[6].toString());
            }
            if (null != countData[7]) {
                totalSProfit = Long.valueOf(countData[7].toString());
            }
            if (null != countData[8]) {
                totalPProfit = Long.valueOf(countData[8].toString());
            }
            dto.setTotalProfit(pTotalSProfit + pTotalPProfit + zTotalSProfit + zTotalPProfit + totalSProfit
                    + totalPProfit);
            dto.setpTotalSPProfit(pTotalSProfit + pTotalPProfit);
            dto.setzTotalSPProfit(zTotalSProfit + zTotalPProfit);
            dto.setTotalSPProfit(totalPProfit + totalSProfit);
        }
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
        fieldName.add(2, "配送方式");
        fieldName.add(3, "订单金额(元)");
        fieldName.add(4, "优惠券金额(元)");
        
        if (dto.getBalanceType().equals(BalanceTypeEnum.normal.getKey())) {
            fieldName.add(5, "配送费(元)");
            fieldName.add(6, "实付金额(元)");
            fieldName.add(7, "平台配送费分利(元)");
            fieldName.add(8, "平台商品分利(元)");
            fieldName.add(9, "区域平台商配送费分利(元)");
            fieldName.add(10, "区域平台商商品分利(元)");
            fieldName.add(11, "终端配送商配送费分利(元)");
            fieldName.add(12, "终端配送商商品分利(元)");
        } else {
            fieldName.add(5, "邮费(元)");
            fieldName.add(6, "实付金额(元)");
            fieldName.add(7, "商品毛利(元)");
        }

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
                rowData.add(2, ShipmentType.getValue(entity.getShipmentType()));
                rowData.add(3, "￥"+formatNumber2Str(entity.getTotalAmount()));
                rowData.add(4, "￥"+formatNumber2Str(entity.getCouponAmount()));
                rowData.add(5, "￥"+formatNumber2Str(entity.getShipAmount()));
                rowData.add(6, "￥"+formatNumber2Str(entity.getActualAmount()));
                if (dto.getBalanceType().equals(BalanceTypeEnum.normal.getKey())) {
                    rowData.add(7, "￥"+formatNumber2Str(entity.getShipProfit()));
                    rowData.add(8, "￥"+formatNumber2Str(entity.getProdProfit()));
                    rowData.add(9, "￥"+formatNumber2Str(entity.getpShipProfit()));
                    rowData.add(10, "￥"+formatNumber2Str(entity.getpProdProfit()));
                    rowData.add(11, "￥"+formatNumber2Str(entity.getzShipProfit()));
                    rowData.add(12, "￥"+formatNumber2Str(entity.getzProdProfit()));
                } else {
                    rowData.add(7, "￥"+formatNumber2Str(entity.getProdTotalProfit()));

                }
                fieldData.add(rowData);
            }
        } else {
            List<Object> rowData = new ArrayList<Object>();
            fieldData.add(rowData);
        }
        List<Object> totalData = new ArrayList<Object>();
        totalData.add(0, "订单总额:￥" + formatNumber2Str(infoDto.getTotalAmount())+"元");
        totalData.add(1, "实付金额:￥"+formatNumber2Str(infoDto.getActualAmount())+"元");
        totalData.add(2, "优惠劵总金额:￥"+formatNumber2Str(infoDto.getCouponAmount())+"元");
        totalData.add(3, "总毛利:￥"+formatNumber2Str(infoDto.getTotalProfit())+"元");
        if (infoDto.getBalanceType().equals(BalanceTypeEnum.normal.getKey())) {
            totalData.add(4, "平台总分利:￥"+formatNumber2Str(infoDto.getTotalSPProfit())+"元");
            totalData.add(5, "区域平台商总分利:￥"+formatNumber2Str(infoDto.getpTotalSPProfit())+"元");
            totalData.add(6, "终端配送商总分利:￥"+formatNumber2Str(infoDto.getzTotalSPProfit())+"元");
        }
        fieldData.add(totalData);
        if (dto.getBalanceType().equals(BalanceTypeEnum.normal.getKey())) {
            sheetName.add(0, "平台报表");
        } else {
            sheetName.add(0, "平台邮寄");
        }
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
                + "B.BRAND_NAME,T.CATE_NAME,O.NUM,O.PRICE,SUM(C.VALUE),P.SPEC_NUM " + " FROM  M_ORDER_DETAIL O "
                + " LEFT JOIN S_COUPON_PRODUCT CP ON O.PRODUCT_ID = CP.PRODUCT_ID "
                + " LEFT JOIN P_PRODUCT P ON O.PRODUCT_ID = P.PRODUCT_ID"
                + " LEFT JOIN P_BRAND B ON P.BRAND_ID = B.BRAND_ID"
                + " LEFT JOIN P_CATEGORY T ON P.CATE_ID = T.CATE_ID "
                + " LEFT JOIN M_COUPONS C ON CP.COUPON_ID = C.COUP_CON_ID AND O.ORDER_ID = C.ORDER_ID AND C.STATUS = :status "
                + " WHERE O.ORDER_ID = :orderId "
                + " GROUP BY O.PRODUCT_ID,O.PRODUCT_NAME,O.NUM,O.PRICE,"
                + " P.PRODUCT_CODE,B.BRAND_NAME,T.CATE_NAME");
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
                    detail.setNum((Long.valueOf(arr[5].toString()))*(Long.valueOf(arr[8].toString())));
                }
                if (null != arr[6]) {
                    detail.setPrice(Long.valueOf(arr[6].toString()));
                }
                if (null != arr[7]) {
                    detail.setCouponAmount(Long.valueOf(arr[7].toString()));
                }
                data.add(detail);
            }
        }
        dto.setDetail(data);
        return dto;
    }
    
    public String formatNumber2Str(Long amount) {
        DecimalFormat decimalFormat = new DecimalFormat("##,##0.00");
        return decimalFormat.format(amount.doubleValue() / 100);
    }

}
