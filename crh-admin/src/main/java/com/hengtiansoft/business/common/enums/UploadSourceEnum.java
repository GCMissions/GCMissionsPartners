/*
 * Project Name: wrw-admin
 * File Name: UploadSourceEnum.java
 * Class Name: UploadSourceEnum
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
package com.hengtiansoft.business.common.enums;

/**
 * Class Name: UploadSourceEnum
 * Description: 上传源
 * @author zhongyidong
 *
 */
public enum UploadSourceEnum {

    PRODUCT("10", "WELY_PRODUCT", "吾儿网平台系统商品"), BRAND("11", "", "吾儿网平台系统品牌"), 
    AD("12", "WELY_AD", "吾儿网平台系统广告"), FPRODUCT("13", "", "吾儿网平台系统楼层商品"), 
    UEDITOR("14", "", "ueeditor"), APPMESSAGEIMG("15", "", "App消息图片"), 
    GROUP("16", "", "团购图片"), APPMESSAGE("20", "", "吾儿网平台系统APP消息"), 
    COOLBAG("21", "", "吾儿酷袋图片"), MYACTIVITY("22", "WELY_MY_ACTIVITY", "我的活动图片"), 
    KDADVERTISE("23", "WELY_CONTEXT", "我的素材库图片"), IMAGEMATERIAL("24", "", "酷袋广告位图片"),APPSTARTUP("26","","吾儿app启动页");
    
    private String key;
    private String flag;
    private String desc;
    
    UploadSourceEnum(String key, String flag, String desc) {
        this.key = key;
        this.flag = flag;
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    public static String getFlag(String key) {
        for (UploadSourceEnum source : UploadSourceEnum.values()) {
            if (source.getKey().equals(key)) {
                return source.getFlag();
            }
        }
        return null;
    }
}
