/*
 * Project Name: wrw-admin
 * File Name: WechatMicropayReqDto.java
 * Class Name: WechatMicropayReqDto
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
 * Class Name: WechatMicropayReqDto
 * Description: 
 * @author xiaoluzhou
 *
 */
public class WechatMicropayReqDto implements Serializable{
    
    private static final long serialVersionUID = 4389821741315213510L;

    private String orderId;
    
    private String authCode;
    
    private String deviceInfo;
    
    private String spBillCreateIP;
    
    private String attach;
    
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getAuthCode() {
        return authCode;
    }
    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }
    public String getDeviceInfo() {
        return deviceInfo;
    }
    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }
    public String getSpBillCreateIP() {
        return spBillCreateIP;
    }
    public void setSpBillCreateIP(String spBillCreateIP) {
        this.spBillCreateIP = spBillCreateIP;
    }
    public String getAttach() {
        return attach;
    }
    public void setAttach(String attach) {
        this.attach = attach;
    }
}
