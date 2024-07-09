package com.nageoffer.shortlink.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nageoffer.shortlink.admin.common.convention.result.Result;
import com.nageoffer.shortlink.admin.remote.ShortLinkRemoteService;
import com.nageoffer.shortlink.admin.remote.dto.req.ShortLinkGroupStatsAccessRecordReqDTO;
import com.nageoffer.shortlink.admin.remote.dto.req.ShortLinkGroupStatsReqDTO;
import com.nageoffer.shortlink.admin.remote.dto.req.ShortLinkStatsAccessRecordReqDTO;
import com.nageoffer.shortlink.admin.remote.dto.req.ShortLinkStatsReqDTO;
import com.nageoffer.shortlink.admin.remote.dto.resp.ShortLinkStatsAccessRecordRespDTO;
import com.nageoffer.shortlink.admin.remote.dto.resp.ShortLinkStatsRespDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "shortLinkStatsControllerByAdmin")
@RequiredArgsConstructor
public class ShortLinkStatsController {
    private final ShortLinkRemoteService shortLinkRemoteService;

    /**
     * 访问单个短链接指定时间内监控数据
     */
    @GetMapping("/api/short-link/admin/v1/stats")
    public Result<ShortLinkStatsRespDTO> shortLinkStats(ShortLinkStatsReqDTO requestParam) {
        return shortLinkRemoteService.oneShortLinkStats(
                requestParam.getFullShortUrl(),
                requestParam.getGid(),
                requestParam.getEnableStatus(),
                requestParam.getStartDate(),
                requestParam.getEndDate()
        );
    }

    /**
     * 访问分组短链接指定时间内监控数据
     */
    @GetMapping("/api/short-link/admin/v1/stats/group")
    public Result<ShortLinkStatsRespDTO> groupShortLinkStats(ShortLinkGroupStatsReqDTO requestParam) {
        return shortLinkRemoteService.groupShortLinkStats(
                requestParam.getGid(),
                requestParam.getStartDate(),
                requestParam.getEndDate()
        );
    }

    /**
     * 访问单个短链接指定时间内访问记录监控数据
     */
    @GetMapping("/api/short-link/admin/v1/stats/access-record")
    public Result<Page<ShortLinkStatsAccessRecordRespDTO>> shortLinkStatsAccessRecord(ShortLinkStatsAccessRecordReqDTO requestParam) {
        return shortLinkRemoteService.shortLinkStatsAccessRecord(
                requestParam.getFullShortUrl(),
                requestParam.getGid(),
                requestParam.getStartDate(),
                requestParam.getEndDate(),
                requestParam.getEnableStatus(),
                requestParam.getCurrent(),
                requestParam.getSize()
        );
    }

    /**
     * 访问分组短链接指定时间内访问记录监控数据
     */
    @GetMapping("/api/short-link/admin/v1/stats/access-record/group")
    public Result<Page<ShortLinkStatsAccessRecordRespDTO>> groupShortLinkStatsAccessRecord(ShortLinkGroupStatsAccessRecordReqDTO requestParam) {
        return shortLinkRemoteService.groupShortLinkStatsAccessRecord(
                requestParam.getGid(),
                requestParam.getStartDate(),
                requestParam.getEndDate(),
                requestParam.getCurrent(),
                requestParam.getSize()
        );
    }
}
