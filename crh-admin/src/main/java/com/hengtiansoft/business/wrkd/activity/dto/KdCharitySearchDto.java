/*
 * Project Name: wrw-admin
 * File Name: KdCharitySearchDto.java
 * Class Name: KdCharitySearchDto
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
package com.hengtiansoft.business.wrkd.activity.dto;

import com.hengtiansoft.common.dto.PagingDto;

/**
 * Class Name: KdCharitySearchDto
 * Description: 酷袋公益活动查询dto
 * @author zhongyidong
 *
 */
public class KdCharitySearchDto extends PagingDto<KdCharityDto>{
    
    private static final long serialVersionUID = 1L;

    private String name;
    
    private String startDate;
    
    private String endDate;
    
    private String status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
