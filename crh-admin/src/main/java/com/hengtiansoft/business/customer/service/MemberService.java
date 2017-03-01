/*
 * Project Name: wrw-admin
 * File Name: MemberService.java
 * Class Name: MemberService
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
package com.hengtiansoft.business.customer.service;

import java.util.List;

import com.hengtiansoft.business.customer.dto.MemberDetailDto;
import com.hengtiansoft.business.customer.dto.MemberOrderDetailDto;
import com.hengtiansoft.business.customer.dto.MemberSearchDto;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.wrw.entity.MMemberEntity;

/**
* Class Name: MemberService
* Description: TODO
* @author xianghuang
*
*/
public interface MemberService {
    
    void search(MemberSearchDto dto);
    
    MemberDetailDto get(Long id);
    
    MemberOrderDetailDto getOrderDetail(String orderId);
    
    ResultDto<String> resetPwd(Long id);
    
    MMemberEntity findByLoginId(String loginId, String status);
    
    /**
     * 
    * Description: 查询所有有效的会员信息
    *
    * @param loginId
    * @param code
    * @return
    * @author chengchaoyin
     */
    List<MMemberEntity> findAllMember();
    
    List<MMemberEntity> findAllByPhones(List<String> memberPhones);
    
    MMemberEntity findByUserId(Long userId);
    
    MMemberEntity findById(Long memberId);
}
