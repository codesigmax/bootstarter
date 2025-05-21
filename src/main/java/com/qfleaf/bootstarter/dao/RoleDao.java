package com.qfleaf.bootstarter.dao;

import com.qfleaf.bootstarter.dao.mapper.RoleMapper;
import com.qfleaf.bootstarter.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RoleDao {
    private final RoleMapper roleMapper;

    public List<Role> findAllByUserId(Long userId) {
        return roleMapper.selectRolesByUserId(userId);
    }
}
