/*
 * Project Name: wrw-common
 * File Name: AppImageType.java
 * Class Name: AppImageType
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
 * Class Name: AppImageType Description: TODO
 * 
 * @author kangruan
 *
 */
public enum AppImageType {

    CAROUSEL("0", "轮播位图片"), TAG("1", "标签图片");

    private String key;

    private String value;

    private AppImageType(String key, String value) {
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
        for (AppImageType type : values()) {
            if (type.getKey().equals(key)) {
                return type.getValue();
            }
        }
        return null;
    }

    public static String getKey(String value) {
        for (AppImageType type : values()) {
            if (type.getValue().equals(value)) {
                return type.getKey();
            }
        }
        return null;
    }

}
