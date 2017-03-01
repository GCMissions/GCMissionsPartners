/*
 * Project Name: wrw-admin
 * File Name: MposWechatMicropayService.java
 * Class Name: MposWechatMicropayService
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

import com.hengtiansoft.business.offline.dto.WechatMicropayReqDto;
import com.hengtiansoft.business.offline.dto.WechatMicropayRespDto;
import com.hengtiansoft.business.offline.dto.WechatOrderQueryReqDto;
import com.tencent.protocol.pay_query_protocol.ScanPayQueryResData;

/**
 * Class Name: MposWechatMicropayService
 * Description:
 * 
 * @author xiaoluzhou
 */
public interface WechatMicropayService {

    WechatMicropayRespDto wechatMicropay(WechatMicropayReqDto reqDto);

    /**
     * Description:
     *
     * @param reqDto
     * @return
     */
    ScanPayQueryResData scanPayQuery(WechatOrderQueryReqDto reqDto);
}
