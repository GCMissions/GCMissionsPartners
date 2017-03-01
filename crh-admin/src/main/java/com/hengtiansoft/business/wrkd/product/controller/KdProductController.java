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
package com.hengtiansoft.business.wrkd.product.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hengtiansoft.business.wrkd.product.dto.KdProductDto;
import com.hengtiansoft.business.wrkd.product.dto.KdProductFreightDto;
import com.hengtiansoft.business.wrkd.product.dto.KdProductSearchDto;
import com.hengtiansoft.business.wrkd.product.dto.KdProductSpecDto;
import com.hengtiansoft.business.wrkd.product.dto.KdProductStockDetailDto;
import com.hengtiansoft.business.wrkd.product.service.KdProductSaleService;
import com.hengtiansoft.business.wrkd.product.service.KdProductService;
import com.hengtiansoft.business.wrkd.product.service.KdProductSpecService;
import com.hengtiansoft.business.wrkd.product.service.KdProductStockService;
import com.hengtiansoft.common.controller.AbstractController;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.wrw.dao.SBasicParaDao;

/**
 * Class Name: ProductController
 * Description: 酷袋商品管理
 * @author zhongyidong
 *
 */
@Controller
@RequestMapping(value = "/coolbag/product")
public class KdProductController extends AbstractController{
    // 添加商品
    private static final String ADD = "add";
    // 查看商品
    private static final String VIEW = "view";
    // 编辑商品
    private static final String EDIT = "edit";
    
    // 其他地区
    private final static String OTHER = "其他";
    // 配置信息
    private final static String HOST_TYPE = "域名信息";
    private final static String WEW_HOST_PARA = "WEW_HOST";
    private final static String ACT_TAG_TYPE = "酷袋商品促销活动";
    
    @Autowired
    private SBasicParaDao basicParaDao;
    @Autowired
    private KdProductService kdProductService;
    @Autowired
    private KdProductSaleService kdProductSaleService;
    @Autowired
    private KdProductSpecService kdProductSpecService;
    @Autowired
    private KdProductStockService kdProductStockService;
    
    /**
     * Description: 商品列表
     *
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String listProduct(Model model){
        model.addAttribute("actTags", basicParaDao.queryByTypeName(ACT_TAG_TYPE));
        model.addAttribute("wechatHost", basicParaDao.findParaValue1ByTypeName(HOST_TYPE, WEW_HOST_PARA));
        return "wrkd/product/list";
    }
    
    /**
     * Description: 商品查询
     *
     * @param searchDto
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public KdProductSearchDto listProduct(@RequestBody KdProductSearchDto searchDto){
        kdProductService.search(searchDto);
        return searchDto;
    }
   
    /**
     * Description: 根据操作引导去哪个页面
     *
     * @param method
     * @return
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public String pageGuide(@RequestParam String oper, @RequestParam(required = false) Long productId, Model model) {
        model.addAttribute("actTags", basicParaDao.queryByTypeName(ACT_TAG_TYPE));
        // 添加商品
        if (ADD.equals(oper)) {
            return "wrkd/product/" + oper;
        }
        // 查看、编辑商品
        if ((VIEW.equals(oper) || EDIT.equals(oper)) && null != productId){
            KdProductDto productDto = kdProductService.viewProductInfo(productId);
            if (null != productDto) {
                model.addAttribute("oper", oper);
                model.addAttribute("listImages", productDto.getListImages());
                Map<String, List<KdProductFreightDto>> freightMap = productDto.getFreightMap();
                if (null != freightMap && !freightMap.isEmpty()) {
                    // 取出默认运费
                    if (freightMap.containsKey(OTHER)) {
                        model.addAttribute("defFreightPrice", freightMap.get(OTHER).get(0).getPrice());
                        freightMap.remove(OTHER);
                    }
                }
                model.addAttribute("freightMap", freightMap);
                model.addAttribute("stockDto", kdProductStockService.findByProductId(productId));
            }
            model.addAttribute("productDto", productDto);
        }
        
        return "wrkd/product/" + oper;
    }
    
    /**
     * Description: 保存商品基本信息
     *
     * @param productDto
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Long> saveProduct(@RequestBody KdProductDto productDto) {
        return kdProductService.saveProduct(productDto);
    }
    
    /**
     * Description: 保存商品详情
     *
     * @param productDto
     * @return
     */
    @RequestMapping(value = "/saveDetail", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Long> saveDetail(@RequestBody KdProductDto productDto) {
        int row = kdProductService.saveDetail(productDto.getId(), productDto.getDetailDesc());
        if (0 == row) {
            return ResultDtoFactory.toNack("保存失败");
        }
        return ResultDtoFactory.toAck("保存成功");
    }
    
    /**
     * Description: 添加商品
     *
     * @param productDto
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> deleteProduct(@RequestParam("productIds[]") List<Long> productIds) {
        boolean status = kdProductSaleService.checkSaleStatus(productIds);
        if (status) {
            return ResultDtoFactory.toNack("删除的商品中含有已上架的商品");
        }
        int row = kdProductService.deleteProduct(productIds);
        if (0 < row) {
            return ResultDtoFactory.toAck("删除成功");
        }
        return ResultDtoFactory.toNack("删除失败");
    }
    
    /**
     * 
    * Description: 通过商品id查询出商品的规格信息
    *
    * @param productId
    * @return
    * @author chengchaoyin
     */
    @RequestMapping(value = "/findSpecInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<List<KdProductSpecDto>> findSpecInfo(@RequestParam(value = "productId") Long productId) {
        List<KdProductSpecDto> list = kdProductSpecService.findSpecInfo(productId);
        if (!CollectionUtils.isEmpty(list)) {
            return ResultDtoFactory.toAck("查询成功！", list);
        } else {
            return new ResultDto<List<KdProductSpecDto>>();
        }
    }
    
    /**
     * 
    * Description: 查询商品价格信息
    *
    * @param productId
    * @return
    * @author chengchaoyin
     */
    @RequestMapping(value = "/findPriceDetail", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<List<KdProductStockDetailDto>> findPriceDetail(@RequestParam(value = "productId") Long productId) {
        List<KdProductStockDetailDto> list = kdProductSpecService.findPriceDetail(productId);
        if (!CollectionUtils.isEmpty(list)) {
            return ResultDtoFactory.toAck("查询成功！", list);
        } else {
            return new ResultDto<List<KdProductStockDetailDto>>();
        }
    }
    
}
