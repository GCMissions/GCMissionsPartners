/*
 * Project Name: wrw-common
 * File Name: KdCharityStatusEnum.java
 * Class Name: KdCharityStatusEnum
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

import java.util.HashMap;
import java.util.Map;

/**
 * Class Name: KdCharityStatusEnum
 * Description: 酷袋公益活动状态枚举
 * @author zhongyidong
 *
 */
public enum KdCharityStatusEnum {
    
    NOTSTART("2", "未开始"), 
    ONGOING("3", "进行中"), 
    FINISHED("4", "已结束"),
    INVALID("5", "已失效"),
    OFFSALE("6", "专辑已下架");
    
    private String key;
    
    private String value;

    private KdCharityStatusEnum(String key, String value) {
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
        for (KdCharityStatusEnum type : values()) {
            if (type.getKey().equals(key)) {
                return type.getValue();
            }
        }
        return null;
    }
    
    public static String getKey(String value) {
        for (KdCharityStatusEnum type : values()) {
            if (type.getValue().equals(value)) {
                return type.getKey();
            }
        }
        return null;
    }
    
    public static Map<String, String> getStatusMap() {
        Map<String, String> statusMap = new HashMap<String, String>(values().length);
        for (KdCharityStatusEnum type : values()) {
            statusMap.put(type.getKey(), type.getValue());
        }
        return statusMap;
    }
}
