package com.qfleaf.bootstarter.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {
    SUCCESS(200, "操作成功"),
    BAD_REQUEST(400, "参数错误"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "访问被拒绝"),
    NOT_FOUND(404, "资源未找到"),
    CONFLICT(409, "请求冲突"),
    INTERNAL_ERROR(500, "系统异常");

    final int code;
    final String message;
}
