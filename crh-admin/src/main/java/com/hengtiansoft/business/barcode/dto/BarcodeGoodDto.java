/*
 * Project Name: wrw-admin
 * File Name: BarcodeGoodDto.java
 * Class Name: BarcodeGoodDto
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
package com.hengtiansoft.business.barcode.dto;

import java.io.Serializable;

/**
* Class Name: BarcodeGoodDto
* Description: 物料信息
* @author zhisongliu
*
*/
public class BarcodeGoodDto implements Serializable{

    private static final long serialVersionUID = 1918515829542706217L;
    
    private Long goodId;
    
    private String goodName;
    
    private Long goodNum;

    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

    public Long getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(Long goodNum) {
        this.goodNum = goodNum;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }
    
}
