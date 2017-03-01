/*
 * Project Name: wrw-admin
 * File Name: OfflinePurchaseProductServiceImpl.java
 * Class Name: OfflinePurchaseProductServiceImpl
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
package com.hengtiansoft.business.offline.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengtiansoft.business.common.util.UserUtil;
import com.hengtiansoft.business.offline.dto.OfflineQueryProductReqDto;
import com.hengtiansoft.business.offline.dto.OfflineQueryProductRespDto;
import com.hengtiansoft.business.offline.service.OfflineProductService;
import com.hengtiansoft.common.converter.ConverterService;
import com.hengtiansoft.common.exception.BizServiceException;
import com.hengtiansoft.wrw.dao.PShiefDao;
import com.hengtiansoft.wrw.dao.PProductDao;
import com.hengtiansoft.wrw.dao.SBarcodeCycleDao;
import com.hengtiansoft.wrw.dao.SBasicCodeDao;
import com.hengtiansoft.wrw.entity.PShiefEntity;
import com.hengtiansoft.wrw.entity.PProductEntity;
import com.hengtiansoft.wrw.entity.SBarcodeCycleEntity;
import com.hengtiansoft.wrw.entity.SBasicCodeEntity;
import com.hengtiansoft.wrw.enums.SBasicCodeCycleStatusEnum;

/**
 * Class Name: OfflinePurchaseProductServiceImpl
 * Description: 
 * @author xiaoluzhou
 *
 */
@Service
public class OfflineProductServiceImpl implements OfflineProductService{
    
    private static final Logger log = LoggerFactory.getLogger(OfflineProductServiceImpl.class);

    @Autowired
    private PProductDao productDao;
    
    @Autowired
    private SBasicCodeDao basicCodeDao;
    
    @Autowired
    private PShiefDao priceDao;
    
    @Autowired
    private SBarcodeCycleDao cycleDao;
    
    @Override
    public List<OfflineQueryProductRespDto> queryProduct(OfflineQueryProductReqDto reqDto) {
        
        String condition = reqDto.getCondition();
        
        List<OfflineQueryProductRespDto> respDtos = new ArrayList<OfflineQueryProductRespDto>();
        if(condition.startsWith("http")){
            respDtos = queryByBarcode(reqDto.getCondition());
        }else {
            respDtos = queryByProductName(condition);
        }
        
        List<Long> productIds = new ArrayList<Long>();
        for(OfflineQueryProductRespDto product : respDtos){
            productIds.add(product.getProductId());
        }
        
        List<PShiefEntity> prices = priceDao.findByProductIdInAndRegionId(productIds, UserUtil.getUserRegionId());
        if(CollectionUtils.isEmpty(prices)){
            throw new BizServiceException("未找到该商品的价格信息");
        }
        
        
        for (PShiefEntity pPriceEntity : prices) {
            
            for(int i = 0; i < respDtos.size(); i++){
                OfflineQueryProductRespDto product = respDtos.get(i);
                if(pPriceEntity.getProductId().equals(product.getProductId())){
//                    product.setSalePrice(new Double(pPriceEntity.getPrice()) / 100);
                }
            }
            
        }
       
        return respDtos;
    }

    private List<OfflineQueryProductRespDto> queryByBarcode(String barcode){
        
        
        if(StringUtils.isBlank(barcode)){
            throw new BizServiceException("二维码信息不正确，请重新扫描");
        }
        
        int index = barcode.indexOf("?c=");
        if(index > 0){
            barcode = barcode.substring(index+3);
        }
        
        int endIndex = barcode.indexOf("=");
        if(endIndex > 0){
            barcode = barcode.substring(0, endIndex);
        }
        
        SBasicCodeEntity basicCode = basicCodeDao.findByQrCode(barcode);
        if(basicCode == null){
            throw new BizServiceException("未找到该二维码的信息");
        }
        
        List<SBasicCodeEntity> basicCodeEntities = new ArrayList<SBasicCodeEntity>();
        basicCodeEntities.add(basicCode);
        //扫描的是母码
        if(basicCode.getPrefixCode().equals(basicCode.getPackCode())){
            basicCodeEntities = basicCodeDao.findbyParentIdEqualPrefixCode(basicCode.getPackCode());
            if(CollectionUtils.isEmpty(basicCodeEntities)){
                throw new BizServiceException("未找到商品二维码明码信息");
            }
        }
        
        List<OfflineQueryProductRespDto> respDtos = new ArrayList<OfflineQueryProductRespDto>();
        List<PProductEntity> productEntities = productDao.findByGoodIdAndSpecNum(basicCode.getGoodId(), 1);
        if(CollectionUtils.isEmpty(productEntities)){
            throw new BizServiceException("未找到该二维码对应的商品");
        }
        
        PProductEntity product = productEntities.get(0);
        
        try {
            //一个母码包含6个子码
            for (int i = 0; i < basicCodeEntities.size(); i++) {
                OfflineQueryProductRespDto productRespDto = ConverterService.convert((PProductEntity) BeanUtils.cloneBean(product), 
                        OfflineQueryProductRespDto.class);
                respDtos.add(productRespDto);
                productRespDto.setBarcode(basicCodeEntities.get(i).getQrCode());
                String prefixCode = basicCodeEntities.get(i).getPrefixCode();
                productRespDto.setPrefixCode(prefixCode);
                
                //校验二维码是否有效
                if(!validateBarcode(prefixCode)) {
                    productRespDto.setHintMsg("商品二维码["+prefixCode+"]不在本店库存，不能出售该商品");
                    productRespDto.setAvailable(false);
                }
                
            }
        } catch (Exception e) {
            log.error("商品转换dto异常", e);
            throw new BizServiceException("服务异常，请稍后再试");
        }
        return respDtos;
    }
    
    private boolean validateBarcode(String prefixCode) {
        List<SBarcodeCycleEntity> listBarcode = cycleDao.findAllCycleOrderByCreateDateDesc(prefixCode);
        if (CollectionUtils.isNotEmpty(listBarcode)) {
            String nowStatus = listBarcode.get(0).getStatus();
            if (SBasicCodeCycleStatusEnum.Z_STORAGE.getKey().equals(nowStatus)
                    || SBasicCodeCycleStatusEnum.C_Z_STOTAGE.getKey().equals(nowStatus)) {
                // 验证toId 与当前的Z是否相等
                String toId = listBarcode.get(0).getToId();
                Long nowToId = UserUtil.getUserOrgId();
                if (toId.equals(nowToId.toString())) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private List<OfflineQueryProductRespDto> queryByProductName(String productName){
        List<PProductEntity> productEntities = null;
        try {
            productEntities = productDao.findByProductNameLike(URLDecoder.decode(productName, "utf-8"));
            
        } catch (UnsupportedEncodingException e) {
            throw new BizServiceException("输入商品名称不对，请检查输入的商品名称");
        }
        if(CollectionUtils.isEmpty(productEntities)){
            throw new BizServiceException("未找到你想要的商品");
        }
        
        List<OfflineQueryProductRespDto> respDtos = new ArrayList<OfflineQueryProductRespDto>();
        for (int i = 0; i < productEntities.size(); i++) {
            OfflineQueryProductRespDto productRespDto = ConverterService.convert(productEntities.get(i), OfflineQueryProductRespDto.class);
            respDtos.add(productRespDto);
        }
        return respDtos;
    }
    
}
