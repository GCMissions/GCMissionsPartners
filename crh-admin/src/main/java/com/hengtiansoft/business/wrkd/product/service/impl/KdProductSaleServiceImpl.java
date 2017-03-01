/*
 * Project Name: wrw-admin
 * File Name: KdProductSaleServiceImpl.java
 * Class Name: KdProductSaleServiceImpl
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.business.wrkd.activity.dto.KdStartDateAndEndDateDto;
import com.hengtiansoft.business.wrkd.activity.service.KdGroupBuyService;
import com.hengtiansoft.business.wrkd.activity.service.KdTwentyFourHoursService;
import com.hengtiansoft.business.wrkd.product.dto.KdProductDto;
import com.hengtiansoft.business.wrkd.product.dto.KdProductSaleDto;
import com.hengtiansoft.business.wrkd.product.dto.KdProductSpecDto;
import com.hengtiansoft.business.wrkd.product.dto.KdProductStockDto;
import com.hengtiansoft.business.wrkd.product.service.KdProductSaleService;
import com.hengtiansoft.business.wrkd.product.service.KdProductService;
import com.hengtiansoft.business.wrkd.product.service.KdProductSpecService;
import com.hengtiansoft.business.wrkd.product.service.KdProductStockService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.util.DateTimeUtil;
import com.hengtiansoft.wrw.dao.KdProductSaleDao;
import com.hengtiansoft.wrw.entity.KdProductSaleEntity;
import com.hengtiansoft.wrw.enums.KdSaleStatus;

/**
 * Class Name: KdProductSaleServiceImpl
 * Description: 酷袋商品上下架
 * @author zhongyidong
 *
 */
