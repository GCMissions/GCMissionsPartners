/*
 * Project Name: wrw-admin
 * File Name: OrgStockServiceImpl.java
 * Class Name: OrgStockServiceImpl
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
package com.hengtiansoft.business.shopStock.platformStock.service.impl;

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

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;

import com.hengtiansoft.business.common.util.QueryUtil;
import com.hengtiansoft.business.message.dto.SOrgDto;
import com.hengtiansoft.business.shopStock.platformStock.dto.GoodsStockDto;
import com.hengtiansoft.business.shopStock.platformStock.dto.OrgStockDetailDto;
import com.hengtiansoft.business.shopStock.platformStock.dto.OrgStockSearchDto;
import com.hengtiansoft.business.shopStock.platformStock.dto.OrgStockSearchRecordDto;
import com.hengtiansoft.business.shopStock.platformStock.service.OrgStockService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.converter.ConverterService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.enumeration.EErrorCode;
import com.hengtiansoft.common.exception.WRWException;
import com.hengtiansoft.common.util.ExcleUtil;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.wrw.dao.SOrgDao;
import com.hengtiansoft.wrw.dao.SStockDao;
import com.hengtiansoft.wrw.dao.SStockRecordDao;
import com.hengtiansoft.wrw.entity.SOrgEntity;
import com.hengtiansoft.wrw.entity.SStockEntity;
import com.hengtiansoft.wrw.entity.SStockRecordEntity;
import com.hengtiansoft.wrw.enums.OrgTypeEnum;
import com.hengtiansoft.wrw.enums.StatusEnum;
import com.hengtiansoft.wrw.enums.StockRecordTypeEmum;
import com.hengtiansoft.wrw.enums.StockState;

/**
 * Class Name: OrgStockServiceImpl Description:
 * 
 * @author xianghuang
 */
@Service
public class OrgStockServiceImpl implements OrgStockService {

    private static final Logger logger = LoggerFactory.getLogger(OrgStockServiceImpl.class);

    @Autowired
    private SStockDao           stockDao;

    @Autowired
    private SOrgDao             orgDao;

    @Autowired
    private SStockRecordDao     stockRecordDao;

    @PersistenceContext
    private EntityManager       entityManager;

    @Override
    public void searchOrg(OrgStockSearchDto dto, StockState state) {
        final List<Long> orgIds = new ArrayList<Long>();
        List<SStockEntity> stockList = stockDao.findAll();

        for (SStockEntity sStockEntity : stockList) {
            if (StockState.warning.getCode().equals(state.getCode())) {
                if (sStockEntity.getStockNum() < sStockEntity.getSafeNum() && !orgIds.contains(sStockEntity.getOrgId())) {
                    orgIds.add(sStockEntity.getOrgId());
                }
            } else if (StockState.normal.getCode().equals(state.getCode())) {
                if (sStockEntity.getStockNum() >= sStockEntity.getSafeNum() && !orgIds.contains(sStockEntity.getOrgId())) {
                    orgIds.add(sStockEntity.getOrgId());
                }
            } else {
                orgIds.add(sStockEntity.getOrgId());
            }

        }

        if (orgIds.size() > 0) {
            PageRequest pageable = new PageRequest(dto.getCurrentPage() - 1, dto.getPageSize(), new Sort(Direction.ASC, "orgId"));
            Page<SOrgEntity> page = orgDao.findAll(new Specification<SOrgEntity>() {
                @Override
                public Predicate toPredicate(Root<SOrgEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    List<Predicate> predicates = new ArrayList<Predicate>();

                    predicates.add(root.<Integer> get("orgId").in(orgIds));

                    predicates.add(cb.equal(root.<String> get("status"), StatusEnum.NORMAL.getCode()));

                    Predicate predicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                    query.where(predicate);
                    return query.getRestriction();
                }
            }, pageable);

            dto.setTotalRecord(page.getTotalElements());
            dto.setTotalPages(page.getTotalPages());
            dto.setList(buildOrgDtos(page.getContent()));
        }

    }

