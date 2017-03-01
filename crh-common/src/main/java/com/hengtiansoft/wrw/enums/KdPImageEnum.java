/*
 * Project Name: wrw-common
 * File Name: KdPImageEnum.java
 * Class Name: KdPImageEnum
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
package com.hengtiansoft.wrw.enums;

/**
 * Class Name: KdPImageEnum
 * Description: 酷袋商品、专享活动图片枚举
 * @author zhongyidong
 *
 */
public enum KdPImageEnum {

    PRODCUT("0", "商品"),
    TEAMBUY("1", "团购"),
    CHARITY("2", "公益"),
    BARGAIN_ACT("3", "24小时-活动图片"),
    BARGAIN_EXPLAIN("4", "24小时-活动说明图片");
    
    private String code;
    
    private String desc;
    
    KdPImageEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    
}
