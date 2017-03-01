/*
 * Project Name: wrw-job
 * File Name: KdProductSaleService.java
 * Class Name: KdProductSaleService
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
package com.hengtiansoft.task.service;

/**
 * Class Name: KdProductSaleService
 * Description: 酷袋商品上下架
 * @author zhongyidong
 *
 */
public interface KdProductSaleService {

    /**
     * Description: 商品自动上架操作
     *
     * @return
     */
    void putOnSale();
    
    /**
     * Description: 商品自动下架操作
     *
     * @return
     */
    void putOffSale();
}
