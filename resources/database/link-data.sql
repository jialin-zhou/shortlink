INSERT INTO `t_group_15` (`id`, `gid`, `name`, `username`, `sort_order`, `create_time`, `update_time`, `del_flag`)
VALUES (1752265619253805057, 'tSUBMP', '默认分组', 'admin', 0, '2024-01-31 21:00:00', '2024-01-31 21:00:00', 0);


INSERT INTO `t_user_15` (`id`, `username`, `password`, `real_name`, `phone`, `mail`, `deletion_time`, `create_time`,
                         `update_time`, `del_flag`)
VALUES (1752265616481370113, 'admin', 'admin123456', 'admin', 'yKZz0xLyjNb9LSCOCfJD4w==', '02/9oF/nWTBK0cM8UPtCOw==',
        NULL, '2024-01-31 21:00:00', '2024-01-31 21:00:00', 0);


CREATE TABLE `link`.`t_link_access_stats`  (
                                               `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                               `gid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分组标识',
                                               `full_short_url` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '完整短链接',
                                               `date` date NULL DEFAULT NULL COMMENT '日期',
                                               `pv` int NULL DEFAULT NULL COMMENT '访问量',
                                               `uv` int NULL DEFAULT NULL COMMENT '独立访问数',
                                               `uip` int NULL DEFAULT NULL COMMENT '独立IP数',
                                               `hour` int NULL DEFAULT NULL COMMENT '小时',
                                               `weekday` int NULL DEFAULT NULL COMMENT '星期',
                                               `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                               `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
                                               `del_flag` tinyint(1) NULL DEFAULT NULL COMMENT '删除标识：0 未删除 1 已删除',
                                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

CREATE TABLE `link`.`t_link_locale_stats` (
                                       `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                       `full_short_url` varchar(128) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '完整短链接',
                                       `gid` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '分组标识',
                                       `date` date DEFAULT NULL COMMENT '日期',
                                       `cnt` int DEFAULT NULL COMMENT '访问量',
                                       `province` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '省份名称',
                                       `city` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '市名称',
                                       `adcode` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '城市编码',
                                       `country` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '国家标识',
                                       `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                       `update_time` datetime NOT NULL COMMENT '修改时间',
                                       `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0表示删除 1表示未删除',
                                       PRIMARY KEY (`id`),
                                       UNIQUE KEY `idx_unique_locale_stats` (`full_short_url`,`gid`,`date`,`adcode`,`province`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `link`.`t_link_os_stats`
(
    `id`             bigint   NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `full_short_url` varchar(128) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '完整短链接',
    `gid`            varchar(32) COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '分组标识',
    `date`           date                                    DEFAULT NULL COMMENT '日期',
    `cnt`            int                                     DEFAULT NULL COMMENT '访问量',
    `os`             varchar(64) COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '操作系统',
    `create_time`    datetime                                DEFAULT NULL COMMENT '创建时间',
    `update_time`    datetime NOT NULL COMMENT '修改时间',
    `del_flag`       tinyint(1)                              DEFAULT NULL COMMENT '删除标识 0表示删除 1表示未删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_unique_locale_stats` (`full_short_url`, `gid`, `date`, `os`) USING BTREE
) COMMENT = '短链接监控操作系统访问状态'
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_general_ci;

CREATE TABLE `link`.`t_link_browser_stats`
(
    `id`             bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `full_short_url` varchar(128) DEFAULT NULL COMMENT '完整短链接',
    `gid`            varchar(32)  DEFAULT 'default' COMMENT '分组标识',
    `date`           date         DEFAULT NULL COMMENT '日期',
    `cnt`            int(11) DEFAULT NULL COMMENT '访问量',
    `browser`        varchar(64)  DEFAULT NULL COMMENT '浏览器',
    `create_time`    datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time`    datetime     DEFAULT NULL COMMENT '修改时间',
    `del_flag`       tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_unique_browser_stats` (`full_short_url`,`gid`,`date`,`browser`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `link`.`t_link_access_logs` (
                                      `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                      `full_short_url` varchar(128) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '完整短链接',
                                      `gid` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '分组标识',
                                      `user` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户信息',
                                      `browser` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '浏览器',
                                      `os` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '操作系统',
                                      `ip` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'IP',
                                      `device` varchar(64) DEFAULT NULL COMMENT '访问设备',
                                      `network` varchar(64) DEFAULT NULL COMMENT '访问网络',
                                      `country` varchar(64) DEFAULT NULL COMMENT '国家标识',
                                      `province` varchar(64) DEFAULT NULL COMMENT '省份名称',
                                      `city` varchar(64) DEFAULT NULL COMMENT '市名称',
                                      `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                      `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                                      `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
                                      PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `link`.`t_link_device_stats` (
                                       `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                       `full_short_url` varchar(128) DEFAULT NULL COMMENT '完整短链接',
                                       `gid` varchar(32) DEFAULT 'default' COMMENT '分组标识',
                                       `date` date DEFAULT NULL COMMENT '日期',
                                       `cnt` int(11) DEFAULT NULL COMMENT '访问量',
                                       `device` varchar(64) DEFAULT NULL COMMENT '访问设备',
                                       `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                       `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                                       `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
                                       PRIMARY KEY (`id`),
                                       UNIQUE KEY `idx_unique_browser_stats` (`full_short_url`,`gid`,`date`,`device`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `link`.`t_link_network_stats` (
                                        `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                        `full_short_url` varchar(128) DEFAULT NULL COMMENT '完整短链接',
                                        `gid` varchar(32) DEFAULT 'default' COMMENT '分组标识',
                                        `date` date DEFAULT NULL COMMENT '日期',
                                        `cnt` int(11) DEFAULT NULL COMMENT '访问量',
                                        `network` varchar(64) DEFAULT NULL COMMENT '访问网络',
                                        `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                        `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                                        `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
                                        PRIMARY KEY (`id`),
                                        UNIQUE KEY `idx_unique_browser_stats` (`full_short_url`,`gid`,`date`,`network`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `link`.`t_link_stats_today` (
                                      `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                      `gid` varchar(32) DEFAULT 'default' COMMENT '分组标识',
                                      `full_short_url` varchar(128) DEFAULT NULL COMMENT '短链接',
                                      `date` date DEFAULT NULL COMMENT '日期',
                                      `today_pv` int(11) DEFAULT '0' COMMENT '今日PV',
                                      `today_uv` int(11) DEFAULT '0' COMMENT '今日UV',
                                      `today_uip` int(11) DEFAULT '0' COMMENT '今日IP数',
                                      `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                      `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                                      `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
                                      PRIMARY KEY (`id`),
                                      UNIQUE KEY `idx_unique_full-short-url` (`full_short_url`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;