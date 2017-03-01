/*
 * Project Name: kd-wechat
 * File Name: KdCharityRecordReposity.java
 * Class Name: KdCharityRecordReposity
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
package com.hengtiansoft.wrw.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.KdCharityRecordEntity;

/**
 * Class Name: KdCharityRecordReposity
 * Description: 公益活动记录表DAO层
 * @author zhisongliu
 *
 */
public interface KdCharityRecordDao extends JpaRepository<KdCharityRecordEntity, Long>{
    
    /**
     * 
    * Description: 判断用户是否参与到这个公益活动中
    *
    * @param actId
    * @param featureId
    * @param memberId
    * @return
     */
    @Query(value ="select count(r) from KdCharityRecordEntity r where r.charityId =?1 and r.featureId =?2 and r.memberId =?3 and r.status = 1 ")
    int findByCharityIdAndFeatureIdAndMemberId(Long actId, Long featureId, Long memberId);
    
    /**
     * 
    * Description: 获取某个公益活动的参与总数
    *
    * @param key
    * @return
     */
    @Query(value ="select count(r) from KdCharityRecordEntity r where r.charityId =?1 and r.status =?2 ")
    int findCountByStutas(Long actId,String status);
 
}
