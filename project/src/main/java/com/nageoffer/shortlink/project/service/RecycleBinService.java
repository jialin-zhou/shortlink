package com.nageoffer.shortlink.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nageoffer.shortlink.project.dao.entity.ShortLinkDO;
import com.nageoffer.shortlink.project.dto.req.RecycleBinRecoverReqDTO;
import com.nageoffer.shortlink.project.dto.req.RecycleBinRemoveReqDTO;
import com.nageoffer.shortlink.project.dto.req.RecycleBinSaveReqDTO;
import com.nageoffer.shortlink.project.dto.req.ShortLinkRecycleBinPageReqDTO;
import com.nageoffer.shortlink.project.dto.resp.ShortLinkPageRespDTO;

/**
 * 回收站管理接口
 */
public interface RecycleBinService extends IService<ShortLinkDO> {

    /**
     * 保存回收站
     * @param requestParam 请求参数
     */
    void saveRecycleBin(RecycleBinSaveReqDTO requestParam);

    /**
     * 回收站 分页查询短链接
     * @param requestParam 分页查询短链接请求参数
     * @return 短链接分页返回参数
     */
    IPage<ShortLinkPageRespDTO> pageRecycleBinShortLink(ShortLinkRecycleBinPageReqDTO requestParam);

    /**
     * 回收站恢复短链接
     * @param requestParam 回收站恢复短链接请求参数
     */
    void recoverRecycleBin(RecycleBinRecoverReqDTO requestParam);

    /**
     * 回收站删除短链接
     * @param requestParam 回收站删除短链接 请求参数
     */
    void removeRecycleBin(RecycleBinRemoveReqDTO requestParam);
}
