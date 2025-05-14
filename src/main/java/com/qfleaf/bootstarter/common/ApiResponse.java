package com.qfleaf.bootstarter.common;

import lombok.Getter;

import static com.qfleaf.bootstarter.common.ResultCode.SUCCESS;

@Getter
public class ApiResponse<T> {
    private final int code;
    private final T data;
    private final String msg;

    // 快速构建方法：成功（带数据）
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(SUCCESS.getCode(), data, SUCCESS.getMessage());
    }

    // 快速构建方法：成功（无数据）
    public static ApiResponse<Void> success() {
        return success(null);
    }

    // 快速构建错误响应（指定状态码和消息）
    public static ApiResponse<?> error(int code, String msg) {
        return new ApiResponse<>(code, null, msg);
    }

    // 构造器（建议私有化，限制只能通过静态方法创建）
    private ApiResponse(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }
}