@Service
public class KdProductSaleServiceImpl implements KdProductSaleService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(KdProductSaleServiceImpl.class);
 
    //　向后推的年份
    private static final int FURTURE_YEAR = 10;
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";

    @PersistenceContext
    private EntityManager entityManager;
    
    @Autowired
    private KdProductSaleDao kdProductSaleDao;
    
    @Autowired
    private KdProductService kdProductService;
    
    @Autowired
    private KdGroupBuyService kdGroupBuyService;
    
    @Autowired
    private KdProductSpecService kdProductSpecService;
    
    @Autowired
    private KdProductStockService kdProductStockService;
    
    @Autowired
    private KdTwentyFourHoursService kdTwentyFourHoursService;
    
    @Override
    public ResultDto<String> checkProductInfo(Long productId) {
        KdProductDto productDto = kdProductService.findById(productId);
        if (null == productDto) {
            return ResultDtoFactory.toNack("商品基本信息缺失，不可以上架！");
        }
        
        KdProductStockDto stockDto = kdProductStockService.findByProductId(productId);
        if (null == stockDto) {
            return ResultDtoFactory.toNack("商品库存信息缺失，不可以上架！");
        } else {
            if (null == stockDto.getRestAmount() || 0 == stockDto.getRestAmount()) {
                return ResultDtoFactory.toNack("商品缺货，不可以上架！");
            }
        }
        
        List<KdProductSpecDto> specDtos = kdProductSpecService.findSpecInfo(productId);
        if (null == specDtos) {
            return ResultDtoFactory.toNack("商品规格信息缺失，不可以上架！");
        }
        
        return ResultDtoFactory.toAck("商品信息完整，可以上架！");
    }

    @Override
    @Transactional
    public ResultDto<String> putOnSale(KdProductSaleDto saleDto) {
        if (StringUtils.isBlank(saleDto.getOnTime())) {
            return ResultDtoFactory.toNack("上架日期为空！");
        }
        KdProductSaleEntity saleEntity = kdProductSaleDao.findByProductId(saleDto.getProductId());
        if (null == saleEntity) {
            return ResultDtoFactory.toNack("该商品不存在！");
        }
        // 上架时间
        Date onSaleDate = null;
        // 下架时间
        Date offSaleDate = null;
        String saleStatus = KdSaleStatus.WAIT_SALE.getCode();
        KdStartDateAndEndDateDto teamBuyDateDto = kdGroupBuyService.getEarlyStartDateAndLateEndDate(saleDto.getProductId());
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
            onSaleDate = dateFormat.parse(saleDto.getOnTime());
            if (null != teamBuyDateDto && onSaleDate.after(teamBuyDateDto.getStartDate())) {
                return ResultDtoFactory.toNack("该商品正在参加活动，上架时间不可晚于活动开始时间！");
            }
            if (onSaleDate.before(new Date())) {
                saleStatus = KdSaleStatus.ON_SALE.getCode();
            } 
            if (StringUtils.isNotBlank(saleDto.getOffTime())){
                offSaleDate = dateFormat.parse(saleDto.getOffTime());
            } else {
                // 设置一个默认时间
                String offTime = DateTimeUtil.getFormatDate(DateTimeUtil.getDateEndAddYear(FURTURE_YEAR), DATE_FORMAT);
                offSaleDate = dateFormat.parse(offTime);
            }
            if (null != teamBuyDateDto && offSaleDate.before(teamBuyDateDto.getEndDate())) {
                return ResultDtoFactory.toNack("该商品正在参加活动，下架时间不可早于活动结束时间！");
            }
        } catch (ParseException e) {
            LOGGER.error("时间格式不合法！{}", e);;
            return ResultDtoFactory.toNack("时间格式不合法！");
        }
        
        long userId = AuthorityContext.getCurrentUser().getUserId();
        // 保存选择的上下架时间
        kdProductSaleDao.saveSaleInfo(saleDto.getProductId(), saleStatus, onSaleDate, offSaleDate, userId);
        return ResultDtoFactory.toAck("操作成功");
    }

    @Override
    @Transactional
    public ResultDto<String> putOffSale(Long productId) {
        if (kdGroupBuyService.isGroupBuyProduct(productId) && kdTwentyFourHoursService.isTfBuyProduct(productId)) {
            return ResultDtoFactory.toNack("该商品正在参加团购活动以及24小时活动，不可下架！");
        }
        
        if (kdGroupBuyService.isGroupBuyProduct(productId)) {
            return ResultDtoFactory.toNack("该商品正在参加团购活动，不可下架！");
        }
        
        if (kdTwentyFourHoursService.isTfBuyProduct(productId)) {
            return ResultDtoFactory.toNack("该商品正在参加24小时活动，不可下架！");
        }
        
        // 将团购活动绑定的商品下架
        kdGroupBuyService.saleOffUpdateStatus(productId);
        // 将24th下架
        kdTwentyFourHoursService.saleOffUpdateStatus(productId);
        long userId = AuthorityContext.getCurrentUser().getUserId();
        int row = kdProductSaleDao.updateSaleStatus(productId, KdSaleStatus.NO_SALE.getCode(), userId);
        if (0 < row) {
            return ResultDtoFactory.toAck("该商品已成功下架！");
        }
        
        return ResultDtoFactory.toNack("该商品下架失败！");
    }

    @Override
    @Transactional
    public KdProductSaleEntity saveSaleInfo(Long productId, Long userId) {
        KdProductSaleEntity saleEntity = kdProductSaleDao.findByProductId(productId);
        // 如果已经存在该商品信息
        if (null != saleEntity) {
            return saleEntity;
        }
        saleEntity = new KdProductSaleEntity();
        saleEntity.setCreateId(userId);
        saleEntity.setCreateDate(new Date());
        saleEntity.setProductId(productId);
        saleEntity.setSaleStatus(KdSaleStatus.NO_SALE.getCode());
        return kdProductSaleDao.save(saleEntity);
    }

    @Override
    public Boolean checkSaleStatus(List<Long> productIds) {
        int count = kdProductSaleDao.countBySaleStatus(productIds, KdSaleStatus.ON_SALE.getCode());
        return count > 0 ? true : false;
    }
    
}
