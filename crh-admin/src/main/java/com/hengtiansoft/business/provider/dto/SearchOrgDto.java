/*
 * Project Name: wrw-admin
 * File Name: searchOrgDto.java
 * Class Name: searchOrgDto
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
package com.hengtiansoft.business.provider.dto;

import java.util.Date;

import com.hengtiansoft.common.dto.PagingDto;

/**
 * Class Name: searchOrgDto
 * Description: TODO
 * 
 * @author chengminmiao
 */
public class SearchOrgDto extends PagingDto<OrgSimpleDto> {

    private static final long serialVersionUID = -1082867753589175299L;

    private String            orgCode;                                 // 组织编码

    private String            orgName;                                 // 组织名称，支持模糊查询

    private String            contact;                                 // 联系人

    private String            phone;                                   // 联系电话
    
    private String            status;                                  // 状态

    private Integer           provinceId;                              // 省ID

    private Integer           cityId;                                  // 市ID

    private Date              beginDate;                               // 开始时间

    private Date              endDate;                                 // 结束时间

    private Long              parentId;

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

    public Integer getProvinceId() {
        return provinceId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

}
