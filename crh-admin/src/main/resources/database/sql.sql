CREATE TABLE /*!32312 IF NOT EXISTS*/ `salutatory` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(125) NOT NULL,
  `content` text NOT NULL,
  `create_time` timestamp NOT NULL,
  `update_time` timestamp NOT NULL,
  PRIMARY KEY (`id`)
) 

INSERT INTO `salutatory` (`title`, `content`) VALUES
('welcom','<p>Welcom</p>\n');

CREATE TABLE /*!32312 IF NOT EXISTS*/ `model` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(125) NOT NULL,
  `display` varchar(25) NOT NULL,
  `create_time` timestamp NOT NULL,
  `update_time` timestamp NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

REPLACE INTO `model` (`id`, `name`, `display`, `create_time`, `update_time`) VALUES
	('1','normal','0','2017-07-19 17:05:45','2017-07-19 17:05:55'),
	('2','special','1','2017-07-19 17:06:36','2017-07-19 17:06:54');

	
INSERT INTO `function_info` ( `FUNCTION_NAME`, `TYPE`, `PRIORITY`, `PARENT_ID`, `PARENT_IDS`, `LEVEL`, `PERMISSION`, `URL`) VALUES ('Mission ', '0', '6', '0', ',0,', '1', 'mission', NULL);
INSERT INTO `function_info` ( `FUNCTION_NAME`, `TYPE`, `PRIORITY`, `PARENT_ID`, `PARENT_IDS`, `LEVEL`, `PERMISSION`, `URL`) VALUES ('Mission Management', '1', '0', '12', ',0,12,', '2', 'mission.show', 'mission/');


INSERT INTO `role_function` (`ROLE_ID`, `FUNCTION_ID`) VALUES ( '1', '12');
INSERT INTO `role_function` (`ROLE_ID`, `FUNCTION_ID`) VALUES ('1', '13');


INSERT INTO `region` (`ID`, `REGION_NAME`, `COLOR`, `DEL_FLAG`, `CREATE_DATE`, `CREATE_ID`, `MODIFY_DATE`, `MODIFY_ID`) VALUES ('17', 'Retirees', '#000000', '1', '2017-07-21 11:49:06', '1', NULL, NULL);
INSERT INTO `region` (`ID`, `REGION_NAME`, `COLOR`, `DEL_FLAG`, `CREATE_DATE`, `CREATE_ID`, `MODIFY_DATE`, `MODIFY_ID`) VALUES ('18', 'Restricted ', '#ff0080', '1', '2017-07-21 11:49:57', '1', NULL, NULL);


INSERT INTO `country` (`ID`, `COUNTRY_SIMPLE_NAME`, `COUNTRY_NAME`, `DEL_FLAG`, `CREATE_DATE`, `CREATE_ID`, `MODIFY_DATE`, `MODIFY_ID`) VALUES ('172', 'RES', 'Restricted ', '1', '2017-07-21 11:45:57', NULL, NULL, NULL);
INSERT INTO `country` (`ID`, `COUNTRY_SIMPLE_NAME`, `COUNTRY_NAME`, `DEL_FLAG`, `CREATE_DATE`, `CREATE_ID`, `MODIFY_DATE`, `MODIFY_ID`) VALUES ('173', 'RET', 'Retirees', '1', '2017-07-21 11:46:38', NULL, NULL, NULL);


INSERT INTO `country_region_ref` (`ID`, `COUNTRY_ID`, `REGION_ID`, `DEL_FLAG`) VALUES ('36', '173', '17', '1');
INSERT INTO `country_region_ref` (`ID`, `COUNTRY_ID`, `REGION_ID`, `DEL_FLAG`) VALUES ('37', '172', '18', '1');

INSERT INTO `partners` (`ID`, `PARTNER_NAME`, `IMAGE`, `MISSION`, `C_R_REF_ID`, `ADDRESS`, `INTRODUCE`, `DEL_FLAG`, `CREATE_DATE`, `CREATE_ID`, `MODIFY_DATE`, `MODIFY_ID`) VALUES ('33', 'CHINA', 'http://172.16.15.131/church_dev/df1bad39-8077-44cf-a8b8-868c8c68ce02.jpg', 'CHINA', '36', NULL, 'test', '1', '2017-07-21 12:03:07', NULL, NULL, NULL);
INSERT INTO `partners` (`ID`, `PARTNER_NAME`, `IMAGE`, `MISSION`, `C_R_REF_ID`, `ADDRESS`, `INTRODUCE`, `DEL_FLAG`, `CREATE_DATE`, `CREATE_ID`, `MODIFY_DATE`, `MODIFY_ID`) VALUES ('34', 'CHIAN', 'http://172.16.15.131/church_dev/df1bad39-8077-44cf-a8b8-868c8c68ce02.jpg', 'CHINA', '37', '', 'ORGIN', '1', '2017-07-21 12:03:40', NULL, '2017-07-21 13:05:56', '0');

	
	
	
