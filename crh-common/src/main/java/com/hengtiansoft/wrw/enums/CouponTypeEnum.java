/*
 * Project Name: wrw-common
 * File Name: CouponConfTypeEnum.java
 * Class Name: CouponConfTypeEnum
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
package com.hengtiansoft.wrw.enums;

/**
 * 
* Class Name: CouponTypeEnum
* Description: 优惠券来源枚举
* @author chengchaoyin
*
 */
public enum CouponTypeEnum {

    PICK("1", "后台发放"), EXCHANGE("2", "购买满足条件赠送");

    private String key;

    private String value;

    private CouponTypeEnum(String key, String value) {
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
        for (CouponTypeEnum state : values()) {
            if (state.getKey().equals(key)) {
                return state.getValue();
            }
        }
        return null;
    }

}
