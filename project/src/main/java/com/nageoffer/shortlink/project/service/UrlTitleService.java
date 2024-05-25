package com.nageoffer.shortlink.project.service;

/**
 * url标题接口层
 */
public interface UrlTitleService {

    /**
     * 根据 URL 获取标题
     * @param url 目标网站路径
     * @return 网站标题
     */
    String getTitleByUrl(String url);
}
