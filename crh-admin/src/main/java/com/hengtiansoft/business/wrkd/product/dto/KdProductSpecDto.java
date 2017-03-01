/*
 * Project Name: wrw-admin
 * File Name: KdPSpecDto.java
 * Class Name: KdPSpecDto
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

import java.util.List;

/**
 * Class Name: KdPSpecDto
 * Description: 酷袋商品规格dto
 * @author zhongyidong
 *
 */
public class KdProductSpecDto {

    private Long id;
    
    private Long productId;
    
    private String mainSpec;
    
    private List<String> subSpecs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getMainSpec() {
        return mainSpec;
    }

    public void setMainSpec(String mainSpec) {
        this.mainSpec = mainSpec;
    }

    public List<String> getSubSpecs() {
        return subSpecs;
    }

    public void setSubSpecs(List<String> subSpecs) {
        this.subSpecs = subSpecs;
    }
    
}
