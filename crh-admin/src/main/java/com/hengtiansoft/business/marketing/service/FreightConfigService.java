/*
 * Project Name: wrw-admin
 * File Name: FreightConfigService.java
 * Class Name: FreightConfigService
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
package com.hengtiansoft.business.marketing.service;

import java.util.List;

import com.hengtiansoft.business.marketing.dto.BrandDto;
import com.hengtiansoft.business.marketing.dto.CateDto;
import com.hengtiansoft.business.marketing.dto.FreePostageDto;
import com.hengtiansoft.business.marketing.dto.FreePostageListDto;
import com.hengtiansoft.business.marketing.dto.FreightConfigListDto;
import com.hengtiansoft.common.dto.ResultDto;

/**
 * Class Name: FreightConfigService
 * Description: TODO
 * 
 * @author chengminmiao
 */
public interface FreightConfigService {

    /**
     * Description: 查询配置信息
     *
     * @param type
     * @return
     */
    FreightConfigListDto configList(String type);

    /**
     * Description: 保存
     *
     * @param dto
     * @param key
     * @return
     */
    ResultDto<String> configSave(FreightConfigListDto dto, String type);

    /**
     * Description: 查询分类
     *
     * @return
     */
    List<CateDto> findCate();

    /**
     * Description: 查询品牌
     *
     * @return
     */
    List<BrandDto> findBrand();

    void freeList(FreePostageListDto dto);

    ResultDto<String> saveFree(FreePostageDto dto);

}
