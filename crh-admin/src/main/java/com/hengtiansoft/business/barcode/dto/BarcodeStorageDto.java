/*
 * Project Name: wrw-admin
 * File Name: BarcodeStorageDto.java
 * Class Name: BarcodeStorageDto
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
import java.util.List;

/**
* Class Name: BarcodeStorageDto
* Description: 入库DTO
* @author zhisongliu
*
*/
public class BarcodeStorageDto implements Serializable{

    private static final long serialVersionUID = 6401989325866904978L;
    
    private String source  = "0";//来源 PC还是MPOS
    
    private List<String> prefixCodes;//订单扫码信息、子码集合
    
    private String orderId;//订单ID
    
    private Integer goodNum;//订单物料总数
    
    private String status;
    
    private List<BarcodeGoodDto> goodDtos;
    
    private List<String> packCodes;//订单扫码信息、母码集合
    
    public List<BarcodeGoodDto> getGoodDtos() {
        return goodDtos;
    }

    public void setGoodDtos(List<BarcodeGoodDto> goodDtos) {
        this.goodDtos = goodDtos;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
    

    public List<String> getPrefixCodes() {
        return prefixCodes;
    }

    public void setPrefixCodes(List<String> prefixCodes) {
        this.prefixCodes = prefixCodes;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(Integer goodNum) {
        this.goodNum = goodNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getPackCodes() {
        return packCodes;
    }

    public void setPackCodes(List<String> packCodes) {
        this.packCodes = packCodes;
    }
    
    
}
