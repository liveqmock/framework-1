
-- ----------------------------
-- Records of COMMON_MEMBER
-- ----------------------------
INSERT INTO `COMMON_MEMBER` VALUES ('1', 'admin', 'admin@oct.com', '34', 'C4CA4238A0B923820DCC509A6F75849B', '0', '0', '127.0.0.1', '2013-09-25 19:19:38', '0', null, '0', '0', '0', null, '0', '0', '1', '1');
-- ----------------------------
-- Records of RIGHT_MENUITEM
-- ----------------------------
INSERT INTO `RIGHT_MENUITEM` VALUES ('1', '根菜单', null, '1', '1', '2013-09-22 15:23:53', '1', null, '0', '1', null, null, null, null);
INSERT INTO `RIGHT_MENUITEM` VALUES ('2', '系统菜单', '框架', 'qwewq', 'admin', '2013-10-28 11:30:43', 'demo-shortcut', 'icon-grid', '22', '1', '0', '0', '', '1');
INSERT INTO `RIGHT_MENUITEM` VALUES ('3', '分组管理', 'groupview', 'framework.GroupController', 'user3', '2013-09-23 14:30:09', 'crm-fenzu', 'small_group', '55', '1', '1', '1', '/group/query.json', '2');
INSERT INTO `RIGHT_MENUITEM` VALUES ('4', '角色管理', 'roleview', 'framework.RoleController', 'user5', '2013-09-23 14:30:09', 'crm-juese', 'small_role', '12', '1', '1', '1', '/role/findRole.json', '2');
INSERT INTO `RIGHT_MENUITEM` VALUES ('5', '菜单管理', 'menuitemview', 'framework.MenuItemController', 'user6', '2013-09-23 14:30:09', 'crm-caidan', 'small_menu', '100', '1', '1', '1', '/menuItem/findMenuItemList.json', '2');
INSERT INTO `RIGHT_MENUITEM` VALUES ('6', '管理员管理', 'userview', 'framework.UserController', 'user', '2013-10-21 11:55:09', 'crm-gly', 'small_gm', '10', '1', '1', '1', 'member/findUser.json,member/delete.json,member/update.json,member/save.json,member/findRoleByGroupId.json,member/findGroup.json', '2');
INSERT INTO `RIGHT_MENUITEM` VALUES ('7', '系统配置', 'configview', 'framework.ConfigController', 'admin', '2013-10-23 12:31:25', 'crm-xtpz', 'small_config', '10', '1', '1', '1', '/config/list.json', '2');
INSERT INTO `RIGHT_MENUITEM` VALUES ('8','操作管理', 'operateview', 'framework.OperateController', 'user+i', '2013-09-24 09:09:59', 'crm-caozuo', 'small_operate', '2', '1', '0', '1', '/operate/findOperate.json', '2');
INSERT INTO `RIGHT_MENUITEM` VALUES ('9','日志管理', 'logView', 'framework.LogController', 'admin', '2013-12-26 13:09:59', 'crm-caozuo', 'small_operate', '2', '1', '0', '1', '', '2');
INSERT INTO `RIGHT_MENUITEM` VALUES ('10','操作日志管理', 'operateLog', 'operateLog', 'admin', '2013-12-26 13:09:59', 'crm-caozuo', 'small_operate', '2', '1', '0', '1', '', '9');
INSERT INTO `RIGHT_MENUITEM` VALUES ('11','登录日志管理', 'logging', 'logging', 'admin', '2013-12-26 13:09:59', 'crm-caozuo', 'small_operate', '2', '1', '0', '1', '', '9');

