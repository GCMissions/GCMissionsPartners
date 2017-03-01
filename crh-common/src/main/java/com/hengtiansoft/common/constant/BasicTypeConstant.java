/*
 * Project Name: zc-collect-common
 * File Name: BasicTypeConstant.java
 * Class Name: BasicTypeConstant
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
package com.hengtiansoft.common.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Name: BasicTypeConstant
 * Description: 基础数据表  basic_type  常量设置
 * @author zhisongliu
 *
 */
public final class BasicTypeConstant {
    
    private BasicTypeConstant(){
        
    }
    
    public static final String  USER_APP_VERSION = "1";              // 用户端App版本
    
    public static final Integer USER_APP_VERSION_ANDROID = 1;        // 用户端App版本——Android
    
    public static final Integer USER_APP_VERSION_IOS = 2;            // 用户端App版本——IOS
    
    public static final String  STORE_APP_VERSION = "2";             // 门店端App版本
    
    public static final Integer STORE_APP_VERSION_ANDROID = 3;       // 门店端App版本——Android
    
    public static final Integer STORE_APP_VERSION_IOS = 4;           // 门店端App版本——IOS
    
    public static final String  PRIZE_SETTING = "3";                 //抽奖管理
    
    public static final Integer PRIZE_AWARD_SETTING = 5;             // 抽奖管理奖项设置
    
    public static final Integer PRIZE_NUMBER_SETTING = 6;            //抽奖管理次数设置
    
    public static final String  USER_SETTING="4";                    // 用户设置
    
    public static final Integer USER_SETTING_SERVICETEL=7;           // 用户设置——客服热线
    
    public static final Integer USER_SETTING_STORERANGE=8;           // 用户设置——周边门店范围
     
    public static final Integer USER_SETTING_SERVICERANGE=9;         // 用户设置———服务范围
    
    public static final Integer USER_SETTING_CHARGINGSTANDARD=10;    // 用户设置———收费标准
    
    public static final String  STORE_SETTING="5";                   // 门店设置
    
    public static final Integer STORE_SETTING_ORDERTIME=11;          // 门店设置———接单时间
    
    public static final Integer STORE_SETTING_SENDTIME=12;           // 门店设置———发车时间
    
    public static final Integer STORE_SETTING_GIVEUPNUM=13;          // 门店设置———放弃服务限制
    
    public static final Integer STORE_SETTING_REQUESTTIME=14;        // 门店设置———请求时间
    
    
    public static final String  STORE_SMS_TEMPLATE = "6";                // 门店端短信模板
    
    public static final Integer STORE_SMS_TEMPLATE_GET_CAPTCHA = 15;     // 门店端短信模板——获取验证码
    
    public static final String  USER_SMS_TEMPLATE = "7";                 // 用户端短信模板
    
    public static final Integer USER_SMS_TEMPLATE_GET_CAPTCHA = 16;      // 司机端短信模板——获取验证码
    
    public static final String  REGISTER_SMS_TEMPLATE = "8";             // 用户注册短信模板
    
    public static final Integer REGISTER_SMS_TEMPLATE_SUPPLIER = 17;     // 用户注册短信模板——经销商
    
    public static final Integer REGISTER_SMS_TEMPLATE_STORE = 18;        // 用户注册短信模板——门店

    public static final Integer REGISTER_SMS_TEMPLATE_PLATFORM = 19;     // 用户注册短信模板——平台
    
    public static final String  USER_APP_SMS = "9";                      //用户APP推送消息
    
    public static final Integer USER_APP_SMS_SERVICE_ORDER = 23;         //用户APP消息推送-门店接单
    
    public static final Integer USER_APP_SMS_STORE_SEND_CAR = 24;        //用户APP消息推送-门店派车
    
    public static final Integer USER_APP_SMS_WIN_PRIZE = 25;             //用户APP消息推送-用户中奖
    
    public static final Integer USER_APP_SMS_CAR_ARRIVE = 26;            //用户APP消息推送-车辆到达
    
    public static final Integer USER_APP_SMS_STORE_GIVE_UP_SERVICE = 34; //用户APP消息推送-门店放弃服务
    
    public static final String  STORE_APP_SMS = "10";                    //门店APP消息推送
    
    public static final Integer STORE_APP_SMS_PUSH_ORDER_TO_STORE = 27;  //门店APP消息推送-推送订单至门店
    
    public static final Integer STORE_APP_SMS_GIVE_UP_SERVICE = 28;      //门店APP消息推送-司机放弃服务
    
