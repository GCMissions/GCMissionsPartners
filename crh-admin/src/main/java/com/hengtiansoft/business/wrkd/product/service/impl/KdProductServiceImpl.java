/*
 * Project Name: wrw-admin
 * File Name: KProductServiceImpl.java
 * Class Name: KProductServiceImpl
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
package com.hengtiansoft.business.wrkd.product.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.business.common.util.QueryUtil;
import com.hengtiansoft.business.wrkd.advertise.service.KdAdvertiseService;
import com.hengtiansoft.business.wrkd.image.dto.KdPImageDto;
import com.hengtiansoft.business.wrkd.image.service.KdPImageService;
import com.hengtiansoft.business.wrkd.product.dto.KdProductDto;
import com.hengtiansoft.business.wrkd.product.dto.KdProductFreightDto;
import com.hengtiansoft.business.wrkd.product.dto.KdProductSearchDto;
import com.hengtiansoft.business.wrkd.product.service.KdProductFreightService;
import com.hengtiansoft.business.wrkd.product.service.KdProductSaleService;
import com.hengtiansoft.business.wrkd.product.service.KdProductService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.converter.ConverterService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.util.CurrencyUtils;
import com.hengtiansoft.common.util.DateTimeUtil;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.wrw.dao.KdProductDao;
import com.hengtiansoft.wrw.entity.KdProductEntity;
import com.hengtiansoft.wrw.enums.KdPImageEnum;

/**
 * Class Name: KProductServiceImpl
 * Description: 酷袋商品
 * @author zhongyidong
 *
 */
@Service
public class KdProductServiceImpl implements KdProductService {
    
    // 商品正常标志
    private static final String NORMAL = "0";
    // 商品删除标志
    private static final String DELETED = "1";
    
    // 商品名称中特殊字符
    private static final char ESCAPE = '\\';
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Autowired
    private KdProductDao kdProductDao;
    @Autowired
    private KdPImageService kdPImageService;
    @Autowired
    private KdProductSaleService kdProductSaleService;
    @Autowired
    private KdProductFreightService kpProductFreightService;
    @Autowired
    private KdAdvertiseService kdAdvertiseService;
    
    @Override
    public KdProductDto findById(Long productId) {
        KdProductEntity product = kdProductDao.findOne(productId);
        if (null != product) {
            KdProductDto kdProductDto = ConverterService.convert(product, KdProductDto.class);
            if (null == product.getLowPrice() && null == product.getHighPrice()) {
                kdProductDto.setPriceRange("-");
            } else {
                Long lowPrice = product.getLowPrice() != null ? Long.valueOf(product.getLowPrice()) : 0l;
                Long highPrice = product.getHighPrice() != null ? Long.valueOf(product.getHighPrice()) : 0l;
                if (lowPrice == highPrice) {
                    kdProductDto.setPriceRange(WRWUtil.transFenToYuan2String(lowPrice));
                } else {
                    kdProductDto.setPriceRange(WRWUtil.transFenToYuan2String(lowPrice) + "~" + WRWUtil.transFenToYuan2String(highPrice));
                }
            }
            return kdProductDto;
        }
        return null;
    }
    
