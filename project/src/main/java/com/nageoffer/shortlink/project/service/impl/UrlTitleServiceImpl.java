package com.nageoffer.shortlink.project.service.impl;

import com.nageoffer.shortlink.project.service.UrlTitleService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

/**
 * url标题接口实现层
 */
@Slf4j
@Service
public class UrlTitleServiceImpl implements UrlTitleService {
    @SneakyThrows
    @Override
    public String getTitleByUrl(String url) {
        Document doc = Jsoup.connect(url).get();
        return doc.title();
    }
}
