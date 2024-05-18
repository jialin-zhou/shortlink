package com.nageoffer.shortlink.admin.common.enums;

import com.nageoffer.shortlink.admin.common.convention.errorcode.IErrorCode;

public enum UserErrorCodeEnum implements IErrorCode {

    USER_NULL_ERROR("B000200", "用户不存在"),
    USER_EXIST_ERROR("B000201", "用户名已存在"),
    PASSWORD_SHORT_ERROR("B000202", "密码长度错误"),
    USER_SAVE_ERROR("B000203", "用户记录新增失败"),
    USER_REGISTER_LOCK_ERROR("B000204", "用户注册锁获取失败"),
    USER_ALREADY_LOGIN_ERROR("B000205", "用戶已经登录"),
    USER_NOT_LOGIN_ERROR("B000206", "用戶未登录");

    private final String code;

    private final String message;

    UserErrorCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
