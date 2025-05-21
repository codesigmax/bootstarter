package com.qfleaf.bootstarter.controller;

import com.qfleaf.bootstarter.common.ApiResponse;
import com.qfleaf.bootstarter.model.request.RegisterRequest;
import com.qfleaf.bootstarter.security.request.UnifiedLoginRequest;
import com.qfleaf.bootstarter.security.response.TokenLoginResponse;
import com.qfleaf.bootstarter.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "鉴权API")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public ApiResponse<Boolean> register(@Valid @RequestBody RegisterRequest registerRequest) {
        boolean register = userService.register(registerRequest);
        return ApiResponse.success(register);
    }

    @Operation(summary = "用户登陆")
    @PostMapping("/login")
    public ApiResponse<TokenLoginResponse> login(@Valid @RequestBody UnifiedLoginRequest loginRequest) {
        TokenLoginResponse login = userService.login(loginRequest);
        return ApiResponse.success(login);
    }

    @Operation(summary = "用户登出")
    @PostMapping("/logout")
    public ApiResponse<Boolean> logout(@RequestHeader("Authorization") String token) {
        boolean logout = userService.logout(token);
        return ApiResponse.success(logout);
    }
}