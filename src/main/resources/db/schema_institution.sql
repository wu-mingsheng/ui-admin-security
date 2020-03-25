-- 机构表
CREATE TABLE IF NOT EXISTS institution (
id bigint(11) NOT NULL AUTO_INCREMENT primary key COMMENT 'ID',
creater_id bigint(11) NOT NULL default 0  COMMENT '创建人id',
updater_id bigint(11) NOT NULL default 0  COMMENT '更新人id',
state varchar(16) NOT NULL DEFAULT '' COMMENT 'DELETED,ENABLED,DISABLED',
create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
name varchar(32) NOT NULL DEFAULT '' COMMENT '机构名称',
category varchar(32) NOT NULL DEFAULT '' COMMENT '业务类别',
sync_state TINYINT(2) NOT NULL DEFAULT 0 COMMENT '同步状态0:未同步1:已同步',
sync_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '同步时间',
account_num INT NOT NULL DEFAULT 0 COMMENT '人数',
band_width INT NOT NULL DEFAULT 0 COMMENT '带宽',
disk_space INT NOT NULL DEFAULT 0 COMMENT '空间'
) engine=innodb AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8mb4 COMMENT='机构表';