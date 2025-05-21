package com.qfleaf.bootstarter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qfleaf.bootstarter.common.ResultCode;
import com.qfleaf.bootstarter.common.exception.BusinessException;
import com.qfleaf.bootstarter.dao.RoleDao;
import com.qfleaf.bootstarter.dao.UserDao;
import com.qfleaf.bootstarter.dao.mapper.UserMapper;
import com.qfleaf.bootstarter.model.Role;
import com.qfleaf.bootstarter.model.User;
import com.qfleaf.bootstarter.model.converter.UserConverter;
import com.qfleaf.bootstarter.model.enums.UserStatus;
import com.qfleaf.bootstarter.model.request.RegisterRequest;
import com.qfleaf.bootstarter.model.request.admin.user.UserCreateRequest;
import com.qfleaf.bootstarter.security.authentication.token.factory.AuthenticationTokenFactory;
import com.qfleaf.bootstarter.security.request.UnifiedLoginRequest;
import com.qfleaf.bootstarter.security.response.TokenLoginResponse;
import com.qfleaf.bootstarter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final AuthenticationManager authenticationManager;
    private final AuthenticationTokenFactory authenticationTokenFactory;
    private final UserDao userDao;
    private final UserConverter userConverter;
    private final RoleDao roleDao;

    @Override
    public boolean register(RegisterRequest registerRequest) {
        UserDao.ExistType exist = userDao.exist(registerRequest);
        if (exist != null) {
            throw new BusinessException(ResultCode.CONFLICT.getCode(), exist.getMsg());
        }
        User entity = userConverter.toEntity(registerRequest);
        return userDao.save(entity);
    }

    @Override
    public TokenLoginResponse login(UnifiedLoginRequest unifiedLoginRequest) {
        Authentication token = authenticationTokenFactory.createAuthenticatedToken(unifiedLoginRequest);
        Authentication authenticate = authenticationManager.authenticate(token);
        UserDetails principal = (UserDetails) authenticate.getPrincipal();
        // jwt存储到redis
        return userDao.cacheToken(principal);
    }

    @Override
    public boolean logout(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            return userDao.expireToken(token);
        }
        throw new BusinessException(ResultCode.UNAUTHORIZED.getCode(), "用户未登陆");
    }

    @Override
    public boolean createUser(UserCreateRequest userCreateRequest) {
        UserDao.ExistType exist = userDao.exist(userCreateRequest);
        if (exist != null) {
            throw new BusinessException(ResultCode.CONFLICT.getCode(), exist.getMsg());
        }
        User entity = userConverter.toEntity(userCreateRequest);
        return userDao.save(entity);
    }

    @Override
    public boolean disableUserById(Long id) {
        List<Role> roles = roleDao.findAllByUserId(id);
        roles.forEach(role -> {
            if (role.getCode().equals("ROLE_ADMIN")) {
                throw new BusinessException(ResultCode.FORBIDDEN.getCode(), "不能禁用管理员");
            }
        });
        User user = userDao.findById(id);
        if (user == null) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "未知用户");
        }
        user.setStatus(UserStatus.BLOCKED);
        return userDao.update(user);
    }
}
