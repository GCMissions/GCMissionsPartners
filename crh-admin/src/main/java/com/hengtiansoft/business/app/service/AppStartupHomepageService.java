/*
 * Project Name: wrw-admin
 * File Name: AppStartupHomepageService.java
 * Class Name: AppStartupHomepageService
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

import com.hengtiansoft.business.app.dto.AppStartUpHomepageDto;
import com.hengtiansoft.business.app.dto.SearchAppStartUpHomepageDto;

/**
* Class Name: AppStartupHomepageService
* Description: TODO
* @author qianqianzhu
*
*/
public interface AppStartupHomepageService {

    /**
    * Description: get list
    *
    * @param dto
    */
    void findList(SearchAppStartUpHomepageDto dto);

    /**
    * Description: add or update 
    *
    * @param addAdDto
    */
    void updateOrInsert(AppStartUpHomepageDto addAdDto);

    /**
    * Description: delete
    *
    * @param id
    */
    void deleteEntity(Long id);

    /**
    * Description: TODO
    *
    * @param id
    * @return
    */
    AppStartUpHomepageDto findDto(String id);

}
