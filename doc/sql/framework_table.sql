

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `COMMON_MEMBER`
-- ----------------------------
DROP TABLE IF EXISTS `COMMON_MEMBER`;
CREATE TABLE `COMMON_MEMBER` (
  `UID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID\n',
  `USERNAME` varchar(20) NOT NULL COMMENT '用户名\n',
  `EMAIL` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `MOBILE` varchar(18) DEFAULT NULL COMMENT '手机号',
  `PASSWORD` varchar(64) DEFAULT NULL COMMENT '密码',
  `EMAIL_STATUS` int(11) DEFAULT '0' COMMENT '邮件验证状态 0：未验证 1：验证\n',
  `MOBILE_STATUS` int(11) DEFAULT '0' COMMENT '手机号码验证状态 0：未验证 1：验证\n',
  `REG_IP` varchar(64) NOT NULL COMMENT '注册IP',
  `REG_DATE` datetime NOT NULL COMMENT '注册时间',
  `REG_SRC` int(11) NOT NULL COMMENT '从那个业务系统过来注册为业务系统ID\n',
  `REG_SRC_NAME` varchar(50) DEFAULT NULL COMMENT '注册',
  `STATUS` int(11) DEFAULT '0' COMMENT '账户状态 0：正常1：停用2：待删除',
  `TYPE` int(11) DEFAULT '0' COMMENT '用户类型 0:管理员1：普通用户 2：员工3：领导 4：导游 5：旅行社 6：特殊客户',
  `AVATAR` int(11) DEFAULT '0' COMMENT '默认头像ID',
  `AVATAR_SRC` varchar(300) DEFAULT NULL COMMENT '自定义头像地址',
  `INTEGRAL` int(11) DEFAULT '0' COMMENT '积分',
  `LEVEL_ID` int(11) DEFAULT '0' COMMENT '级别ID\n',
  `GROUP_ID` int(11) DEFAULT NULL COMMENT '分组id',
  `ROLE_ID` int(11) DEFAULT NULL COMMENT '角色ID\n',
  PRIMARY KEY (`UID`),
  KEY `INDEX_EMAIL` (`EMAIL`),
  KEY `INDEX_MOBILE` (`MOBILE`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `COMMON_MEMBER_ACTION`
-- ----------------------------
DROP TABLE IF EXISTS `COMMON_MEMBER_ACTION`;
CREATE TABLE `COMMON_MEMBER_ACTION` (
  `ACTION_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '操作日志id',
  `UID` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `USERNAME` varchar(20) DEFAULT NULL COMMENT '用户名',
  `ACTION` varchar(150) NOT NULL COMMENT '日志信息',
  `ACTION_DATE` datetime DEFAULT NULL COMMENT '日志时间',
  `ACTION_CODE` int(11) DEFAULT NULL COMMENT '操作代码',
  `ACTION_SRC` int(11) DEFAULT NULL COMMENT '业务系统',
  `ACTION_TYPE` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ACTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `COMMON_MEMBER_LOGIN`
-- ----------------------------
DROP TABLE IF EXISTS `COMMON_MEMBER_LOGIN`;
CREATE TABLE `COMMON_MEMBER_LOGIN` (
  `LOG_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日值ID',
  `LOG_UID` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `LOG_NAME` varchar(20) DEFAULT NULL COMMENT '用户名',
  `LOG_MOBILE` varchar(18) DEFAULT NULL COMMENT '电话',
  `LOG_IP` varchar(64) NOT NULL COMMENT '登陆IP',
  `LOG_DATE` datetime NOT NULL COMMENT '登陆时间',
  `LOG_SITE_ID` int(11) DEFAULT NULL COMMENT '从那个业务系统登陆',
  `LOG_SITE_STRING` VARCHAR(256) COMMENT '业务系统String类型ID',
  `LOG_SITE_NAME` varchar(50) DEFAULT NULL,
  `LOG_CLIENT_TYPE` int(11) NOT NULL COMMENT '应用类型 1:web 2:ios 3:android 4:html5 5:wifi',
  `LOG_INFO` varchar(2000) DEFAULT NULL COMMENT '信息',
  PRIMARY KEY (`LOG_ID`)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `COMMON_MEMBER_PROFILE`
-- ----------------------------
DROP TABLE IF EXISTS `COMMON_MEMBER_PROFILE`;
CREATE TABLE `COMMON_MEMBER_PROFILE` (
  `UID` bigint(20) NOT NULL COMMENT '用户id',
  `MEMBER_NAME` varchar(12) DEFAULT NULL COMMENT '真实姓名',
  `MEMBER_AGE` int(11) DEFAULT '0' COMMENT '年龄',
  `MEMBER_YEAR` int(11) DEFAULT '1900' COMMENT '生日年',
  `MEMBER_MONTH` int(11) DEFAULT '1' COMMENT '生日月12',
  `MEMBER_DAY` int(11) DEFAULT '1' COMMENT '生日日31~30 28~29',
  `MEMBER_GENDER` int(11) DEFAULT '0' COMMENT '性别 0：女 1：男',
  `MEMBER_CONSTELLATION` int(11) DEFAULT '0' COMMENT '星座星座列表从0开始',
  `MEMBER_ZODIAC` int(11) DEFAULT '0' COMMENT '生肖生效列表从0开始',
  `MEMBER_IDCARD_TYPE` int(11) DEFAULT NULL COMMENT '证件类型',
  `MEMBER_IDCARD_NUMBER` varchar(30) DEFAULT NULL COMMENT '证件号码',
  `MEMBER_ADDRESS` varchar(80) DEFAULT NULL COMMENT '住址',
  `MEMBER_BIRTH_PROVINCE` varchar(15) DEFAULT NULL COMMENT '出生省',
  `MEMBER_BIRTH_CITY` varchar(15) DEFAULT NULL COMMENT '出生市',
  `MEMBER_BIRTH_DISTRICT` varchar(15) DEFAULT NULL COMMENT '出生区县',
  `MEMBER_RESIDE_PROVINCE` varchar(15) DEFAULT NULL COMMENT '所在省',
  `MEMBER_RESIDE_CITY` varchar(15) DEFAULT NULL COMMENT '所在市',
  `MEMBER_RESIDE_DISTRICT` varchar(15) DEFAULT NULL COMMENT '所在区县',
  `MEMBER_ZIPCODE` varchar(8) DEFAULT NULL COMMENT '邮编',
  `MEMBER_SCHOOL` varchar(60) DEFAULT NULL COMMENT '毕业学校或在读学校',
  `MEMBER_COMPANY` varchar(60) DEFAULT NULL COMMENT '单位',
  `MEMBER_EDU` varchar(4) DEFAULT NULL COMMENT '学历',
  `MEMBER_OCCUPATION` varchar(10) DEFAULT NULL COMMENT '职业',
  `MEMBER_POSITION` varchar(10) DEFAULT NULL COMMENT '职务',
  `MEMBER_MARRY` varchar(45) DEFAULT NULL COMMENT '是否已婚',
  `MEMBER_QQ` varchar(30) DEFAULT NULL COMMENT 'QQ号码',
  `MEMBER_WEIBO` varchar(60) DEFAULT NULL COMMENT '微博主页',
  `MEMBER_KEY` varchar(200) DEFAULT NULL COMMENT '搜索关键字',
  PRIMARY KEY (`UID`),
  KEY `INDEX_UID` (`UID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `QRTZ_BLOB_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_BLOB_TRIGGERS`;
CREATE TABLE `QRTZ_BLOB_TRIGGERS` (
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `TRIGGER_NAME` (`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `QRTZ_CALENDARS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CALENDARS`;
CREATE TABLE `QRTZ_CALENDARS` (
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `QRTZ_CRON_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CRON_TRIGGERS`;
CREATE TABLE `QRTZ_CRON_TRIGGERS` (
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `TRIGGER_NAME` (`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `QRTZ_FIRED_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_FIRED_TRIGGERS`;
CREATE TABLE `QRTZ_FIRED_TRIGGERS` (
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `IS_VOLATILE` varchar(1) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_STATEFUL` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`ENTRY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `QRTZ_JOB_DETAILS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_JOB_DETAILS`;
CREATE TABLE `QRTZ_JOB_DETAILS` (
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_VOLATILE` varchar(1) NOT NULL,
  `IS_STATEFUL` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`JOB_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `QRTZ_JOB_LISTENERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_JOB_LISTENERS`;
CREATE TABLE `QRTZ_JOB_LISTENERS` (
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `JOB_LISTENER` varchar(200) NOT NULL,
  PRIMARY KEY (`JOB_NAME`,`JOB_GROUP`,`JOB_LISTENER`),
  KEY `JOB_NAME` (`JOB_NAME`,`JOB_GROUP`),
  CONSTRAINT `qrtz_job_listeners_ibfk_1` FOREIGN KEY (`JOB_NAME`, `JOB_GROUP`) REFERENCES `QRTZ_JOB_DETAILS` (`JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `QRTZ_LOCKS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_LOCKS`;
CREATE TABLE `QRTZ_LOCKS` (
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `QRTZ_PAUSED_TRIGGER_GRPS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_PAUSED_TRIGGER_GRPS`;
CREATE TABLE `QRTZ_PAUSED_TRIGGER_GRPS` (
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `QRTZ_SCHEDULER_STATE`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SCHEDULER_STATE`;
CREATE TABLE `QRTZ_SCHEDULER_STATE` (
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `QRTZ_SIMPLE_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPLE_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPLE_TRIGGERS` (
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `TRIGGER_NAME` (`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `QRTZ_TRIGGER_LISTENERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_TRIGGER_LISTENERS`;
CREATE TABLE `QRTZ_TRIGGER_LISTENERS` (
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `TRIGGER_LISTENER` varchar(200) NOT NULL,
  PRIMARY KEY (`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_LISTENER`),
  KEY `TRIGGER_NAME` (`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_trigger_listeners_ibfk_1` FOREIGN KEY (`TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `QRTZ_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_TRIGGERS`;
CREATE TABLE `QRTZ_TRIGGERS` (
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `IS_VOLATILE` varchar(1) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `JOB_NAME` (`JOB_NAME`,`JOB_GROUP`),
  CONSTRAINT `QRTZ_TRIGGERS_ibfk_1` FOREIGN KEY (`JOB_NAME`, `JOB_GROUP`) REFERENCES `QRTZ_JOB_DETAILS` (`JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `RIGHT_GROUP`
-- ----------------------------
DROP TABLE IF EXISTS `RIGHT_GROUP`;
CREATE TABLE `RIGHT_GROUP` (
  `GROUP_ID` int(11) NOT NULL AUTO_INCREMENT,
  `GROUP_NAME` varchar(30) NOT NULL,
  `CREATE_USER` varchar(20) NOT NULL,
  `CREATE_DATE` datetime NOT NULL,
  `GROUP_DESC` varchar(200) DEFAULT NULL,
  `GROUP_TYPE` int(11) NOT NULL,
  `PARENT_GROUP_ID` int(11) NOT NULL DEFAULT '0' COMMENT '父ID',
  PRIMARY KEY (`GROUP_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `RIGHT_GROUP_ROLE`
-- ----------------------------
DROP TABLE IF EXISTS `RIGHT_GROUP_ROLE`;
CREATE TABLE `RIGHT_GROUP_ROLE` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `GROUP_ID` int(11) NOT NULL,
  `ROLE_ID` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_right_group_role_right_group1_idx` (`GROUP_ID`),
  KEY `fk_right_group_role_right_role1_idx` (`ROLE_ID`),
  CONSTRAINT `fk_right_group_role_right_group1` FOREIGN KEY (`GROUP_ID`) REFERENCES `RIGHT_GROUP` (`GROUP_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_right_group_role_right_role1` FOREIGN KEY (`ROLE_ID`) REFERENCES `RIGHT_ROLE` (`ROLE_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `RIGHT_MENUITEM`
-- ----------------------------
DROP TABLE IF EXISTS `RIGHT_MENUITEM`;
CREATE TABLE `RIGHT_MENUITEM` (
  `MENU_ID` int(11) NOT NULL AUTO_INCREMENT,
  `MENU_NAME` varchar(50) NOT NULL,
  `MENU_W_NAME` varchar(30) DEFAULT NULL,
  `MENU_ACTION` varchar(300) NOT NULL,
  `CREATE_USER` varchar(20) NOT NULL,
  `CREATE_DATE` datetime NOT NULL,
  `MENU_ICON_CLS` varchar(300) NOT NULL,
  `MENU_SMALL_CLS` varchar(300) DEFAULT NULL,
  `MENU_PRIORITY` int(11) NOT NULL DEFAULT '0',
  `MENU_STATUS` int(11) NOT NULL,
  `MENU_STARTMENU` int(11) DEFAULT NULL,
  `MENU_QUICKMENU` int(11) DEFAULT NULL,
  `MENU_URL` varchar(250) DEFAULT NULL COMMENT '菜单url',
  `MAIN_MENU_ID` int(11) DEFAULT '0',
  PRIMARY KEY (`MENU_ID`),
  UNIQUE KEY `MENU_W_NAME` (`MENU_W_NAME`),
  KEY `fk_right_menuitem_right_menuitem1_idx` (`MAIN_MENU_ID`),
  CONSTRAINT `fk_right_menuitem_right_menuitem1` FOREIGN KEY (`MAIN_MENU_ID`) REFERENCES `RIGHT_MENUITEM` (`MENU_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `RIGHT_OPERATE`
-- ----------------------------
DROP TABLE IF EXISTS `RIGHT_OPERATE`;
CREATE TABLE `RIGHT_OPERATE` (
  `OP_ID` int(11) NOT NULL AUTO_INCREMENT,
  `OP_NAME` varchar(30) NOT NULL,
  `CREATE_USER` varchar(20) NOT NULL,
  `CREATE_DATE` datetime NOT NULL,
  `OP_ACTION` varchar(300) NOT NULL,
  `OP_IS_BTN` int(11) NOT NULL,
  `OP_DESC` text,
  `OP_URL` varchar(250) DEFAULT NULL COMMENT '按钮url',
  `OP_MENUID` int(11) DEFAULT NULL,
  `OP_SORT` int(11) DEFAULT NULL,
  PRIMARY KEY (`OP_ID`),
  KEY `fk_right_operate_right_menuitem1_idx` (`OP_MENUID`),
  CONSTRAINT `fk_right_operate_right_menuitem1` FOREIGN KEY (`OP_MENUID`) REFERENCES `RIGHT_MENUITEM` (`MENU_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `RIGHT_ROLE`
-- ----------------------------
DROP TABLE IF EXISTS `RIGHT_ROLE`;
CREATE TABLE `RIGHT_ROLE` (
  `ROLE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ROLE_NAME` varchar(30) NOT NULL,
  `CREATE_USER` varchar(20) NOT NULL,
  `CREATE_DATE` datetime NOT NULL,
  `ROLE_DESC` varchar(200) DEFAULT NULL,
  `PARENT_ROLE_ID` int(11) DEFAULT '0',
  PRIMARY KEY (`ROLE_ID`),
  KEY `fk_right_role_right_role1_idx` (`PARENT_ROLE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `RIGHT_ROLE_MENUITEM`
-- ----------------------------
DROP TABLE IF EXISTS `RIGHT_ROLE_MENUITEM`;
CREATE TABLE `RIGHT_ROLE_MENUITEM` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ROLE_ID` int(11) NOT NULL,
  `MENU_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_Role_has_MenuItem_MenuItem1_idx` (`MENU_ID`),
  KEY `fk_right_role_menuitem_right_role1_idx` (`ROLE_ID`),
  CONSTRAINT `fk_right_role_menuitem_right_role1` FOREIGN KEY (`ROLE_ID`) REFERENCES `RIGHT_ROLE` (`ROLE_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Role_has_MenuItem_MenuItem1` FOREIGN KEY (`MENU_ID`) REFERENCES `RIGHT_MENUITEM` (`MENU_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `RIGHT_ROLE_OPERATE`
-- ----------------------------
DROP TABLE IF EXISTS `RIGHT_ROLE_OPERATE`;
CREATE TABLE `RIGHT_ROLE_OPERATE` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ROLE_ID` int(11) NOT NULL,
  `OP_ID` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_Role_has_Operate_Operate1_idx` (`OP_ID`),
  KEY `fk_right_role_operate_right_role1_idx` (`ROLE_ID`),
  CONSTRAINT `fk_right_role_operate_right_role1` FOREIGN KEY (`ROLE_ID`) REFERENCES `RIGHT_ROLE` (`ROLE_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Role_has_Operate_Operate1` FOREIGN KEY (`OP_ID`) REFERENCES `RIGHT_OPERATE` (`OP_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `SYS_CONFIG`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_CONFIG`;
CREATE TABLE `SYS_CONFIG` (
  `CKEY` varchar(50) NOT NULL,
  `CVALUE` varchar(2000) DEFAULT NULL,
  `KEY_DESC` varchar(200) DEFAULT NULL,
  `VALUE_DESC` varchar(200) DEFAULT NULL,
  `CREATE_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`CKEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `SYS_DICT`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_DICT`;
CREATE TABLE `SYS_DICT` (
  `CODE_TYPE` varchar(32) NOT NULL DEFAULT '' COMMENT '代码类型',
  `CODE_ID` varchar(32) NOT NULL DEFAULT '' COMMENT '代码编号',
  `CODE_NAME` varchar(50) NOT NULL DEFAULT '' COMMENT '代码名称',
  `SORT` int(2) NOT NULL COMMENT '排序',
  `REMARK` varchar(200) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`CODE_TYPE`,`CODE_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='数据字典';

-- ----------------------------
-- Table structure for `SYS_TABLE`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_TABLE`;
CREATE TABLE `SYS_TABLE` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `TABLENAME` varchar(200) NOT NULL,
  `CREATEDATE` datetime NOT NULL,
  `STATUS` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `SYS_TABLE_RULE`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_TABLE_RULE`;
CREATE TABLE `SYS_TABLE_RULE` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `TABLENAME` varchar(200) NOT NULL,
  `STARTDATE` datetime NOT NULL,
  `INTERVALS` int(11) NOT NULL,
  `UNION_TABLE` varchar(200) NOT NULL,
  `CREATE_SQL` varchar(500) NOT NULL,
  `UNION_CREATE_SQL` varchar(500) NOT NULL,
  `UNION_UPDATE_SQL` varchar(500) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `SYS_OPERATE_LOG`;
CREATE TABLE `SYS_OPERATE_LOG` (
  `OP_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `OP_TYPE` int(11) DEFAULT NULL,
  `OP_UID` bigint(20) DEFAULT NULL,
  `OP_NAME` varchar(20) DEFAULT NULL,
  `OP_DATE` datetime DEFAULT NULL,
  `OP_SITE_ID` int(11) DEFAULT NULL,
  `OP_SITE_STRING` VARCHAR(256) COMMENT '业务系统String类型ID',
  `OP_SITE` varchar(50) DEFAULT NULL,
  `OP_INFO` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`OP_ID`)
) ENGINE=MyISAM AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;
