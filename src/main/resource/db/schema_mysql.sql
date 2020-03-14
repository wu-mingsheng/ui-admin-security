-- create database timer  DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_general_ci; 
-- timer
--create table if not exists sys_user (
--
--    id int unsigned auto_increment primary key COMMENT 'ID',
--    creater_id varchar(32) NOT NULL default ''  COMMENT 'creater id',
--    updater_id varchar(32) NOT NULL default ''  COMMENT 'updater id',
--    status varchar(16) NOT NULL DEFAULT '' COMMENT 'base status enum',
--    create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
--    update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
--    username varchar(32) NOT NULL default '' COMMENT '用户名',
--    password varchar(32) NOT NULL default '' COMMENT '密码',
--    role_id int unsigned NOT NULL default 1 COMMENT '角色id',
--    
--
--	unique index (username, password) USING BTREE,
--    unique index usernmae_index(username),
--    unique index role_id_index(role_id)
--
--) engine=innodb AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `role`;
DROP TABLE IF EXISTS `user_role`;
DROP TABLE IF EXISTS `role_permission`;
DROP TABLE IF EXISTS `permission`;

CREATE TABLE `user` (
`id` bigint(11) NOT NULL AUTO_INCREMENT,
`username` varchar(255) NOT NULL,
`password` varchar(255) NOT NULL,
PRIMARY KEY (`id`) 
);
CREATE TABLE `role` (
`id` bigint(11) NOT NULL AUTO_INCREMENT,
`name` varchar(255) NOT NULL,
PRIMARY KEY (`id`) 
);
CREATE TABLE `user_role` (
`user_id` bigint(11) NOT NULL,
`role_id` bigint(11) NOT NULL
);
CREATE TABLE `role_permission` (
`role_id` bigint(11) NOT NULL,
`permission_id` bigint(11) NOT NULL
);
CREATE TABLE `permission` (
`id` bigint(11) NOT NULL AUTO_INCREMENT,
`url` varchar(255) NOT NULL,
`name` varchar(255) NOT NULL,
`description` varchar(255) NULL,
`pid` bigint(11) NOT NULL,
PRIMARY KEY (`id`) 
);























