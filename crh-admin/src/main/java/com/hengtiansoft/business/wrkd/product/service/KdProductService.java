/*
 * Project Name: wrw-admin
 * File Name: KProductService.java
 * Class Name: KProductService
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
package com.hengtiansoft.business.wrkd.product.service;

import java.util.List;

import com.hengtiansoft.business.wrkd.product.dto.KdProductDto;
import com.hengtiansoft.business.wrkd.product.dto.KdProductSearchDto;
import com.hengtiansoft.common.dto.ResultDto;

/**
 * Class Name: KProductService
 * Description: 酷袋商品
 * @author zhongyidong
 *
 */
public interface KdProductService {

    /**
     * Description: 根据id查询商品信息
     *
     * @param productId
     * @return
     */
    KdProductDto findById(Long productId);
    
    /**
     * Description: 查看商品信息
     *
     * @param productId
     * @return
     */
    KdProductDto viewProductInfo(Long productId);
    
    /**
     * Description: 根据条件查询商品信息
     *
     * @param productDto
     * @return
     */
    void search(KdProductSearchDto searchDto);
    
    /**
     * Description: 添加商品
     *
     * @param productDto
     * @return
     */
    ResultDto<Long> saveProduct(KdProductDto productDto);
    
    /**
     * Description: 添加商品
     *
     * @param productId
     * @param detailDesc
     * @return
     */
    int saveDetail(Long productId, String detailDesc);
    
    /**
     * Description: 删除商品
     *
     * @param productIds
     * @return
     */
    int deleteProduct(List<Long> productIds);
}
