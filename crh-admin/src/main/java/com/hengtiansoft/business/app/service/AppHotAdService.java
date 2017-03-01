/*
 * Project Name: wrw-admin
 * File Name: AppHotAdService.java
 * Class Name: AppHotAdService
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
package com.hengtiansoft.business.app.service;

import java.util.List;

import com.hengtiansoft.business.activity.dto.ActivityCopyPageDto;
import com.hengtiansoft.business.app.dto.AppAddHotAdDto;
import com.hengtiansoft.business.app.dto.AppAdvertiseDto;
import com.hengtiansoft.business.app.dto.SearchAppHotAdDto;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.wrw.entity.AppHotAdEntity;

/**
* Class Name: AppHotAdService
* Description: TODO
* @author qianqianzhu
*
*/
public interface AppHotAdService {

    /**
    * Description: TODO
    *
    * @param dto
    */
    void findAppHotAd(SearchAppHotAdDto dto);

    /**
    * Description: TODO
    *
    * @param adId
    * @param description
    * @param url
    * @return
    */
    ResultDto<?> editorDesc(Long adId, String description, String url);

    /**
    * Description: TODO
    *
    * @param addAdDto
    * @return
    */
    AppHotAdEntity updateOrInsert(AppAddHotAdDto addAdDto);

    /**
    * Description: TODO
    *
    * @param adId
    * @param sort
    * @return
    */
    Integer deleteAd(Long adId, Integer sort);

    /**
    * Description: TODO
    *
    * @param activityCopyPageDto
    */
    void search(ActivityCopyPageDto activityCopyPageDto);

    /**
    * Description: TODO
    *
    * @param code
    * @return
    */
    List<AppAdvertiseDto> getAdvertise(String code);

    /**
    * Description: TODO
    *
    * @param adId
    * @param sort
    * @return
    */
    ResultDto<?> upOrDownAd(Long adId, Integer sort);

}
