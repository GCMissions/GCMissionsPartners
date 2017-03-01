/*
 * Project Name: wrw-admin
 * File Name: LianyijiaConsumeReqDto.java
 * Class Name: LianyijiaConsumeReqDto
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
 * Class Name: LianyijiaConsumeReqDto
 * Description: 
 * @author xiaoluzhou
 *
 */
public class LianyijiaConsumeReqDto extends LianyijiaBaseApplicationReqDto{
    
    private static final long serialVersionUID = -6090929976685663775L;

    //金额yuan
    private String amount;
    
    private String icData;
    
    private String memo;
    
    private String orderNo;
    
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getIcData() {
        return icData;
    }

    public void setIcData(String icData) {
        this.icData = icData;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

}