-- ----------------------------
-- Records of RIGHT_OPERATE
-- ----------------------------
INSERT INTO `RIGHT_OPERATE` VALUES ('1', '删除菜单', 'admin', '2013-10-21 21:26:40', 'delMenuItem', '0',  '删除菜单', '/framework/menuItem/del.json', '5', '2');
INSERT INTO `RIGHT_OPERATE` VALUES ('2', '添加菜单', 'admin', '2013-10-21 21:27:58', 'addMenuItem', '1',  '添加菜单', '/menuItem/saveOrUpdateMenuItem.json', '5', '1');
INSERT INTO `RIGHT_OPERATE` VALUES ('3', '编辑菜单', 'admin', '2013-10-21 21:29:21', 'editMenuItem', '0',  '编辑菜单', '/role/getMenuTree.json,/role/getRoleTree.json,/menuItem/saveOrUpdateMenuItem.json', '5', '1');
INSERT INTO `RIGHT_OPERATE` VALUES ('4', '删除操作', 'admin', '2013-10-22 16:47:22', 'delOperate', '0',  '删除操作', '/operate/del.json', '8', '2');
INSERT INTO `RIGHT_OPERATE` VALUES ('5', '添加操作', 'admin', '2013-10-22 16:49:23', 'addOperate', '1', '添加操作', '/role/getMenuTree.json', '8', '1');
INSERT INTO `RIGHT_OPERATE` VALUES ('6', '编辑操作', 'admin', '2013-10-22 16:49:53', 'editOperate', '0', '修改操作', '/role/getMenuTree.json,/operate/saveOrUpdateOperate.json', '8', '1');
INSERT INTO `RIGHT_OPERATE` VALUES ('7', '添加角色', 'admin', '2013-10-22 16:54:47', 'addRole', '1',  '添加角色', '/role/addRole.json,/role/getMenuTree.json', '4', '1');
INSERT INTO `RIGHT_OPERATE` VALUES ('8', '编辑角色', 'admin', '2013-10-22 17:08:26', 'editRole', '0',  '编辑角色', '/role/editRole.json,/role/getMenuTree.json', '4', '1');
INSERT INTO `RIGHT_OPERATE` VALUES ('9', '删除角色', 'admin', '2013-10-22 17:21:25', 'delRole', '0', '删除角色', '/role/delRole.json', '4', '1');
INSERT INTO `RIGHT_OPERATE` VALUES ('10', '添加分组', 'admin', '2013-10-22 17:30:48', 'addGroup', '1', '添加分组', '/group/add.json,/role/getRoleTree.json,/group/getGroupTree.json', '3', '1');
INSERT INTO `RIGHT_OPERATE` VALUES ('11', '修改分组', 'admin', '2013-10-22 17:31:18', 'editGroup', '0', '修改分组', '/group/edit.json,/role/getRoleTree.json,/group/getGroupTree.json', '3', '1');
INSERT INTO `RIGHT_OPERATE` VALUES ('12', '删除分组', 'admin', '2013-10-22 17:31:43', 'delGroup', '0','删除分组', '/group/remove.json', '3', '1');
INSERT INTO `RIGHT_OPERATE` VALUES ('13', '删除成员', 'admin', '2013-10-22 18:06:08', 'delMember', '0', '删除成员', 'member/delete.json', '6', '1');
INSERT INTO `RIGHT_OPERATE` VALUES ('14', '添加成员', 'admin', '2013-10-22 18:10:19', 'addMember', '1',  '添加成员', 'member/save.json,group/getGroupTree.json,member/findRoleByGroupId.json', '6', '1');
INSERT INTO `RIGHT_OPERATE` VALUES ('15', '编辑成员', 'admin', '2013-10-22 18:10:42', 'editMember', '1', '编辑成员', 'member/update.json,group/getGroupTree.json,member/findRoleByGroupId.json', '6', '1');
INSERT INTO `RIGHT_OPERATE` VALUES ('16', '删除系统配置', 'admin', '2013-10-23 15:49:45', 'delConfig', '0', '删除系统配置', null, '7', '1');
INSERT INTO `RIGHT_OPERATE` VALUES ('18', '添加系统配置', 'admin', '2013-10-23 15:50:56', 'addConfig', '1', '添加系统配置', null, '7', '1');
INSERT INTO `RIGHT_OPERATE` VALUES ('19', '编辑系统配置', 'admin', '2013-10-23 15:53:35', 'editConfig', '1', '编辑系统配置', null, '7', '1');

-- ----------------------------
-- Records of RIGHT_ROLE
-- ----------------------------
INSERT INTO `RIGHT_ROLE` VALUES ('1', '超级角色', 'admin', '2013-10-18 14:51:54', '超级角色   ', '0');

