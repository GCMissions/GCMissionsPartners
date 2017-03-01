/*
 * Project Name: wrw-common
 * File Name: StockRecordTypeEmum.java
 * Class Name: StockRecordTypeEmum
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
* Class Name: StockRecordTypeEmum
* Description: 库存变更操作类型
* @author xianghuang
*
*/
public enum StockRecordTypeEmum {
    AUTOADD("0", "收货入库"),AUTOREDUCE("1", "发货出库"), ADD("2", "手动增加"),REDUCE("3", "手动减少");

    private String code;

    private String text;

    private StockRecordTypeEmum(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public String getCode() {
        return code;
    }

    public String getText() {
        return text;
    }
    
    public static String getTextByCode(String code){
         for (StockRecordTypeEmum type : values()) {
             if (type.getCode().equals(code)) {
                 return type.getText();
             }
         }
         return null;
    }
    public static String getCodeByText(String text){
     for (StockRecordTypeEmum type : values()) {
            if (type.getText().equals(text)) {
                return type.getCode();
            }
        }
        return null;
   }
}
