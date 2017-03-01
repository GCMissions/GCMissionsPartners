/*
 * Project Name: wrw-admin
 * File Name: OrgServicers.java
 * Class Name: OrgServicers
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
package com.hengtiansoft.business.provider.service;

import java.util.List;

import com.hengtiansoft.business.provider.dto.OrgDto;
import com.hengtiansoft.business.provider.dto.OrgSaveDto;
import com.hengtiansoft.business.provider.dto.SearchOrgDto;
import com.hengtiansoft.business.provider.dto.SearchSalerDto;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.wrw.entity.SOrgEntity;

/**
 * Class Name: OrgServicers
 * Description: TODO
 * 
 * @author chengminmiao
 */
public interface OrgService {

    void findOrg(SearchOrgDto dto, String orgType);

    OrgDto findById(Long orgId);

    ResultDto<String> update(OrgDto dto);

    ResultDto<String> save(OrgDto dto);
    
    ResultDto<String> saveSOrg(OrgSaveDto dto);

    ResultDto<String> resetPwd(Long orgId);

    void findSaler(SearchSalerDto dto, String orgType);
    
    ResultDto<String> getOrgCode();
    
    List<SOrgEntity> findByType(String orgType);
    
    ResultDto<String> editorOrg(OrgSaveDto dto);
    
    ResultDto<String> deleteOrg(String orgCode);
}
