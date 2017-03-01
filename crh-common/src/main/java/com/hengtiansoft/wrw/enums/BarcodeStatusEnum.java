/*
 * Project Name: wrw-common
 * File Name: BarcodeStatusEnum.java
 * Class Name: BarcodeStatusEnum
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
* Class Name: BarcodeStatusEnum
* Description: TODO
* @author zhisongliu
*
*/
public enum BarcodeStatusEnum {
    EFFECT("0", "生效"), INVALID("1", "失效");
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
    private BarcodeStatusEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
