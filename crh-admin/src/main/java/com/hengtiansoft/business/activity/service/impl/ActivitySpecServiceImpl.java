/*
 * Project Name: wrw-admin
 * File Name: ActivitySpecServiceImpl.java
 * Class Name: ActivitySpecServiceImpl
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
package com.hengtiansoft.business.activity.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.business.activity.dto.ActivitySpecDto;
import com.hengtiansoft.business.activity.dto.ActivityStockDto;
import com.hengtiansoft.business.activity.service.ActivitySpecService;
import com.hengtiansoft.common.converter.ConverterService;
import com.hengtiansoft.common.exception.WRWException;
import com.hengtiansoft.wrw.dao.ActivitySpecDao;
import com.hengtiansoft.wrw.entity.ActivitySpec;
import com.hengtiansoft.wrw.entity.ActivityStock;

/**
 * Class Name: ActivitySpecServiceImpl
 * Description: 活动规格信息
 * @author zhongyidong
 *
 */
@Service
public class ActivitySpecServiceImpl implements ActivitySpecService {

    @Autowired
    private ActivitySpecDao actSpecDao;
    
    @Override
    public List<ActivitySpec> saveStock(ActivityStockDto actStockDto, List<String> subSpecList, ActivityStock actStock, Long userId) {
        List<ActivitySpec> actSpecList = new ArrayList<ActivitySpec>(); 
        for (ActivitySpecDto actSpecDto : actStockDto.getActSpecList()) {
            subSpecList.add(actSpecDto.getSubSpec());
            ActivitySpec actSpec = ConverterService.convert(actSpecDto, ActivitySpec.class);
            Long actSpecId = findIdByStockIdAndSpec(actStock.getId(), actSpec.getSubSpec());
            if (null != actSpecId) {
                // 更新操作
                actSpec.setId(actSpecId);
                actSpec.setModifyId(userId);
                actSpec.setModifyDate(new Date());
            } else {
                actSpec.setCreateId(userId);
            }
            actSpec.setPrices(actSpecDto.getPrices().toString());
            actSpec.setLimits(actSpecDto.getLimits().toString());
            actSpec.setVipPrices(actSpecDto.getVipPrices().toString());
            actSpec.setPriceDesc(actSpecDto.getPriceDescJson().toString());
            actSpec.setActStockId(actStock.getId());
            actSpecList.add(actSpec);
        }
        return actSpecDao.save(actSpecList);
    }

    @Override
    public List<ActivitySpec> findByStockId(Long actStockId) {
        return actSpecDao.findByStockId(actStockId);
    }

    @Override
    public Long findIdByStockIdAndSpec(Long actStockId, String subSpec) {
        return actSpecDao.findIdByStockIdAndSpec(actStockId, subSpec);
    }

    @Override
    @Transactional
    public Integer deleteByStockId(List<BigInteger> actStockIdList) {
        if (null == actStockIdList || actStockIdList.isEmpty()) {
            return null;
        }
        
        return actSpecDao.deleteByStockId(actStockIdList);
    }
    
    @Override
    @Transactional
    public void deleteByStockIdAndSpec(Long actStockId, List<String> subSpecList) {
        if (null == subSpecList || subSpecList.isEmpty()) {
            return;
        }
        int count = actSpecDao.countByStockIdAndSpec(actStockId, subSpecList);
        if (count > 0) {
            int row = actSpecDao.deleteByStockIdAndSpec(actStockId, subSpecList);
            if (row == 0) {
                throw new WRWException("删除活动规格记录失败！");
            }
        }
    }

    @Override
    public List<ActivitySpec> findFirstSubspecs(Long productId) {
        return actSpecDao.findFirstSubspecs(productId);
    }

}
