package com.hengtiansoft.business.shopStock.regionalPlatform.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.hengtiansoft.business.common.util.QueryUtil;
import com.hengtiansoft.business.shopStock.regionalPlatform.dto.TerminalDetailDto;
import com.hengtiansoft.business.shopStock.regionalPlatform.dto.TerminalStockDto;
import com.hengtiansoft.business.shopStock.regionalPlatform.dto.TerminalStockSearchDto;
import com.hengtiansoft.business.shopStock.regionalPlatform.dto.ZSafeStockExcelDto;
import com.hengtiansoft.business.shopStock.regionalPlatform.service.TerminalStockService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.util.DateTimeUtil;
import com.hengtiansoft.common.util.ExcleUtil;
import com.hengtiansoft.wrw.dao.SStockDao;
import com.hengtiansoft.wrw.dao.SUserDao;
import com.hengtiansoft.wrw.entity.SUserEntity;
import com.hengtiansoft.wrw.enums.OrgTypeEnum;
import com.hengtiansoft.wrw.enums.StockState;

/**
 * Class Name: TerminalStockServiceImpl Description: 终端配送商库存查看接口实现
 * 
 * @author fengpan
 */
@Service
public class TerminalStockServiceImpl implements TerminalStockService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private SUserDao      sUserDao;

    @Autowired
    private SStockDao     stockDao;

    /**
     * 区域平台商查看关联终端配送商库存查询方法
     */
    @SuppressWarnings("unchecked")
    @Override
    public void searchDto(TerminalStockSearchDto searchDto) {
        Long useId = AuthorityContext.getCurrentUser().getUserId();
        SUserEntity user = sUserDao.findOne(useId);
        StringBuilder dataSql = new StringBuilder();
        StringBuilder countSql = new StringBuilder();
        StringBuilder conditionSql = new StringBuilder();
        Map<String, Object> param = new HashMap<String, Object>();
        dataSql.append("select SO.ORG_CODE,SO.ORG_NAME,SO.CONTACT,SO.PHONE,"
                + "sum(SS.STOCK_NUM) newNum,SUM(SS.SAFE_NUM) safeNum,SO.CREATE_DATE,SO.ORG_ID from "
                + "S_STOCK SS left join S_ORG SO on SS.ORG_ID = SO.ORG_ID" + " WHERE  SO.ORG_TYPE = '" + OrgTypeEnum.Z.getKey()
                + "' and SO.PARENT_ID = '" + user.getOrgId() + "' and SO.ORG_ID in(select org_id from S_ORG where PARENT_ID = :parentOrgId) ");
        countSql.append("select count(1) from (").append(dataSql);
        if (!StringUtils.isEmpty(searchDto.getOrgCode())) {
            conditionSql.append(" and SO.ORG_CODE like :orgCode");
            param.put("orgCode", "%" + searchDto.getOrgCode() + "%");
        }
        if (!StringUtils.isEmpty(searchDto.getOrgName())) {
            conditionSql.append(" and SO.ORG_NAME = :orgName");
            param.put("orgName", searchDto.getOrgName());
        }
        if (!StringUtils.isEmpty(searchDto.getContactName())) {
            conditionSql.append(" and SO.CONTACT = :contact");
            param.put("orgCode", searchDto.getContactName());
        }
        if (!StringUtils.isEmpty(searchDto.getPhone())) {
            conditionSql.append(" and SO.PHONE like :phone");
            param.put("phone", "%" + searchDto.getPhone() + "%");
        }
        if (!StringUtils.isEmpty(searchDto.getStartTime())) {
            conditionSql.append(" and SO.CREATE_DATE >= :startTime");
            param.put("startTime", DateTimeUtil.getDateBegin(searchDto.getStartTime()));
        }
        if (!StringUtils.isEmpty(searchDto.getEndTime())) {
            conditionSql.append(" and SO.CREATE_DATE <= :endTime");
            param.put("endTime", DateTimeUtil.getDateEnd(searchDto.getEndTime()));
        }
        param.put("parentOrgId", user.getOrgId());
        conditionSql.append(" group by SO.ORG_CODE,SO.ORG_NAME,SO.CONTACT,SO.PHONE,SO.CREATE_DATE,SO.ORG_ID");
        Query query = entityManager.createNativeQuery(dataSql.append(conditionSql).toString());
        QueryUtil.processParamForQuery(query, param);
        Query countQuery = entityManager.createNativeQuery(countSql.append(conditionSql).append(" ) A").toString());
        QueryUtil.processParamForQuery(countQuery, param);
        BigInteger totalRecord = (BigInteger) countQuery.getSingleResult();
        query.setFirstResult(searchDto.getPageSize() * (searchDto.getCurrentPage() - 1));
        query.setMaxResults(searchDto.getPageSize());
        searchDto.setTotalRecord(totalRecord.longValue());
        searchDto.setTotalPages(totalRecord.intValue() % searchDto.getPageSize() == 0 ? totalRecord.intValue() / searchDto.getPageSize()
                : ((totalRecord.intValue() / searchDto.getPageSize()) + 1));
        searchDto.setList(getList(query.getResultList(), searchDto));
    }

    private List<TerminalStockDto> getList(List<Object[]> list, TerminalStockSearchDto searchDto) {
        List<TerminalStockDto> dtos = new ArrayList<TerminalStockDto>();
        List<String> warnOrgs = new ArrayList<String>();
        if (searchDto.getOrgIds() != null && !"".equals(searchDto.getOrgIds())) {
            String[] orgIds = searchDto.getOrgIds().split(",");
            warnOrgs = Arrays.asList(orgIds);
        }
        for (Object[] array : list) {
            String orgId = array[7] == null ? "" : array[7].toString();
            if (!warnOrgs.contains(orgId) && "T".equals(searchDto.getIsWaring())) {
                continue;
            }
            TerminalStockDto dto = new TerminalStockDto();
            dto.setOrgCode(array[0] == null ? "" : array[0].toString());
            dto.setOrgName(array[1] == null ? "" : array[1].toString());
            dto.setContactName(array[2] == null ? "" : array[2].toString());
            dto.setPhone(array[3] == null ? "" : array[3].toString());
            Integer stock = Integer.valueOf(array[4] == null ? "0" : array[4].toString());
            dto.setStockNum(stock);
            dto.setDate(array[6] == null ? "" : array[6].toString());
            if (warnOrgs.contains(orgId)) {
                dto.setStatus(StockState.warning.getText());
            } else {
                dto.setStatus(StockState.normal.getText());
            }
            dtos.add(dto);
        }
        return dtos;
    }

    @SuppressWarnings("unchecked")
    @Override
    public TerminalDetailDto terminalDetail(String orgCode) {
        String sql = "select SO.ORG_CODE,SO.ORG_NAME,SU.USER_NAME,SO.CONTACT,"
                + "SO.PHONE,SR.NAME,SO.ADDRESS,SO.LONGITUDE,SO.LATITUDE from S_ORG SO left join S_USER SU on SO.ORG_ID = SU.ORG_ID "
                + "left join S_REGION SR on SO.REGION = SR.ID where SO.ORG_CODE = '" + orgCode + "'";
        Query query = entityManager.createNativeQuery(sql);
        List<Object[]> list = query.getResultList();
        TerminalDetailDto dto = new TerminalDetailDto();
        dto.setOrgCode(list.get(0)[0] == null ? "" : list.get(0)[0].toString());
        dto.setOrgName(list.get(0)[1] == null ? "" : list.get(0)[1].toString());
        dto.setLoginName(list.get(0)[2] == null ? "" : list.get(0)[2].toString());
        dto.setContactName(list.get(0)[3] == null ? "" : list.get(0)[3].toString());
        dto.setPhone(list.get(0)[4] == null ? "" : list.get(0)[4].toString());
        dto.setRegion(list.get(0)[5] == null ? "" : list.get(0)[5].toString());
        dto.setAddress(list.get(0)[6] == null ? "" : list.get(0)[6].toString());
        dto.setLongitude(list.get(0)[7] == null ? "" : list.get(0)[7].toString());
        dto.setLatitude(list.get(0)[8] == null ? "" : list.get(0)[8].toString());
        return dto;
    }

    @Override
    public List<Integer> getWarnZStock(String orgType) {
        Long useId = AuthorityContext.getCurrentUser().getUserId();
        SUserEntity user = sUserDao.findOne(useId);
        return stockDao.getZWarnStockCountByP(orgType, user.getOrgId());
    }

    @SuppressWarnings("unchecked")
    private List<ZSafeStockExcelDto> findAll() {

        Long userId = AuthorityContext.getCurrentUser().getUserId();
        SUserEntity curUser = sUserDao.findOne(userId);

        Assert.notNull(curUser);

        Map<String, Object> param = new HashMap<String, Object>();

        StringBuilder sqlSb = new StringBuilder();
        sqlSb.append("SELECT date(now()), O.ORG_NAME, G.GOOD_NAME, G.SPECS, S.STOCK_NUM, S.STANDARD_NUM - S.STOCK_NUM "
                + "FROM S_STOCK S INNER JOIN P_GOODS G ON S.GOODS_ID = G.GOODS_ID AND S.STOCK_NUM < S.SAFE_NUM "
                + "INNER JOIN S_ORG O ON S.ORG_ID = O.ORG_ID AND O.ORG_TYPE = 'Z' ");
        if (curUser.getOrgId() != null) {
            sqlSb.append(" AND O.PARENT_ID = :orgId ");
            param.put("orgId", curUser.getOrgId());
        }
        sqlSb.append(" WHERE 1=1 ");

        Query query = entityManager.createNativeQuery(sqlSb.toString());
        QueryUtil.processParamForQuery(query, param);

        List<Object[]> safeStockList = query.getResultList();

        List<ZSafeStockExcelDto> list = new ArrayList<ZSafeStockExcelDto>();

        for (Object[] obj : safeStockList) {
            ZSafeStockExcelDto dto = new ZSafeStockExcelDto();
            dto.setCreateDate(obj[0] != null ? obj[0].toString() : "");
            dto.setOrgName(obj[1] != null ? obj[1].toString() : "");
            dto.setGoodName(obj[2] != null ? obj[2].toString() : "");
            dto.setSpecs(obj[3] != null ? obj[3].toString() : "");
            dto.setStockNum(obj[4] != null ? Integer.parseInt(obj[4].toString()) : 0);
            dto.setNum(obj[5] != null ? Integer.parseInt(obj[5].toString()) : 0);
            list.add(dto);
        }

        return list;
    }

    @Override
    public HSSFWorkbook toExcel() {
        ArrayList<String> fieldName = new ArrayList<String>();
        ArrayList<String> sheetName = new ArrayList<String>();
        ArrayList<String> fieldStyle = new ArrayList<String>();
        List<List<Object>> fieldData = new ArrayList<List<Object>>();
        fieldName.add(0, "序号");
        fieldName.add(1, "时间");
        fieldName.add(2, "终端配送商名称");
        fieldName.add(3, "单瓶商品名称");
        fieldName.add(4, "规格");
        fieldName.add(5, "库存数量");
        fieldName.add(6, "补货数量");

        for (int j = 0; j < fieldName.size(); j++) {
            fieldStyle.add(j, "6000");
        }

        List<ZSafeStockExcelDto> list = findAll();

        Integer stockNums = 0;
        Integer nums = 0;

        if (list.size() > 0) {
            int i = 0;
            for (ZSafeStockExcelDto dto : list) {
                List<Object> rowData = new ArrayList<Object>();
                rowData.add(0, i + 1);
                rowData.add(1, dto.getCreateDate());
                rowData.add(2, dto.getOrgName());
                rowData.add(3, dto.getGoodName());
                rowData.add(4, dto.getSpecs());
                rowData.add(5, dto.getStockNum());
                rowData.add(6, dto.getNum());
                stockNums += dto.getStockNum();
                nums += dto.getNum();
                i++;
                fieldData.add(rowData);
            }
        } else {
            List<Object> rowData = new ArrayList<Object>();
            fieldData.add(rowData);
        }

        List<Object> totalData = new ArrayList<Object>();
        totalData.add(0, "汇总");
        totalData.add(1, "");
        totalData.add(2, "");
        totalData.add(3, "");
        totalData.add(4, "");
        totalData.add(5, stockNums);
        totalData.add(6, nums);
        fieldData.add(totalData);

        sheetName.add(0, "商家安全库存预警");
        ExcleUtil ex = new ExcleUtil(fieldName, sheetName, fieldData, fieldStyle);
        return ex.createWorkbook();
    }

}
