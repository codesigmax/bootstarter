package com.qfleaf.bootstarter.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qfleaf.bootstarter.dao.mapper.RoleMapper;
import com.qfleaf.bootstarter.model.Role;
import com.qfleaf.bootstarter.model.request.admin.role.RoleCreateRequest;
import lombok.Getter;
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

    @Getter
    @RequiredArgsConstructor
    public enum ExistType {
        NAME("角色名称已存在"),
        CODE("角色编码已存在");
        final String msg;
    }

    public ExistType exist(RoleCreateRequest role) {
        return existByName(role.getName()) ?
                ExistType.NAME : existByCode(role.getCode()) ?
                ExistType.CODE : null;
    }

    private boolean existByName(String roleName) {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Role::getName, roleName);
        return roleMapper.exists(wrapper);
    }

    private boolean existByCode(String roleCode) {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Role::getName, roleCode);
        return roleMapper.exists(wrapper);
    }

    public boolean save(Role role) {
        return roleMapper.insert(role) > 0;
    }

}
