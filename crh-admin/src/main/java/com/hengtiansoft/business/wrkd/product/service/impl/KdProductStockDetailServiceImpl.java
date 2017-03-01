/*
 * Project Name: wrw-admin
 * File Name: KdProductStockDetailServiceImpl.java
 * Class Name: KdProductStockDetailServiceImpl
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
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.business.wrkd.product.dto.KdProductStockDetailDto;
import com.hengtiansoft.business.wrkd.product.service.KdProductStockDetailService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.converter.ConverterService;
import com.hengtiansoft.wrw.dao.KdProductStockDetailDao;
import com.hengtiansoft.wrw.entity.KdProductStockDetailEntity;

/**
 * Class Name: KdProductStockDetailServiceImpl
 * Description: 酷袋商品库存详情
 * @author zhongyidong
 *
 */
@Service
public class KdProductStockDetailServiceImpl implements KdProductStockDetailService {
    // 正常标志
    private static final String NORMAL = "0";
    // 删除标志
    private static final String DELETED = "1";
    
    @Autowired
    private KdProductStockDetailDao kdProductStockDetailDao;
    
    @Override
    public List<KdProductStockDetailDto> findByStockId(Long stockId) {
        List<KdProductStockDetailDto> stockInfo = new ArrayList<KdProductStockDetailDto>();
        List<KdProductStockDetailEntity> stockDetails = kdProductStockDetailDao.findByStockId(stockId, NORMAL);
        if (CollectionUtils.isNotEmpty(stockDetails)) {
            for (KdProductStockDetailEntity detailEntity : stockDetails) {
                stockInfo.add(ConverterService.convert(detailEntity, KdProductStockDetailDto.class));
            }
        }
        return stockInfo;
    }

    @Override
    @Transactional
    public List<KdProductStockDetailEntity> saveStockDetail(Long stockId, List<KdProductStockDetailDto> stockDetailDtos) {
        // 库存详情
        int specGroupLen = stockDetailDtos.size();
        List<String> specGroups = new ArrayList<String>(specGroupLen);
        List<KdProductStockDetailEntity> stockDetails = new ArrayList<KdProductStockDetailEntity>(specGroupLen);
        Date curDate = new Date();
        KdProductStockDetailEntity stockDetail = null;
        Long userId = AuthorityContext.getCurrentUser().getUserId();
        for (KdProductStockDetailDto stockDetailDto : stockDetailDtos) {
            specGroups.add(stockDetailDto.getSpecGroup());
            stockDetail = kdProductStockDetailDao.findBySpecGroup(stockId, stockDetailDto.getSpecGroup(), NORMAL);
            if (null == stockDetail) {
                stockDetail = new KdProductStockDetailEntity();
                stockDetail.setCreateId(userId);
                stockDetail.setCreateDate(curDate);
            } else {
                stockDetail.setModifyId(userId);
                stockDetail.setModifyDate(curDate);
            }
            stockDetail.setIsDeleted(NORMAL);
            stockDetail.setStockId(stockId);
            stockDetail.setPrice(stockDetailDto.getPrice());
            stockDetail.setVipPrice(stockDetailDto.getVipPrice());
            stockDetail.setSpecGroup(stockDetailDto.getSpecGroup());
            stockDetail.setOriginAmount(stockDetailDto.getOriginAmount());
            stockDetail.setRestAmount(stockDetailDto.getOriginAmount());
            stockDetail.setLimitAmount(stockDetailDto.getLimitAmount());
            stockDetails.add(stockDetail);
        }
        // 删除规格（此次不包含的规格对应的库存信息）
        kdProductStockDetailDao.deleteSpecGroup(stockId, specGroups, DELETED);
        
        return kdProductStockDetailDao.save(stockDetails);
    }

}
