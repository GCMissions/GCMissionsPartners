package com.hengtiansoft.business.shopStock.zStock.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengtiansoft.business.shopStock.zStock.dto.StockDto;
import com.hengtiansoft.business.shopStock.zStock.dto.StockRecordDto;
import com.hengtiansoft.business.shopStock.zStock.dto.StockSearchDto;
import com.hengtiansoft.business.shopStock.zStock.service.DistributionService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.util.ExcleUtil;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.wrw.dao.SOrgDao;
import com.hengtiansoft.wrw.dao.SStockDao;
import com.hengtiansoft.wrw.dao.SUserDao;
import com.hengtiansoft.wrw.entity.SUserEntity;
import com.hengtiansoft.wrw.enums.StockRecordTypeEmum;
import com.hengtiansoft.wrw.enums.StockState;

@Service
public class DistributionServiceImpl implements DistributionService {

    @Autowired
    private SStockDao stockDao;

    @Autowired
    private SUserDao sUserDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private SOrgDao sOrgDao;


    @SuppressWarnings("unchecked")
    @Override
    public void searchStock(StockSearchDto infoDto) {
        Query query = null;
        Query countQuery = null;
        Long orgId = 0L;
        if (infoDto.getOrgCode() != null) {
            orgId = sOrgDao.findOrgIdByOrgCode(infoDto.getOrgCode());
        } else {
            Long useId = AuthorityContext.getCurrentUser().getUserId();
            SUserEntity user = sUserDao.findOne(useId);
            orgId = user.getOrgId();
        }

        StringBuilder sqlDataBuilder = new StringBuilder();
        StringBuilder countDataBuilder = new StringBuilder();
        sqlDataBuilder
                .append("select s.STOCK_ID,g.GOOD_NAME,g.GOOD_CODE,g.PRICE,g.CREATE_DATE,s.STOCK_NUM,s.SAFE_NUM from S_STOCK s  "
                        + "left join P_GOODS g on g.GOODS_ID =s.GOODS_ID "
                        + " left join S_ORG o on o.ORG_ID =s.ORG_ID " + "where  o.ORG_TYPE = '")
                .append(infoDto.getOrgType()).append("' ").append("and s.ORG_ID = ").append(orgId);

        countDataBuilder.append("select count(1) from (").append(sqlDataBuilder);

        StringBuffer conditionBuffer = new StringBuffer();
        // 物料名称
        if (!StringUtils.isEmpty(infoDto.getName())) {
            conditionBuffer.append(" and g.GOOD_NAME like '%").append(infoDto.getName()).append("%' ");
        }
        // 物料编码
        if (!StringUtils.isEmpty(infoDto.getSn())) {
            conditionBuffer.append(" and g.GOOD_CODE = '").append(infoDto.getSn()).append("'");
        }
        // 预警列表
        if ("T".equals(infoDto.getIsWarning())) {
            infoDto.setStatus("1");
        }

        if (!StringUtils.isEmpty(infoDto.getStockLow()) || !StringUtils.isEmpty(infoDto.getStockHigh())
                || !StringUtils.isEmpty(infoDto.getStatus())) {
            conditionBuffer
                    .append(" group by s.STOCK_ID,g.GOOD_NAME,g.GOOD_CODE,g.PRICE,g.CREATE_DATE,s.STOCK_NUM,s.SAFE_NUM  ");
        }
        // 库存状态正常0
        if ("0".equals(infoDto.getStatus())) {
            if (conditionBuffer.toString().contains("having")) {
                conditionBuffer.append("  and s.STOCK_NUM >= s.SAFE_NUM");
            } else {
                conditionBuffer.append("  having s.STOCK_NUM >= s.SAFE_NUM");
            }
        }
        // 库存状态预警1
        if ("1".equals(infoDto.getStatus())) {
            if (conditionBuffer.toString().contains("having")) {
                conditionBuffer.append("  and s.STOCK_NUM < s.SAFE_NUM");
            } else {
                conditionBuffer.append("  having s.STOCK_NUM < s.SAFE_NUM");
            }
        }
        // 库存总量low
        if (!StringUtils.isEmpty(infoDto.getStockLow())) {
            if (conditionBuffer.toString().contains("having")) {
                conditionBuffer.append("  and s.STOCK_NUM >= ").append(infoDto.getStockLow());
            } else {
                conditionBuffer.append("  having s.STOCK_NUM >= ").append(infoDto.getStockLow());
            }
        }
        // 库存总量high
        if (!StringUtils.isEmpty(infoDto.getStockHigh())) {
            if (conditionBuffer.toString().contains("having")) {
                conditionBuffer.append("   and s.STOCK_NUM <= ").append(infoDto.getStockHigh());
            } else {
                conditionBuffer.append("   having s.STOCK_NUM <= ").append(infoDto.getStockHigh());
            }
        }
        if (StringUtils.isEmpty(infoDto.getStockLow()) && StringUtils.isEmpty(infoDto.getStockHigh())
                && StringUtils.isEmpty(infoDto.getStatus())) {
            conditionBuffer
                    .append(" group by s.STOCK_ID,g.GOOD_NAME,g.GOOD_CODE,g.PRICE,g.CREATE_DATE,s.STOCK_NUM,s.SAFE_NUM ) A");
            query = entityManager.createNativeQuery(sqlDataBuilder.append(conditionBuffer)
                    .deleteCharAt(sqlDataBuilder.length() - 1).deleteCharAt(sqlDataBuilder.length() - 2).toString());
            countQuery = entityManager.createNativeQuery(countDataBuilder.append(conditionBuffer).toString());
        } else {
            query = entityManager.createNativeQuery(sqlDataBuilder.append(conditionBuffer).toString());
            countQuery = entityManager.createNativeQuery(countDataBuilder.append(conditionBuffer).append(" ) A")
                    .toString());
        }
        BigInteger totalRecord = (BigInteger) countQuery.getSingleResult();
        query.setFirstResult(infoDto.getPageSize() * (infoDto.getCurrentPage() - 1));
        query.setMaxResults(infoDto.getPageSize());
        infoDto.setTotalRecord(totalRecord.longValue());
        infoDto.setTotalPages(totalRecord.intValue() % infoDto.getPageSize() == 0 ? totalRecord.intValue()
                / infoDto.getPageSize() : ((totalRecord.intValue() / infoDto.getPageSize()) + 1));
        infoDto.setList(buildStockTotalDtos(query.getResultList()));

    }

