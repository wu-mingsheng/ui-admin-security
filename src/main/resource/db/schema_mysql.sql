CREATE TABLE IF NOT EXISTS `user` (
`id` bigint(11) NOT NULL AUTO_INCREMENT primary key COMMENT 'ID',
`username` varchar(32) NOT NULL default '' comment '用户名',
`password` varchar(32) NOT NULL default '' comment '密码',
creater_id bigint(11) NOT NULL default 0  COMMENT '创建人id',
updater_id bigint(11) NOT NULL default 0  COMMENT '更新人id',
state varchar(16) NOT NULL DEFAULT '' COMMENT 'DELETED,ENABLED,DISABLED',
create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
unique index username_index(username)
) engine=innodb AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

CREATE TABLE IF NOT EXISTS `role` (
`id` bigint(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'ID',
`name` varchar(32) NOT NULL DEFAULT '' COMMENT '角色名称',
`alias` varchar(64) NOT NULL DEFAULT '' COMMENT '角色描述',
creater_id bigint(11) NOT NULL default 0  COMMENT '创建人id',
updater_id bigint(11) NOT NULL default 0  COMMENT '更新人id',
state varchar(16) NOT NULL DEFAULT '' COMMENT 'DELETED,ENABLED,DISABLED',
create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) engine=innodb AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

CREATE TABLE IF NOT EXISTS `user_role` (
`id` bigint(11) NOT NULL AUTO_INCREMENT,
`user_id` bigint(11) NOT NULL,
`role_id` bigint(11) NOT NULL,
creater_id bigint(11) NOT NULL default 0  COMMENT '创建人id',
updater_id bigint(11) NOT NULL default 0  COMMENT '更新人id',
state varchar(16) NOT NULL DEFAULT '' COMMENT 'DELETED,ENABLED,DISABLED',
create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
PRIMARY KEY (`id`)
)  engine=innodb AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关系表';

CREATE TABLE IF NOT EXISTS `permission` (
`id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
`url` varchar(255) NOT NULL DEFAULT '' COMMENT 'URL资源',
`name` varchar(255) NOT NULL DEFAULT '' COMMENT '权限名称',
`description` varchar(255) NULL DEFAULT '' COMMENT '权限描述',
`pid` bigint(11) NOT NULL,
creater_id bigint(11) NOT NULL default 0  COMMENT '创建人id',
updater_id bigint(11) NOT NULL default 0  COMMENT '更新人id',
state varchar(16) NOT NULL DEFAULT '' COMMENT 'DELETED,ENABLED,DISABLED',
create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
PRIMARY KEY (`id`) 
) engine=innodb AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

CREATE TABLE IF NOT EXISTS `role_permission` (
`id` bigint(11) NOT NULL AUTO_INCREMENT,
`role_id` bigint(11) NOT NULL,
`permission_id` bigint(11) NOT NULL,
creater_id bigint(11) NOT NULL default 0  COMMENT '创建人id',
updater_id bigint(11) NOT NULL default 0  COMMENT '更新人id',
state varchar(16) NOT NULL DEFAULT '' COMMENT 'DELETED,ENABLED,DISABLED',
create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
PRIMARY KEY (`id`)
) engine=innodb AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关系表';
























