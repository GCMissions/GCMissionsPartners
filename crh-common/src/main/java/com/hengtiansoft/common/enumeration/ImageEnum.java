/*
 * Project Name: wuliangye-collect-common
 * File Name: ImageEnum.java
 * Class Name: ImageEnum
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
 * Class Name: ImageEnum
 * Description: 图片上传枚举类
 * @author zhisongliu
 *
 */
public enum ImageEnum {
    PLATFORM("0","五品库平台系统","/wrw-admin/static/upload/image"),
    MOBILE("1","五品库手机端系统","/mobile/static/upload/image");
    
    private String key;
    private String name;
    private String value;
    
    private ImageEnum(String key ,String name, String value){
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
        for(ImageEnum status:values()){
            if(status.getKey().equals(key)){
                return status.getValue();
            }
        }
        return null;
       
    }
}
