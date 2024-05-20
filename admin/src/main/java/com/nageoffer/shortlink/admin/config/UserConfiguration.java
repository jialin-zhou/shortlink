package com.nageoffer.shortlink.admin.config;

import com.nageoffer.shortlink.admin.common.biz.user.UserTransmitFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 用户配置自动装配
 */
@Configuration
public class UserConfiguration {

    /**
     * 注册 UserTransmitFilter(用户信息传递过滤器) 过滤器
     *
     * @Bean 说明这个方法的返回对象应该被注册为一个 Bean
     * @Param stringRedisTemplate 表明这个 Bean 依赖于一个 StringRedisTemplate 类型的 Bean
     * registration.addUrlPatterns("/*") 适用于所有 URL 模式 拦截所有请求
     *
     * 过滤器是 Java Web 应用中的一种组件，可以在请求到达 Servlet 之前和响应返回客户端之前对请求和响应进行预处理和后处理
     * 常用于实现诸如认证、日志记录、数据压缩等功能
     */
    @Bean
    public FilterRegistrationBean<UserTransmitFilter> globalUserTransmitFilter(StringRedisTemplate stringRedisTemplate) {
        // 创建过滤器
        FilterRegistrationBean<UserTransmitFilter> registration = new FilterRegistrationBean<>();
        // 设置过滤器
        registration.setFilter(new UserTransmitFilter(stringRedisTemplate));
        registration.addUrlPatterns("/*"); // 适用于所有 URL 模式
        registration.setOrder(0); // 最高优先级
        return registration;
    }
}
