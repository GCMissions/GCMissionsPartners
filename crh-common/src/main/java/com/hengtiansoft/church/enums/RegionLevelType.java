/*
 * Project Name: wrw-common
 * File Name: RegionLevelType.java
 * Class Name: RegionLevelType
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
package com.hengtiansoft.church.enums;

/**
 * Class Name: RegionLevelType
 * Description: TODO
 * 
 * @author chengminmiao
 */
public enum RegionLevelType {

    COUNTRY(0, "国家"), PROVINCE(1, "省"), CITY(2, "市"), DISTRICT(3, "区");

    private Integer key;

    private String  value;

    private RegionLevelType(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static String getValue(Integer key) {
        for (RegionLevelType type : values()) {
            if (type.getKey().equals(key)) {
                return type.getValue();
            }
        }
        return null;
    }

}
