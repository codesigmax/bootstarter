package com.qfleaf.bootstarter.common.exception;

import com.qfleaf.bootstarter.common.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final HttpServletResponse response;

    @ExceptionHandler(BusinessException.class)
    public ApiResponse<?> handleBusinessException(BusinessException e) {
        response.setStatus(e.getCode());
        return ApiResponse.error(e.getCode(), e.getMsg());
    }
}
