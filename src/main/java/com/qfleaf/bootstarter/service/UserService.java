package com.qfleaf.bootstarter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qfleaf.bootstarter.model.User;
import com.qfleaf.bootstarter.model.request.RegisterRequest;
import com.qfleaf.bootstarter.model.request.admin.user.UserCreateRequest;
import com.qfleaf.bootstarter.model.request.admin.user.UserPageRequest;
import com.qfleaf.bootstarter.model.response.PageResponse;
import com.qfleaf.bootstarter.model.response.admin.user.UserPageResponse;
import com.qfleaf.bootstarter.security.request.UnifiedLoginRequest;
import com.qfleaf.bootstarter.security.response.TokenLoginResponse;

public interface UserService extends IService<User> {
    boolean register(RegisterRequest registerRequest);

    TokenLoginResponse login(UnifiedLoginRequest unifiedLoginRequest);

    boolean logout(String token);

    boolean createUser(UserCreateRequest userCreateRequest);

    boolean disableUserById(Long id);

    PageResponse<UserPageResponse> mPage(UserPageRequest userPageRequest);
}
