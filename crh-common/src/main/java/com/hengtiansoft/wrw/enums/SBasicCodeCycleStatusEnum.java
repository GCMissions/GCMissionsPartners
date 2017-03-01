/*
 * Project Name: wrw-common
 * File Name: SBasicCodeCycleStatusEnum.java
 * Class Name: SBasicCodeCycleStatusEnum
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
* Class Name: SBasicCodeCycleStatusEnum
* Description: 条码流水枚举表
* @author zhisongliu
*
*/
public enum SBasicCodeCycleStatusEnum {
    
    ADMIN_STORAGE("0","平台入库"),
    ADMIN_P_SHIPMENT("1", "平台发货给P"),
    P_STORAGE("2", "P端入库"),
    P_Z_SHIPMENT("3", "P端发货给Z端"),
    Z_STORAGE("4", "P发货给Z端入库"),
    Z_C_SHIPMENT("5", "Z端发货给消费者"),
    C_STORAGE("6", "消费者确认收货"),
    C_Z_STOTAGE("7","消费者退货Z端入库"),
    C_SALE_STOTAGE("8","消费者退货专卖店入库");
    
    private String key;
    
    private String value;

    private SBasicCodeCycleStatusEnum(String key, String value) {
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
    
    
}
