package com.nageoffer.shortlink.project.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 消息队列配置文件
 */
@Getter
@Configuration
public class MessageQueueConfig {

    @Value("${short-link.messageQueue}")
    private String messageQueue;

}
