package com.qfleaf.bootstarter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qfleaf.bootstarter.model.User;
import com.qfleaf.bootstarter.model.request.RegisterRequest;
import com.qfleaf.bootstarter.security.request.UnifiedLoginRequest;
import com.qfleaf.bootstarter.security.response.TokenLoginResponse;

public interface UserService extends IService<User> {
    boolean register(RegisterRequest registerRequest);

    TokenLoginResponse login(UnifiedLoginRequest unifiedLoginRequest);

    boolean logout(String token);
}
