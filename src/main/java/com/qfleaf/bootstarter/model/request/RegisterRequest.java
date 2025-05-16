package com.qfleaf.bootstarter.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 16, message = "用户名长度必须在3~16个字符")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
    @NotBlank(message = "邮箱不能为空")
    @Email
    private String email;
    @NotBlank(message = "手机号不能为空")
    private String phone;
}