    @Override
    public KdProductDto viewProductInfo(Long productId) {
        KdProductDto productDto = findById(productId);
        if (null != productDto) {
            // 运费
            productDto.setFreightMap(kpProductFreightService.findFreightInfo(productId));
            // 图片
            productDto.setListImages(kdPImageService.queryImage(productId, KdPImageEnum.PRODCUT.getCode()));
        }
        return productDto;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void search(KdProductSearchDto searchDto) {
        StringBuilder countBuilder = new StringBuilder("");
        countBuilder.append("SELECT count(*) FROM (");
        StringBuilder searchBuilder = new StringBuilder();
        searchBuilder.append("SELECT P.id, P.pcode, P.pname, P.origin_price, P.low_price, "
                + "P.high_price, P.create_date, S.sale_status, S.on_sale_time, S.off_sale_time "
                + "FROM kd_p_product P LEFT JOIN kd_p_sale S on P.id = S.product_id "
                + "WHERE 1=1 AND P.is_deleted ='0' ");
        
        Map<String, Object> param = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(searchDto.getPcode())) {
            searchBuilder.append(" AND P.PCODE LIKE :CODE");
            param.put("CODE", "%" + searchDto.getPcode() + "%");
        }
        
        if (StringUtils.isNotBlank(searchDto.getActTag())) {
            searchBuilder.append(" AND P.ACT_TAG = :ACT_TAG");
            param.put("ACT_TAG", searchDto.getActTag());
        }
        
        if (StringUtils.isNotBlank(searchDto.getPname())) {
            String productName = searchDto.getPname();
            productName = productName.replace("\\", ESCAPE + "\\");
            productName = productName.replace("%", ESCAPE + "%");
            productName = productName.replace("_", ESCAPE + "_");
            searchBuilder.append(" AND P.PNAME LIKE :NAME escape '\\\\' ");
            param.put("NAME", "%" + productName + "%");
        }
        
        if (null != searchDto.getLowPrice()) {
            searchBuilder.append(" AND P.LOW_PRICE >= :LOW_PRICE ");
            param.put("LOW_PRICE", CurrencyUtils.rmbYuanToFen(searchDto.getLowPrice()));
        }
        
        if (null != searchDto.getHighPrice()) {
            searchBuilder.append(" AND P.HIGH_PRICE <= :HIGH_PRICE ");
            param.put("HIGH_PRICE", CurrencyUtils.rmbYuanToFen(searchDto.getHighPrice()));
        }
        
        if (StringUtils.isNotBlank(searchDto.getSaleStatus())) {
            String saleStatus = searchDto.getSaleStatus();
            String[] saleArray = saleStatus.split(",");
            if (saleArray.length > 1) {
                List<String> saleStatusList = new ArrayList<String>();
                Collections.addAll(saleStatusList, saleArray);
                searchBuilder.append(" AND S.SALE_STATUS IN (:saleStatus) ");
                param.put("saleStatus", saleStatusList);
            } else {
                searchBuilder.append(" AND S.SALE_STATUS = :SALE_STATUS ");
                param.put("SALE_STATUS", searchDto.getSaleStatus());
            }
        }
        
        //　查询结果总数
        countBuilder.append(searchBuilder).append(" ) A");
        Query coutQuery = entityManager.createNativeQuery(countBuilder.toString());
        QueryUtil.processParamForQuery(coutQuery, param);
        int totalRecord = ((BigInteger)coutQuery.getSingleResult()).intValue();
        searchDto.setTotalRecord((long)totalRecord);

        searchBuilder.append(" ORDER BY P.id DESC");
        Query query = entityManager.createNativeQuery(searchBuilder.toString());
        QueryUtil.processParamForQuery(query, param);
        query.setFirstResult(searchDto.getPageSize() * (searchDto.getCurrentPage() - 1));
        query.setMaxResults(searchDto.getPageSize());
        int pages = totalRecord / searchDto.getPageSize();
        searchDto.setTotalPages(totalRecord % searchDto.getPageSize() == 0 ? pages : pages + 1);
        searchDto.setList(buildProductDtos(query.getResultList()));
    }

