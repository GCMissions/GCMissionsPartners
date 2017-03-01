/*
 * Project Name: wrw-admin
 * File Name: regionService.java
 * Class Name: regionService
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
package com.hengtiansoft.business.region.service;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.hengtiansoft.business.region.dto.ProvAndCityDto;
import com.hengtiansoft.business.region.dto.ProvDto;
import com.hengtiansoft.business.region.dto.RegionDto;
import com.hengtiansoft.business.region.dto.RegionOpenDto;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.xmemcached.constant.CacheType;
import com.hengtiansoft.wrw.entity.SRegionEntity;

/**
 * Class Name: regionService
 * Description: TODO
 * 
 * @author chengminmiao
 */
public interface RegionService {

    @Cacheable(value = CacheType.DEFAULT, key = "#levelType + '_RegionLevelType'")
    ResultDto<List<RegionDto>> findByLevelType(Integer levelType);

    @Cacheable(value = CacheType.DEFAULT, key = "#provinceId + '_RegionParentId'")
    ResultDto<List<RegionDto>> findByParentId(Integer provinceId);

    @Cacheable(value = CacheType.DEFAULT, key = "#regionId + '_RegionProvByCity'")
    ResultDto<RegionDto> findProvByCity(Integer regionId);

    ResultDto<List<ProvDto>> findAllProvOpen();

    ResultDto<List<RegionDto>> findProvOpenCity(Integer regionId);

    @Cacheable(value = CacheType.DEFAULT, key = "#'AllCities'")
    List<SRegionEntity> findAllCities();

    List<SRegionEntity> findCitiesByIds(List<Integer> ids);

    ResultDto<List<RegionOpenDto>> findOpenCity();

    /**
     * Description: 查询所有的省以及城市
     *
     * @return
     */
    @Cacheable(value = CacheType.DEFAULT, key = "'_ProvAndCity'")
    ResultDto<List<ProvAndCityDto>> findProvAndCity();

    /**
    * Description: TODO
    *
    * @param city
    * @param province
    * @return
    */
    SRegionEntity findbyCityAndProv(String city, String province);

}
