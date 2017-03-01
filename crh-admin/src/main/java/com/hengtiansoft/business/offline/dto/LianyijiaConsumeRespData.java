/*
 * Project Name: wrw-admin
 * File Name: LianyijiaConsumeRespData.java
 * Class Name: LianyijiaConsumeRespData
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
package com.hengtiansoft.business.offline.dto;

/**
 * Class Name: LianyijiaConsumeRespData
 * Description: 
 * @author xiaoluzhou
 *
 */
public class LianyijiaConsumeRespData extends LianyijiaRespBaseDto{

    private static final long serialVersionUID = 4715831476171336685L;

    private String amount;
    
    private String cent;
    
    private String feeAmout;
    
    private String feeCent;
    
    private String orderNo;
    
    private String referNo;
    
    private String orgCode;
    
    private String icData;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCent() {
        return cent;
    }

    public void setCent(String cent) {
        this.cent = cent;
    }

    public String getFeeAmout() {
        return feeAmout;
    }

    public void setFeeAmout(String feeAmout) {
        this.feeAmout = feeAmout;
    }

    public String getFeeCent() {
        return feeCent;
    }

    public void setFeeCent(String feeCent) {
        this.feeCent = feeCent;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getReferNo() {
        return referNo;
    }

    public void setReferNo(String referNo) {
        this.referNo = referNo;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getIcData() {
        return icData;
    }

    public void setIcData(String icData) {
        this.icData = icData;
    }
}