    public static final Integer STORE_APP_SMS_ARREARS_REMIND = 29;       //门店APP消息推送-欠款提醒
    
    
    public static final String  PLATFORM_SETTING="11";                   //平台系统设置
    
    public static final Integer FTP_SETTING_PLATFORM=30;                 //ftp静态资源加载
    
    public static final Integer PLATFORM_SETTING_PLATFORM=31;            //平台静态资源加载
    
    public static final Integer SUPPLIER_SETTING_PLATFORM=32;            //经销商静态资源加载
    
    public static final Integer STORE_SETTING_PLATFORM=33;               //门店静态资源加载
    
    public static final String  PDA_APP_VERSION = "12";                  // PDA软件版本信息
    
    public static final Integer PDA_APP_VERSION_ANDROID = 35;            // PDA软件版本信息 —— Android
    
    
    public static final Integer  STORE_SETTING_UPLOADLOCATIONSECONDS =36; //门店设置 -技师上传时间
    
    public static final Integer  LOGIN_FORGET_PWD_SMS_TEMPLATE=37;        //首页找回密码
    
    public static final Integer  REGISTER_PWD_SMS_TEMPLATE=37;        //注册验证码
    
    public static final Integer  ORDER_SMS_TEMPLATE=38;        //用户下单对其端提醒
    
    
    public static List<Integer> getList(String type){
        List<Integer> list =new ArrayList<Integer>();
        
        if(USER_APP_VERSION.equals(type)) {          
            list.add(USER_APP_VERSION_ANDROID); 
            list.add(USER_APP_VERSION_IOS);
        } 
        
        else if (STORE_APP_VERSION.equals(type)) {
            list.add(STORE_APP_VERSION_ANDROID);
            list.add(STORE_APP_VERSION_IOS);
        } 
        
        else if(USER_SETTING.equals(type)){
            list.add(USER_SETTING_SERVICETEL);
            list.add(USER_SETTING_STORERANGE);
            list.add(USER_SETTING_SERVICERANGE);
            list.add(USER_SETTING_CHARGINGSTANDARD);
        }
        
        else if(STORE_SETTING.equals(type)){
            list.add(STORE_SETTING_ORDERTIME);
            list.add(STORE_SETTING_SENDTIME);
            list.add(STORE_SETTING_GIVEUPNUM);
            list.add(STORE_SETTING_REQUESTTIME);
            list.add(STORE_SETTING_UPLOADLOCATIONSECONDS);
        }
        
        else if(STORE_SMS_TEMPLATE.equals(type)) {
            list.add(STORE_SMS_TEMPLATE_GET_CAPTCHA);
        }
        
        else if(USER_SMS_TEMPLATE.equals(type)) {
            list.add(USER_SMS_TEMPLATE_GET_CAPTCHA);            
        }
        
        else if(REGISTER_SMS_TEMPLATE.equals(type)) {
            list.add(REGISTER_SMS_TEMPLATE_SUPPLIER);
            list.add(REGISTER_SMS_TEMPLATE_STORE);
            list.add(REGISTER_SMS_TEMPLATE_PLATFORM);
        }
        
        else if(PRIZE_SETTING.equals(type)) {
        	list.add(PRIZE_AWARD_SETTING);
        	list.add(PRIZE_NUMBER_SETTING);
        }
        
        else if(USER_APP_SMS.equals(type)) {
        	list.add(USER_APP_SMS_SERVICE_ORDER);
        	list.add(USER_APP_SMS_STORE_SEND_CAR);
        	list.add(USER_APP_SMS_WIN_PRIZE);
        	list.add(USER_APP_SMS_CAR_ARRIVE);
        	list.add(USER_APP_SMS_STORE_GIVE_UP_SERVICE);
        }
        
        else if(STORE_APP_SMS.equals(type)) {
        	list.add(STORE_APP_SMS_PUSH_ORDER_TO_STORE);
        	list.add(STORE_APP_SMS_GIVE_UP_SERVICE);
        	list.add(STORE_APP_SMS_ARREARS_REMIND);
        }
        else if(PLATFORM_SETTING.equals(type)){
            list.add(PLATFORM_SETTING_PLATFORM);
            list.add(SUPPLIER_SETTING_PLATFORM);
            list.add(STORE_SETTING_PLATFORM);
            list.add(FTP_SETTING_PLATFORM);
        }
        else if(PDA_APP_VERSION.equals(type)) {
            list.add(PDA_APP_VERSION_ANDROID);
        }
        return list;
    }
}
