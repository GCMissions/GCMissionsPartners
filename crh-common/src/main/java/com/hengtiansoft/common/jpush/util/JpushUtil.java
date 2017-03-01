/*
 * Project Name: zc-collect-common
 * File Name: JpushUtils.java
 * Class Name: JpushUtils
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
package com.hengtiansoft.common.jpush.util;

import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hengtiansoft.common.jpush.bean.JpushBean;

/**
 * Class Name: JpushUtil Description: 推送工具类
 * 
 * @author jialiangli
 *
 */
public class JpushUtil {

    private final static LinkedBlockingQueue<JpushBean> jpushBeans = new LinkedBlockingQueue<JpushBean>(50000);
    private static final Logger log= LoggerFactory.getLogger(JpushUtil.class);
    /**
     * Description: 将需要推送的消息放入推送队列
     *
     * @param jpushBean
     */
    public static void push(JpushBean jpushBean) {
        try {
            getJpushBeans().put(jpushBean);
        } catch (InterruptedException e) {
            log.error("msg",e);
        }
    }

    /**
     * Description: 从推送队列中取出待推送消息
     *
     * @param jpushBean
     */
    public static JpushBean take() {
        JpushBean jpushBean = null;
        try {
            jpushBean = getJpushBeans().take();
        } catch (InterruptedException e) {
            log.error("msg",e);
        }
        return jpushBean;
    }

    public static LinkedBlockingQueue<JpushBean> getJpushBeans() {
        return jpushBeans;
    }

}
