package com.hengtiansoft.task.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hengtiansoft.task.service.MobileMessageService;

@Component
public class MessageTask {
    @Autowired
    private MobileMessageService mobileMessageService;

//     每2秒发送一次短信
    @Scheduled(cron = "0/2 * *  * * ?")
    public void sendMessage() {
         mobileMessageService.send();
    }
}