    @Override
    @Transactional
    public ResultDto<Long> saveProduct(KdProductDto productDto) {
        // 运费信息
        List<KdProductFreightDto> freightInfo = productDto.getFreightInfo();
        if (CollectionUtils.isEmpty(freightInfo)) {
            return ResultDtoFactory.toNack("运费信息为空！");
        }
        List<KdPImageDto> images = productDto.getListImages();
        if (CollectionUtils.isEmpty(images)) {
            return ResultDtoFactory.toNack("商品图片为空！");
        }
        int count = kdProductDao.findProductByName(productDto.getPname(), NORMAL);
        if (null == productDto.getId() && 0 < count) {
            return ResultDtoFactory.toNack("商品名称已经存在！");
        }
        
        // 获取当前用户id
        KdProductEntity product = null;
        Long userId = AuthorityContext.getCurrentUser().getUserId();
        if (null == productDto.getId()) {
            // 添加商品信息
            product = ConverterService.convert(productDto, KdProductEntity.class);
            product.setImgUrl(images.get(0).getImageUrl());
            product.setCreateDate(new Date());
            product.setCreateId(userId);
            product.setIsDeleted(NORMAL);
            product = kdProductDao.save(product);
            if (null != product) {
                // 生成商品编号
                product.setPcode("K" + product.getId());
            } 
        } else {
            // 更新商品信息
            product = kdProductDao.findOne(productDto.getId());
            product.setModifyId(userId);
            product.setModifyDate(new Date());
            product.setPname(productDto.getPname());
            product.setActTag(productDto.getActTag());
            product.setImgUrl(images.get(0).getImageUrl());
            product.setOriginPrice(productDto.getOriginPrice());
            product.setQuickDesc(productDto.getQuickDesc());
            product.setSpecialNote(productDto.getSpecialNote());
            product.setIsShow(productDto.getIsShow());
            product.setVip(productDto.getVip());
        }
        
        if (null == product || null == product.getId()) {
            return ResultDtoFactory.toNack("保存商品信息失败！");
        }
        
        if (null != product.getId()) {
            //　保存商品图片
            kdPImageService.saveImage(product.getId(), KdPImageEnum.PRODCUT.getCode(), images);
            // 保存上下架状态
            kdProductSaleService.saveSaleInfo(product.getId(), userId);
            // 保存运费
            kpProductFreightService.saveFreightInfo(product.getId(), freightInfo);
        }
                
        return ResultDtoFactory.toAck("添加成功", product.getId());
    }

    @Override
    @Transactional
    public int saveDetail(Long productId, String detailDesc) {
        return kdProductDao.saveDetailDesc(productId, detailDesc);
    }
    
    @Override
    @Transactional(value = "jpaTransactionManager")
    public int deleteProduct(List<Long> productIds) {
        // 获取当前用户id
        Long userId = AuthorityContext.getCurrentUser().getUserId();
        kdAdvertiseService.updateAdvertiseStatus(productIds);
        return kdProductDao.deleteProduct(productIds, DELETED, userId);
    }

    /**
     * Description: 将entity转为dto
     *
     * @param results
     * @param searchDto
     * @return
     */
    private List<KdProductDto> buildProductDtos(List<Object[]> results) {
        List<KdProductDto> productDtos = new ArrayList<KdProductDto>(results.size());
        KdProductDto dto = null;
        for (Object[] array : results) {
            dto = new KdProductDto();
            dto.setId(WRWUtil.objToLong(array[0]));
            dto.setPcode(WRWUtil.objToString(array[1]));
            dto.setPname(WRWUtil.objToString(array[2]));
            dto.setSaleStatus(WRWUtil.objToString(array[7]));
            Long lowPrice = WRWUtil.objToLong(array[4]);
            Long highPrice = WRWUtil.objToLong(array[5]);
            if (lowPrice == highPrice) {
                if (null == array[4]) {
                    dto.setPriceRange("-");
                } else {
                    dto.setPriceRange(WRWUtil.transFenToYuan2String(lowPrice));
                }
            } else {
                dto.setPriceRange(WRWUtil.transFenToYuan2String(lowPrice) + "~" + WRWUtil.transFenToYuan2String(highPrice));
            }
            if (null != array[6]) {
                dto.setCreateDate(DateTimeUtil.parseDate(array[6].toString(), DateTimeUtil.SIMPLE_FMT));
            }
            if (null != array[8]) {
                dto.setOnTime(DateTimeUtil.parseDateToString((Date)array[8], DateTimeUtil.SIMPLE_FMT_MINUTE));
            }
            if (null != array[9]) {
                dto.setOffTime(DateTimeUtil.parseDateToString((Date)array[9], DateTimeUtil.SIMPLE_FMT_MINUTE));
            }
            productDtos.add(dto);
        }
        return productDtos;
    }

}
