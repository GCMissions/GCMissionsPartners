/*
 * Project Name: wrw-admin
 * File Name: ProductController.java
 * Class Name: ProductController
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
package com.hengtiansoft.business.shopStock.platformStock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.shopStock.platformStock.dto.GoodsStockDetailDto;
import com.hengtiansoft.business.shopStock.platformStock.dto.GoodsStockSearchDto;
import com.hengtiansoft.business.shopStock.platformStock.service.ProductStockService;
import com.wordnik.swagger.annotations.Api;


/**
* Class Name: ProductController
* Description: 商品库存管理
* @author xianghuang
*
*/
@Api(value="ProductStockMngController")
@Controller
@RequestMapping("productStock")
public class ProductStockMngController {
    
    @Autowired
    private  ProductStockService productStockService;
    
    @RequestMapping(value="/",method=RequestMethod.GET)
    public ModelAndView list(){
        return new ModelAndView("plateformStock/productStock_list");
    }
    
    @RequestMapping(value="/list",method=RequestMethod.POST)
    @ResponseBody
    public GoodsStockSearchDto list(@RequestBody GoodsStockSearchDto searchDto){
        return productStockService.searchProductStock(searchDto);
    }
    
    @RequestMapping(value = { "/detail/{goodsId}" }, method = RequestMethod.GET)
    public ModelAndView searchOne(@PathVariable(value = "goodsId") Long goodsId) {
        GoodsStockDetailDto dto=productStockService.findOrgTreeAndStockNumDetail(goodsId);
        ModelAndView mav = new ModelAndView("plateformStock/productStock_detail");
        mav.addObject("productStockDetailDto", dto);
        return mav;
    }

}