-- ----------------------------
-- Records of RIGHT_ROLE_MENUITEM
-- ----------------------------
INSERT INTO `RIGHT_ROLE_MENUITEM` VALUES ('1', '1', '2');
INSERT INTO `RIGHT_ROLE_MENUITEM` VALUES ('2', '1', '8');
INSERT INTO `RIGHT_ROLE_MENUITEM` VALUES ('3', '1', '3');
INSERT INTO `RIGHT_ROLE_MENUITEM` VALUES ('4', '1', '4');
INSERT INTO `RIGHT_ROLE_MENUITEM` VALUES ('5', '1', '5');
INSERT INTO `RIGHT_ROLE_MENUITEM` VALUES ('6', '1', '6');
INSERT INTO `RIGHT_ROLE_MENUITEM` VALUES ('7', '1', '7');
INSERT INTO `RIGHT_ROLE_MENUITEM` VALUES ('8', '1', '9');
INSERT INTO `RIGHT_ROLE_MENUITEM` VALUES ('9', '1', '10');
INSERT INTO `RIGHT_ROLE_MENUITEM` VALUES ('10', '1', '11');

-- ----------------------------
-- Records of RIGHT_ROLE_OPERATE
-- ----------------------------
INSERT INTO `RIGHT_ROLE_OPERATE` VALUES ('1', '1', '4');
INSERT INTO `RIGHT_ROLE_OPERATE` VALUES ('2', '1', '5');
INSERT INTO `RIGHT_ROLE_OPERATE` VALUES ('3', '1', '6');
INSERT INTO `RIGHT_ROLE_OPERATE` VALUES ('4', '1', '10');
INSERT INTO `RIGHT_ROLE_OPERATE` VALUES ('5', '1', '11');
INSERT INTO `RIGHT_ROLE_OPERATE` VALUES ('6', '1', '12');
INSERT INTO `RIGHT_ROLE_OPERATE` VALUES ('7', '1', '7');
INSERT INTO `RIGHT_ROLE_OPERATE` VALUES ('8', '1', '8');
INSERT INTO `RIGHT_ROLE_OPERATE` VALUES ('9', '1', '9');
INSERT INTO `RIGHT_ROLE_OPERATE` VALUES ('10', '1', '1');
INSERT INTO `RIGHT_ROLE_OPERATE` VALUES ('11', '1', '2');
INSERT INTO `RIGHT_ROLE_OPERATE` VALUES ('12', '1', '3');
INSERT INTO `RIGHT_ROLE_OPERATE` VALUES ('13', '1', '13');
INSERT INTO `RIGHT_ROLE_OPERATE` VALUES ('14', '1', '14');
INSERT INTO `RIGHT_ROLE_OPERATE` VALUES ('15', '1', '15');
INSERT INTO `RIGHT_ROLE_OPERATE` VALUES ('16', '1', '16');
INSERT INTO `RIGHT_ROLE_OPERATE` VALUES ('17', '1', '18');
INSERT INTO `RIGHT_ROLE_OPERATE` VALUES ('18', '1', '19');

-- ----------------------------
-- Records of SYS_CONFIG
-- ----------------------------
INSERT INTO `SYS_CONFIG` VALUES ('NO_RIGHT_PATHS', '/system/index.html,/system/login.json,/system/center.html,/menuItem/showMenu.json,/system/loginOut.html,dictionary/read.json,/test/', '不需要权限过滤', '非过滤请求地址以\',\'号分隔', '2013-10-18 14:45:19');