    @Override
    public Integer getOrgNum(StockState state) {
        final List<Long> orgIds = new ArrayList<Long>();
        List<SStockEntity> stockList = stockDao.findAll();

        for (SStockEntity sStockEntity : stockList) {
            if (StockState.warning.getCode().equals(state.getCode())) {
                if (sStockEntity.getStockNum() < sStockEntity.getSafeNum() && !orgIds.contains(sStockEntity.getOrgId())) {
                    orgIds.add(sStockEntity.getOrgId());
                }
            } else if (StockState.normal.getCode().equals(state.getCode())) {
                if (sStockEntity.getStockNum() >= sStockEntity.getSafeNum() && !orgIds.contains(sStockEntity.getOrgId())) {
                    orgIds.add(sStockEntity.getOrgId());
                }
            } else {
                orgIds.add(sStockEntity.getOrgId());
            }

        }

        if (orgIds.size() > 0) {
            List<SOrgEntity> orgList = orgDao.findAll(new Specification<SOrgEntity>() {
                @Override
                public Predicate toPredicate(Root<SOrgEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    List<Predicate> predicates = new ArrayList<Predicate>();

                    predicates.add(root.<Integer> get("orgId").in(orgIds));
                    predicates.add(cb.equal(root.<String> get("status"), StatusEnum.NORMAL.getCode()));

                    Predicate predicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                    query.where(predicate);
                    return query.getRestriction();
                }
            });
            return orgList.size();
        }

        return 0;

    }

