package com.qfleaf.bootstarter.model.converter;

import com.qfleaf.bootstarter.model.Role;
import com.qfleaf.bootstarter.model.request.admin.role.RoleCreateRequest;
import org.springframework.stereotype.Component;

@Component
public class RoleConverter {
    public Role toEntity(RoleCreateRequest request) {
        return Role.builder()
                .name(request.getName())
                .code(request.getCode())
                .description(request.getDescription())
                .build();
    }
}