-- ----------------------------
-- Records of SYS_DICT
-- ----------------------------
INSERT INTO `SYS_DICT` VALUES ('FRAMEWORK_DESKTOPICON', 'crm-gly', '管理员', '10', '');
INSERT INTO `SYS_DICT` VALUES ('FRAMEWORK_GROUPTYPE', '0', '本公司', '1', '');
INSERT INTO `SYS_DICT` VALUES ('FRAMEWORK_GROUPTYPE', '1', '渠道商', '2', '');
INSERT INTO `SYS_DICT` VALUES ('FRAMEWORK_GROUPTYPE', '2', '分销商', '3', '');
INSERT INTO `SYS_DICT` VALUES ('FRAMEWORK_GROUPTYPE', '3', '合作商', '4', '');
INSERT INTO `SYS_DICT` VALUES ('FRAMEWORK_DESKTOPICON', 'crm-yonghu', '用户', '20', '');
INSERT INTO `SYS_DICT` VALUES ('FRAMEWORK_DESKTOPICON', 'crm-baobiao', '报表', '30', '');
INSERT INTO `SYS_DICT` VALUES ('FRAMEWORK_DESKTOPICON', 'crm-caidan', '菜单', '30', '');
INSERT INTO `SYS_DICT` VALUES ('FRAMEWORK_DESKTOPICON', 'crm-caozuo', '操作', '40', '');
INSERT INTO `SYS_DICT` VALUES ('FRAMEWORK_DESKTOPICON', 'crm-fenzu', '分组', '50', '');
INSERT INTO `SYS_DICT` VALUES ('FRAMEWORK_DESKTOPICON', 'crm-juese', '角色', '60', '');
INSERT INTO `SYS_DICT` VALUES ('FRAMEWORK_DESKTOPICON', 'crm-xtpz', '系统配置', '70', '');
INSERT INTO `SYS_DICT` VALUES ('FRAMEWORK_DESKTOPICON', 'crm-yewu', '业务', '80', '');
INSERT INTO `SYS_DICT` VALUES ('FRAMEWORK_DESKTOPICON', 'crm-yingxiao', '营销', '90', '');
INSERT INTO `SYS_DICT` VALUES ('FRAMEWORK_MENUSTATE', '1', '启用', '10', '');
INSERT INTO `SYS_DICT` VALUES ('FRAMEWORK_MENUSTATE', '0', '禁用', '1', '');
INSERT INTO `SYS_DICT` VALUES ('FRAMEWORK_MENUISSHOW', '1', '显示', '10', '');
INSERT INTO `SYS_DICT` VALUES ('FRAMEWORK_MENUISSHOW', '0', '隐藏', '10', '');
INSERT INTO `SYS_DICT` VALUES ('FRAMEWORK_STARTICON', 'small_gm', '管理员', 20, '');
INSERT INTO `SYS_DICT` VALUES ('FRAMEWORK_STARTICON', 'small_from', '报表', 30, '');
INSERT INTO `SYS_DICT` VALUES ('FRAMEWORK_STARTICON', 'small_menu', '菜单', 30, '');
INSERT INTO `SYS_DICT` VALUES ('FRAMEWORK_STARTICON', 'small_operate', '操作', 40, '');
INSERT INTO `SYS_DICT` VALUES ('FRAMEWORK_STARTICON', 'small_group', '分组', 50, '');
INSERT INTO `SYS_DICT` VALUES ('FRAMEWORK_STARTICON', 'small_role', '角色', 60, '');
INSERT INTO `SYS_DICT` VALUES ('FRAMEWORK_STARTICON', 'small_config', '系统配置', 70, '');
INSERT INTO `SYS_DICT` VALUES ('FRAMEWORK_STARTICON', 'small_business', '业务', 80, '');
INSERT INTO `SYS_DICT` VALUES ('FRAMEWORK_STARTICON', 'small_marketing', '营销', 90, '');
INSERT INTO `SYS_DICT` VALUES ('FRAMEWORK_STARTICON', 'small_crm_user', '用户', 90, '');

-- ----------------------------
-- Records of SYS_TABLE_RULE
-- ----------------------------
INSERT INTO `SYS_TABLE_RULE` VALUES ('24', 'SYS_LOG_INFO', '2013-11-06 11:30:20', '5000', 'SYS_LOG', 'CREATE TABLE [#####] (\r   LOG_ID VARCHAR(100) NOT NULL ,\r   LOG_DATE DATETIME NOT NULL ,\r   LOG_INFO VARCHAR(300) NOT NULL ,\r   LOG_UID BIGINT(20) NOT NULL ,\r   LOG_USERNAME VARCHAR(30) NOT NULL ,\r   PRIMARY KEY (LOG_ID) )\r ENGINE = MyISAM\r DEFAULT CHARACTER SET = utf8', 'CREATE TABLE SYS_LOG (\n  LOG_ID VARCHAR(100) NOT NULL ,\n  LOG_DATE DATETIME NOT NULL ,\n  LOG_INFO VARCHAR(300) NOT NULL ,\n  LOG_UID BIGINT(20) NOT NULL ,\n  LOG_USERNAME VARCHAR(30) NOT NULL ,\n  PRIMARY KEY (LOG_ID) )\nENGINE = MERGE \nDEFAULT CHARACTER SET = utf8\nINSERT_METHOD = LAST', 'ALTER TABLE SYS_ALL UNION = ([$$$$$])');
