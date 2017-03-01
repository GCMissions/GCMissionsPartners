/*
 * Project Name: wrw-admin
 * File Name: LianyijiaConsume.java
 * Class Name: LianyijiaConsume
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
 * Class Name: LianyijiaConsume
 * Description: TODO
 * @author xiaoluzhou
 *
 */
public class LianyijiaConsumeBean {
    
    private LianyijiaSystemParamBean systemParams;
    
    private String acctNo;
    
    private String amount;
    
    private String cardSeqNo;
    
    private String channelNo;
    
    private String checkCode;
    
    private String customNo;
    
    
    private String icData;
    
    private String ksn;
    
    private String memo;
    
    private String orderNo;
    
    private String paysEnum = "OUTER_PAY";
    
    private String pinValue;
    
    private String track2;
    
    private String track2Length;
    
    private String track3;
    
    private String track3Length;
    
    private String workKey = "0000000000000000";

    public LianyijiaSystemParamBean getSystemParams() {
        return systemParams;
    }

    public void setSystemParams(LianyijiaSystemParamBean systemParams) {
        this.systemParams = systemParams;
    }

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCardSeqNo() {
        return cardSeqNo;
    }

    public void setCardSeqNo(String cardSeqNo) {
        this.cardSeqNo = cardSeqNo;
    }

    public String getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(String channelNo) {
        this.channelNo = channelNo;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

    public String getCustomNo() {
        return customNo;
    }

    public void setCustomNo(String customNo) {
        this.customNo = customNo;
    }

    public String getIcData() {
        return icData;
    }

    public void setIcData(String icData) {
        this.icData = icData;
    }

    public String getKsn() {
        return ksn;
    }

    public void setKsn(String ksn) {
        this.ksn = ksn;
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

    public String getPaysEnum() {
        return paysEnum;
    }

    public String getPinValue() {
        return pinValue;
    }

    public void setPinValue(String pinValue) {
        this.pinValue = pinValue;
    }

    public String getTrack2() {
        return track2;
    }

    public void setTrack2(String track2) {
        this.track2 = track2;
    }

    public String getTrack2Length() {
        return track2Length;
    }

    public void setTrack2Length(String track2Length) {
        this.track2Length = track2Length;
    }

    public String getTrack3() {
        return track3;
    }

    public void setTrack3(String track3) {
        this.track3 = track3;
    }

    public String getTrack3Length() {
        return track3Length;
    }

    public void setTrack3Length(String track3Length) {
        this.track3Length = track3Length;
    }

    public String getWorkKey() {
        return workKey;
    }

}
