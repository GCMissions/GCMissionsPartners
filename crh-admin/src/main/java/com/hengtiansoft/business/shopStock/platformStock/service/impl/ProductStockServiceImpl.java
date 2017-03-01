/*
 * Project Name: wrw-admin
 * File Name: StockServiceImpl.java
 * Class Name: StockServiceImpl
 *
 * Copyright 2014 Hengtian Software Inc
 *
 * Licensed under the Hengtiansoft
 *
 * http://www.hengtiansoft.com
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hengtiansoft.business.shopStock.platformStock.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengtiansoft.business.common.util.QueryUtil;
import com.hengtiansoft.business.shopStock.platformStock.dto.OrgEntityTreeDto;
import com.hengtiansoft.business.shopStock.platformStock.dto.GoodsStockDetailDto;
import com.hengtiansoft.business.shopStock.platformStock.dto.GoodsStockSearchDto;
import com.hengtiansoft.business.shopStock.platformStock.dto.GoodsStockDto;
import com.hengtiansoft.business.shopStock.platformStock.service.ProductStockService;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.wrw.dao.PGoodsDao;
import com.hengtiansoft.wrw.dao.SOrgDao;
import com.hengtiansoft.wrw.dao.SStockDao;
import com.hengtiansoft.wrw.entity.PGoodsEntity;
import com.hengtiansoft.wrw.entity.SOrgEntity;
import com.hengtiansoft.wrw.enums.OrgTypeEnum;

/**
 * Class Name: StockServiceImpl Description:
 * 
 * @author xianghuang
 *
 */
@Service
public class ProductStockServiceImpl implements ProductStockService {

    private static final Logger logger = LoggerFactory.getLogger(ProductStockServiceImpl.class);

    @Autowired
    private SStockDao stockDao;

    @Autowired
    private PGoodsDao goodsDao;

    @Autowired
    private SOrgDao orgDao;

    @Autowired
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public GoodsStockSearchDto searchProductStock(GoodsStockSearchDto searchDto) {
        Map<String, Object> param = new HashMap<String, Object>();

        List<GoodsStockDto> pagedList = new ArrayList<GoodsStockDto>();

        StringBuilder sqlDataBuilder = new StringBuilder();
        sqlDataBuilder
                .append(" SELECT G.GOOD_CODE,G.GOOD_NAME,G.PRICE,SUM(S.STOCK_NUM),G.CREATE_DATE,G.GOODS_ID FROM S_STOCK S RIGHT JOIN P_GOODS G ON S.GOODS_ID=G.GOODS_ID ");

        if (searchDto.getOrgId() != null) {
            sqlDataBuilder.append(" AND S.ORG_ID = :orgId ");
            param.put("orgId", searchDto.getOrgId());
        }
        sqlDataBuilder.append(" WHERE 1=1  ");

        if (StringUtils.isNotBlank(searchDto.getGoodsCode())) {
            sqlDataBuilder.append(" AND G.GOOD_CODE =:code ");
            param.put("code", searchDto.getGoodsCode().trim());
        }
        if (StringUtils.isNotBlank(searchDto.getGoodsName())) {
            sqlDataBuilder.append(" AND G.GOOD_NAME LIKE :name ");
            param.put("name", "%" + searchDto.getGoodsName().trim() + "%");
        }
        sqlDataBuilder
                .append(" GROUP BY G.GOOD_CODE,G.GOOD_NAME,G.PRICE,G.CREATE_DATE,G.GOODS_ID ORDER BY G.CREATE_DATE DESC ");

        Query query = entityManager.createNativeQuery(sqlDataBuilder.toString());
        QueryUtil.processParamForQuery(query, param);

        Long totalRecord = Long.valueOf(query.getResultList().size());
        query.setFirstResult(searchDto.getPageSize() * (searchDto.getCurrentPage() - 1));
        query.setMaxResults(searchDto.getPageSize());

        List<Object[]> list = query.getResultList();
        for (Object[] objects : list) {
            GoodsStockDto dto = new GoodsStockDto();

            dto.setGoodsCode(String.valueOf(objects[0]));
            dto.setGoodsName(String.valueOf(objects[1]));
            dto.setPrice(WRWUtil.transFenToYuan(WRWUtil.objToLong(objects[2])).toString());
            dto.setCreateDate(String.valueOf(objects[4]));
            dto.setGoodsId(WRWUtil.objToLong(objects[5]));

            if (StringUtils.isNotBlank(String.valueOf(objects[3]))) {
                dto.setProductStockNum(WRWUtil.objToLong(objects[3]));
            } else {
                dto.setProductStockNum(0L);
            }

            pagedList.add(dto);
        }

        QueryUtil.buildPagingDto(searchDto, totalRecord, pagedList);
        return searchDto;
    }

