/*
 * Project Name: wrw-admin
 * File Name: ShopStaticServiceImpl.java
 * Class Name: ShopStaticServiceImpl
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
package com.hengtiansoft.business.shopMall.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengtiansoft.business.shopMall.service.ShopStaticService;
import com.hengtiansoft.wrw.dao.PShiefDao;
import com.hengtiansoft.wrw.dao.PProductDao;
import com.hengtiansoft.wrw.dao.SRegionDao;
import com.hengtiansoft.wrw.entity.PProductEntity;
import com.hengtiansoft.wrw.entity.SRegionEntity;

/**
 * Class Name: ShopStaticServiceImpl
 * Description: TODO
 * 
 * @author zhisongliu
 */
@Service
public class ShopStaticServiceImpl implements ShopStaticService {

    @Autowired
    private PShiefDao   priceDao;

    @Autowired
    private PProductDao productDao;

    @Autowired
    private SRegionDao  sRegionDao;

    @Override
    public List<SRegionEntity> findByRegions(List<Integer> list) {
        List<SRegionEntity> listEntity = new ArrayList<SRegionEntity>();
        if (list != null && list.size() > 0) {
            boolean idFlag = false;
            List<Integer> listIds = new ArrayList<Integer>();
            for (Integer id : list) {
                if (id.equals(Integer.valueOf(100000))) {
                    idFlag = true;
                } else {
                    listIds.add(id);
                }
            }
            List<SRegionEntity> listE = sRegionDao.findAll(listIds);
            List<SRegionEntity> listEn = new ArrayList<SRegionEntity>();
            if (idFlag) {// 说明包含了无网点配送城市
                listEn = sRegionDao.findAllOther();
            }
            listEntity.addAll(listE);
            listEntity.addAll(listEn);
        }
        return listEntity;
    }

    @Override
    public List<PProductEntity> findAllShief() {
        List<Long> ids = priceDao.findByShelf();
        return productDao.findAll(ids);
    }

}
