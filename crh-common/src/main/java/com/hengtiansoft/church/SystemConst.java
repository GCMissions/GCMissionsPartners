/*
 * Project Name: wrw-common
 * File Name: SystemConst.java
 * Class Name: SystemConst
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
package com.hengtiansoft.church;

import java.util.Arrays;
import java.util.List;

import com.hengtiansoft.church.enums.OrderStatus;
import com.hengtiansoft.church.enums.ShipmentState;

/**
 * Class Name: SystemConst
 * Description: 默认常量定义类
 * 
 * @author chengminmiao
 */
public class SystemConst {

    // 默认配送费
    public static final Long         DEF_FREIGHT               = 1L;

    // 默认邮寄费
    public static final Long         DEF_POSTAGE               = 2L;

    // P的角色ID
    public static final Long         P_ROLE_ID                 = 1L;

    // Z的角色ID
    public static final Long         Z_ROLE_ID                 = 2L;

    // 系统角色ID
    public static final Long         SYSTEM_ROLE_ID            = 3L;

    // 专卖店角色ID
    public static final Long         SALE_ROLE_ID              = 4L;

    // 平台的角色ID
    public static final Long         PLATFORM_ROLE_ID          = 104L;

    /**
     * 平台的组织ID
     */
    public static final Long         PLATFORM_USER_ORG_ID      = 0L;

    /**
     * 虚拟的向平台发货的组织ID
     */
    public static final Long         SYSTEM_USER_ORG_ID        = -1L;

    /**
     * 虚拟的向平台发货的订单ID
     */
    public static final String       SYSTEM_USER_ORDER_ID      = "0";

    /**
     * 系统账户ID
     */
    public static final Long         SYSTEM_USER_ID            = 1L;

    /**
     * 权限树 --默认节点
     */
    public static final String       TREE_ROOT_ID              = "0";

    /**
     * 一级权限的父节点
     */
    public static final Long         ROLE_FUNCTION_PARENT_ID   = 0L;

    // 开放城市的openFlag
    public static final String       REGION_OPEN_FLAG          = "1";

    // 登录城市的loginFlag
    public static final String       REGION_LOGIN_FLAG         = "1";

    // 热门城市的hotFlag
    public static final String       REGION_HOT_FLAG           = "1";

    // 非开放城市的默认查询ID
    public static final Integer      REGION_NOT_OPEN_CITY      = 100000;

    // 设置默认城市为杭州
    public static final Integer      REGION_CITY_ID            = 330100;

    // 未完成的发货单状态
    public static final List<String> SHIPMENT_UNCOMPLET_STATUS = Arrays.asList(ShipmentState.CREATE.getKey(), ShipmentState.SHIPMENT.getKey());

    // 未完成的订单状态
    public static final List<String> ORDER_UNCOMPLET_STATUS    = Arrays.asList(OrderStatus.WAIT_PAY.getKey());

    // 1入库发货方默认名称
    public static final String       SYSTEM_USERNAME           = "系统操作";

    // 1入库收货方名称
    public static final String       PLATFORM_USERNAME         = "五品库";

    // 1入库操作名称
    public static final String       PLATFORM_CONTENT          = "五品库采购入库";

    // 发货操作
    public static final String       SHIPMENT_CONTENT          = "发货给";

    // 退货操作
    public static final String       RETURN_CONTENT            = "退货给";

    // 收货操作
    public static final String       STORAGE_CONTENT           = "操作入库";

    // 报错信息之订单状态
    public static final String       EXCEPTION_ORDER_STATUS    = "1";

    // 报错信息之条码空值
    public static final String       EXCEPTION_CODE_ISEMPTY    = "0";

    // 报错信息之条码验证
    public static final String       EXCEPTION_CODE_CHECK      = "2";

}
