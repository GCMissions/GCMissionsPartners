/*
 * Project Name: wrw-admin
 * File Name: SaleStatus.java
 * Class Name: SaleStatus
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
 * Class Name: KSaleStatus
 * Description: 上下架状态枚举
 * @author zhongyidong
 *
 */
public enum KdSaleStatus {

    NO_SALE("0", "未上架"),
    WAIT_SALE("1", "待上架"),
    ON_SALE("2", "已上架");
    
    private String code;
    private String name;
    
    private KdSaleStatus(String code, String name) {
        this.setCode(code);
        this.setName(name);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public static boolean containCode(String code) {
        for(KdSaleStatus status : KdSaleStatus.values()){
            if (status.getCode().equals(code)) {
                return true;
            }
        }
        
        return false;
    }
}
