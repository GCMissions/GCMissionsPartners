/*
 * Project Name: wrw-admin
 * File Name: StockService.java
 * Class Name: StockService
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
package com.hengtiansoft.business.shopStock.platformStock.service;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hengtiansoft.business.message.dto.SOrgDto;
import com.hengtiansoft.business.shopStock.platformStock.dto.OrgStockDetailDto;
import com.hengtiansoft.business.shopStock.platformStock.dto.OrgStockSearchDto;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.wrw.enums.StockState;

/**
 * Class Name: OrgStockService Description: 商家库存业务类
 * 
 * @author xianghuang
 *
 */
public interface OrgStockService {

    OrgStockSearchDto searchOrgStock(OrgStockSearchDto searchDto);

    OrgStockDetailDto getDetail(Long orgId);

    OrgStockDetailDto getWarnList(Long orgId);

    /**
     * Description: 设置现有库存
     *
     * @param stockId
     * @param stockSetting
     * @return
     */
    ResultDto<String> saveSetting(Long stockId, Long stockSetting);

    /**
     * Description: 设置安全库存，标准库存
     *
     * @param stockId
     * @param safeStockSetting
     * @param standardStockSetting
     * @return
     */
    ResultDto<String> saveSetting(Long stockId, Long safeStockSetting, Long standardStockSetting);

    Integer getOrgNum(StockState state);

    /**
     * Description: 查询商家列表
     *
     * @param dto
     * @param state
     * @return
     */
    void searchOrg(OrgStockSearchDto dto, StockState state);

    /**
     * 
     * Description: 平台导出商家库存报表
     *
     * @param searchDto
     * @return
     */
    HSSFWorkbook toExcel(OrgStockSearchDto searchDto);

    List<SOrgDto> findAllP();
}
