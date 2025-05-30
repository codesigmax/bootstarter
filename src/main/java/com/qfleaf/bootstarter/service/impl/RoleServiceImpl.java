package com.qfleaf.bootstarter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qfleaf.bootstarter.common.ResultCode;
import com.qfleaf.bootstarter.common.exception.BusinessException;
import com.qfleaf.bootstarter.dao.RoleDao;
import com.qfleaf.bootstarter.dao.mapper.RoleMapper;
import com.qfleaf.bootstarter.model.Role;
import com.qfleaf.bootstarter.model.converter.RoleConverter;
import com.qfleaf.bootstarter.model.request.admin.role.RoleCreateRequest;
import com.qfleaf.bootstarter.model.request.admin.role.RolePageRequest;
import com.qfleaf.bootstarter.model.request.admin.role.RoleUpdateRequest;
import com.qfleaf.bootstarter.model.response.PageResponse;
import com.qfleaf.bootstarter.model.response.admin.role.RoleDetailsResponse;
import com.qfleaf.bootstarter.model.response.admin.role.RolePageResponse;
import com.qfleaf.bootstarter.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    private final RoleDao roleDao;
    private final RoleConverter roleConverter;

    @Override
    public boolean createRole(RoleCreateRequest roleCreateRequest) {
        Role entity = roleConverter.toEntity(roleCreateRequest);

        RoleDao.ExistType exist = roleDao.exist(entity);
        if (exist != null) {
            throw new BusinessException(ResultCode.CONFLICT.getCode(), exist.getMsg());
        }

        return roleDao.save(entity);
    }

    @Override
    public PageResponse<RolePageResponse> mPage(RolePageRequest rolePageRequest) {
        return roleDao.mPage(rolePageRequest);
    }

    @Override
    public boolean updateRole(RoleUpdateRequest roleUpdateRequest) {
        Role entity = roleConverter.toEntity(roleUpdateRequest);
        roleDao.exist(entity);
        return roleDao.update(entity);
    }

    @Override
    public RoleDetailsResponse getRole(Long id) {
        return roleDao.findRoleById(id);
    }
}
