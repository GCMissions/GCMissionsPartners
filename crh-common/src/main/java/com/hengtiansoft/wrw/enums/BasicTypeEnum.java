/*
 * Project Name: wrw-common
 * File Name: BasicTypeEnum.java
 * Class Name: BasicTypeEnum
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
 * Class Name: BasicTypeEnum
 * Description: TODO
 * 
 * @author chengminmiao
 */
public enum BasicTypeEnum {
    
    POINT_REGISTER(1, "注册积分"), POINT_BUY(2, "购买获得积分"),
    SHIP_TIME_RANGE(4, "发货时间范围"), SHIP_TIME_PERIOD(5, "发货时间段"),
    FREE_FRIEIGHT(6,"免配送费额度"),FREE_POSTAGE(7,"免邮寄费额度"),
    REFEREE_REBATE(8,"邀请额度");
    
    

    private Integer key;

    private String  value;

    private BasicTypeEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static String getValue(Integer key) {
        for (BasicTypeEnum type : values()) {
            if (type.getKey().equals(key)) {
                return type.getValue();
            }
        }
        return null;
    }

    public static Integer getKey(String value) {
        for (BasicTypeEnum type : values()) {
            if (type.getValue().equals(value)) {
                return type.getKey();
            }
        }
        return null;
    }

}
