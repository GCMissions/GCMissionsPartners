/*
 * Project Name: zc-collect-web-user-trunk
 * File Name: EBizError.java
 * Class Name: EBizError
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

package com.hengtiansoft.common.enumeration;

import java.util.Arrays;

import com.hengtiansoft.common.exception.DisplayableError;

/**
 * Class Name: EBizError Description: business errors which may be recoverable, or should be displayed on web page.
 * 
 * @author SC
 */
/**
 * 错误code规则定义
 * 1000开始的 入参错误 （如非空字段为空，参数不合法）
 * 2000开始 数据实体类错误（如实体找不到）
 * 3000开始 业务不变合法
 */
public enum EErrorCode implements DisplayableError {
    
    SUCCESS("0", "操作成功!"),


    // 1000开始的 入参错误 （如非空字段为空，参数不合法）
    PARA_MEMBER_ID_IS_NULL("1000", "输入客户ID为空"),
    
    PARA_ATTR_NAME_IS_NULL("1001", "属性名称不能为空，请重新输入!"),
    PARA_ATTR_VALUE_IS_NULL("1002", "属性值不能为空,请至少填写一个!"),
    
    USER_NAME_VALUE_IS_NULL("1003","姓名不能为空,请重新输入!"),
    LOGIN_ID_VALUE_IS_NULL("1004","用户名不能为空,请重新输入!"),
    PHONE_VALUE_IS_NULL("1005","手机号码不能为空,请重新输入!"),
    USER_ROLE_VALUE_IS_NULL("1006","角色不能为空,请至少选择一个角色!"),
    
    ROLE_NAME_VALUE_IS_NULL("1007","角色名称不能为空,请输入角色名!"),
    ROLE_NAME_TOO_LONG("1008","角色名称过长，请输入不多于6个字的名称！"),
    ROLE_FUNCTION_IS_NULL("1009","请至少选择一个权限!"),
    ROLE_DESC_TOO_LONG("1010","描述过长，请输入不多于20个字的名称!"),

    // 2000开始 数据实体类错误（如实体找不到）
    ENTITY_MEMBER_NOT_EXIST("2000", "客户不存在"), 
    ENTITY_MEMBER_IS_EXIST("2001", "客户已存在"), 
    
    ENTITY_REGION_NOT_EXIST("2004", "找不到对应的地区信息"), 

    ENTITY_PRODUCT_NOT_EXIST("2050", "商品不存在"),
    ENTITY_PRODUCT_IS_EXIST("2051", "商品已存在"),
    
    ENTITY_CATE_NOT_EXIST("2056", "分类不存在"),
    ENTITY_CATE_IS_EXIST("2057", "分类已存在,请重新输入!"),
    
    ENTITY_BARND_NOT_EXIST("2058", "品牌不存在"),
    ENTITY_BARND_IS_EXIST("2059", "品牌已存在,请重新输入!"),
    
    ENTITY_ATTR_NOT_EXIST("2060", "属性不存在"),
    ENTITY_ATTR_IS_EXIST("2061", "属性已存在,请重新输入!"),
    
    ENTITY_ATTR_ATTR_NOT_EXIST("2062", "属性值不存在"),
    ENTITY_ATTR_ATTR_IS_EXIST("2063", "属性值不能有重复,请重新输入!"),
    
    ENTITY_USER_IS_EXIST("2100","用户已存在,请重新输入!"),
    ENTITY_USER_NOT_EXIST("2101","用户不存在"),
    
    ENTITY_ROLE_IS_EXIST("2200","角色名称已存在，请重新输入！"),
    ENTITY_ROLE_NOT_EXIST("2201","角色不存在"),

    ENTITY_COUPON_CONFIG_NOT_EXIST("2110", "优惠券配置不存在"),
    ENTITY_COUPON_CONFIG_IS_EXIST("2111", "优惠券配置已存在"),
    
    ENTITY_RECHARGE_CONFIG_NOT_EXIST("2112", "充值优惠配置不存在"),
    ENTITY_RECHARGE_CONFIG_IS_EXIST("2113", "充值优惠配置已存在"),
    
