package com.qfleaf.bootstarter.model.request.admin.role;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "角色修改请求参数")
public class RoleUpdateRequest extends RoleCreateRequest {
    @NotNull(message = "角色ID不能为空")
    private Long id;
}