    private List<OrgStockSearchRecordDto> buildOrgDtos(List<SOrgEntity> orgList) {
        List<OrgStockSearchRecordDto> dtos = new ArrayList<OrgStockSearchRecordDto>();
        for (SOrgEntity entity : orgList) {
            OrgStockSearchRecordDto dto = new OrgStockSearchRecordDto();
            dto.setOrgId(entity.getOrgId());
            dto.setOrgCode(entity.getOrgCode());
            dto.setOrgName(entity.getOrgName());
            dto.setOrgCate(OrgTypeEnum.getValue(entity.getOrgType()));
            dtos.add(dto);
        }
        return dtos;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public OrgStockSearchDto searchOrgStock(OrgStockSearchDto searchDto) {
        Map<String, Object> param = new HashMap<String, Object>();

        List<OrgStockSearchRecordDto> pagedList = new ArrayList<OrgStockSearchRecordDto>();

        StringBuilder sqlDataBuilder = new StringBuilder();
        sqlDataBuilder.append("SELECT O.ORG_CODE,O.ORG_NAME,O.ORG_TYPE,SUM(S.STOCK_NUM),O.ORG_ID,SUM(S.SAFE_NUM),SUM(S.STANDARD_NUM) ");
        sqlDataBuilder.append(" FROM S_STOCK S RIGHT JOIN S_ORG O ON O.ORG_ID=S.ORG_ID WHERE 1=1 ");

        if (StringUtils.isNotBlank(searchDto.getOrgCode())) {
            sqlDataBuilder.append(" AND O.ORG_CODE= :code ");
            param.put("code", searchDto.getOrgCode().trim());
        }
        if (StringUtils.isNotBlank(searchDto.getOrgName())) {
            sqlDataBuilder.append(" AND O.ORG_NAME LIKE :name ");
            param.put("name", "%" + searchDto.getOrgName().trim() + "%");
        }
        if (StringUtils.isNotBlank(searchDto.getOrgCate())) {
            sqlDataBuilder.append(" AND O.ORG_TYPE= :type ");
            param.put("type", searchDto.getOrgCate());
        }
        if (searchDto.getOrgP() != null) {
            sqlDataBuilder.append(" AND O.PARENT_ID = :orgP ");
            param.put("orgP", searchDto.getOrgP());
        }

        sqlDataBuilder.append(" GROUP BY O.ORG_CODE,O.ORG_NAME,O.ORG_TYPE,O.ORG_ID ORDER BY O.CREATE_DATE ");

        Query query = entityManager.createNativeQuery(sqlDataBuilder.toString());
        QueryUtil.processParamForQuery(query, param);

        List<Object[]> orgAllList = query.getResultList();
        if (StringUtils.isNotBlank(searchDto.getOrgState())) {
            // TODO status搜索 需要优化
            List<OrgStockSearchRecordDto> afterFilterData = new ArrayList<OrgStockSearchRecordDto>();
            for (Object[] obj : orgAllList) {
                String stockStackText = getStackStateByOrgId(WRWUtil.objToLong(obj[4]));
                if (StockState.normal.getCode().equals(searchDto.getOrgState()) && StockState.normal.getText().equals(stockStackText)) {
                    afterFilterData.add(toDto(obj));
                } else if (StockState.warning.getCode().equals(searchDto.getOrgState()) && StockState.warning.getText().equals(stockStackText)) {
                    afterFilterData.add(toDto(obj));
                }
            }

            int start = (searchDto.getCurrentPage() - 1) * searchDto.getPageSize();
            int end = afterFilterData.size() < searchDto.getCurrentPage() * searchDto.getPageSize() ? afterFilterData.size() : searchDto
                    .getCurrentPage() * searchDto.getPageSize();
            for (int i = start; i < end; i++) {
                pagedList.add(afterFilterData.get(i));
            }

            Long totalRecord = Long.valueOf(afterFilterData.size());
            QueryUtil.buildPagingDto(searchDto, totalRecord, pagedList);

        } else {
            Long totalRecord = Long.valueOf(orgAllList.size());
            query.setFirstResult(searchDto.getPageSize() * (searchDto.getCurrentPage() - 1));
            query.setMaxResults(searchDto.getPageSize());

            List<Object[]> orgList = query.getResultList();
            for (Object[] obj : orgList) {
                pagedList.add(toDto(obj));
            }

            QueryUtil.buildPagingDto(searchDto, totalRecord, pagedList);
        }

        return searchDto;
    }

    private OrgStockSearchRecordDto toDto(Object[] obj) {
        OrgStockSearchRecordDto recordDto = new OrgStockSearchRecordDto();
        recordDto.setOrgCode(String.valueOf(obj[0]));
        recordDto.setOrgName(String.valueOf(obj[1]));
        recordDto.setOrgCate(OrgTypeEnum.getValue(String.valueOf(obj[2])));
        recordDto.setStockNum(WRWUtil.objToLong(obj[3]));
        recordDto.setOrgId(WRWUtil.objToLong(obj[4]));
        recordDto.setSafeNum(WRWUtil.objToLong(obj[5]));
        recordDto.setStandardNum(WRWUtil.objToLong(obj[6]));
        recordDto.setOrgState(getStackStateByOrgId(recordDto.getOrgId()));
        if (obj.length > 7) {
            recordDto.setGoodName(obj[7] != null ? String.valueOf(obj[7]) : "");
            recordDto.setSpecs(obj[8] != null ? String.valueOf(obj[8]) : "");
        }
        return recordDto;
    }

    private String getStackStateByOrgId(Long orgId) {
        List<Object[]> productList = stockDao.findDetailByOrgId(orgId);
        for (Object[] o : productList) {
            // safeNum>stockNum
            if (WRWUtil.objToLong(o[4]) > WRWUtil.objToLong(o[5])) {
                return StockState.warning.getText();
            }
        }
        return StockState.normal.getText();
    }

    @Override
    public OrgStockDetailDto getDetail(Long orgId) {
        OrgStockDetailDto detailDto = new OrgStockDetailDto();

        SOrgEntity orgEntity = orgDao.findOne(orgId);
        if (null != orgEntity) {
            detailDto.setOrgCode(orgEntity.getOrgCode());
            detailDto.setOrgName(orgEntity.getOrgName());
            detailDto.setOrgCate(OrgTypeEnum.getValue(orgEntity.getOrgType()));

            // 安全库存详情页中用到
            detailDto.setContact(orgEntity.getContact());
            detailDto.setPhone(orgEntity.getPhone());
        } else {
            if (logger.isInfoEnabled()) {
                logger.info(String.format("根据orgId:%s没有找到orgEntity", orgId));
            }
        }

        List<Object> list = stockDao.getStockNumByOrgId(orgId);
        if (!list.isEmpty() && list.size() > 0) {
            detailDto.setStockNum(WRWUtil.objToLong(list.get(0)));
        } else {
            detailDto.setStockNum(0L);
        }

        List<Object[]> goodsList = stockDao.findDetailByOrgId(orgId);
        List<GoodsStockDto> pDtos = new ArrayList<GoodsStockDto>();
        for (Object[] o : goodsList) {
            // G.GOODS_ID,G.GOOD_CODE,G.GOOD_NAME,G.PRICE,S.SAFE_NUM,S.STOCK_NUM,S.STOCK_ID,S.STANDARD_NUM
            GoodsStockDto entityDto = new GoodsStockDto();
            entityDto.setGoodsId(WRWUtil.objToLong(o[0]));
            entityDto.setGoodsCode(String.valueOf(o[1]));
            entityDto.setGoodsName(String.valueOf(o[2]));
            entityDto.setPrice(WRWUtil.transFenToYuan(WRWUtil.objToLong(o[3])).toString());
            entityDto.setSafeStockSetting(WRWUtil.objToLong(o[4]));
            entityDto.setStockId(WRWUtil.objToLong(o[6]));
            entityDto.setStandardStockSetting(WRWUtil.objToLong(o[7]));
            entityDto.setStockSetting(WRWUtil.objToLong(o[5]));
            entityDto.setOrgState(WRWUtil.objToLong(o[4]) > WRWUtil.objToLong(o[5]) ? StockState.warning.getText() : StockState.normal.getText());
            pDtos.add(entityDto);
        }

        detailDto.setList(pDtos);
        return detailDto;
    }

    @Override
    public OrgStockDetailDto getWarnList(Long orgId) {
        OrgStockDetailDto detailDto = new OrgStockDetailDto();
        SOrgEntity orgEntity = orgDao.findOne(orgId);

        if (orgEntity == null) {
            if (logger.isInfoEnabled()) {
                logger.info(String.format("根据orgId:%s 没有找到orgEntity", orgId));
            }
        } else {
            detailDto.setOrgCode(orgEntity.getOrgCode());
            detailDto.setOrgName(orgEntity.getOrgName());
            detailDto.setOrgCate(OrgTypeEnum.getValue(orgEntity.getOrgType()));
        }

        List<Object[]> productList = stockDao.findDetailByOrgId(orgId);
        List<GoodsStockDto> pDtos = new ArrayList<GoodsStockDto>();
        for (Object[] o : productList) {
            GoodsStockDto entityDto = new GoodsStockDto();
            entityDto.setSafeStockSetting(WRWUtil.objToLong(o[4]));
            entityDto.setStockSetting(WRWUtil.objToLong(o[5]));
            if (entityDto.getSafeStockSetting() > entityDto.getStockSetting()) {

                entityDto.setGoodsId(WRWUtil.objToLong(o[0]));
                entityDto.setGoodsCode(String.valueOf(o[1]));
                entityDto.setGoodsName(String.valueOf(o[2]));
                entityDto.setPrice(String.valueOf(WRWUtil.transFenToYuan(WRWUtil.objToLong(o[3]))));

                entityDto.setStandardStockSetting(WRWUtil.objToLong(o[7]));

                pDtos.add(entityDto);
            }
        }
        detailDto.setList(pDtos);
        return detailDto;
    }

    @Override
    @Transactional
    public ResultDto<String> saveSetting(Long stockId, Long stockSetting) {
        if (null == stockId || null == stockSetting) {
            return ResultDtoFactory.toNack("库存设置有数据为空");
        }

        SStockEntity stockEntity = stockDao.findOne(stockId);
        if (stockEntity == null) {
            if (logger.isInfoEnabled()) {
                logger.info(String.format("根据stockId：%s 没有找到对象", stockId));
            }
            throw new WRWException(EErrorCode.ENTITY_STOCKID_NOT_EXIST);
        } else {
            stockDao.updateStockNumById(stockSetting, stockId);

            SStockRecordEntity entity = new SStockRecordEntity();
            entity.setStockId(stockId);
            entity.setOperDate(new Date());
            entity.setOperId(AuthorityContext.getCurrentUser().getUserId());
            if (stockEntity.getStockNum() < stockSetting) {
                entity.setOperType(StockRecordTypeEmum.ADD.getCode());
                entity.setChangeNum(Long.valueOf(String.valueOf(stockSetting - stockEntity.getStockNum())));
            } else {
                entity.setOperType(StockRecordTypeEmum.REDUCE.getCode());
                entity.setChangeNum(Long.valueOf(String.valueOf(stockEntity.getStockNum() - stockSetting)));
            }

            stockRecordDao.save(entity);
        }

        return ResultDtoFactory.toAck("设置成功");
    }

    @Override
    @Transactional
    public ResultDto<String> saveSetting(Long stockId, Long safeStockSetting, Long standardStockSetting) {
        if (null == stockId || null == safeStockSetting || null == standardStockSetting) {
            return ResultDtoFactory.toNack("库存设置有数据为空");
        }

        stockDao.updateStockById(safeStockSetting, standardStockSetting, stockId);

        return ResultDtoFactory.toAck("设置成功");
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public OrgStockSearchDto findOrgProductStock(OrgStockSearchDto searchDto) {
        Map<String, Object> param = new HashMap<String, Object>();

        List<OrgStockSearchRecordDto> pagedList = new ArrayList<OrgStockSearchRecordDto>();

        StringBuilder sqlDataBuilder = new StringBuilder();
        sqlDataBuilder
                .append("SELECT O.ORG_CODE,O.ORG_NAME,O.ORG_TYPE,SUM(S.STOCK_NUM),O.ORG_ID,SUM(S.SAFE_NUM),SUM(S.STANDARD_NUM),G.GOOD_NAME,G.SPECS ");
        sqlDataBuilder.append(" FROM S_STOCK S INNER JOIN S_ORG O ON O.ORG_ID=S.ORG_ID ");
        sqlDataBuilder.append(" LEFT JOIN P_GOODS G ON S.GOODS_ID=G.GOODS_ID WHERE 1=1 ");

        if (StringUtils.isNotBlank(searchDto.getOrgCode())) {
            sqlDataBuilder.append(" AND O.ORG_CODE= :code ");
            param.put("code", searchDto.getOrgCode().trim());
        }
        if (StringUtils.isNotBlank(searchDto.getOrgName())) {
            sqlDataBuilder.append(" AND O.ORG_NAME LIKE :name ");
            param.put("name", "%" + searchDto.getOrgName().trim() + "%");
        }
        if (StringUtils.isNotBlank(searchDto.getOrgCate())) {
            sqlDataBuilder.append(" AND O.ORG_TYPE= :type ");
            param.put("type", searchDto.getOrgCate());
        }
        if (searchDto.getOrgPct() != null) {
            sqlDataBuilder.append(" AND S.STOCK_NUM <= :pct * S.STANDARD_NUM ");
            param.put("pct", searchDto.getOrgPct() / 100);
        }
        if (searchDto.getOrgP() != null) {
            sqlDataBuilder.append(" AND O.PARENT_ID = :orgP ");
            param.put("orgP", searchDto.getOrgP());
        }

        sqlDataBuilder.append(" GROUP BY G.GOOD_NAME,G.SPECS,O.ORG_CODE,O.ORG_NAME,O.ORG_TYPE,O.ORG_ID ORDER BY CONVERT(O.ORG_NAME USING GBK) ");

        Query query = entityManager.createNativeQuery(sqlDataBuilder.toString());
        QueryUtil.processParamForQuery(query, param);

        List<Object[]> orgAllList = query.getResultList();
        if (StringUtils.isNotBlank(searchDto.getOrgState())) {
            List<OrgStockSearchRecordDto> afterFilterData = new ArrayList<OrgStockSearchRecordDto>();
            for (Object[] obj : orgAllList) {
                String stockStackText = getStackStateByOrgId(WRWUtil.objToLong(obj[4]));
                if (StockState.normal.getCode().equals(searchDto.getOrgState()) && StockState.normal.getText().equals(stockStackText)) {
                    afterFilterData.add(toDto(obj));
                } else if (StockState.warning.getCode().equals(searchDto.getOrgState()) && StockState.warning.getText().equals(stockStackText)) {
                    afterFilterData.add(toDto(obj));
                }
            }

            int start = (searchDto.getCurrentPage() - 1) * searchDto.getPageSize();
            int end = afterFilterData.size() < searchDto.getCurrentPage() * searchDto.getPageSize() ? afterFilterData.size() : searchDto
                    .getCurrentPage() * searchDto.getPageSize();
            for (int i = start; i < end; i++) {
                pagedList.add(afterFilterData.get(i));
            }

            Long totalRecord = Long.valueOf(afterFilterData.size());
            QueryUtil.buildPagingDto(searchDto, totalRecord, pagedList);

        } else {
            Long totalRecord = Long.valueOf(orgAllList.size());
            query.setFirstResult(searchDto.getPageSize() * (searchDto.getCurrentPage() - 1));
            query.setMaxResults(searchDto.getPageSize());

            List<Object[]> orgList = query.getResultList();
            for (Object[] obj : orgList) {
                pagedList.add(toDto(obj));
            }

            QueryUtil.buildPagingDto(searchDto, totalRecord, pagedList);
        }

        return searchDto;
    }

    @Override
    @Transactional
    public HSSFWorkbook toExcel(OrgStockSearchDto searchDto) {
        ArrayList<String> fieldName = new ArrayList<String>();
        ArrayList<String> sheetName = new ArrayList<String>();
        ArrayList<String> fieldStyle = new ArrayList<String>();
        List<List<Object>> fieldData = new ArrayList<List<Object>>();
        fieldName.add(0, "序号");
        fieldName.add(1, "商家名称");
        fieldName.add(2, "商家类别");
        fieldName.add(3, "商品名称");
        fieldName.add(4, "规格");
        fieldName.add(5, "当前库存数量");
        fieldName.add(6, "标准库存数量");
        fieldName.add(7, "库存比例");

        for (int j = 0; j < fieldName.size(); j++) {
            fieldStyle.add(j, "6000");
        }

        searchDto.setPageSize(Integer.MAX_VALUE);
        OrgStockSearchDto stockDto = findOrgProductStock(searchDto);

        Long stockNums = 0L;
        Long nums = 0L;
        List<CellRangeAddress> crds = new ArrayList<CellRangeAddress>();
        if (stockDto.getList().size() > 0) {
            List<String> orgNames = new ArrayList<String>();
            for (int i = 0; i < stockDto.getList().size(); i++) {
                OrgStockSearchRecordDto list = stockDto.getList().get(i);
                List<Object> rowData = new ArrayList<Object>();
                rowData.add(0, i + 1);
                rowData.add(1, list.getOrgName());
                rowData.add(2, list.getOrgCate());
                rowData.add(3, list.getGoodName());
                rowData.add(4, list.getSpecs());
                rowData.add(5, list.getStockNum());
                rowData.add(6, list.getStandardNum());
                rowData.add(7, searchDto.getOrgPct() + "%");

                // 合并最后一列单元格
                CellRangeAddress crd = new CellRangeAddress(1, stockDto.getList().size(), 7, 7);
                crds.add(crd);

                stockNums += list.getStockNum();
                nums += list.getStandardNum();
                fieldData.add(rowData);
                orgNames.add(list.getOrgName());
            }
            // 在List末尾添加一条空值，防止出现lastCol < firstCol的情况
            orgNames.add("");

            // 从orgNames里面取出重复行的firstCol和lastCol
            int i = 0;
            int j = 1;
            for (; i < orgNames.size() - 1;) {
                for (; j < orgNames.size(); j++) {
                    if (!orgNames.get(i).equals(orgNames.get(j))) {
                        CellRangeAddress crd = new CellRangeAddress(i + 1, j, 1, 1);
                        crds.add(crd);
                        crd = new CellRangeAddress(i + 1, j, 2, 2);
                        crds.add(crd);
                        i = j;
                    }
                }
            }

        } else {
            List<Object> rowData = new ArrayList<Object>();
            fieldData.add(rowData);
        }

        List<Object> totalData = new ArrayList<Object>();
        totalData.add("汇总");
        totalData.add("");
        totalData.add("");
        totalData.add("");
        totalData.add("");
        totalData.add(stockNums);
        totalData.add(nums);
        fieldData.add(totalData);

        sheetName.add(0, "商家库存");
        ExcleUtil ex = new ExcleUtil(fieldName, sheetName, fieldData, fieldStyle, crds);
        return ex.createWorkbook();
    }

    @Override
    public List<SOrgDto> findAllP() {
        List<SOrgEntity> entities = orgDao.findAllP();
        List<SOrgDto> dtos = new ArrayList<SOrgDto>();
        for (SOrgEntity entity : entities) {
            SOrgDto dto = ConverterService.convert(entity, SOrgDto.class);
            dtos.add(dto);
        }
        return dtos;
    }
}
