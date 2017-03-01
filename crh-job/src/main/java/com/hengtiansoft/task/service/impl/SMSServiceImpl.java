/*
 * Project Name: wrw-admin
 * File Name: SMSServiceImpl.java
 * Class Name: SMSServiceImpl
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
package com.hengtiansoft.task.service.impl;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.hengtiansoft.common.exception.WRWException;
import com.hengtiansoft.task.dto.SBasicParaDto;
import com.hengtiansoft.task.service.MessageModelService;
import com.hengtiansoft.task.service.SMSService;
import com.hengtiansoft.wrw.dao.MSmsDao;
import com.hengtiansoft.wrw.entity.MSmsEntity;
import com.hengtiansoft.wrw.enums.MessageModelEnum;
import com.hengtiansoft.wrw.enums.SmsStatus;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * Class Name: SMSServiceImpl
 * Description: TODO
 * 
 * @author chengminmiao
 */
@Service
public class SMSServiceImpl implements SMSService {

    @Autowired
    private MSmsDao              smsDao;


    @Autowired
    private MessageModelService  messageModelService;

    @Resource(name = "freemarkerConfigurer")
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Transactional
    @Override
    public void sendSMS(String phone, MessageModelEnum messageType, HashMap<String, String> dataMap) {
        // 获取短信模板
        SBasicParaDto paraDto = messageModelService.getBasicParaByParaName(messageType.getValue());
        String message = paraDto.getParaValue1();
        Configuration configuration = freeMarkerConfigurer.getConfiguration();

        StringWriter out = new StringWriter();
        try {
            new Template("template", new StringReader(message), configuration).process(dataMap, out);
        } catch (Exception e) {
            throw new WRWException("验证码短信模板解析失败！");
        }
        message = out.toString();
        MSmsEntity sms = new MSmsEntity();
        sms.setCreateDate(new Date());
        sms.setPhone(phone);
        sms.setStatus(SmsStatus.UNSEND.getKey());
        sms.setContent(message);
        sms.setType(String.valueOf(messageType.getKey()));
        smsDao.save(sms);
    }

}
