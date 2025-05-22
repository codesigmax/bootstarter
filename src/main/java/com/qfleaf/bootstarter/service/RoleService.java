package com.qfleaf.bootstarter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qfleaf.bootstarter.model.Role;
import com.qfleaf.bootstarter.model.request.admin.role.RoleCreateRequest;

public interface RoleService extends IService<Role> {
    boolean createRole(RoleCreateRequest roleCreateRequest);
}
