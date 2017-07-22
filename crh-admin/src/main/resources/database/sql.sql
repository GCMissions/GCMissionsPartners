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
	
	
	
