/*
 * Project Name: wrw-admin
 * File Name: KdProductShiefController.java
 * Class Name: KdProductShiefController
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

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hengtiansoft.business.wrkd.product.dto.KdProductSaleDto;
import com.hengtiansoft.business.wrkd.product.service.KdProductSaleService;
import com.hengtiansoft.common.controller.AbstractController;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;

/**
 * Class Name: KdProductSaleController
 * Description: 商品上下架管理
 * @author zhongyidong
 *
 */
@Controller
@RequestMapping(value = "/coolbag/product/sale")
public class KdProductSaleController extends AbstractController {
    
    
    @Autowired
    private KdProductSaleService kdProductSaleService;
    
    /**
     * Description: 商品上下架管理界面
     *
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "wrkd/product/sale";
    }
    
    /**
     * Description: 商品上架
     *
     * @return
     */
    @RequestMapping(value = "/onsale", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> onSale(@RequestBody KdProductSaleDto saleDto) {
        return kdProductSaleService.putOnSale(saleDto);
    }
    
    /**
     * Description: 商品下架
     *
     * @return
     */
    @RequestMapping(value = "/offsale", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> putOffSale(@RequestParam Long productId) {
        return kdProductSaleService.putOffSale(productId);
    }
    
    /**
     * Description: 检查商品信息是否完整
     *
     * @return
     */
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<String> checkInfo(@RequestParam Long productId) {
        return kdProductSaleService.checkProductInfo(productId);
    }
    
    
    /**
     * Description: 检查商品是否在上架状态
     *
     * @return
     */
    @RequestMapping(value = "/checkStatus", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Boolean> checkStatus(@RequestParam Long productId) {
        List<Long> productIds = new ArrayList<Long>();
        productIds.add(productId);
        return ResultDtoFactory.toAck("操作成功", kdProductSaleService.checkSaleStatus(productIds));
    }
}
