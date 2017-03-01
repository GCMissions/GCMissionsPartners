/*
 * Project Name: wrw-admin
 * File Name: MposLianyijiaService.java
 * Class Name: MposLianyijiaService
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

import com.hengtiansoft.business.offline.dto.LianyijiaBalanceQueryReqDto;
import com.hengtiansoft.business.offline.dto.LianyijiaBalanceQueryRespData;
import com.hengtiansoft.business.offline.dto.LianyijiaCancelReqDto;
import com.hengtiansoft.business.offline.dto.LianyijiaCancelRespData;
import com.hengtiansoft.business.offline.dto.LianyijiaConsumeReqDto;
import com.hengtiansoft.business.offline.dto.LianyijiaConsumeRespData;
import com.hengtiansoft.business.offline.dto.LianyijiaReverseReqDto;
import com.hengtiansoft.business.offline.dto.LianyijiaReverseRespData;

/**
 * Class Name: MposLianyijiaService
 * Description:
 * 
 * @author xiaoluzhou
 */
public interface LianyijiaPayService {

    LianyijiaConsumeRespData consume(LianyijiaConsumeReqDto reqDto);

    LianyijiaCancelRespData cancel(LianyijiaCancelReqDto reqDto);

    LianyijiaReverseRespData reverse(LianyijiaReverseReqDto reqDto);

    LianyijiaBalanceQueryRespData queryBalance(LianyijiaBalanceQueryReqDto reqDto);

}
