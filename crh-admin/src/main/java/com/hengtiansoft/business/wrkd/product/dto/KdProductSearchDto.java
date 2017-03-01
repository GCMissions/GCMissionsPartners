/*
 * Project Name: wrw-admin
 * File Name: KProductSearchDto.java
 * Class Name: KProductSearchDto
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
package com.hengtiansoft.business.wrkd.product.dto;

import com.hengtiansoft.common.dto.PagingDto;

/**
 * Class Name: KProductSearchDto
 * Description: 酷袋商品查询dto
 * @author zhongyidong
 *
 */
public class KdProductSearchDto extends PagingDto<KdProductDto>{

    private static final long serialVersionUID = 1L;

    private String pcode;
    
    private String pname;
    
    private String actTag;
    
    private String saleStatus;
    
    private Double lowPrice;
    
    private Double highPrice;

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getActTag() {
        return actTag;
    }

    public void setActTag(String actTag) {
        this.actTag = actTag;
    }

    public String getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(String saleStatus) {
        this.saleStatus = saleStatus;
    }

    public Double getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(Double lowPrice) {
        this.lowPrice = lowPrice;
    }

    public Double getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(Double highPrice) {
        this.highPrice = highPrice;
    }
    
}
