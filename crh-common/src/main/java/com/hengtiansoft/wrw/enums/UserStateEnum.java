/*
 * Project Name: wrw-common
 * File Name: UserStateEnum.java
 * Class Name: UserStateEnum
 * Copyright 2014 Hengtian Software Inc
 * Licensed under the Hengtiansoft
 * http://www.hengtiansoft.com
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hengtiansoft.wrw.enums;

/**
 * Class Name: UserStateEnum
 * Description: 用户状态枚举
 * 
 * @author chengminmiao
 */
public enum UserStateEnum {
    DELETE("0", "禁用"), NORMAL("1", "启用"), LOCK("2", "冻结");

    private String code;

    private String text;

    private UserStateEnum(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public String getCode() {
        return code;
    }

    public String getText() {
        return text;
    }

    public static String getTextByCode(String code) {
        for (UserStateEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type.getText();
            }
        }
        return null;
    }

    public static String getCodeByText(String text) {
        for (UserStateEnum type : values()) {
            if (type.getText().equals(text)) {
                return type.getCode();
            }
        }
        return null;
    }
}
