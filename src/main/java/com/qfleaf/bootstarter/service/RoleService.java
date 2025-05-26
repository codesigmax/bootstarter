package com.qfleaf.bootstarter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qfleaf.bootstarter.model.Role;
import com.qfleaf.bootstarter.model.request.admin.role.RoleCreateRequest;
import com.qfleaf.bootstarter.model.request.admin.role.RolePageRequest;
import com.qfleaf.bootstarter.model.request.admin.role.RoleUpdateRequest;
import com.qfleaf.bootstarter.model.response.PageResponse;
import com.qfleaf.bootstarter.model.response.admin.role.RoleDetailsResponse;
import com.qfleaf.bootstarter.model.response.admin.role.RolePageResponse;

public interface RoleService extends IService<Role> {
    boolean createRole(RoleCreateRequest roleCreateRequest);

    PageResponse<RolePageResponse> mPage(RolePageRequest rolePageRequest);

    boolean updateRole(RoleUpdateRequest roleUpdateRequest);

    RoleDetailsResponse getRole(Long id);
}
