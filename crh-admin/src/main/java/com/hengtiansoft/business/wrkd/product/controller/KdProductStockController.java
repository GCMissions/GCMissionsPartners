/*
 * Project Name: wrw-admin
 * File Name: KPStockController.java
 * Class Name: KPStockController
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
package com.hengtiansoft.business.wrkd.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hengtiansoft.business.wrkd.product.dto.KdProductStockDto;
import com.hengtiansoft.business.wrkd.product.service.KdProductStockService;
import com.hengtiansoft.common.controller.AbstractController;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;

/**
 * Class Name: KdProductStockController
 * Description: 酷袋商品库存
 * @author zhongyidong
 *
 */
@Controller
@RequestMapping("/coolbag/stock")
public class KdProductStockController extends AbstractController{
    
    @Autowired
    private KdProductStockService kdProductStockService;
    
    /**
     * Description: 查询库存信息
     *
     * @param productDto
     * @return
     */
    @RequestMapping(value = "/query/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<KdProductStockDto> queryStock(@PathVariable("id") long productId) {
        KdProductStockDto stockDto = kdProductStockService.findByProductId(productId);
        if (null == stockDto) {
            return ResultDtoFactory.toNack("查询失败！");
        }
        return ResultDtoFactory.toAck("查询成功", stockDto);
    }
    
    /**
     * Description: 保存库存信息
     *
     * @param stockDto
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Long> saveStock(@RequestBody KdProductStockDto stockDto) {
        return kdProductStockService.saveStock(stockDto);
    }

}
