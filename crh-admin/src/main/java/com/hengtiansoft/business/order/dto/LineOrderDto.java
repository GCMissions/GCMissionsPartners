/*
 * Project Name: wrw-wechat
 * File Name: OfflineProductDto.java
 * Class Name: OfflineProductDto
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
package com.hengtiansoft.business.order.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 
* Class Name: OnlineProductDto
* Description: 主要记录导入的excel商品信息
* @author chengchaoyin
*
 */
public class LineOrderDto implements Serializable{

    /**
    * Variables Name: serialVersionUID
    * Description: TODO
    * Value Description: TODO
    */
    private static final long serialVersionUID = 1L;

    /**
     * 商品id
     */
    private Long productId;
    
    /**
     * 商品名
     */
    private String productName;
    
    /**
     * 规格信息
     */
    private List<SpecInfoDto> specInfo;
    
    /**
     * 活动日期
     */
    private String actDate;
    
    /**
     * 订单详情集合
     */
    private List<LineOrderDetailDto> lineOrderDetailDtos;
    
    
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public List<SpecInfoDto> getSpecInfo() {
        return specInfo;
    }

    public void setSpecInfo(List<SpecInfoDto> specInfo) {
        this.specInfo = specInfo;
    }

    public String getActDate() {
        return actDate;
    }

    public void setActDate(String actDate) {
        this.actDate = actDate;
    }

    public List<LineOrderDetailDto> getLineOrderDetailDtos() {
        return lineOrderDetailDtos;
    }

    public void setLineOrderDetailDtos(List<LineOrderDetailDto> lineOrderDetailDtos) {
        this.lineOrderDetailDtos = lineOrderDetailDtos;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

}
