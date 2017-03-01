/*
* Project Name: xinyunlian-ecom
* File Name: CommonAttributes.java
* Class Name: CommonAttributes
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

/**
 * 公共参数
 * 
 * @author Hengtiansoft Team
 * @version 1.0_beta
 */
public final class CommonAttributes {

    /** 日期格式配比 */
    public static final String[] DATE_PATTERNS = new String[] { "yyyy", "yyyy-MM", "yyyyMM", "yyyy/MM", "yyyy-MM-dd", "yyyyMMdd", "yyyy/MM/dd",
            "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss", "yyyy/MM/dd HH:mm:ss" };

    private static String        xylXmlPath    = "/wrw.xml";


    /**
     * 不可实例化
     */
    private CommonAttributes() {}

    /**
     * @return the xylXmlPath
     */
    public static String getXylXmlPath() {
        return xylXmlPath;
    }

}
