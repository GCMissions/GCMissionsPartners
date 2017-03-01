/*
 * Project Name: wrw-admin
 * File Name: OrgStockSearchDto.java
 * Class Name: OrgStockSearchDto
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

import com.hengtiansoft.common.dto.PagingDto;

/**
 * Class Name: OrgStockSearchDto Description: TODO
 * 
 * @author xianghuang
 *
 */
public class OrgStockSearchDto extends PagingDto<OrgStockSearchRecordDto> {

    private static final long serialVersionUID = 1L;

    // 商家编码
    private String orgCode;

    // 商品名称
    private String orgName;

    // 商家类别
    private String orgCate;

    // 库存状态
    private String orgState;

    // 库存百分比
    private Double orgPct;

    // 关联区域平台商
    private Long orgP;

    private List<Long> orgIds;

    public Long getOrgP() {
        return orgP;
    }

    public void setOrgP(Long orgP) {
        this.orgP = orgP;
    }

    public List<Long> getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(List<Long> orgIds) {
        this.orgIds = orgIds;
    }

    public Double getOrgPct() {
        return orgPct;
    }

    public void setOrgPct(Double orgPct) {
        this.orgPct = orgPct;
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

    public String getOrgCate() {
        return orgCate;
    }

    public void setOrgCate(String orgCate) {
        this.orgCate = orgCate;
    }

    public String getOrgState() {
        return orgState;
    }

    public void setOrgState(String orgState) {
        this.orgState = orgState;
    }

}
