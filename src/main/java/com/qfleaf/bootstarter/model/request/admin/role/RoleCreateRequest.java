package com.qfleaf.bootstarter.model.request.admin.role;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "角色创建请求参数")
public class RoleCreateRequest {

    @NotBlank(message = "角色名称不能为空")
    @Size(min = 2, max = 20, message = "角色名称长度需在 2-20 个字符之间")
    @Schema(description = "角色名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "admin")
    private String name;

    @NotBlank(message = "角色编码不能为空")
    @Size(min = 2, max = 20, message = "角色编码长度需在 2-20 个字符之间")
    @Pattern(regexp = "^[A-Z_]+$", message = "角色编码必须由大写字母和下划线组成")
    @Schema(description = "角色编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "ROLE_ADMIN")
    private String code;

    @Size(max = 200, message = "角色描述不能超过 200 个字符")
    @Schema(description = "角色描述", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "系统管理员角色，拥有最高权限")
    private String description;
}
