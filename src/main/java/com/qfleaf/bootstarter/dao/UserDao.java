package com.qfleaf.bootstarter.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.qfleaf.bootstarter.dao.mapper.PermissionMapper;
import com.qfleaf.bootstarter.dao.mapper.RoleMapper;
import com.qfleaf.bootstarter.dao.mapper.UserMapper;
import com.qfleaf.bootstarter.model.Permission;
import com.qfleaf.bootstarter.model.Role;
import com.qfleaf.bootstarter.model.User;
import com.qfleaf.bootstarter.model.request.RegisterRequest;
import com.qfleaf.bootstarter.model.request.admin.user.UserPageRequest;
import com.qfleaf.bootstarter.model.response.PageResponse;
import com.qfleaf.bootstarter.model.response.admin.user.UserPageResponse;
import com.qfleaf.bootstarter.security.jwt.JwtTokenProvider;
import com.qfleaf.bootstarter.security.response.TokenLoginResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserDao {
    private final UserMapper userMapper;
    private final PermissionMapper permissionMapper;
    private final RoleMapper roleMapper;
    private final RedisTemplate<String, UserDetails> userCache;
    private final RedisTemplate<String, Object> configCache;

    private static final String loginTokenCacheKeyPrefix = "login:token:";
    private final JwtTokenProvider jwtTokenProvider;

    public PageResponse<UserPageResponse> mPage(UserPageRequest request) {
        IPage<UserPageResponse> page = new PageDTO<>(request.getCurrent(), request.getSize());
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .like(StringUtils.hasText(request.getUsername()), User::getUsername, request.getUsername())
                .like(StringUtils.hasText(request.getEmail()), User::getEmail, request.getEmail())
                .like(StringUtils.hasText(request.getPhone()), User::getPhone, request.getPhone())
                .eq(request.getStatus() != null, User::getStatus, request.getStatus());
        IPage<UserPageResponse> pageResult = userMapper.selectPageVo(page, wrapper);
        return PageResponse.<UserPageResponse>builder()
                .records(pageResult.getRecords())
                .total(pageResult.getTotal())
                .page(pageResult.getCurrent())
                .size(pageResult.getSize())
                .build();
    }

    @Getter
    @RequiredArgsConstructor
    public enum ExistType {
        USERNAME("用户名已被使用"),
        EMAIL("邮箱已被使用"),
        PHONE("手机号已被使用");

        final String msg;
    }

    public Optional<User> findByAccount(String account) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .eq(User::getUsername, account)
                .or()
                .eq(User::getPhone, account)
                .or()
                .eq(User::getEmail, account);
        User user = userMapper.selectOne(wrapper);
        if (user == null) {
            return Optional.empty();
        }
        // load authorities
        List<Role> roles = roleMapper.selectRolesByUserId(user.getId());
        List<Long> roleIds = roles.stream()
                .map(Role::getId)
                .toList();
        List<Permission> permissions = permissionMapper.selectPermissionsByRoleIds(roleIds.toArray(new Long[0]));
        user.addAuthorities(roles.stream().map(Role::getCode).collect(Collectors.toSet()))
                .addAuthorities(permissions.stream().map(Permission::getCode).collect(Collectors.toSet()));
        return Optional.of(user);
    }

    public TokenLoginResponse cacheToken(UserDetails principal) {
        String accessJwt = jwtTokenProvider.generateAccessToken(principal);

        userCache
                .opsForValue()
                .set(loginTokenCacheKeyPrefix + accessJwt, principal, 15, TimeUnit.MINUTES);

        return TokenLoginResponse.builder()
                .token(accessJwt)
                .build();
    }

    public UserDetails loadUserFromCache(String jwt) {
        return userCache.opsForValue().get(loginTokenCacheKeyPrefix + jwt);
    }

    public boolean expireToken(String token) {
        return userCache.delete(loginTokenCacheKeyPrefix + token);
    }

    public User findById(Long id) {
        return userMapper.selectById(id);
    }

    // region exist
    public ExistType exist(RegisterRequest register) {
        return existByUsername(register.getUsername()) ?
                ExistType.USERNAME : existByEmail(register.getEmail()) ?
                ExistType.EMAIL : existByPhone(register.getPhone()) ?
                ExistType.PHONE : null;
    }

    public boolean existByUsername(String username) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        return userMapper.exists(wrapper);
    }

    public boolean existByEmail(String email) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getEmail, email);
        return userMapper.exists(wrapper);
    }

    public boolean existByPhone(String phone) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getPhone, phone);
        return userMapper.exists(wrapper);
    }
    // endregion

    @Transactional
    public boolean save(User entity) {
        int userInsert = userMapper.insert(entity);
        String defaultRoleIdString = (String) configCache.opsForHash().get("globalConfig", "defaultUserRole");
        assert defaultRoleIdString != null;
        Long defaultRoleId = Long.valueOf(defaultRoleIdString);
        int roleInsert = userMapper.insertDefaultRoles(entity.getId(), defaultRoleId);
        return userInsert > 0 && roleInsert > 0;
    }

    public boolean update(User entity) {
        return userMapper.updateById(entity) > 0;
    }
}
