package com.qfleaf.bootstarter.model.response.admin.role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "角色详情响应数据")
public class RoleDetailsResponse {
    @Schema(description = "角色ID", example = "1")
    private Long id;

    @Schema(description = "角色名称", example = "管理员")
    private String name;

    @Schema(description = "角色编码", example = "ADMIN")
    private String code;

    @Schema(description = "角色描述", example = "系统管理员，拥有最高权限")
    private String description;
}
