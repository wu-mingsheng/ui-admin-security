
-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` 
(url, name, description, pid, creater_id, updater_id, state,create_time, update_time) 
VALUES 
('/user', 'user', '用户管理', 0, 10000, 10000, 'ENABLED', now(), now());

INSERT INTO `permission` 
(url, name, description, pid, creater_id, updater_id, state,create_time, update_time) 
VALUES 
('/user/list', 'user:list', '用户列表', 10000, 10000, 10000, 'ENABLED', now(), now());

INSERT INTO `permission` 
(url, name, description, pid, creater_id, updater_id, state,create_time, update_time) 
VALUES 
('/user/add', 'user:add', '添加用户', 10001, 10000, 10000, 'ENABLED', now(), now());

INSERT INTO `permission` 
(url, name, description, pid, creater_id, updater_id, state,create_time, update_time) 
VALUES 
('/permission/list', 'permission:list', '权限管理', 10000, 10000, 10000, 'ENABLED', now(), now());


INSERT INTO `permission` 
(url, name, description, pid, creater_id, updater_id, state,create_time, update_time) 
VALUES 
('/permission/author', 'permission:author', '角色添加权限', 10003, 10000, 10000, 'ENABLED', now(), now());


