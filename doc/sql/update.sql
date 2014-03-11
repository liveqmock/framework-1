-----------update sql------------------

---------------------------------version 1.0.12-------------------------------------
alter table RIGHT_GROUP add PARENT_GROUP_ID int(11) default 0 comment '父ID';

------------------------version update 1.0.21 to 1.0.22---------------------------------------------
update RIGHT_OPERATE set OP_URL = 'member/save.json,group/getGroupTree.json,member/findRoleByGroupId.json' where OP_ID = 14;
update RIGHT_OPERATE set OP_URL = 'member/update.json,group/getGroupTree.json,member/findRoleByGroupId.json' where OP_ID = 15;
update RIGHT_OPERATE set OP_URL = 'member/delete.json' where OP_ID = 13;



----version update 1.0.22 to 1.0.23----
DROP TABLE IF EXISTS `SYS_OPERATE_LOG`;
CREATE TABLE `SYS_OPERATE_LOG` (
  `OP_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `OP_TYPE` int(11) DEFAULT NULL,
  `OP_UID` bigint(20) DEFAULT NULL,
  `OP_NAME` varchar(20) DEFAULT NULL,
  `OP_DATE` datetime DEFAULT NULL,
  `OP_SITE_ID` int(11) DEFAULT NULL,
  `OP_SITE` varchar(50) DEFAULT NULL,
  `OP_INFO` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`OP_ID`)
) ENGINE=MyISAM AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;


ALTER TABLE COMMON_MEMBER_LOGIN ADD LOG_INFO VARCHAR(2000) COMMENT '日志信息';
ALTER TABLE `COMMON_MEMBER_LOGIN` MODIFY COLUMN `LOG_UID`  bigint(20) NULL COMMENT '用户ID' AFTER `LOG_ID`;

INSERT INTO `RIGHT_MENUITEM` VALUES ('9','日志管理', 'logView', 'framework.LogController', 'admin', '2013-12-26 13:09:59', 'crm-caozuo', 'small_operate', '2', '1', '0', '1', '', '2');
INSERT INTO `RIGHT_MENUITEM` VALUES ('10','操作日志管理', 'operateLog', 'operateLog', 'admin', '2013-12-26 13:09:59', 'crm-caozuo', 'small_operate', '2', '1', '0', '1', '', '9');
INSERT INTO `RIGHT_MENUITEM` VALUES ('11','登录日志管理', 'logging', 'logging', 'admin', '2013-12-26 13:09:59', 'crm-caozuo', 'small_operate', '2', '1', '0', '1', '', '9');


INSERT INTO `RIGHT_ROLE_MENUITEM` VALUES ('8', '1', '9');
INSERT INTO `RIGHT_ROLE_MENUITEM` VALUES ('9', '1', '10');
INSERT INTO `RIGHT_ROLE_MENUITEM` VALUES ('10', '1', '11');

