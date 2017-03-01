/*
 * Project Name: wrw-admin
 * File Name: OrgStockSearchRecordDto.java
 * Class Name: OrgStockSearchRecordDto
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

/**
 * Class Name: OrgStockSearchRecordDto Description:
 * 
 * @author xianghuang
 *
 */
public class OrgStockSearchRecordDto {
    // 商家id
    private Long orgId;

    // 商家编码
    private String orgCode;

    // 商家名称
    private String orgName;

    // 商家类别
    private String orgCate;

    // 库存总量
    private Long stockNum;

    // 安全库存
    private Long safeNum;

    // 标准库存
    private Long standardNum;

    // 库存状态
    private String orgState;

    private String goodName;

    private String specs;

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
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

    public Long getSafeNum() {
        return safeNum;
    }

    public void setSafeNum(Long safeNum) {
        this.safeNum = safeNum;
    }

    public Long getStandardNum() {
        return standardNum;
    }

    public void setStandardNum(Long standardNum) {
        this.standardNum = standardNum;
    }

    public String getOrgState() {
        return orgState;
    }

    public void setOrgState(String orgState) {
        this.orgState = orgState;
    }
}
