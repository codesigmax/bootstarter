package com.qfleaf.bootstarter.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qfleaf.bootstarter.common.ApiResponse;
import com.qfleaf.bootstarter.common.ResultCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

@RequiredArgsConstructor
public class UnifiedAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        response.sendError(ResultCode.FORBIDDEN.getCode(), accessDeniedException.getMessage());
        String result = objectMapper.writeValueAsString(ApiResponse.error(ResultCode.UNAUTHORIZED.getCode(), "权限错误"));
        response.getWriter().write(result);
    }
}
