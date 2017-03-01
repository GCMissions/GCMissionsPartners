package com.hengtiansoft.task.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.standard.MessageOperator;
import com.hengtiansoft.task.domain.SmsMessage;
import com.hengtiansoft.task.enumeration.MessageStatusEnum;
import com.hengtiansoft.task.repository.ISmsMessageRepository;
import com.hengtiansoft.task.service.MobileMessageService;
import com.hengtiansoft.wrw.enums.MessageModelEnum;

/**
 * @author yigesong
 */
@Service
public class MobileMessageServiceImpl implements MobileMessageService {
    
    private static final Logger logger = LoggerFactory.getLogger(MobileMessageServiceImpl.class);

    @Autowired
    private ISmsMessageRepository smsMessageRepository;

    @Autowired
    private MessageOperator messageOperator;

    private static final int MAX_SEND_TIME = 3;

    private static final int MAX_SEND_NUM = 30;

    private static int CLOCK = 0;

    private static Calendar currentTime = Calendar.getInstance();

    private static Map<String, String> smsMap = new HashMap<>();

    private static Map<String, Integer> sendNumMap = new HashMap<>();

    /**
     * 发送短信
     */
    @Override
    @Transactional(value = "jpaTransactionManager")
    public void send() {

        // 1分钟清除短信缓存
        if (CLOCK == 30) {
            smsMap.clear();
            CLOCK = 0;
        }
        // 隔天清除短信发送数量
        int dayOfYear = currentTime.get(Calendar.DAY_OF_YEAR);
        if (dayOfYear != Calendar.getInstance().get(Calendar.DAY_OF_YEAR)) {
            currentTime.set(Calendar.DAY_OF_YEAR, ++dayOfYear);
            sendNumMap.clear();
        }

        List<SmsMessage> messages = smsMessageRepository.findByStatusAndTimesLessThanOrTimesIsNull(
                MessageStatusEnum.UNSEND.getKey(), MAX_SEND_TIME);
        for (SmsMessage message : messages) {
            String phone = message.getPhone();
            String key = phone + "_" + message.getType() + "_" + message.getContent();
            //为了校验一分钟之内发送同一手机号次数不能超过30条
            if (sendNumMap.containsKey(phone) && sendNumMap.get(phone) >= MAX_SEND_NUM) {
                logger.error("一分钟之内发送同一手机号次数不能超过30条");
                message.setTimes(MAX_SEND_TIME);
                continue;
            }

            // 存在1分钟内已发送的短信
            if (smsMap.containsKey(key)) {
                logger.error("一分钟之内不能发送相同短信");
                message.setStatus(MessageStatusEnum.UNSEND.getKey());
//                message.setContent(smsMap.get(key));
                message.setTimes(MAX_SEND_TIME);
                continue;
            }
            // 无1分钟内已发短信
            else {
                // 发送短信
                boolean result;
                try {
                    String messageContent = message.getContent();
                    result = messageOperator.sendMessage(messageContent, phone);
                    String status = result ? MessageStatusEnum.SEND.getKey() : MessageStatusEnum.UNSEND.getKey();
                    message.setMessage(messageContent);
                    message.setStatus(status);
                    message.setTimes(null == message.getTimes() ? 1 : message.getTimes() + 1);

                    // 短信发送成功则记录发送时间
                    if(result){
                        message.setSendTs(new Date());
                    }

                    // 如果发送成功，则放入缓存中
                    if (MessageStatusEnum.SEND.getKey().equals(status)) {
                        if(!MessageModelEnum.sms_C_sendCouponNoitfy.getKey().equals(message.getType())){
                            smsMap.put(key, message.getContent());
                        }
                        sendNumMap.put(phone, sendNumMap.containsKey(phone) ? sendNumMap.get(phone) + 1 : 1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        smsMessageRepository.save(messages);
        CLOCK++;
    }

}
