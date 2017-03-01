/*
 * Project Name: wuliangye-collect-common
 * File Name: ImagePathEnum.java
 * Class Name: ImagePathEnum
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
package com.hengtiansoft.common.enumeration;

/**
 * Class Name: ImagePathEnum
 * Description: 图片上传二级目录
 * @author yigesong
 *
 */
public enum ImagePathEnum {
 ADMIN_PRODUCT("10","五品库平台系统商品","/product"),
 
 ADMIN_BRAND("11","五品库平台系统品牌","/brand"),
 
 ADMIN_AD("12","五品库平台系统广告","/da"),
 
 PFLOOR_PRODUCT("13","五品库平台系统楼层商品","/fproduct"),
 
 PRODUCT_DETAIL("14","五品库平台系统楼层商品","/productdetail"),
 
 APP_IMAGE("15","五品库平台系统APP消息主图","/appImage"),
 
 ADMIN_APP_MESSAGE("20","五品库平台系统APP消息","/appmessage"),
 
 ADMIN_TEAM("16","五品库平台系统高端产品团购图片","/adminteam"),
 
 ADMIN_MEDIA("21","吾儿酷袋图片管理","/kd");
 
    private String key;
    private String name;
    private String value;
    
    private ImagePathEnum(String key ,String name, String value){
        this.key=key;
        this.name=name;
        this.value=value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static String getValue(String key){
        for(ImagePathEnum status:values()){
            if(status.getKey().equals(key)){
                return status.getValue();
            }
        }
        return null;
       
    }

}