    public GoodsStockDetailDto findOrgTreeAndStockNumDetail(Long goodsId) {
        GoodsStockDetailDto dto = new GoodsStockDetailDto();
        List<Long> zOrgAddedOwnProduct = new ArrayList<Long>();
        List<Long> zOrgAddedNotOwnProduct = new ArrayList<Long>();

        // 获取数据
        List<Object[]> stockList = stockDao.findByProductId(goodsId);

        List<Long> ids = new ArrayList<Long>();
        Map<Long, Object[]> stockMap = new HashMap<Long, Object[]>();
        for (Object[] obj : stockList) {
            ids.add(WRWUtil.objToLong(obj[0]));
            stockMap.put(WRWUtil.objToLong(obj[0]), obj);
        }
        List<SOrgEntity> orgList = orgDao.findAll(ids);
        List<OrgEntityTreeDto> pTree = new ArrayList<OrgEntityTreeDto>();

        for (SOrgEntity org : orgList) {
            if (OrgTypeEnum.P.getKey().equals(org.getOrgType())) {

                OrgEntityTreeDto pNode = new OrgEntityTreeDto();
                List<OrgEntityTreeDto> zTree = new ArrayList<OrgEntityTreeDto>();
                for (SOrgEntity orgChild : orgList) {

                    if (OrgTypeEnum.Z.getKey().equals(orgChild.getOrgType())
                            && org.getOrgId().equals(orgChild.getParentId())) {
                        OrgEntityTreeDto zNode = new OrgEntityTreeDto();
                        zNode.setOrgId(orgChild.getOrgId());
                        zNode.setOrgName(orgChild.getOrgName());
                        zNode.setStockNum(Long.valueOf(String.valueOf(stockMap.get(orgChild.getOrgId())[1])));
                        zTree.add(zNode);
                        zOrgAddedOwnProduct.add(orgChild.getOrgId());
                    }
                }
                pNode.setChildList(zTree);

                pNode.setOrgId(org.getOrgId());
                pNode.setOrgName(org.getOrgName());
                pNode.setStockNum(Long.valueOf(String.valueOf(stockMap.get(org.getOrgId())[1])));
                pTree.add(pNode);
            } else if (OrgTypeEnum.Z.getKey().equals(org.getOrgType()) && !zOrgAddedOwnProduct.contains(org.getOrgId())) {// 商品对应的Z，有对应P但是没有该商品
                Long parentId = org.getParentId();

                if (parentId == null) {
                    if (logger.isInfoEnabled()) {
                        logger.info("Z不应该没有找到P");
                    }
                } else if (!zOrgAddedNotOwnProduct.contains(parentId)) {
                    OrgEntityTreeDto pNode = new OrgEntityTreeDto();
                    SOrgEntity pOrgEntity = orgDao.findOne(parentId);

                    if (pOrgEntity != null) {
                        List<SOrgEntity> zList = orgDao.findByParentIdAndOrgType(parentId, OrgTypeEnum.Z.getKey());

                        List<OrgEntityTreeDto> zTree = new ArrayList<OrgEntityTreeDto>();
                        for (SOrgEntity zOrgEntity : zList) {
                            if (null != stockMap.get(zOrgEntity.getOrgId())) {
                                OrgEntityTreeDto zNode = new OrgEntityTreeDto();
                                zNode.setOrgId(org.getOrgId());
                                zNode.setOrgName(org.getOrgName());
                                zNode.setStockNum(WRWUtil.objToLong(stockMap.get(zOrgEntity.getOrgId())[1]));
                                zTree.add(zNode);
                            }
                        }
                        pNode.setOrgName(pOrgEntity.getOrgName());
                        pNode.setStockNum(0L);
                        pNode.setChildList(zTree);
                        pNode.setOrgId(parentId);

                        pTree.add(pNode);
                    } else {
                        if (logger.isInfoEnabled()) {
                            logger.info(String.format("Z的parentid:%s 没有找到P", parentId));
                        }
                    }
                    zOrgAddedNotOwnProduct.add(parentId);
                }

            }
        }
        dto.setTreeList(pTree);

        // 查询物料详情
        PGoodsEntity entity = goodsDao.findOne(goodsId);
        if (null != entity) {
            dto.setGoodsCode(entity.getGoodCode());
            dto.setGoodsName(entity.getGoodName());
            dto.setPrice(String.valueOf(WRWUtil.transFenToYuan(entity.getPrice())));

            List<Object> stocksList = stockDao.findStockNumByProductId(goodsId);

            if (stocksList.size() > 0) {
                dto.setStockNum(WRWUtil.objToLong(stocksList.get(0)));
            } else {
                dto.setStockNum(0L);
            }

        }

        return dto;
    }

}
