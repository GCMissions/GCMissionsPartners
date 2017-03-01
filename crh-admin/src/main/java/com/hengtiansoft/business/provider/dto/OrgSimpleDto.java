/*
 * Project Name: wrw-admin
 * File Name: OrgDto.java
 * Class Name: OrgDto
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

import java.io.Serializable;
import java.util.Date;

/**
 * Class Name: OrgDto
 * Description: TODO
 * 
 * @author chengminmiao
 */
public class OrgSimpleDto implements Serializable {

    private static final long serialVersionUID = -5403654589215099293L;

    private Long              orgId;                                   // 内部ID

    private String            orgCode;                                 // 组织CODE

    private String            orgName;                                 // 组织名称

    private String            orgType;                                 // 组织类型

    private String            status;                                  // 状态

    private String            address;                                 // 地址

    private String            phone;                                   // 联系电话

    private String            contact;                                 // 联系人

    private Date              createDate;                              // 创建日期

    private String            provinceName;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

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

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

}
