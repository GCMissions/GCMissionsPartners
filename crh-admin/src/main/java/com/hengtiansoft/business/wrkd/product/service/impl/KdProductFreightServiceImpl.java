/*
 * Project Name: wrw-admin
 * File Name: KdProductFreightServiceImpl.java
 * Class Name: KdProductFreightServiceImpl
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.business.wrkd.product.dto.KdProductFreightDto;
import com.hengtiansoft.business.wrkd.product.service.KdProductFreightService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.converter.ConverterService;
import com.hengtiansoft.wrw.dao.KdProductFreightDao;
import com.hengtiansoft.wrw.entity.KdProductFreightEntity;

/**
 * Class Name: KdProductFreightServiceImpl
 * Description: 酷袋商品运费
 * @author zhongyidong
 *
 */
@Service
public class KdProductFreightServiceImpl implements KdProductFreightService {
    // 其他地区
    private static final String OTHER = "其他";
    
    @Autowired
    private KdProductFreightDao kpProductFreightDao;
    
    @Override
    public Map<String, List<KdProductFreightDto>> findFreightInfo(Long productId) {
        List<KdProductFreightEntity> freightEntitys = kpProductFreightDao.findByProductId(productId);
        if (!CollectionUtils.isEmpty(freightEntitys)) {
            Map<String, List<KdProductFreightDto>> freightMap = new HashMap<String, List<KdProductFreightDto>>(freightEntitys.size());
            KdProductFreightDto freightDto = null;
            List<KdProductFreightDto> freightDtos = null;
            for (KdProductFreightEntity freightEntity : freightEntitys) {
                freightDto = ConverterService.convert(freightEntity, KdProductFreightDto.class);
                if (OTHER.equals(freightEntity.getRegionName())) {
                    freightDtos = new ArrayList<KdProductFreightDto>();
                    freightDtos.add(freightDto);
                    freightMap.put(OTHER, freightDtos);
                } else {
                    if (freightMap.containsKey(freightEntity.getPrice().toString())) {
                        freightDtos = freightMap.get(freightEntity.getPrice().toString());
                        freightDtos.add(freightDto);
                    } else {
                        freightDtos = new ArrayList<KdProductFreightDto>();
                        freightDtos.add(freightDto);
                        freightMap.put(freightEntity.getPrice().toString(), freightDtos);
                    }
                }
            }
            return freightMap;
        }
        return null;
    }

    @Override
    @Transactional
    public List<KdProductFreightEntity> saveFreightInfo(Long productId, List<KdProductFreightDto> freightInfo) {
        if (CollectionUtils.isEmpty(freightInfo)) {
            return null;
        }
        // 删除原有的运费信息
        kpProductFreightDao.deleteByProductId(productId);
        List<KdProductFreightEntity> freightEntitys = new ArrayList<KdProductFreightEntity>(freightInfo.size());
        KdProductFreightEntity freightEntity = null;
        Date curDate = new Date();
        Long userId = AuthorityContext.getCurrentUser().getUserId();
        for (KdProductFreightDto freightDto : freightInfo) {
            freightEntity = ConverterService.convert(freightDto, KdProductFreightEntity.class);
            freightEntity.setProducId(productId);
            freightEntity.setCreateId(userId);
            freightEntity.setCreateDate(curDate);
            freightEntitys.add(freightEntity);
        }
        
        return kpProductFreightDao.save(freightEntitys);
    }

}
