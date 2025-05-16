package com.qfleaf.bootstarter.security.service;

import com.qfleaf.bootstarter.dao.UserDao;
import com.qfleaf.bootstarter.model.User;
import com.qfleaf.bootstarter.model.enums.UserStatus;
import com.qfleaf.bootstarter.security.model.SystemUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SystemUserDetailsService implements UserDetailsService {

    private final UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名、手机号或邮箱查找用户
        User user = userDao.findByAccount(username)
                .orElseThrow(() -> new UsernameNotFoundException("用户不存在"));

        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new LockedException("用户未激活或被锁定");
        }

        return SystemUserDetails.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getAuthorities())
                .userStatus(user.getStatus())
                .build();
    }
}
