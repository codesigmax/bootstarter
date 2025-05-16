package com.qfleaf.bootstarter.security.request;

import com.qfleaf.bootstarter.security.enums.LoginType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UnifiedLoginRequest {
    @NotNull(message = "登陆类型不能为空")
    private LoginType type;      // 登录类型
    @NotBlank(message = "帐户名不能为空")
    private String account;    // 账号(用户名/手机号/邮箱)
    private String password;   // 密码(type=1时使用)
    private String code;       // 验证码(type=2,3时使用)
}