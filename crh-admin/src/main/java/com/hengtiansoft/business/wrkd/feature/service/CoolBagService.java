/*
 * Project Name: wrw-admin
 * File Name: CoolBagService.java
 * Class Name: CoolBagService
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
package com.hengtiansoft.business.wrkd.feature.service;

import java.util.List;

import com.hengtiansoft.business.wrkd.feature.dto.FeatureInfoDto;
import com.hengtiansoft.business.wrkd.feature.dto.FeaturePageDto;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.wrw.entity.CoolbagFeatureEntity;
import com.hengtiansoft.wrw.entity.CoolbagImageEntity;
import com.hengtiansoft.wrw.enums.AppImageType;

/**
 * Class Name: CoolBagService Description: TODO
 * 
 * @author kangruan
 *
 */
public interface CoolBagService {

    /**
     * 
     * Description: 获取酷袋图片列表
     *
     * @param type
     *            图片类型
     */
    List<CoolbagImageEntity> findImageList(AppImageType type);
    
    /**
     * 
     * Description: 更新图片信息
     *
     * @param entity
     * @return
     */
    CoolbagImageEntity updateImage(CoolbagImageEntity entity);

    /**
     * 
     * Description: 获取专辑分页信息
     *
     * @param pageDto
     */
    void findFeaturePage(FeaturePageDto pageDto);

    /**
     * 
     * Description: 保存或更新 专辑信息
     *
     * @param entity
     * @return
     */
    CoolbagFeatureEntity saveOrUpdate(FeatureInfoDto dto);

    /**
     * 
     * Description: 根据ID查找专辑信息
     *
     * @param id
     * @return
     */
    FeatureInfoDto findFeature(Long id);

    /**
     * 
     * Description: 修改排序
     *
     * @param id  专辑id
     * @param sort 专辑 序号
     * @param type asc：升序 desc：降序
     */
    void updateFeatureSort(Long id, Integer sort, String type);
    
    
    /**
     * Description:删除专辑操作
     * 
     * @param id
     */
    ResultDto<String> deleteFeature(Long id);
    
    /**
     * Description:专辑上下架操作
     * @param id
     * @return
     */
	ResultDto<String> sheifFeature(Long id);
	/**
	 * 删除轮播广告
	 * @param id
	 * @return
	 */
	ResultDto<String> deleteImage(Long id);
}
