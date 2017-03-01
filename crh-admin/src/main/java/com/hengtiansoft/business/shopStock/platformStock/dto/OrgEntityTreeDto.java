/*
 * Project Name: wrw-admin
 * File Name: OrgEntity.java
 * Class Name: OrgEntity
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
package com.hengtiansoft.business.shopStock.platformStock.dto;

import java.util.List;

/**
* Class Name: OrgEntity
* Description: TODO
* @author xianghuang
*
*/
public class OrgEntityTreeDto{
    //商家Id
    private Long orgId;
    //商家名称
    private String orgName;
    //商家库存
    private Long stockNum;
    //子节点
    List<OrgEntityTreeDto> childList;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Long getStockNum() {
        return stockNum;
    }

    public void setStockNum(Long stockNum) {
        this.stockNum = stockNum;
    }

    public List<OrgEntityTreeDto> getChildList() {
        return childList;
    }

    public void setChildList(List<OrgEntityTreeDto> childList) {
        this.childList = childList;
    }
 
}
