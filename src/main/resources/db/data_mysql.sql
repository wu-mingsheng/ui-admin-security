-- user
INSERT INTO sys_admin.user
(id, username, password, creater_id, updater_id, state,create_time, update_time)
VALUES (10000, 'admin','e10adc3949ba59abbe56e057f20f883e',10000, 10000, 'ENABLED', now(), now() );

-- role
INSERT INTO sys_admin.role (id, name,alias, creater_id, updater_id, state,create_time, update_time)
VALUES 
(10000, 'admin','管理角色', 10000, 10000, 'ENABLED', now(), now() );

-- INSERT INTO role (id, name,alias, creater_id, updater_id, state,create_time, update_time)
-- VALUES (20000,'view','查看角色', 10000, 10000, 'ENABLED', now(), now() );

-- user_role
INSERT INTO sys_admin.user_role (user_id, role_id, creater_id, updater_id, state,create_time, update_time)
VALUES (10000, 10000, 10000, 10000, 'ENABLED', now(), now() );

-- role_permission

INSERT INTO sys_admin.role_permission (role_id, permission_id, creater_id, updater_id, state, create_time, update_time)
VALUES
(10000, 100201, 10000, 10000, 'ENABLED', now(), now());

-- permission

INSERT INTO sys_admin.permission
(id, url, name, description, pid, creater_id, updater_id, state,create_time, update_time)
VALUES
(100000, '/user', 'user', '用户管理', 0, 10000, 10000, 'ENABLED', now(), now());

INSERT INTO sys_admin.permission
(id, url, name, description, pid, creater_id, updater_id, state,create_time, update_time)
VALUES
(100300, '/user/permission', 'user:permission', '权限管理', 100000, 10000, 10000, 'ENABLED', now(), now());


INSERT INTO sys_admin.permission
(id, url, name, description, pid, creater_id, updater_id, state,create_time, update_time)
VALUES
(100301, '/permission/add', 'permission:add', '添加根权限菜单', 100300, 10000, 10000, 'ENABLED', now(), now());

INSERT INTO sys_admin.permission
(id, url, name, description, pid, creater_id, updater_id, state,create_time, update_time)
VALUES
(100200, '/permission/role', 'permission:role', '角色管理', 100000, 10000, 10000, 'ENABLED', now(), now());


INSERT INTO sys_admin.permission
(id, url, name, description, pid, creater_id, updater_id, state,create_time, update_time)
VALUES
(100201, '/permission/addRolePermiss', 'permission:addRolePermiss', '设置权限', 100200, 10000, 10000, 'ENABLED', now(), now());














