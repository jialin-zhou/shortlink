package com.nageoffer.shortlink.project.toolkit;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.Optional;

import static com.nageoffer.shortlink.project.common.constant.ShortLinkConstant.DEFAULT_CACHE_VALID_TIME;

/**
 * 短链接工具类
 */
public class LinkUtil {
    /**
     * 获取链接缓存的有效数据时间。
     *
     * @param vaildDate 指定的有效日期。如果此参数为null，则返回默认的缓存有效时间。
     * @return 返回从当前时间到指定日期的毫秒数。如果输入为null，则返回默认缓存有效时间的毫秒数。
     */
    public static long getLinkCacheValidData(Date vaildDate) {
        // 将输入的日期映射到当前时间与指定时间的毫秒差，如果输入为null，则返回默认值
        return Optional.ofNullable(vaildDate)
                .map(each -> DateUtil.between(new Date(), each, DateUnit.MS))
                .orElse(DEFAULT_CACHE_VALID_TIME);

    }

    /**
     * 获取请求的 IP 地址
     * @param request 获取用户真实ip请求
     * @return 用户真实ip地址
     */
    public static String getIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");

        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }

        return ipAddress;
    }
}
