package com.hengtiansoft.common.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Name: BasicTypeConstant Description: Basic data sheet basic_type
 * 
 * @author taochen
 *
 */
public final class BasicTypeConstant {

    private BasicTypeConstant() {

    }

    public static final String USER_APP_VERSION = "1"; // The user version of the App

    public static final Integer USER_APP_VERSION_ANDROID = 1; // The user version of the App——Android

    public static final Integer USER_APP_VERSION_IOS = 2; // The user version of the App——IOS

    public static final String STORE_APP_VERSION = "2"; // The store version of the App

    public static final Integer STORE_APP_VERSION_ANDROID = 3; // The store version of the App——Android

    public static final Integer STORE_APP_VERSION_IOS = 4; // The store version of the App——IOS

    public static final String PRIZE_SETTING = "3"; // Prize management settings

    public static final Integer PRIZE_AWARD_SETTING = 5; // Prize management of the award setting

    public static final Integer PRIZE_NUMBER_SETTING = 6; // Number of prize management settings

    public static final String USER_SETTING = "4"; // user setting

    public static final Integer USER_SETTING_SERVICETEL = 7; // user setting——Customer Service Hotline

    public static final Integer USER_SETTING_STORERANGE = 8; // user setting——Around the range of stores

    public static final Integer USER_SETTING_SERVICERANGE = 9; // user setting———Service scope

    public static final Integer USER_SETTING_CHARGINGSTANDARD = 10; // user setting———Charges the standard

    public static final String STORE_SETTING = "5"; // store setting

    public static final Integer STORE_SETTING_ORDERTIME = 11; // store setting———Receive order time

    public static final Integer STORE_SETTING_SENDTIME = 12; // store setting———Send time

    public static final Integer STORE_SETTING_GIVEUPNUM = 13; // store setting———Give up service restrictions

    public static final Integer STORE_SETTING_REQUESTTIME = 14; // store setting———Request time

    public static final String STORE_SMS_TEMPLATE = "6"; // Store SMS templates

    public static final Integer STORE_SMS_TEMPLATE_GET_CAPTCHA = 15; // Store SMS templates——get verification code

    public static final String USER_SMS_TEMPLATE = "7"; // Client SMS template

    public static final Integer USER_SMS_TEMPLATE_GET_CAPTCHA = 16; // Client SMS template——get verification code

    public static final String REGISTER_SMS_TEMPLATE = "8"; // Registration SMS template

    public static final Integer REGISTER_SMS_TEMPLATE_SUPPLIER = 17; // Registration SMS template——Dealer

    public static final Integer REGISTER_SMS_TEMPLATE_STORE = 18; // Registration SMS template——Store

    public static final Integer REGISTER_SMS_TEMPLATE_PLATFORM = 19; // Registration SMS template——platform

    public static final String USER_APP_SMS = "9"; // User APP push messages

    public static final Integer USER_APP_SMS_SERVICE_ORDER = 23; // User APP push messages-Store to receive orders

    public static final Integer USER_APP_SMS_STORE_SEND_CAR = 24; // User APP push messages-Store sent

    public static final Integer USER_APP_SMS_WIN_PRIZE = 25; // User APP push messages-The user gets the reward

    public static final Integer USER_APP_SMS_CAR_ARRIVE = 26; // User APP push messages-After the vehicle arrives

    public static final Integer USER_APP_SMS_STORE_GIVE_UP_SERVICE = 34; // User APP push messages-Store to give up
                                                                         // service

    public static final String STORE_APP_SMS = "10"; // store APP push messages

    public static final Integer STORE_APP_SMS_PUSH_ORDER_TO_STORE = 27; // store APP push messages-Push the order to the
                                                                        // store

    public static final Integer STORE_APP_SMS_GIVE_UP_SERVICE = 28; // store APP push messages-give up service

    public static final Integer STORE_APP_SMS_ARREARS_REMIND = 29; // store APP push messages-Arrears reminder

    public static final String PLATFORM_SETTING = "11"; // Platform system settings

    public static final Integer FTP_SETTING_PLATFORM = 30; // Ftp static resource load

