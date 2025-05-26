package com.qfleaf.bootstarter.controller.admin;

import com.qfleaf.bootstarter.common.ApiResponse;
import com.qfleaf.bootstarter.model.request.admin.user.UserCreateRequest;
import com.qfleaf.bootstarter.model.request.admin.user.UserPageRequest;
import com.qfleaf.bootstarter.model.response.PageResponse;
import com.qfleaf.bootstarter.model.response.admin.user.UserPageResponse;
import com.qfleaf.bootstarter.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/m")
@PreAuthorize("hasAnyRole('ADMIN')")
@Tag(name = "用户管理")
@RequiredArgsConstructor
public class AdminUserController {
    private final UserService userService;

    @Operation(summary = "查询用户列表")
    @GetMapping("/list")
    public ApiResponse<PageResponse<UserPageResponse>> list(@Valid @ParameterObject UserPageRequest userPageRequest) {
        PageResponse<UserPageResponse> pageResponse = userService.mPage(userPageRequest);
        return ApiResponse.success(pageResponse);
    }

    @Operation(summary = "创建用户")
    @PostMapping
    public ApiResponse<Boolean> createUser(@Valid @RequestBody UserCreateRequest userCreateRequest) {
        boolean save = userService.createUser(userCreateRequest);
        return ApiResponse.success(save);
    }

    @Operation(summary = "禁用用户")
    @PostMapping("/disable/{id}")
    public ApiResponse<Boolean> disableUser(@PathVariable("id") Long id) {
        boolean disable = userService.disableUserById(id);
        return ApiResponse.success(disable);
    }

}
