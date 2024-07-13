package com.nageoffer.shortlink.project.common.constant;

/**
 * redis key 常量类
 */
public class RedisKeyConstant {

    /**
     * 短链接跳转前缀 key
     */
    public static final String GOTO_SHORT_LINK_KEY = "short-link_goto_%s";

    /**
     * 短链接空值跳转前缀 key
     */
    public static final String GOTO_IS_NULL_SHORT_LINK_KEY = "short-link_is_null_goto_%s";

    /**
     * 短链接跳转锁前缀 key
     */
    public static final String LOCK_GOTO_SHORT_LINK_KEY = "short-link_lock_goto_%s";

    /**
     * 短链接UV统计前缀 key
     */
    public static String UV_SHORT_LINK_UV_KEY = "short-link:stats:uv:";

    /**
     * 短链接UIP统计前缀 key
     */
    public static String UV_SHORT_LINK_UIP_KEY = "short-link:stats:uv:";

    /**
     * 短链接修改分组 ID 锁前缀 Key
     */
    public static final String LOCK_GID_UPDATE_KEY = "short-link:lock:update-gid:%s";
}
