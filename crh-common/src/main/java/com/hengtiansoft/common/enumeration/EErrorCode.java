package com.hengtiansoft.common.enumeration;

import java.util.Arrays;

import com.hengtiansoft.common.exception.DisplayableError;

/**
 * Class Name: EBizError Description: business errors which may be recoverable, or should be displayed on web page.
 * 
 * @author taochen
 */
/**
 * Error code rule definition 
 * 1000+ start of the incoming parameter error (such as non-empty field is empty, the parameter is not legitimate) 
 * 2000+ data entity class error (such as the entity can not be found) 
 * 3000+ operation is
 * not legal
 */
public enum EErrorCode implements DisplayableError {

    SUCCESS("0", "Success!"),

    PARA_MEMBER_ID_IS_NULL("1000", "The entered customer ID is empty"),

    PARA_ATTR_NAME_IS_NULL("1001", "Attribute name can not be empty, please re-enter!"), PARA_ATTR_VALUE_IS_NULL(
            "1002", "Attribute value can not be empty, please fill out at least one!"),

    LOGIN_ID_VALUE_IS_NULL("1004", "User Name can't be empty!"), PHONE_VALUE_IS_NULL("1005",
            "Phone number can not be empty, please re-enter!"), USER_ROLE_VALUE_IS_NULL("1006",
            "Select at least one role!"),

    ROLE_NAME_VALUE_IS_NULL("1007", "Role name cannot be empty. Please enter a role name!"), ROLE_NAME_TOO_LONG("1008",
            "Role name is too long, please type in the name of not more than six words!"), ROLE_FUNCTION_IS_NULL(
            "1009", "Please select at least one permission!"), ROLE_DESC_TOO_LONG("1010",
            "Description is too long, please enter the name of not more than 20 words!"),

    ENTITY_MEMBER_NOT_EXIST("2000", "Customer does not exist"), ENTITY_MEMBER_IS_EXIST("2001",
            "Customer already exists"),

    ENTITY_REGION_NOT_EXIST("2004", "Can not find the corresponding area information"),

    ENTITY_PRODUCT_NOT_EXIST("2050", "Goods do not exist"), ENTITY_PRODUCT_IS_EXIST("2051", "Goods already exists"),

    ENTITY_CATE_NOT_EXIST("2056", "Classification does not exist"), ENTITY_CATE_IS_EXIST("2057",
            "Classification already exists, please re-enter!"),

    ENTITY_BARND_NOT_EXIST("2058", "Brand does not exist"), ENTITY_BARND_IS_EXIST("2059",
            "Brand already exists, please re-enter!"),

    ENTITY_ATTR_NOT_EXIST("2060", "Attribute does not exist"), ENTITY_ATTR_IS_EXIST("2061",
            "Attribute already exists, please re-enter!"),

    ENTITY_ATTR_ATTR_NOT_EXIST("2062", "Attribute value does not exist"), ENTITY_ATTR_ATTR_IS_EXIST("2063",
            "Attribute value can not be repeated, please re-enter!"),

    ENTITY_USER_IS_EXIST("2100", "User already exists!"), ENTITY_USER_NOT_EXIST("2101", "The user does not exist"),

    ENTITY_ROLE_IS_EXIST("2200", "Role name already exists. Please try again"), ENTITY_ROLE_NOT_EXIST("2201",
            "Role does not exist"),

    ENTITY_COUPON_CONFIG_NOT_EXIST("2110", "Coupon configuration does not exist"), ENTITY_COUPON_CONFIG_IS_EXIST(
            "2111", "Coupons configuration already exists"),

    ENTITY_RECHARGE_CONFIG_NOT_EXIST("2112", "Recharge preferential configuration does not exist"), ENTITY_RECHARGE_CONFIG_IS_EXIST(
            "2113", "Recharge preferential configuration already exists"),

    ENTITY_AD_NOT_EXIST("2112", "Advertising does not exist"), ENTITY_AD_IS_EXIST("2113", "Advertising already exists"), ENTITY_AD_PRODUCTCODE_NOTEXIST(
            "2114", "Product bar code does not exist, please enter the correct bar code!"),

    ENTITY_ORG_NOT_EXIST("2130", "Can not find corresponding organization"), ENTITY_ORG_IS_EXIST("2131",
            "Organization already exists"),

    ENTITY_STOCKID_NOT_EXIST("2121", "When setting inventory stockId does not exist"), ENTITY_CREATESHIPMENT_RECEIVINGORG_NOT_EXIST(
            "2122", "Consignee does not exist"),

    ORG_IS_EXIST("2131", "Business ID already exists"), ORG_NAME_IS_EXIST("2132", "Business name already exists"),

    BIZ_MEMBER_NOT_ALLOW("3000", "Customer status does not allow operation"), BIZ_STOCK_NOT_ENOUGH("3001",
            "Insufficient stock, delivery failed!"),

    BIZ_ATTR_IS_USING("3002", "Attribute is used by type, can not be deleted!"), BIZ_BRAND_IS_USING("3003",
            "Brand is used by type, can not be deleted!"), BIZ_ORGSON_IS_USING("3003",
            "Associated organization relationship can not be deleted!"),

    USER_DELETE_IS_USING("3004", "This account is currently logged in account, can't delete!"), USER_UPDATE_IS_USE(
            "3005", "Update failed, current user is logged in user, please edit in the profile center!"), SYSTEM_USER_NOT_DELETE(
            "3006", "The account for the system to delete!"), USER_UPDATE_NOT_USING("3007",
            "Update fails, change is not the currently logged on user"),

    ROLE_SYSTEM_NOT_DELETE("3008", "The role for a system role to logout operation!"), ROLE_DELETE_IS_USING("3009",
            "Please remove the related user accounts before deleting it!"), BIZ_HAS_ORDER("3010",
            "Have outstanding orders, can not be deleted!"), BIZ_HAS_CONFIG("3011",
            "Have the same configuration can not be added repeatedly!"), BIZ_ATTRVALUE_HAS_USING("3012",
            "Attribute value applied, can not be deleted!"), BIZ_COUPON_HAS_USING("3013",
            "Coupons have been used to recharge the configuration, can not be deleted!"), EMAIL_VALUE_IS_NULL("3014",
            "Email can't be empty!"), EMAIL_VALUE_IS_EXIST("3015", "Email has been registered!"),

    // Default error
    COMM_INTERNAL_ERROR("COMM0001"),

    // Errors for internal technical issues.
    TECH_PARAM_REQUIRED("TECH0001"), TECH_DATA_NOT_EXIST("TECH0002"), TECH_DATA_INVALID("TECH0003"), TECH_OPTIMISTIC_LOCK(
            "TECH0004"),
    // file prase exception
    FILE_PARSE_EXCEPTION("FILE PARSE EXCEPTION!"),

    NULL("", "");

    private String errorCode;

    // this field is only for display, do not set it if it is not needed.
    private String displayMsg;

    private Object[] args;

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
