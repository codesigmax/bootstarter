package com.qfleaf.bootstarter.controller.admin;

import com.qfleaf.bootstarter.common.ApiResponse;
import com.qfleaf.bootstarter.model.request.admin.role.RoleCreateRequest;
import com.qfleaf.bootstarter.model.request.admin.role.RolePageRequest;
import com.qfleaf.bootstarter.model.response.PageResponse;
import com.qfleaf.bootstarter.model.response.admin.role.RolePageResponse;
import com.qfleaf.bootstarter.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role/m")
@PreAuthorize("hasAnyRole('ADMIN')")
@Tag(name = "角色管理")
@RequiredArgsConstructor
public class AdminRoleController {
    private final RoleService roleService;

    @Operation(summary = "角色分页列表")
    @GetMapping("/list")
    public ApiResponse<PageResponse<RolePageResponse>> list(@ParameterObject RolePageRequest rolePageRequest) {
        PageResponse<RolePageResponse> pageResponse = roleService.mPage(rolePageRequest);
        return ApiResponse.success(pageResponse);
    }

    @Operation(summary = "创建角色")
    @PostMapping
    public ApiResponse<Boolean> createRole(RoleCreateRequest roleCreateRequest) {
        boolean create = roleService.createRole(roleCreateRequest);
        return ApiResponse.success(create);
    }

    @Operation(summary = "删除角色")
    @DeleteMapping("{id}")
    public ApiResponse<Boolean> deleteRole(@PathVariable("id") Long id) {
        boolean delete = roleService.removeById(id);
        return ApiResponse.success(delete);
    }
}
