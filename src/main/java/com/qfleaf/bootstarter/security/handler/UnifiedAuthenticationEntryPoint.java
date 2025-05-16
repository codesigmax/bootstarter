package com.qfleaf.bootstarter.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qfleaf.bootstarter.common.ApiResponse;
import com.qfleaf.bootstarter.common.ResultCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

@RequiredArgsConstructor
public class UnifiedAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if (authException instanceof BadCredentialsException) {
            response.sendError(ResultCode.UNAUTHORIZED.getCode(), authException.getMessage());
        }
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(ResultCode.UNAUTHORIZED.getCode());
        String result = objectMapper.writeValueAsString(ApiResponse.error(ResultCode.UNAUTHORIZED.getCode(), "认证错误"));
        response.getWriter().write(result);
    }
}
