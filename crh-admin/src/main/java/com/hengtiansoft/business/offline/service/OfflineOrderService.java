/*
 * Project Name: wrw-admin
 * File Name: MposOrderService.java
 * Class Name: MposOrderService
 * Copyright 2014 Hengtian Software Inc
 * Licensed under the Hengtiansoft
 * http://www.hengtiansoft.com
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hengtiansoft.business.offline.service;

import com.hengtiansoft.business.offline.dto.OfflineOrderSubmitReqDto;
import com.hengtiansoft.business.offline.dto.OfflineOrderSubmitRespDto;

/**
 * Class Name: MposOrderService
 * Description:
 * 
 * @author xiaoluzhou
 */
public interface OfflineOrderService {

    OfflineOrderSubmitRespDto submitOrder(OfflineOrderSubmitReqDto submitDto);

}
