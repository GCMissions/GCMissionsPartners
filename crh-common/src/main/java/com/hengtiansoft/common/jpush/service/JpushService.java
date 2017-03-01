/*
 * Project Name: zc-collect-common
 * File Name: JpushService.java
 * Class Name: JpushService
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
package com.hengtiansoft.common.jpush.service;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hengtiansoft.common.jpush.bean.JpushBean;
import com.hengtiansoft.common.jpush.util.JpushUtil;
import com.hengtiansoft.common.util.AppConfigUtil;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

/**
 * Class Name: JpushService Description: 推送服务类
 * 
 * @author jialiangli
 */
public class JpushService implements Runnable {

    private static final Logger LOGGER                  = LoggerFactory.getLogger(JpushService.class);

    private static final String IOS_DEFAULT_SOUND       = "default";

    private static final String JPUSH_APPKEY_USER       = "Jpush.User.AppKey";

    private static final String JPUSH_MASTERSECRET_USER = "Jpush.User.MasterSecret";

    private static final String JPUSH_APNS_PRODUCTION   = "Jpush.Apns.Production";

    private static JPushClient  jpushUserClient;

    private boolean             jpuhSwitch              = false;

    public void setJpuhSwitch(boolean jpuhSwitch) {
        this.jpuhSwitch = jpuhSwitch;
    }

    /**
     * Description: 初始化时启动jpush线程
     */
    @PostConstruct
    public void init() {
        if (jpuhSwitch) {
            Thread jpushThread = new Thread(this);
            jpushThread.setDaemon(true);
            jpushThread.start();
        }
    }

    @Override
    public void run() {
        while (true) {
            JpushBean jpushBean = JpushUtil.take();
            if (null != jpushBean) {
                JPushClient jPushClient = getCustomerJPushClient();

                // 设置不同的发送对象
                PushPayload pushPayload = null;
                if (!jpushBean.getAliases().isEmpty()) {
                    pushPayload = toAllPlatformNotificationByRegistrationId(jpushBean);
                } else if (!jpushBean.getTagValues().isEmpty()) {
                    pushPayload = toAllPlatformNotificationByTag(jpushBean);
                } else {
                    continue;
                }

                // 发送消息
                try {
                    jPushClient.sendPush(pushPayload);
//                    saveMessage(jpushBean);
                    LOGGER.info("Jpushs推送成功" + jpushBean);
                    LOGGER.info("消息保存成功");
                } catch (APIConnectionException e) {
                    LOGGER.error("Jpush连接错误", e);
                } catch (APIRequestException e) {
                    LOGGER.error("JPush推送失败", e);
                    LOGGER.debug("HTTP Status" + e.getStatus());
                    LOGGER.debug("Error Code" + e.getErrorCode());
                    LOGGER.debug("Error Message" + e.getErrorMessage());
                } catch (Exception e) {
                    LOGGER.error("消息发送失败", e);
                }
            }
        }
    }

    /**
     * Description: 获得用户端JpushClient
     *
     * @return
     */
    private JPushClient getCustomerJPushClient() {
        if (jpushUserClient == null) {
            synchronized (JpushService.class) {
                if (jpushUserClient == null) {
                    jpushUserClient = new JPushClient(AppConfigUtil.getConfig(JPUSH_MASTERSECRET_USER), AppConfigUtil.getConfig(JPUSH_APPKEY_USER));
                }
            }
        }
        return jpushUserClient;
    }

    /**
     * Description:构建消息内容
     *
     * @param platform
     * @param audience
     * @param notification
     * @param message
     * @param options
     * @return
     */
    private PushPayload buildPushObject(Platform platform, Audience audience, Notification notification, Message message, Options options) {
        return PushPayload.newBuilder().setPlatform(platform).setAudience(audience).setNotification(notification).setMessage(message)
                .setOptions(options).build();
    }

    /**
     * Description: 向全平台根据用户ID表示发送通知
     *
     * @return
     */
    private PushPayload toAllPlatformNotificationByRegistrationId(JpushBean jpushBean) {
        Platform platform = Platform.all();
        Audience audience = Audience.alias(jpushBean.getAliases());
        Notification notification = Notification.newBuilder().setAlert(jpushBean.getAlert())
                .addPlatformNotification(IosNotification.newBuilder().setSound(IOS_DEFAULT_SOUND).addExtras(jpushBean.getExtras()).build())
                .addPlatformNotification(AndroidNotification.newBuilder().setTitle(jpushBean.getTitle()).addExtras(jpushBean.getExtras()).build()).build();
        Options options = Options.newBuilder().setApnsProduction(Boolean.valueOf(AppConfigUtil.getConfig(JPUSH_APNS_PRODUCTION))).build();
        return buildPushObject(platform, audience, notification, null, options);
    }

    /**
     * Description: 向全平台根据标签表示发送通知
     *
     * @return
     */
    private PushPayload toAllPlatformNotificationByTag(JpushBean jpushBean) {
        Platform platform = Platform.all();
        Audience audience = Audience.tag(jpushBean.getTagValues());
        Notification notification = Notification.newBuilder().setAlert(jpushBean.getAlert())
                .addPlatformNotification(IosNotification.newBuilder().setSound(IOS_DEFAULT_SOUND).addExtras(jpushBean.getExtras()).build())
                .addPlatformNotification(AndroidNotification.newBuilder().setTitle(jpushBean.getTitle()).addExtras(jpushBean.getExtras()).build()).build();
        Options options = Options.newBuilder().setApnsProduction(Boolean.valueOf(AppConfigUtil.getConfig(JPUSH_APNS_PRODUCTION))).build();
        return buildPushObject(platform, audience, notification, null, options);
    }

    /**
     * Description: 保存消息至数据库
     *
     * @param jpushBean
     */
//    private void saveMessage(JpushBean jpushBean) {
//        String param = WRWUtil.parseObjTOString(jpushBean.getExtras());
//        Date date = new Date();
//
//        // 发送给特定别名用户的消息
//        if (!jpushBean.getToIds().isEmpty()) {
//            List<MMessageEntity> messages = new ArrayList<>();
//            for (Long toId : jpushBean.getToIds()) {
//                MMessageEntity message = new MMessageEntity();
//                message.setMemberId(toId);
//                message.setType(jpushBean.getType());
//                message.setTitle(jpushBean.getTitle());
//                message.setContent(jpushBean.getAlert());
//                message.setParam(param);
//                message.setReadStatus(MMessageReadStatusEnum.UNREAD.getKey());
//                message.setCreateDate(date);
//                messages.add(message);
//            }
//            messageDao.save(messages);
//        }
//        // 发送给特定标签用户的消息
//        else if (!jpushBean.getTagValues().isEmpty()) {
//            MMessageEntity message = new MMessageEntity();
//            message.setType(jpushBean.getType());
//            message.setTitle(jpushBean.getTitle());
//            message.setContent(jpushBean.getAlert());
//            message.setParam(param);
//            message.setReadStatus(MMessageReadStatusEnum.UNREAD.getKey());
//            message.setCreateDate(date);
//            messageDao.save(message);
//        }
//    }
}
