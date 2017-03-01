/*
 * Project Name: zc-collect-common
 * File Name: BaseStatus.java
 * Class Name: BaseStatus
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
 * Class Name: BaseStatus Description: 默认STATUS的状态枚举
 * 
 * @author chengminmiao
 */
public enum BaseStatus {

    INVALID("0", "失效"), EFFECT("1", "生效"), FROZEN("2", "冻结");
    private String key;

    private String value;

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key
     *            the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @param key
     * @param value
     */
    private BaseStatus(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
