/*
 * Project Name: wrw-common
 * File Name: OrderResendFlagEnum.java
 * Class Name: OrderResendFlagEnum
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

/**
* Class Name: OrderResendFlagEnum
* 
* Description: 订单再次派单标识
* 
* @author huizhuang
*/
public enum OrderResendFlagEnum {
    
    UNRESEND("0", "未再次派单"), RESEND("1", "已再次派单");
    
    private String key;
    
    private String value;
    
    private OrderResendFlagEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    public static String getValue(String key) {
        for (OrderResendFlagEnum state : values()) {
            if (state.getKey().equals(key)) {
                return state.getValue();
            }
        }
        return null;
    }
}
