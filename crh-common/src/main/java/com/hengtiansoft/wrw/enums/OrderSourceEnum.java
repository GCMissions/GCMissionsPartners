/*
 * Project Name: wrw-common
 * File Name: OrderSourceEnum.java
 * Class Name: OrderSourceEnum
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
package com.hengtiansoft.wrw.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Name: OrderSourceEnum
 * Description: 
 * @author xiaoluzhou
 *
 */
public enum OrderSourceEnum {
    MOBILE_APP("1", "app下单"),
    MOBILE_ANDROID("2", "app android下单"),
    MOBILE_IOS("3", "app ios下单"),
    WECHAT("4", "微信下单"),
    B2C("5", "b2c商城下单"), 
    MPOS("6", "mpos下单"),
    OFFLINE("7", "线下购买");
    
    
    private OrderSourceEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private String code;
    
    private String desc;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    public static List<String> offlineOrderSource(){
        List<String> orderSources = new ArrayList<String>();
        orderSources.add(MPOS.getCode());
        orderSources.add(OFFLINE.getCode());
        return orderSources;
    }
}
