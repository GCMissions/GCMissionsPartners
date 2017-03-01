/*
 * Project Name: wrw-job
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
package com.hengtiansoft.task.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.task.service.KdProductSaleService;
import com.hengtiansoft.wrw.dao.KdProductSaleDao;
import com.hengtiansoft.wrw.dao.KdTeamBuyProductDao;
import com.hengtiansoft.wrw.dao.KdTwentyFourHoursDao;
import com.hengtiansoft.wrw.entity.KdProductSaleEntity;
import com.hengtiansoft.wrw.enums.KdSaleStatus;
import com.hengtiansoft.wrw.enums.KdTeamBuyStatusEnum;

/**
 * Class Name: KdProductSaleServiceImpl
 * Description: 酷袋商品上下架
 * @author zhongyidong
 *
 */
@Service
public class KdProductSaleServiceImpl implements KdProductSaleService {

    @Autowired
    private KdProductSaleDao kdProductSaleDao;
    @Autowired
    private KdTeamBuyProductDao kdTeamBuyProductDao;
    @Autowired
    private KdTwentyFourHoursDao kdTwentyFourHoursDao;
    
    @Override
    @Transactional
    public void putOnSale() {
        List<KdProductSaleEntity> saleEntitys = kdProductSaleDao.putOnSale(KdSaleStatus.WAIT_SALE.getCode());
        if (CollectionUtils.isNotEmpty(saleEntitys)) {
            Date curDate = new Date();
            for (KdProductSaleEntity saleEntity : saleEntitys) {
                saleEntity.setSaleStatus(KdSaleStatus.ON_SALE.getCode());
                saleEntity.setModifyId(0L);
                saleEntity.setModifyDate(curDate);
            }
        }
    }

    @Override
    @Transactional
    public void putOffSale() {
        List<KdProductSaleEntity> saleEntitys = kdProductSaleDao.findOffSaleingProducts(KdSaleStatus.ON_SALE.getCode());
        if (CollectionUtils.isNotEmpty(saleEntitys)) {
            String notStart = KdTeamBuyStatusEnum.NOSTART.getCode();
            String notSale = KdTeamBuyStatusEnum.PRODUCT_NOT_SALE.getCode();
            List<Long> productIds = new ArrayList<Long>(saleEntitys.size());
            Date curDate = new Date();
            for (KdProductSaleEntity saleEntity : saleEntitys) {
                productIds.add(saleEntity.getProductId());
                // 将关联的活动下架
                kdTeamBuyProductDao.updateStatus(saleEntity.getProductId(), notSale, notStart);
                kdTwentyFourHoursDao.updateStatus(saleEntity.getProductId(), notSale, notStart);
                
                saleEntity.setOnTime(null);
                saleEntity.setOffTime(null);
                saleEntity.setSaleStatus(KdSaleStatus.NO_SALE.getCode());
                
                saleEntity.setModifyId(0L);
                saleEntity.setModifyDate(curDate);
            }
        }

    }

}
