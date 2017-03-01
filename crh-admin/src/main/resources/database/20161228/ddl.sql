-- qqz app首页管理
CREATE TABLE `app_hot_ad` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`title` VARCHAR(255) NULL DEFAULT NULL COMMENT '标题',
	`type` VARCHAR(1) NULL DEFAULT NULL COMMENT '类型（6：轮播广告位  7：热门推荐）',
	`status` VARCHAR(1) NULL DEFAULT NULL COMMENT '状态（0-无效，1-有效）',
	`product_id` INT(11) NULL DEFAULT NULL COMMENT '关联商品id',
	`upload_image_url` VARCHAR(255) NULL DEFAULT NULL COMMENT '上传图片的链接地址',
	`related_url` VARCHAR(255) NULL DEFAULT NULL COMMENT '关联URL',
	`sort` SMALLINT(6) NULL DEFAULT NULL COMMENT '排序',
	`create_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`create_id` INT(11) NULL DEFAULT NULL COMMENT '创建人',
	`modify_date` TIMESTAMP NULL DEFAULT NULL COMMENT '修改时间',
	`modify_id` INT(11) NULL DEFAULT NULL COMMENT '修改人',
	PRIMARY KEY (`id`),
	INDEX `product_id` (`product_id`),
	INDEX `type` (`type`),
	INDEX `status` (`status`)
)
COMMENT='【表名】：app首页管理：热门推荐+轮播广告表\r\n【表类别】：业务表\r\n【用途】：广告表\r\n【建表人/负责人】：朱倩倩\r\n【建表时间】：2016年12月28日\r\n【备注】：根据实际需求修改'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;


-- qqz app启动页
CREATE TABLE `app_startup_homepage` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`mobile_type` VARCHAR(1) NULL DEFAULT NULL COMMENT '1-Android；2-ios',
	`status` VARCHAR(1) NULL DEFAULT NULL COMMENT '状态（0-无效，1-有效）',
	`type` VARCHAR(1) NULL DEFAULT NULL COMMENT '业务类型（0-启动页，1-广告业）',
	`upload_image_url` VARCHAR(255) NULL DEFAULT NULL COMMENT '上传图片地址',
	`ad_url` VARCHAR(255) NULL DEFAULT NULL COMMENT '活动链接地址',
	`height` VARCHAR(30) NULL DEFAULT NULL COMMENT '长度',
	`width` VARCHAR(30) NULL DEFAULT NULL COMMENT '宽度',
	`version` VARCHAR(30) NULL DEFAULT NULL COMMENT '版本号',
	`create_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`create_id` INT(11) NULL DEFAULT NULL COMMENT '创建人',
	`modify_date` TIMESTAMP NULL DEFAULT NULL COMMENT '修改时间',
	`modify_id` INT(11) NULL DEFAULT NULL COMMENT '修改人',
	PRIMARY KEY (`id`),
	INDEX `mobile_type` (`mobile_type`),
	INDEX `status` (`status`),
	INDEX `type` (`type`)
)
COMMENT='【表名】：app启动页管理 \r\n【表类别】：业务表\r\n【用途】：广告表\r\n【建表人/负责人】：朱倩倩\r\n【建表时间】：2017年1月6日\r\n【备注】：根据实际需求修改'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;


