/*
 * Project Name: zc-collect-web-user-trunk
 * File Name: AppConfig.java
 * Class Name: AppConfig
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

package com.hengtiansoft.common.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;

import org.springframework.context.MessageSource;

/**
 * Class Name: AppConfigUtil
 * Description: 获取应用全局配置
 * 
 * @author SC
 * 
 */
public final class AppConfigUtil {

    private static final String  ENVIRONMENT = "env";
    
    private static final String APPNAME = "app_name";

    private static MessageSource messageSource;

    public static String getConfig(String key) {
        return messageSource.getMessage(key, null, Locale.ROOT);
    }

    public static String getAppName() {
        return  getConfig(APPNAME);
    }
    
    /**
     * Whether current profile is for PROD environment.
     * 
     * @return
     */
    public static boolean isProdEnv() {
        return "PROD".equalsIgnoreCase(getConfig(ENVIRONMENT));
    }

    /**
     * Whether current profile is for DEV environment.
     * 
     * @return
     */
    public static boolean isDevEnv() {
        return "DEV".equalsIgnoreCase(getConfig(ENVIRONMENT));
    }

    /**
     * @param messageSource
     *            Set messageSource value
     */
    public static void setMessageSource(MessageSource messageSource) {
        AppConfigUtil.messageSource = messageSource;
    }

    public static String getLocalAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new IllegalAccessError(e.getMessage());
        }
    }

}
