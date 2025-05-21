package com.qfleaf.bootstarter.model.request.admin.user;

import com.qfleaf.bootstarter.model.enums.UserStatus;
import com.qfleaf.bootstarter.model.request.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserPageRequest extends PageRequest {
    private String username;
    private String phone;
    private String email;
    private UserStatus status;
}