    ENTITY_AD_NOT_EXIST("2112", "广告不存在"),
    ENTITY_AD_IS_EXIST("2113", "广告已存在"),
    ENTITY_AD_PRODUCTCODE_NOTEXIST("2114", "商品条码不存在，请输入正确的商品条码!"),
    
    ENTITY_ORG_NOT_EXIST("2130", "找不到对应的组织ORG"), 
    ENTITY_ORG_IS_EXIST("2131", "组织ORG已经存在"), 
    
    ENTITY_STOCKID_NOT_EXIST("2121","设置库存时stockId不存在"),
    ENTITY_CREATESHIPMENT_RECEIVINGORG_NOT_EXIST("2122","创建发货单收货人不存在"),
    
    ORG_IS_EXIST("2131", "商家编号已被占用"),
    ORG_NAME_IS_EXIST("2132", "商家已存在"),
    
    // 3000开始 业务不变合法
    BIZ_MEMBER_NOT_ALLOW("3000", "客户状态不允许操作"),
    BIZ_STOCK_NOT_ENOUGH("3001", "库存不足，发货失败！"),
    
    BIZ_ATTR_IS_USING("3002", "属性被类型使用,无法删除!"),
    BIZ_BRAND_IS_USING("3003", "品牌被类型使用,无法删除!"),
    BIZ_ORGSON_IS_USING("3003", "有关联组织关系，不能被删除!"),
    
    USER_DELETE_IS_USING("3004","该账户为当前登录账户,无法删除!"),
    USER_UPDATE_IS_USE("3005","更新失败,当前用户为登录用户,请去个人资料中进行修改!"),
    SYSTEM_USER_NOT_DELETE("3006","该账户为系统账户,无法删除!"),
    USER_UPDATE_NOT_USING("3007","更新失败，修改的不是当前登录用户"),
    
    ROLE_SYSTEM_NOT_DELETE("3008","该角色为系统角色，无法操作注销！"),
    ROLE_DELETE_IS_USING("3009","该角色已绑定账户，无法操作注销，请先解除绑定关系！"),
    BIZ_HAS_ORDER("3010", "有未完成的订单，不能被删除!"),
    BIZ_HAS_CONFIG("3011", "有相同配置不能重复添加!"),
    BIZ_ATTRVALUE_HAS_USING("3012", "属性值被应用，不能被删除！"),
    BIZ_COUPON_HAS_USING("3013", "优惠券被充值配置引用，不能被删除！"),
    EMAIL_VALUE_IS_NULL("3014","邮箱地址不能为空,请重新输入!"),


    
    // Default error
    COMM_INTERNAL_ERROR("COMM0001"),

    // Errors for internal technical issues.
    TECH_PARAM_REQUIRED("TECH0001"), 
    TECH_DATA_NOT_EXIST("TECH0002"), 
    TECH_DATA_INVALID("TECH0003"), 
    TECH_OPTIMISTIC_LOCK("TECH0004"),

    NULL("", "");

    private String              errorCode;

    // this field is only for display, do not set it if it is not needed.
    private String              displayMsg;

    private Object[]            args;

    private static final String DEFAULT_ERROR_MSG = "error.common.unknown";

    private EErrorCode(String errorCode, String displayMsg) {
        this.errorCode = errorCode;
        this.displayMsg = displayMsg;
    }

    private EErrorCode(String errorCode, String displayMsg, String[] args) {
        this.errorCode = errorCode;
        this.displayMsg = displayMsg;
        if (args != null) {
            this.args = Arrays.copyOf(args, args.length);
        }
    }

    private EErrorCode(String errorCode) {
        this.errorCode = errorCode;
        this.displayMsg = DEFAULT_ERROR_MSG;
    }

    @Override
    public boolean isBizError() {
        return !displayMsg.equals(DEFAULT_ERROR_MSG);
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getDisplayMsg() {
        return displayMsg;
    }

    public void setDisplayMsg(String displayMsg) {
        this.displayMsg = displayMsg;
    }

    /**
     * @return return the value of the var args
     */
    @Override
    public Object[] getArgs() {
        return args;
    }

}
