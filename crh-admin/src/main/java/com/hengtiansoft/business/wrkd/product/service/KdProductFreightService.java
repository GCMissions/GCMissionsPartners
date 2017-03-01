/*
 * Project Name: wrw-admin
 * File Name: KdPShiefService.java
 * Class Name: KdPShiefService
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
import java.util.Map;

import com.hengtiansoft.business.wrkd.product.dto.KdProductFreightDto;
import com.hengtiansoft.wrw.entity.KdProductFreightEntity;

/**
 * Class Name: KdPShiefService
 * Description: 酷袋商品运费
 * @author zhongyidong
 *
 */
public interface KdProductFreightService {
    
    /**
     * Description: 查询商品运费信息
     *
     * @param productId
     * @return
     */
    Map<String, List<KdProductFreightDto>> findFreightInfo(Long productId);
    
    /**
     * Description: 保存商品运费信息
     *
     * @param productId
     * @param freightInfo
     * @return
     */
    List<KdProductFreightEntity> saveFreightInfo(Long productId, List<KdProductFreightDto> freightInfo);
}