    private List<StockDto> buildStockTotalDtos(List<Object[]> list) {
        List<StockDto> detailDtos = new ArrayList<StockDto>();
        for (Object[] array : list) {
            StockDto dto = new StockDto();
            // s.STOCK_ID,g.GOOD_NAME,g.GOOD_CODE,g.PRICE,g.CREATE_DATE,s.STOCK_NUM,s.SAFE_NUM
            dto.setId(Integer.valueOf(array[0].toString()));

            dto.setName(null != array[1] ? array[1].toString() : "");
            dto.setGoodCode(null != array[2] ? array[2].toString() : "");
            dto.setPrice(null != array[3] ? array[3].toString() : "0");
            dto.setCreateDate(null != array[4] ? array[4].toString() : "");
            dto.setStockNum(null != array[5] ? Integer.valueOf(array[5].toString()) : 0);
            dto.setSafeNum(null != array[6] ? Integer.valueOf(array[6].toString()) : 0);

            if (Integer.valueOf(String.valueOf(array[5])) < Integer.valueOf(String.valueOf(array[6]))) {
                dto.setStatus(StockState.getTextByCode("1"));
            } else {
                dto.setStatus(StockState.getTextByCode("0"));
            }

            dto.setPriceYuan(WRWUtil.transFenToYuan2String(Long.parseLong(dto.getPrice())));
            detailDtos.add(dto);
        }

        return detailDtos;
    }

    @SuppressWarnings("unchecked")
    @Override
    public StockDto findStock(Long id) {
        StringBuilder sqlDataBuilder = new StringBuilder();
        sqlDataBuilder.append(
                "select s.STOCK_ID,g.GOOD_NAME,g.GOOD_CODE,g.PRICE,g.CREATE_DATE,s.STOCK_NUM newNum,s.SAFE_NUM  from S_STOCK s "
                        + " left join P_GOODS g on g.GOODS_ID = s.GOODS_ID  "
                        + " left join S_ORG o on o.ORG_ID =s.ORG_ID " + "where  s.STOCK_ID = ").append(id);
        Query query = entityManager.createNativeQuery(sqlDataBuilder.toString());
        List<Object[]> list = query.getResultList();
        StockDto stockDto = new StockDto();
        if (list.size() > 0) {
            stockDto.setId(Integer.valueOf(list.get(0)[0].toString()));
            stockDto.setName(list.get(0)[1] == null ? "" : list.get(0)[1].toString());
            stockDto.setGoodCode(list.get(0)[2] == null ? "" : list.get(0)[2].toString());
            stockDto.setPrice(list.get(0)[3] == null ? "" : WRWUtil.transFenToYuan2String(Long.parseLong(list.get(0)[3]
                    .toString())));
            stockDto.setCreateDate(list.get(0)[4] == null ? "" : list.get(0)[4].toString());
            stockDto.setStockNum(Integer.valueOf(list.get(0)[5].toString()));
            stockDto.setSafeNum(Integer.valueOf(list.get(0)[6].toString()));
        }
        return stockDto;
    }

