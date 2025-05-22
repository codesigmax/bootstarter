package com.qfleaf.bootstarter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qfleaf.bootstarter.common.ResultCode;
import com.qfleaf.bootstarter.common.exception.BusinessException;
import com.qfleaf.bootstarter.dao.RoleDao;
import com.qfleaf.bootstarter.dao.mapper.RoleMapper;
import com.qfleaf.bootstarter.model.Role;
import com.qfleaf.bootstarter.model.converter.RoleConverter;
import com.qfleaf.bootstarter.model.request.admin.role.RoleCreateRequest;
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
        RoleDao.ExistType exist = roleDao.exist(roleCreateRequest);
        if (exist != null) {
            throw new BusinessException(ResultCode.CONFLICT.getCode(), exist.getMsg());
        }
        Role entity = roleConverter.toEntity(roleCreateRequest);
        return roleDao.save(entity);
    }
}
