/*
 * Project Name: wrw-common
 * File Name: RewardOrderType.java
 * Class Name: RewardOrderType
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
* Class Name: RewardOrderType
* 
* Description: 配送商奖惩订单类型
* 
* @author huizhuang
*/
public enum RewardOrderTypeEnum {
    REWARD("1","转单奖励"), TAKE_TIMEOUT("2","超时惩罚"), INVITE("3", "邀请奖励");
    
    private String key;
    
    private String value;

    private RewardOrderTypeEnum(String key, String value) {
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
        for (RewardOrderTypeEnum type : values()) {
            if (type.getKey().equals(key)) {
                return type.getValue();
            }
        }
        return null;
    }
}
