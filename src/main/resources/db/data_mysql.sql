-- user
INSERT INTO user 
(id, username, password, creater_id, updater_id, state,create_time, update_time)
VALUES (10000, 'admin','e10adc3949ba59abbe56e057f20f883e',10000, 10000, 'ENABLED', now(), now() );

-- role
INSERT INTO role (id, name,alias, creater_id, updater_id, state,create_time, update_time)
VALUES 
(10000, 'admin','管理角色', 10000, 10000, 'ENABLED', now(), now() );

-- INSERT INTO role (id, name,alias, creater_id, updater_id, state,create_time, update_time)
-- VALUES (20000,'view','查看角色', 10000, 10000, 'ENABLED', now(), now() );

-- user_role
INSERT INTO user_role (user_id, role_id, creater_id, updater_id, state,create_time, update_time) 
VALUES (10000, 10000, 10000, 10000, 'ENABLED', now(), now() );

-- role_permission


