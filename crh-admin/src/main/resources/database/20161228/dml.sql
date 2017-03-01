-- qqz app首页管理
INSERT INTO `s_function_info` (`FUNCTION_ID`, `FUNCTION_NAME`, `TYPE`, `PRIORITY`, `PARENT_ID`, `PARENT_IDS`, `LEVEL`, `PERMISSION`) VALUES (94, 'APP首页管理', '0', 12, 0, ',0,', 1, 'appHotAd');
INSERT INTO `s_function_info` (`FUNCTION_ID`, `FUNCTION_NAME`, `TYPE`, `PRIORITY`, `PARENT_ID`, `PARENT_IDS`, `LEVEL`, `PERMISSION`, `URL`) VALUES (95, 'APP首页管理', '1', 0, 94, ',0,94,', 2, 'appHotAd.indexManager', 'appHotAd/');

INSERT INTO `app_hot_ad` (`id`, `title`, `type`, `status`, `product_id`, `upload_image_url`, `related_url`, `sort`, `create_date`, `create_id`, `modify_date`, `modify_id`) VALUES (1, NULL, '6', '1', NULL, NULL, NULL, 1, now(), NULL, NULL, NULL);
INSERT INTO `app_hot_ad` (`id`, `title`, `type`, `status`, `product_id`, `upload_image_url`, `related_url`, `sort`, `create_date`, `create_id`, `modify_date`, `modify_id`) VALUES (2, NULL, '6', '1', NULL, NULL, NULL, 2, now(), NULL, NULL, NULL);
INSERT INTO `app_hot_ad` (`id`, `title`, `type`, `status`, `product_id`, `upload_image_url`, `related_url`, `sort`, `create_date`, `create_id`, `modify_date`, `modify_id`) VALUES (3, NULL, '6', '1', NULL, NULL, NULL, 3, now(), NULL, NULL, NULL);
INSERT INTO `app_hot_ad` (`id`, `title`, `type`, `status`, `product_id`, `upload_image_url`, `related_url`, `sort`, `create_date`, `create_id`, `modify_date`, `modify_id`) VALUES (4, NULL, '6', '1', NULL, NULL, NULL, 4, now(), NULL, NULL, NULL);
INSERT INTO `app_hot_ad` (`id`, `title`, `type`, `status`, `product_id`, `upload_image_url`, `related_url`, `sort`, `create_date`, `create_id`, `modify_date`, `modify_id`) VALUES (5, NULL, '6', '1', NULL, NULL, NULL, 5, now(), NULL, NULL, NULL);

-- qqz APP启动页管理
INSERT INTO `s_function_info` (`FUNCTION_ID`, `FUNCTION_NAME`, `TYPE`, `PRIORITY`, `PARENT_ID`, `PARENT_IDS`, `LEVEL`, `PERMISSION`) VALUES (96, 'APP启动页管理', '0', 13, 0, ',0,', 1, 'appStartupHomepage');
INSERT INTO `s_function_info` (`FUNCTION_ID`, `FUNCTION_NAME`, `TYPE`, `PRIORITY`, `PARENT_ID`, `PARENT_IDS`, `LEVEL`, `PERMISSION`, `URL`) VALUES (97, 'APP启动页管理', '1', 0, 96, ',0,96,', 2, 'appStartupHomepage.ashManager', 'appStartupHomepage/');