/*
 * Project Name: wrw-admin
 * File Name: OrgStockDetailDto.java
 * Class Name: OrgStockDetailDto
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
* Class Name: OrgStockDetailDto
* Description: 商家库存详情页、安全库存详情页Bean
* @author xianghuang
*
*/
public class OrgStockDetailDto {
    //商家编码
    private String orgCode;
    //商家名称
    private String orgName;
    //商家类别
    private String orgCate;
    
    //库存总量
    private Long stockNum;
    
    //联系人
    private String contact;
    //联系电话
    private String phone;
    
    List<GoodsStockDto> list;

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgCate() {
        return orgCate;
    }

    public void setOrgCate(String orgCate) {
        this.orgCate = orgCate;
    }

    public Long getStockNum() {
        return stockNum;
    }

    public void setStockNum(Long stockNum) {
        this.stockNum = stockNum;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<GoodsStockDto> getList() {
        return list;
    }

    public void setList(List<GoodsStockDto> list) {
        this.list = list;
    }

}
