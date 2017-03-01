/*
 * Project Name: wrw-admin
 * File Name: LianyijiaBaseReqDto.java
 * Class Name: LianyijiaBaseReqDto
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

import java.io.Serializable;

/**
 * Class Name: LianyijiaBaseReqDto
 * Description: 
 * @author xiaoluzhou
 *
 */
public class LianyijiaBaseApplicationReqDto extends LianyijiaSystemParamBean implements Serializable{
    
    private static final long serialVersionUID = 1408781208912819580L;

    private String acctNo;
    
    private String cardSeqNo;
    
    private String channelNo;
    
    private String checkCode;
    
    private String customNo;
    
    private String ksn;
    
    private String paysEnum;
    
    private String pinValue;
    
    private String track2;
    
    private String track2Length;
    
    private String track3;
    
    private String track3Length;
    
    private String workKey;

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
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

    public String getKsn() {
        return ksn;
    }

    public void setKsn(String ksn) {
        this.ksn = ksn;
    }

    public String getPaysEnum() {
        return paysEnum;
    }

    public void setPaysEnum(String paysEnum) {
        this.paysEnum = paysEnum;
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

    public void setWorkKey(String workKey) {
        this.workKey = workKey;
    }
    
}
