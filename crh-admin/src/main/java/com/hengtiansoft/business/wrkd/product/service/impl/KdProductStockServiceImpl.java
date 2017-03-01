/*
 * Project Name: wrw-admin
 * File Name: KdProductStockServiceImpl.java
 * Class Name: KdProductStockServiceImpl
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.business.wrkd.product.dto.KdProductStockDto;
import com.hengtiansoft.business.wrkd.product.service.KdProductSpecService;
import com.hengtiansoft.business.wrkd.product.service.KdProductStockDetailService;
import com.hengtiansoft.business.wrkd.product.service.KdProductStockService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.converter.ConverterService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.wrw.dao.KdProductDao;
import com.hengtiansoft.wrw.dao.KdProductStockDao;
import com.hengtiansoft.wrw.entity.KdProductStockEntity;

/**
 * Class Name: KdProductStockServiceImpl
 * Description: 酷袋商品库存
 * @author zhongyidong
 *
 */
@Service
public class KdProductStockServiceImpl implements KdProductStockService {

    @Autowired
    private KdProductDao kdProductDao;
    
    @Autowired
    private KdProductStockDao kdProductStockDao;
    
    @Autowired
    private KdProductSpecService kdProductSpecService;
    
    @Autowired
    private KdProductStockDetailService kdProductStockDetailService;
    
    @Override
    public KdProductStockDto findByProductId(Long productId) {
        KdProductStockEntity stockEntity = kdProductStockDao.findByProductId(productId);
        if (null != stockEntity) {
            return ConverterService.convert(stockEntity, KdProductStockDto.class);
        }
        return null;
    }
    
    @Override
    public KdProductStockDto findDetailByProductId(Long productId) {
        KdProductStockDto stockDto = findByProductId(productId);
        if (null != stockDto) {
            stockDto.setStockDetails(kdProductStockDetailService.findByStockId(stockDto.getId()));
            return stockDto;
        }
        return null;
    }

    @Override
    @Transactional
    public ResultDto<Long> saveStock(KdProductStockDto stockDto) {
        if (StringUtils.isBlank(stockDto.getSpecInfo())) {
            return ResultDtoFactory.toNack("规格信息为空！");
        }
        if (CollectionUtils.isEmpty(stockDto.getPriceList())) {
            return ResultDtoFactory.toNack("价格信息为空！");
        }
        if (CollectionUtils.isEmpty(stockDto.getVipPriceList())) {
            return ResultDtoFactory.toNack("VIP价格信息为空！");
        }
        if (CollectionUtils.isEmpty(stockDto.getStockDetails())) {
            return ResultDtoFactory.toNack("库存信息为空！");
        }
        
        KdProductStockEntity stockEntity = ConverterService.convert(stockDto, KdProductStockEntity.class);
        // 查询是否已有库存信息
        KdProductStockEntity stockOld = kdProductStockDao.findByProductId(stockDto.getProductId());
        Long userId = AuthorityContext.getCurrentUser().getUserId();
        if (null != stockOld) {
            stockEntity.setId(stockOld.getId());
            stockEntity.setCreateId(stockOld.getCreateId());
            stockEntity.setCreateDate(stockOld.getCreateDate());
            stockEntity.setModifyId(userId);
            stockEntity.setModifyDate(new Date());
        } else {
            stockEntity.setCreateId(userId);
            stockEntity.setCreateDate(new Date());
        }
        stockEntity = kdProductStockDao.save(stockEntity);
        
        // 库存详情
        kdProductStockDetailService.saveStockDetail(stockEntity.getId(), stockDto.getStockDetails());
        // 保存价格范围
        List<Integer> priceList = stockDto.getPriceList();
        List<Integer> vipPriceList = stockDto.getVipPriceList();
        // 移出0的会员价
        List<Integer> removeVipPrice = new ArrayList<Integer>();
        for (Integer vipPrice : vipPriceList) {
            if (vipPrice == 0) {
                removeVipPrice.add(vipPrice);
            }
        }
        Integer minVipPrice = 0;
        Integer maxVipPrice = 0;
        vipPriceList.removeAll(removeVipPrice);
        if (!vipPriceList.isEmpty()) {
            minVipPrice = Collections.min(vipPriceList);
            maxVipPrice = Collections.max(vipPriceList);
        }
        kdProductDao.updatePriceRange(stockDto.getProductId(),  Collections.min(priceList), Collections.max(priceList),
                minVipPrice, maxVipPrice);
        
        return ResultDtoFactory.toAck("保存成功", stockEntity.getId());
    }

}
