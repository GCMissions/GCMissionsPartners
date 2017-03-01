
DROP TABLE IF EXISTS `coolbag_image`;
CREATE TABLE `coolbag_image` (
  `ID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `URL` varchar(255) DEFAULT NULL COMMENT '图片地址',
  `TYPE` char(1) NOT NULL COMMENT '类型 0：轮播位图片，1：标签图片',
  `FEATURE_ID` int(11) unsigned DEFAULT NULL COMMENT '关联专辑ID',
  `NAME` varchar(50) DEFAULT NULL COMMENT '标签名称/专辑名称',
  `SORT` smallint(6) NOT NULL COMMENT '排序',
  `CREATE_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `CREATE_ID` int(11) DEFAULT NULL COMMENT '创建人',
  `MODIFY_DATE` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `MODIFY_ID` int(11) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8 COMMENT='【表名】：酷袋图片表\n【表类别】：业务表\n【用途】：记录酷袋相关图片的信息\n【建表人/负责人】：阮康\n【建表时间】：2016年08月01日'

DROP TABLE IF EXISTS `coolbag_feature`;
CREATE TABLE `coolbag_feature` (
  `ID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `TAG_ID` int(11) unsigned NOT NULL COMMENT '关联coolbag_image表 标签图片ID',
  `TAG` varchar(50) NOT NULL COMMENT '页面标签',
  `NAME` varchar(50) NOT NULL COMMENT '名称',
  `DESCRIPTION` varchar(200) DEFAULT NULL COMMENT '描述',
  `DETAILS` longtext COMMENT '详情',
  `IMAGE` varchar(255) DEFAULT NULL COMMENT '专辑图片',
  `SORT` smallint(6) DEFAULT NULL COMMENT '排序',
  `CREATE_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `CREATE_ID` int(11) DEFAULT NULL COMMENT '创建人',
  `MODIFY_DATE` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `MODIFY_ID` int(11) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8 COMMENT='【表名】：酷袋专辑表\r\n【表类别】：业务表\r\n【用途】：记录酷袋相关专辑的信息\r\n【建表人/负责人】：阮康\r\n【建表时间】：2016年08月01日\r\n【备注】：'

-- 负责人：殷成超    时间：2016/08/05 修改表结构
ALTER TABLE `s_coupon_config`
	ALTER `COUPON_NAME` DROP DEFAULT,
	ALTER `TYPE` DROP DEFAULT;
ALTER TABLE `s_coupon_config`
	CHANGE COLUMN `COUPON_NAME` `COUPON_NAME` VARCHAR(255) NOT NULL COMMENT '优惠券名称' AFTER `COUPON_ID`,
	CHANGE COLUMN `TYPE` `TYPE` VARCHAR(1) NOT NULL COMMENT '优惠券获取类型 1后台发放 2用户获取  3充值赠送' AFTER `REMARK`,
	CHANGE COLUMN `PRODUCT_FLAG` `GET_TYPE` VARCHAR(1) NULL DEFAULT '1' COMMENT '获取品类限制 0匹配 1全品类 ' AFTER `TYPE`,
	ADD COLUMN `USE_TYPE` VARCHAR(1) NULL DEFAULT '1' COMMENT '使用品类限制 0匹配 1全品类 ' AFTER `GET_TYPE`,
	ADD COLUMN `GET_TYPE_DETAIL` VARCHAR(100) NULL DEFAULT NULL COMMENT '获取匹配品类类型,逗号分隔，存品类最低品类id' AFTER `USE_TYPE`,
	ADD COLUMN `USE_TYPE_DETAIL` VARCHAR(100) NULL DEFAULT NULL COMMENT '使用匹配品类类型,逗号分隔，存品类最低品类id' AFTER `GET_TYPE_DETAIL`,
	CHANGE COLUMN `LIMIT_VALUE` `GET_VALUE_LIMIT` INT(11) NULL DEFAULT NULL COMMENT '优惠券获取最低金额' AFTER `MOBILE_USE`,
	ADD COLUMN `USER_VALUE_LIMIT` INT(11) NULL DEFAULT NULL COMMENT '优惠券使用最低金额' AFTER `GET_VALUE_LIMIT`;
ALTER TABLE `s_coupon_config`
	CHANGE COLUMN `GET_TYPE` `FETCH_TYPE` VARCHAR(1) NULL DEFAULT '1' COMMENT '获取品类限制 0匹配 1全品类 ' AFTER `TYPE`;
ALTER TABLE `s_coupon_config`
	CHANGE COLUMN `GET_TYPE_DETAIL` `FETCH_TYPE_DETAIL` VARCHAR(100) NULL DEFAULT NULL COMMENT '获取匹配品类类型,逗号分隔，存品类最低品类id' AFTER `USE_TYPE`,
	CHANGE COLUMN `GET_VALUE_LIMIT` `FETCH_VALUE_LIMIT` INT(11) NULL DEFAULT NULL COMMENT '优惠券获取最低金额' AFTER `MOBILE_USE`;

-- 增加价格区间字段，@zhongyidong
ALTER TABLE `act_stock` ADD COLUMN `HIGH_PRICE` INT(8) NULL DEFAULT 0 COMMENT '最高价'  AFTER `SPEC_INFO` , 
	ADD COLUMN `LOW_PRICE` INT(8) NULL DEFAULT 0 COMMENT '最低价'  AFTER `HIGH_PRICE`;
	
--增加热门搜索词存储表   @panfeng
DROP TABLE IF EXISTS `hot_search_key`;
CREATE TABLE `hot_search_key` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(45) NOT NULL,
  `CREATE_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `CREATE_ID` varchar(45) DEFAULT NULL,
  `MODIFY_DATE` timestamp NULL DEFAULT NULL,
  `MODIFY_ID` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='热门搜索词'

--增加商品排名参数表   @panfeng
DROP TABLE IF EXISTS `product_sort_param`;
CREATE TABLE `product_sort_param` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PRODUCT_ID` int(11) NOT NULL,
  `SALES` int(11) NOT NULL DEFAULT '0' COMMENT '销量',
  `SHARE_COUNT` int(11) NOT NULL DEFAULT '0' COMMENT '分享推荐次数',
  `CLICKS` int(11) NOT NULL DEFAULT '0' COMMENT '点击量',
  `SORT` double DEFAULT NULL COMMENT '排序值',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品排序参数表'

-- 功能所需字段以及建表语句 @yiminli
alter table s_org add column `CONTACT` VARCHAR(45) NULL DEFAULT NULL COMMENT '联系人' after `phone`,
add column `BUSINESSER` VARCHAR(45) NULL DEFAULT NULL COMMENT '企业法人' after `CONTACT`,
add column `REGISTRATION_LICENSE` VARCHAR(20) NULL DEFAULT NULL COMMENT '工商执照注册号' after `BUSINESSER`;
-- 功能所需字段以及建表语句 @yiminli
alter table s_ad add column `AC_ID` INT(11) NULL DEFAULT NULL COMMENT '广告关联的活动id' after `STATUS`;
-- 功能所需字段以及建表语句 @yiminli
CREATE TABLE `image_material` (
	`ID` INT(11) NOT NULL AUTO_INCREMENT,
	`url` VARCHAR(255) NOT NULL COMMENT '图片路径',
	`STATUS` VARCHAR(1) NOT NULL COMMENT '消息状态 0失效，1生效',
	`CREATE_DATE` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`CREATE_ID` INT(11) NULL DEFAULT NULL COMMENT '创建人',
	`MODIFY_DATE` TIMESTAMP NULL DEFAULT NULL COMMENT '修改时间',
	`MODIFY_ID` INT(11) NULL DEFAULT NULL COMMENT '修改人',
	PRIMARY KEY (`ID`)
)
COMMENT='【表名】：图片素材库\r\n【表类别】：业务表\r\n【用途】：图片素材库\r\n【建表人/负责人】：李以敏\r\n【建表时间】：2016年08月05日'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

-- 功能所需字段以及建表语句 @yiminli
CREATE TABLE `act_image_record` (
	`ID` INT(11) NOT NULL AUTO_INCREMENT,
	`ACT_STOCK_ID` INT(11) NOT NULL COMMENT '关联act_stock主键',
	`TYPE` VARCHAR(1) NOT NULL COMMENT '图片类型（0：活动介绍图片；1：活动图片）',
	`url` VARCHAR(255) NOT NULL COMMENT '图片路径',
	`STATUS` VARCHAR(1) NOT NULL COMMENT '消息状态 0失效，1生效',
	`CREATE_DATE` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`CREATE_ID` INT(11) NULL DEFAULT NULL COMMENT '创建人',
	`MODIFY_DATE` TIMESTAMP NULL DEFAULT NULL COMMENT '修改时间',
	`MODIFY_ID` INT(11) NULL DEFAULT NULL COMMENT '修改人',
	PRIMARY KEY (`ID`)
)
COMMENT='【表名】：活动图片关系表\r\n【表类别】：业务表\r\n【用途】：活动图片关系表\r\n【建表人/负责人】：李以敏\r\n【建表时间】：2016年08月05日'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

--品类添加图片@chenghongtu
ALTER TABLE `p_category`
	CHANGE COLUMN `IMAGE` `IMAGE` VARCHAR(255) NULL DEFAULT NULL COMMENT '图片地址' AFTER `SORT`,
	ADD COLUMN `IMAGE_KEY` VARCHAR(255) NULL DEFAULT NULL COMMENT '图片key' AFTER `IMAGE`;

--表字段允许为空@yiminli
ALTER TABLE s_ad CHANGE COLUMN `URL_FLAG` `URL_FLAG` VARCHAR(1) NULL DEFAULT '1' COMMENT '是否为URL 0 否 1 是';

--轮播广告基础数据添加@yiminli
INSERT INTO s_ad (TYPE, STATUS,sort) VALUES ('4','1',1);
INSERT INTO s_ad (TYPE, STATUS,sort) VALUES ('4','1',2);
INSERT INTO s_ad (TYPE, STATUS,sort) VALUES ('4','1',3);
INSERT INTO s_ad (TYPE, STATUS,sort) VALUES ('4','1',4);
INSERT INTO s_ad (TYPE, STATUS,sort) VALUES ('4','1',5);


---添加商品时，在商品的基础属性中增加一个“是否需要验证码”的checkbox框，如勾选，购买了该商品之后会产生验证码，如不勾选，则购买商品后不产生验证码；
ALTER TABLE `p_product`
	ADD COLUMN `IS_CAPTCHA` CHAR(1) NOT NULL DEFAULT '0' COMMENT '是否需要验证码：0是，1否' AFTER `IMAGE_KEY`;
	
---原有库存总量，@zhongyidong
ALTER TABLE `act_stock` ADD COLUMN `ORIGINAL_COUNT` INT(11) NULL DEFAULT 0 COMMENT '原有总库存'  AFTER `STOCK_TYPE` ;

---订单详情表增加子规格库存id，@zhongyidong
ALTER TABLE `m_order_detail` ADD COLUMN `ACT_SPEC_ID` INT(11) NULL COMMENT '规格id'  AFTER `ACT_STOCK_ID` ;

---商品验证码@yiminli
ALTER TABLE `m_order_detail`
	ADD COLUMN `VERIFICATION_CODE` VARCHAR(20) NULL DEFAULT NULL COMMENT '商品验证码' AFTER `PERSONAL_INFO`;
	
---商品验证码相关@yiminli
ALTER TABLE `m_order_detail`
	ADD COLUMN `CODE_USED` VARCHAR(1) NULL DEFAULT NULL COMMENT '验证码是否已经被验证（0：还未 1：已验证）' AFTER `VERIFICATION_CODE`;

---限购数量，@zhongyidong
ALTER TABLE `act_spec` ADD COLUMN `LIMITS` TEXT NULL DEFAULT NULL COMMENT '子规格组合限购数量json,key-value形式'  AFTER `PRICES` ;

---活动商品主规格，@zhongyidong
INSERT INTO `s_basic_type` (`TYPE_ID`, `TYPE_NAME`, `CONFIG_FLAG`, `MODIFY_ID`) VALUES (2, '活动商品主规格', 1, 0);
INSERT INTO `s_basic_para`(type_id, para_name, para_value1) VALUES (2, '人数', '1'),(2, '课程', '2'), (2, '活动', '3'),
(2, '规格', '4'),(2, '款式', '5'),(2, '版本', '6'),(2, '套餐', '7'),(2, '尺寸', '8'),(2, '系列', '9');

---活动商品购买信息，@zhongyidong
INSERT INTO `s_basic_type` (`TYPE_ID`, `TYPE_NAME`, `CONFIG_FLAG`, `MODIFY_ID`) VALUES (3, '活动商品购买信息', 1, 0);
INSERT INTO `s_basic_para`(type_id, para_name, para_value1) VALUES (3, '参与人数', '1'),(3, '姓名', '2'), (3, '手机号', '3'),
(3, '家长姓名', '4'),(3, '家长联系方式', '5'),(3, '家长身份证号', '6'),(3, '孩子姓名', '7'),(3, '孩子年龄', '8'),(3, '孩子性别', '9'),
(3, '孩子身份证号', '10');
INSERT INTO `s_basic_para` (`TYPE_ID`, `PARA_NAME`, `PARA_VALUE1`) VALUES (3, '其他留言', '11');

---活动商品购买信息-必填字段是否选填，@zhongyidong
ALTER TABLE `act_constranint` ADD COLUMN `REQUIRE_MASK` VARCHAR(45) NULL COMMENT '标记必填字段是否必填：0-非必填，1-必填'  AFTER `REQUIRE_FIELDS` ;
ALTER TABLE `s_basic_para`
	CHANGE COLUMN `PARA_VALUE1` `PARA_VALUE1` VARCHAR(500) NULL DEFAULT NULL COMMENT '参数1' AFTER `PARA_NAME`;