    public static final Integer PLATFORM_SETTING_PLATFORM = 31; // Platform static resource loading

    public static final Integer SUPPLIER_SETTING_PLATFORM = 32; // Dealer static resource load

    public static final Integer STORE_SETTING_PLATFORM = 33; // store static resource load

    public static final String PDA_APP_VERSION = "12"; // PDA software version information

    public static final Integer PDA_APP_VERSION_ANDROID = 35; // PDA software version information —— Android

    public static final Integer STORE_SETTING_UPLOADLOCATIONSECONDS = 36; // Store settings - Upload time

    public static final Integer LOGIN_FORGET_PWD_SMS_TEMPLATE = 37; // Login page for forgotten password information
                                                                    // template

    public static final Integer REGISTER_PWD_SMS_TEMPLATE = 37; // The information template for the verification code at
                                                                // the time of registration

    public static final Integer ORDER_SMS_TEMPLATE = 38; // User order reminder message template

    public static List<Integer> getList(String type) {
        List<Integer> list = new ArrayList<Integer>();

        if (USER_APP_VERSION.equals(type)) {
            list.add(USER_APP_VERSION_ANDROID);
            list.add(USER_APP_VERSION_IOS);
        }

        else if (STORE_APP_VERSION.equals(type)) {
            list.add(STORE_APP_VERSION_ANDROID);
            list.add(STORE_APP_VERSION_IOS);
        }

        else if (USER_SETTING.equals(type)) {
            list.add(USER_SETTING_SERVICETEL);
            list.add(USER_SETTING_STORERANGE);
            list.add(USER_SETTING_SERVICERANGE);
            list.add(USER_SETTING_CHARGINGSTANDARD);
        }

        else if (STORE_SETTING.equals(type)) {
            list.add(STORE_SETTING_ORDERTIME);
            list.add(STORE_SETTING_SENDTIME);
            list.add(STORE_SETTING_GIVEUPNUM);
            list.add(STORE_SETTING_REQUESTTIME);
            list.add(STORE_SETTING_UPLOADLOCATIONSECONDS);
        }

        else if (STORE_SMS_TEMPLATE.equals(type)) {
            list.add(STORE_SMS_TEMPLATE_GET_CAPTCHA);
        }

        else if (USER_SMS_TEMPLATE.equals(type)) {
            list.add(USER_SMS_TEMPLATE_GET_CAPTCHA);
        }

        else if (REGISTER_SMS_TEMPLATE.equals(type)) {
            list.add(REGISTER_SMS_TEMPLATE_SUPPLIER);
            list.add(REGISTER_SMS_TEMPLATE_STORE);
            list.add(REGISTER_SMS_TEMPLATE_PLATFORM);
        }

        else if (PRIZE_SETTING.equals(type)) {
            list.add(PRIZE_AWARD_SETTING);
            list.add(PRIZE_NUMBER_SETTING);
        }

        else if (USER_APP_SMS.equals(type)) {
            list.add(USER_APP_SMS_SERVICE_ORDER);
            list.add(USER_APP_SMS_STORE_SEND_CAR);
            list.add(USER_APP_SMS_WIN_PRIZE);
            list.add(USER_APP_SMS_CAR_ARRIVE);
            list.add(USER_APP_SMS_STORE_GIVE_UP_SERVICE);
        }

        else if (STORE_APP_SMS.equals(type)) {
            list.add(STORE_APP_SMS_PUSH_ORDER_TO_STORE);
            list.add(STORE_APP_SMS_GIVE_UP_SERVICE);
            list.add(STORE_APP_SMS_ARREARS_REMIND);
        } else if (PLATFORM_SETTING.equals(type)) {
            list.add(PLATFORM_SETTING_PLATFORM);
            list.add(SUPPLIER_SETTING_PLATFORM);
            list.add(STORE_SETTING_PLATFORM);
            list.add(FTP_SETTING_PLATFORM);
        } else if (PDA_APP_VERSION.equals(type)) {
            list.add(PDA_APP_VERSION_ANDROID);
        }
        return list;
    }
}
