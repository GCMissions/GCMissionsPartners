/*
 * Project Name: wrw-admin
 * File Name: LianyijiaCancelRespData.java
 * Class Name: LianyijiaCancelRespData
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
 * Class Name: LianyijiaCancelRespData
 * Description: 
 * @author xiaoluzhou
 *
 */
public class LianyijiaCancelRespData extends LianyijiaRespBaseDto{
    
    private static final long serialVersionUID = -2798361235322327119L;

    private String amount;
    
    private String cent;
    
    private String orderNo;
    
    private String referNo;
    
    private String respCode;
    
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

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }
}
