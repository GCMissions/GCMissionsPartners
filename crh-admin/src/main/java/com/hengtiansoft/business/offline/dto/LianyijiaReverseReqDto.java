/*
 * Project Name: wrw-admin
 * File Name: LianyijiaReverseReqDto.java
 * Class Name: LianyijiaReverseReqDto
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
package com.hengtiansoft.business.offline.dto;

/**
 * Class Name: LianyijiaReverseReqDto
 * Description:
 * 
 * @author xiaoluzhou
 */
public class LianyijiaReverseReqDto extends LianyijiaBaseApplicationReqDto {

    private static final long serialVersionUID = -5462892242905746227L;

    // 金额元
    private String            amount;

    private String            icData;

    private String            memo;

    // 冲正单号，系统生成
    private String            orderNo;

    // 原订单号，需要冲正的订单号
    private String            origOrderNo;

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

    public String getOrigOrderNo() {
        return origOrderNo;
    }

    public void setOrigOrderNo(String origOrderNo) {
        this.origOrderNo = origOrderNo;
    }

}
