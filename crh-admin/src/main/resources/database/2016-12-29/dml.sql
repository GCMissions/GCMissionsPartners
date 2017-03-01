
--吾儿乐园购买信息增加购买人数字段 @zhongyidong
ALTER TABLE `act_constranint` ADD COLUMN `PARTAKE_INFO` VARCHAR(255) NULL DEFAULT NULL AFTER `BUY_DEADLINE`;

--吾儿乐园商品基本信息添加是否VIP商品标识@zhongyidong
ALTER TABLE `p_product` ADD COLUMN `VIP` CHAR(1) NULL DEFAULT 0 COMMENT '是否会员商品：1是，0否';
ALTER TABLE `kd_p_product` CHANGE COLUMN `VIP` `VIP` CHAR(1) NULL DEFAULT 0 COMMENT '是否会员商品：1是，0否'  ;


