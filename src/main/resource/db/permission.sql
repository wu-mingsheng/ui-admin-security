
-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (10000, '/user', 'user', '用户管理', 0, 10000, 10000, 'ENABLED', '2020-03-21 02:54:29', '2020-03-21 03:06:54');
INSERT INTO `permission` VALUES (10001, '/user/list', 'user:list', '用户列表', 10000, 10000, 10000, 'ENABLED', '2020-03-21 02:55:34', '2020-03-21 03:07:21');
INSERT INTO `permission` VALUES (10002, '/user/add', 'user:add', '添加用户', 10001, 10000, 10000, 'ENABLED', '2020-03-21 02:56:25', '2020-03-21 03:07:31');
INSERT INTO `permission` VALUES (10003, '/permission/list', 'permission:list', '权限管理', 10000, 10000, 10000, 'ENABLED', '2020-03-21 03:03:39', '2020-03-21 03:07:43');
INSERT INTO `permission` VALUES (10004, '/permission/author', 'permission:author', '角色添加权限', 10003, 10000, 10000, 'ENABLED', '2020-03-21 03:05:16', '2020-03-21 03:08:00');


