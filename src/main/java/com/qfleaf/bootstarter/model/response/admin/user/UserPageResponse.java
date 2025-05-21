package com.qfleaf.bootstarter.model.response.admin.user;

import com.qfleaf.bootstarter.model.enums.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户分页响应对象")
public class UserPageResponse {
    @Schema(description = "用户ID", example = "1")
    private Long id;

    @Schema(description = "用户名", example = "admin")
    private String username;

    @Schema(description = "手机号", example = "13800138000")
    private String phone;

    @Schema(description = "电子邮箱", example = "user@example.com")
    private String email;

    @Schema(description = "创建时间", example = "2023-01-01 12:00:00")
    private java.sql.Timestamp createTime;

    @Schema(description = "更新时间", example = "2023-01-02 13:30:00")
    private java.sql.Timestamp updateTime;

    @Schema(description = "用户状态", example = "ACTIVE",
            allowableValues = {"ACTIVE", "INACTIVE", "BLOCK"}) // 假设枚举有这些值
    private UserStatus status;
}

