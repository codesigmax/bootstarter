package com.qfleaf.bootstarter.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.qfleaf.bootstarter.dao.mapper.RoleMapper;
import com.qfleaf.bootstarter.model.Role;
import com.qfleaf.bootstarter.model.request.admin.role.RolePageRequest;
import com.qfleaf.bootstarter.model.response.PageResponse;
import com.qfleaf.bootstarter.model.response.admin.role.RoleDetailsResponse;
import com.qfleaf.bootstarter.model.response.admin.role.RolePageResponse;
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

    public PageResponse<RolePageResponse> mPage(RolePageRequest rolePageRequest) {
        IPage<RolePageResponse> page = new PageDTO<>(rolePageRequest.getCurrent(), rolePageRequest.getSize());
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        IPage<RolePageResponse> pageResult = roleMapper.selectPageVo(page, wrapper);
        return PageResponse.<RolePageResponse>builder()
                .records(pageResult.getRecords())
                .total(pageResult.getTotal())
                .page(pageResult.getCurrent())
                .size(pageResult.getSize())
                .build();
    }

    public RoleDetailsResponse findRoleById(Long id) {
        return roleMapper.selectVoById(id);
    }

    @Getter
    @RequiredArgsConstructor
    public enum ExistType {
        NAME("角色名称已存在"),
        CODE("角色编码已存在");
        final String msg;
    }

    // region exist
    public ExistType exist(Role role) {
        Long id = role.getId();
        return existByName(role.getName(), id) ?
                ExistType.NAME : existByCode(role.getCode(), id) ?
                ExistType.CODE : null;
    }

    private boolean existByName(String roleName, Long id) {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Role::getName, roleName)
                .ne(Role::getId, id);
        return roleMapper.exists(wrapper);
    }

    private boolean existByCode(String roleCode, Long id) {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Role::getName, roleCode)
                .ne(Role::getId, id);
        return roleMapper.exists(wrapper);
    }
    // endregion

    public boolean save(Role role) {
        return roleMapper.insert(role) > 0;
    }

    public boolean update(Role role) {
        return roleMapper.updateById(role) > 0;
    }

}
