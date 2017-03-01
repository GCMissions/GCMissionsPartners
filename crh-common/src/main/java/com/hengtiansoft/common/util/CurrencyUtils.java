/*
 * Project Name: wrw-common
 * File Name: MoneyUtils.java
 * Class Name: MoneyUtils
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

import java.text.DecimalFormat;

/**
* Class Name: CurrencyUtils
* Description: 金额转换工具类：
* 使用场景：
* （1）提供从元转换成分 存储到数据库
* （2）提供从分转换成元，展示到页面
* 
* @author xianghuang
*
*/
public class CurrencyUtils {
    
    /**
    * Description: 元转换为分，
    *
    * @param yuan 值保留两位小数,本方法是多余的会丢到；
    *     具体多余的是丢到还是四舍五入要看业务场景
    * @return
    */
    public static Long rmbYuanToFen(Double yuan){
        if(yuan == null){
            return 0L;
        }
        
        DecimalFormat decimalFormat = new DecimalFormat("##");
        String str = decimalFormat.format(yuan.doubleValue() * 100);
        
        return Long.parseLong(str);
    }
    
    /**
    * Description: 分转换为元，值保留两位小数
    *
    * @param fen
    * @return
    */
    public static Double rmbFenToYuan(Long fen){
        if(fen == null){
            return new Double("0.00");
        }
        DecimalFormat decimalFormat = new DecimalFormat("##.##");
        String str = decimalFormat.format(fen.doubleValue() / 100);
        return Double.valueOf(str);
    }
    
    public static void main(String[] args) {
        System.out.println(rmbYuanToFen(new Double(0.100001)));
        System.out.println(rmbFenToYuan(new Long(0L)));
    }
  
}
