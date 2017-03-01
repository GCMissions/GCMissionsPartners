/*
 * Project Name: kd-wechat
 * File Name: KdTfRecordRepository.java
 * Class Name: KdTfRecordRepository
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
package com.hengtiansoft.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.KdTfRecordEntity;

public interface KdTfRecordRepository extends JpaRepository<KdTfRecordEntity, Long> {

    @Modifying
    @Query(value = "update kd_tf_record r left join (select t.RECORD_ID,t.money from "
            + "(select b.RECORD_ID,sum(b.BARGAIN_PRICE) money from kd_tf_bargain_record b "
            + "group by b.RECORD_ID) t ) nt on r.RECORD_ID = nt.RECORD_ID "
            + "set r.RETURN_TYPE = '2',r.WELFARE_MONEY = nt.money "
            + "where r.RETURN_TYPE = '0' and r.STATUS = '1' and timestampdiff(minute,r.SHARE_TIME,now()) > "
            + "(select sum(PARA_VALUE1) from s_basic_para p where p.TYPE_ID in (13,14))", nativeQuery = true)
    Integer updateReturnType();
}
