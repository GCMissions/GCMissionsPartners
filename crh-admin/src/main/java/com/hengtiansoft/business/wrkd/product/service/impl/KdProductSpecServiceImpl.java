/*
 * Project Name: wrw-admin
 * File Name: KdProductSpecService.java
 * Class Name: KdProductSpecService
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.hengtiansoft.business.wrkd.product.dto.KdProductSpecDto;
import com.hengtiansoft.business.wrkd.product.dto.KdProductStockDetailDto;
import com.hengtiansoft.business.wrkd.product.service.KdProductSpecService;
import com.hengtiansoft.business.wrkd.product.service.KdProductStockDetailService;
import com.hengtiansoft.wrw.dao.KdProductStockDao;
import com.hengtiansoft.wrw.entity.KdProductStockEntity;

/**
 * Class Name: KdProductSpecService
 * Description: 酷袋商品规格
 * @author zhongyidong
 *
 */
@Service
public class KdProductSpecServiceImpl implements KdProductSpecService {

    @Autowired
    private KdProductStockDao kdProductStockDao;
    
    @Autowired
    private KdProductStockDetailService kdProductStockDetailService;
    
    @Override
    @SuppressWarnings("unchecked")
    public List<KdProductSpecDto> findSpecInfo(Long productId) {
        List<KdProductSpecDto> specDtos = new ArrayList<KdProductSpecDto>();
        KdProductStockEntity productStock = kdProductStockDao.findByProductId(productId);
        if (null != productStock && StringUtils.isNotBlank(productStock.getSpecInfo())) {
            JSONArray array = JSONArray.fromObject(productStock.getSpecInfo());
            for (Iterator<JSONObject> iter = array.iterator(); iter.hasNext();) {
                JSONObject object = (JSONObject)iter.next();
                specDtos.add((KdProductSpecDto)JSONObject.toBean(object, KdProductSpecDto.class));
            }
        }
        return specDtos;
    }

    @Override
    public List<KdProductStockDetailDto> findPriceDetail(Long productId) {
        KdProductStockEntity productStock = kdProductStockDao.findByProductId(productId);
        if (null != productStock) {
            return kdProductStockDetailService.findByStockId(productStock.getId());
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, List<String>> getProductSpecMapById(Long productId) {
        Map<String,List<String>> productSpecMap = new LinkedHashMap<String, List<String>>();
        KdProductStockEntity productStock = kdProductStockDao.findByProductId(productId);
        if (null != productStock && StringUtils.isNotBlank(productStock.getSpecInfo())) {
            JSONArray array = JSONArray.fromObject(productStock.getSpecInfo());
            for (Iterator<JSONObject> iter = array.iterator(); iter.hasNext();) {
                JSONObject object = (JSONObject)iter.next();
                KdProductSpecDto kdProductSpecDto = (KdProductSpecDto)JSONObject.toBean(object, KdProductSpecDto.class);
                productSpecMap.put(kdProductSpecDto.getMainSpec(), kdProductSpecDto.getSubSpecs());
            }
        }
        return productSpecMap;
    }

    @Override
    public Map<String, Long> findPriceMapDetail(Long productId) {
        KdProductStockEntity productStock = kdProductStockDao.findByProductId(productId);
        if (null != productStock) {
            List<KdProductStockDetailDto> kpsdList =  kdProductStockDetailService.findByStockId(productStock.getId());
            Map<String, Long> map = new HashMap<String, Long>();
            if(!CollectionUtils.isEmpty(kpsdList)){
                for (KdProductStockDetailDto kdProductStockDetailDto : kpsdList) {
                    map.put(kdProductStockDetailDto.getSpecGroup(), kdProductStockDetailDto.getPrice());
                }
                return map;
            }
        }
        return null;
    }

}
