/*
 * Project Name: wrw-admin
 * File Name: regionServiceImpl.java
 * Class Name: regionServiceImpl
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
package com.hengtiansoft.business.region.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengtiansoft.business.region.dto.ProvAndCityDto;
import com.hengtiansoft.business.region.dto.ProvDto;
import com.hengtiansoft.business.region.dto.RegionDto;
import com.hengtiansoft.business.region.dto.RegionOpenDto;
import com.hengtiansoft.business.region.service.RegionService;
import com.hengtiansoft.common.converter.ConverterService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.wrw.dao.SRegionDao;
import com.hengtiansoft.wrw.entity.SRegionEntity;
import com.hengtiansoft.wrw.enums.RegionLevelType;

/**
 * Class Name: regionServiceImpl
 * Description: TODO
 * 
 * @author chengminmiao
 */
@Service
public class RegionServiceImpl implements RegionService {
    @Autowired
    private SRegionDao sRegionDao;

    @Override
    public ResultDto<List<RegionDto>> findByLevelType(Integer levelType) {
        List<SRegionEntity> entities = sRegionDao.findByLevelType(levelType);
        List<RegionDto> dtos = new ArrayList<RegionDto>();
        if (null != entities && entities.size() > 0) {
            for (SRegionEntity entity : entities) {
                dtos.add(ConverterService.convert(entity, new RegionDto()));
            }
            return ResultDtoFactory.toAck("sucess", dtos);
        }
        return ResultDtoFactory.toNack("查询不到数据");
    }

    @Override
    public ResultDto<List<RegionDto>> findByParentId(Integer provinceId) {
        List<SRegionEntity> entities = sRegionDao.findByParentId(provinceId);
        List<RegionDto> dtos = new ArrayList<RegionDto>();
        if (null != entities && entities.size() > 0) {
            for (SRegionEntity entity : entities) {
                dtos.add(ConverterService.convert(entity, new RegionDto()));
            }
            return ResultDtoFactory.toAck("sucess", dtos);
        }
        return ResultDtoFactory.toNack("查询不到数据");
    }

    @Override
    public ResultDto<RegionDto> findProvByCity(Integer regionId) {
        SRegionEntity cityEntity = sRegionDao.findOne(regionId);
        if (cityEntity == null) {
            return ResultDtoFactory.toNack("查询不到数据");

        }
        SRegionEntity provEntity = sRegionDao.findOne(cityEntity.getParentId());
        if (provEntity == null) {
            return ResultDtoFactory.toNack("查询不到数据");
        }
        return ResultDtoFactory.toAck("sucess", ConverterService.convert(provEntity, new RegionDto()));
    }

    // 查询开放城市数量
    @Override
    public ResultDto<List<ProvDto>> findAllProvOpen() {
        List<Object[]> objects = sRegionDao.findAllProvOpen();
        List<ProvDto> dtos = new ArrayList<ProvDto>();
        for (Object[] obj : objects) {
            ProvDto dto = new ProvDto();
            dto.setId((Integer) obj[0]);
            dto.setName((String) obj[1]);
            dto.setCityNum((Integer) obj[2]);
            dto.setOpenCityNum((Integer) obj[3]);
            dtos.add(dto);
        }
        return ResultDtoFactory.toAck("", dtos);
    }

    // 查询省下面的开放城市
    @Override
    public ResultDto<List<RegionDto>> findProvOpenCity(Integer regionId) {
        List<SRegionEntity> entities = sRegionDao.findByParentId(regionId);
        List<RegionDto> dtos = new ArrayList<RegionDto>();
        for (SRegionEntity entity : entities) {
            RegionDto dto = new RegionDto();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setOpenFlag(entity.getOpenFlag());
            dtos.add(dto);
        }
        return ResultDtoFactory.toAck("", dtos);
    }

    @Override
    public List<SRegionEntity> findCitiesByIds(List<Integer> ids) {
        // 从缓存获取所有城市
        List<SRegionEntity> cities = findAllCities();
        // 如果条件id列表不为空，则从所有的城市里去根据id匹配
        if (!CollectionUtils.isEmpty(ids)) {
            int citiesSize = cities.size() - 1;
            for (int i = citiesSize; i >= 0; i--) {
                // 如果当前城市不在条件id列表中，则从结果集中移除
                if (!ids.contains(cities.get(i).getId())) {
                    cities.remove(i);
                }
            }
        }
        return cities;
    }

    @Override
    public List<SRegionEntity> findAllCities() {
        return sRegionDao.findByLevelType(RegionLevelType.CITY.getKey());
    }

    @Override
    public ResultDto<List<RegionOpenDto>> findOpenCity() {
        List<SRegionEntity> entities = sRegionDao.findByOpenFlag();
        List<RegionOpenDto> list = new ArrayList<RegionOpenDto>();
        Map<Integer, RegionOpenDto> map = new LinkedHashMap<Integer, RegionOpenDto>();
        for (SRegionEntity sRegionEntity : entities) {
            RegionOpenDto rdto = new RegionOpenDto();
            rdto.setRegionId(sRegionEntity.getParentId());
            String[] names = sRegionEntity.getMergerName().split(",");
            rdto.setRegionName(names.length > 1 ? names[1] : null);
            RegionOpenDto dto = new RegionOpenDto();
            dto.setRegionId(sRegionEntity.getId());
            dto.setRegionName(sRegionEntity.getName());
            dto.setParentId(sRegionEntity.getParentId());
            if (rdto.getRegionId().equals(0)) {
                map.put(dto.getRegionId(), dto);
            } else {
                if (map.containsKey(dto.getParentId())) {
                    map.get(dto.getParentId()).getChildrenList().add(dto);
                } else {
                    rdto.getChildrenList().add(dto);
                    map.put(rdto.getRegionId(), rdto);
                }
            }
        }

        for (Map.Entry<Integer, RegionOpenDto> entry : map.entrySet()) {
            list.add(entry.getValue());
        }
        return ResultDtoFactory.toAck("success", list);
    }

    /**
     * 地址配置查询省市信息
     */
    @Override
    public ResultDto<List<ProvAndCityDto>> findProvAndCity() {
        List<SRegionEntity> provEntities = sRegionDao.findByLevelTypeOrderByPinyinAsc(RegionLevelType.PROVINCE.getKey());
        LinkedHashMap<Integer, ProvAndCityDto> provMap = new LinkedHashMap<Integer, ProvAndCityDto>();
        for (SRegionEntity entity : provEntities) {
            ProvAndCityDto dto = new ProvAndCityDto();
            dto.setFirstLetter(entity.getPinyin().substring(1, 2));
            dto.setProvId(entity.getId());
            dto.setProvName(entity.getName());
            provMap.put(dto.getProvId(), dto);
        }
        List<SRegionEntity> cityEntities = sRegionDao.findByLevelType(RegionLevelType.CITY.getKey());
        for (SRegionEntity entity : cityEntities) {
            provMap.get(entity.getParentId()).setCityDto(ConverterService.convert(entity, RegionDto.class));
        }
        List<ProvAndCityDto> dtoList = new ArrayList<ProvAndCityDto>(provMap.values());
        return ResultDtoFactory.toAck("", dtoList);
    }

    @Override
    public SRegionEntity findbyCityAndProv(String city, String province) {
        List<SRegionEntity> list = sRegionDao.findByName(city);
        if(CollectionUtils.isNotEmpty(list)){
            for (SRegionEntity sRegionEntity : list) {
                if(sRegionEntity.getMergerName().contains(province)){
                    return sRegionEntity;
                }
            }
        }
        return null;
    }
}
