/*
 * Project Name: wrw-admin
 * File Name: MsmService.java
 * Class Name: MsmService
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
package com.hengtiansoft.business.common.service;

import java.util.HashMap;

import com.hengtiansoft.wrw.enums.MessageModelEnum;

/**
 * Class Name: MsmService
 * Description: TODO
 * 
 * @author chengminmiao
 */
public interface SMSService {

    void sendSMS(String phone, MessageModelEnum messageType, HashMap<String, String> dataMap);

}