    @Override
    public StockRecordDto findStockRecord(StockRecordDto dto) {
        List<StockRecordDto> stockRecordDtos = new ArrayList<StockRecordDto>();
        StringBuilder sqlDataBuilder = new StringBuilder();
        sqlDataBuilder.append("select * from  S_STOCK_RECORD where STOCK_ID = ").append(dto.getStockId())
                .append(" order by OPER_DATE desc");
        String countSql = "select count(1) from S_STOCK_RECORD WHERE STOCK_ID = " + dto.getStockId();
        Query countQuery = entityManager.createNativeQuery(countSql);
        Query query = entityManager.createNativeQuery(sqlDataBuilder.toString());
        BigInteger totalRecord = (BigInteger) countQuery.getSingleResult();
        query.setFirstResult(dto.getPageSize() * (dto.getCurrentPage() - 1));
        query.setMaxResults(dto.getPageSize());
        dto.setTotalRecord(totalRecord.longValue());
        dto.setTotalPages(totalRecord.intValue() % dto.getPageSize() == 0 ? totalRecord.intValue() / dto.getPageSize()
                : ((totalRecord.intValue() / dto.getPageSize()) + 1));
        @SuppressWarnings("unchecked")
        List<Object[]> list = query.getResultList();
        if (null != list && list.size() > 0) {
            for (Object[] array : list) {
                StockRecordDto stockRecordDto = new StockRecordDto();
                stockRecordDto.setOperDate(null != array[5] ? array[5].toString().substring(0,
                        array[5].toString().length() - 2) : "");
                if (StockRecordTypeEmum.AUTOREDUCE.getCode().equals(array[2].toString())) {
                    stockRecordDto.setOperType(StockRecordTypeEmum.AUTOREDUCE.getText());
                    stockRecordDto.setChangeNum(-Integer.valueOf(array[3].toString()));
                }
                if (StockRecordTypeEmum.AUTOADD.getCode().equals(array[2].toString())) {
                    stockRecordDto.setOperType(StockRecordTypeEmum.AUTOADD.getText());
                    stockRecordDto.setChangeNum(Integer.valueOf(array[3].toString()));
                }
                if (StockRecordTypeEmum.ADD.getCode().equals(array[2].toString())) {
                    stockRecordDto.setOperType(StockRecordTypeEmum.ADD.getText());
                    stockRecordDto.setChangeNum(Integer.valueOf(array[3].toString()));
                }
                if (StockRecordTypeEmum.REDUCE.getCode().equals(array[2].toString())) {
                    stockRecordDto.setOperType(StockRecordTypeEmum.REDUCE.getText());
                    stockRecordDto.setChangeNum(-Integer.valueOf(array[3].toString()));
                }
                stockRecordDtos.add(stockRecordDto);
            }
        }
        dto.setList(stockRecordDtos);
        return dto;
    }

    @Override
    public Integer getWarnStockCount(String orgType) {
        Long useId = AuthorityContext.getCurrentUser().getUserId();
        SUserEntity user = sUserDao.findOne(useId);
        return stockDao.getZzarnStockCount(orgType, user.getOrgId());
    }

    @Override
    public HSSFWorkbook toExcel(StockSearchDto searchDto) {
        ArrayList<String> fieldName = new ArrayList<String>();
        ArrayList<String> sheetName = new ArrayList<String>();
        ArrayList<String> fieldStyle = new ArrayList<String>();
        List<List<Object>> fieldData = new ArrayList<List<Object>>();
        fieldName.add(0, "序号");
        fieldName.add(1, "时间");
        fieldName.add(2, "商品名称");
        fieldName.add(3, "商容量");
        fieldName.add(4, "当前库存数量");
        fieldName.add(5, "补货数量");

        for (int j = 0; j < fieldName.size(); j++) {
            fieldStyle.add(j, "6000");
        }

        searchDto.setPageSize(Integer.MAX_VALUE);
        searchStock(searchDto);
        StockSearchDto stockDto = searchDto;
        if (stockDto.getList().size() > 0) {
            int i = 0;
            for (StockDto dto : stockDto.getList()) {
                List<Object> rowData = new ArrayList<Object>();
                rowData.add(0, i + 1);
                rowData.add(1, dto.getCreateDate());
                rowData.add(2, null);
                rowData.add(3, "");
                rowData.add(4, dto.getStockNum());
                rowData.add(5, "");
                i++;
                fieldData.add(rowData);
            }
        } else {
            List<Object> rowData = new ArrayList<Object>();
            fieldData.add(rowData);
        }
        sheetName.add(0, "商家安全库存预警");
        ExcleUtil ex = new ExcleUtil(fieldName, sheetName, fieldData, fieldStyle);
        return ex.createWorkbook();
    }
}
