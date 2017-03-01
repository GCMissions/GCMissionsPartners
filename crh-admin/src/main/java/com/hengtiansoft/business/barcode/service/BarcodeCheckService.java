/*
 * Project Name: wrw-admin
 * File Name: BarcodeCheckService.java
 * Class Name: BarcodeCheckService
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
package com.hengtiansoft.business.barcode.service;

import com.hengtiansoft.business.barcode.dto.BarcodeCheckDto;
import com.hengtiansoft.business.barcode.dto.BarcodeDto;
import com.hengtiansoft.common.dto.ResultDto;

/**
* Class Name: BarcodeCheckService
* Description: 二维码验证
* @author zhisongliu
*
*/
public interface BarcodeCheckService {

  ResultDto<BarcodeCheckDto> checkUrl(BarcodeDto dto);

}
