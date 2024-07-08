package com.nageoffer.shortlink.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nageoffer.shortlink.project.dao.entity.LinkOsStatsDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
 * 地区统计持久层
 */
public interface LinkOsStatsMapper extends BaseMapper<LinkOsStatsDO> {

    /**
     * 短链地区访问统计
     * @param linkOsStatsDO
     */
    @Insert("INSERT INTO t_link_os_stats (full_short_url, gid, date, cnt, os, create_time, update_time, del_flag) " +
            "VALUES (#{linkOsStats.fullShortUrl}, #{linkOsStats.gid}, #{linkOsStats.date}, #{linkOsStats.cnt}, #{linkOsStats.os}, NOW(), NOW(), 0) " +
            "ON DUPLICATE KEY UPDATE cnt = cnt + #{linkOsStats.cnt};")
    void shortLinkOsStats(@Param("linkOsStats") LinkOsStatsDO linkOsStatsDO);
}
