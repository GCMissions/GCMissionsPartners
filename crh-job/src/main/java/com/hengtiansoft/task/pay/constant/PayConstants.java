/*
 * Project Name: wuerwang_wechat
 * File Name: PayConstants.java
 * Class Name: PayConstants
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
package com.hengtiansoft.task.pay.constant;

/**
 * Class Name: PayConstants
 * Description: 支付相关静态变量
 * @author zhongyidong
 *
 */
public class PayConstants {

    private PayConstants(){
        
    }
    
    /*---------------银联配置文件参数名称--------------*/
    public static final String UNION_VERSION = "5.0.0";
    public static final String UNION_ENCODING = "UTF-8";
    public static final String UNION_MERID = "802310053111771";
    public static final String UNION_FILENAME = "acp_sdk.properties";
    public static final String UNION_MERCHANTID = "acpsdk.merid";
    public static final String UNION_LOG_PATH = "acpsdk.log.back.rootPath";
    public static final String UNION_FRON_TRANS_URL = "acpsdk.frontTransUrl";
    public static final String UNION_BACK_TRANS_URL = "acpsdk.backTransUrl";
    public static final String UNION_SINGLE_QUERY_URL = "acpsdk.singleQueryUrl";
    public static final String UNION_BATCH_TRANS_URL = "acpsdk.batchTransUrl";
    public static final String UNION_FILE_TRANS_URL = "acpsdk.fileTransUrl";

    public static final String UNION_SIGN_CERT_PATH = "acpsdk.signCert.path";
    public static final String UNION_SIGN_CERT_PWD = "acpsdk.signCert.pwd";
    public static final String UNION_SIGN_CERT_TYPE = "acpsdk.signCert.type";
    public static final String UNION_ENCRYPT_CERT_PATH = "acpsdk.encryptCert.path";
    public static final String UNION_VALIDATE_CERT_DIR = "acpsdk.validateCert.dir";
    public static final String UNION_SINGLE_MODE = "acpsdk.singleMode";
    
    /*---------------银联在线配置文件参数名称--------------*/
    // 默认编码
    public static final String CHINA_ENCODING = "UTF-8";
    // 文件分隔符
    public static final String CHINA_FILE_SPLIT_STR = "/";
    // 默认错误码
    public static final String CHINA_DEFAULT_ERROR_CODE = "9999";
    // 配置文件名称
    public static final String CHINA_FILENAME = "security.properties";
    // 私钥证书路径
    public static final String CHINA_SIGN_FILE_PATH = "sign.file";
    // 验签证书路径
    public static final String CHINA_VERIFY_FILE_PATH = "verify.file";
    // 交易成功状态（respCode）
    public static final String CHINA_PAY_SUCCESS = "0000";
    // 商户已审核（respCode）
    public static final String CHINA_VERIFIED = "1003";
    
    /*---------------银联在线配置文件参数名称 - 报文字段--------------*/
    // 商户号
    public static final String CHINA_MER_ID = "531111609200005";
    //　接入机构号
    public static final String CHINA_INSTU_ID = "InstuId";
    // 认证支付-版本号
    public static final String CHINA_VERSION = "20150922";
    // 卡信息字段
    public static final String CHINA_CARD_TRAN_DATA = "CardTranData";
    // 交易保留域
    public static final String CHINA_TRAN_RESERVED = "TranReserved";
    // 签名
    public static final String CHINA_SIGNATURE = "Signature";
    // 响应信息
    public static final String CHINA_RESP_MSG = "respMsg";
    // 响应码
    public static final String CHINA_RESP_CODE = "respCode";
    // 订单状态
    public static final String CHINA_ORDER_STATUS = "OrderStatus";
    // 签约状态
    public static final String CHINA_SIGN_STATUS = "SignState";
    // 协议号
    public static final String CHINA_PROTOCOL_NO = "ProtocolNo";
    // 签约成功
    public static final String CHINA_SIGN_SUCCESS = "01";
    //　验签结果
    public static final String CHINA_VERIFY_KEY = "VERIFY_KEY";

    
}
