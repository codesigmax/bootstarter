package com.qfleaf.bootstarter.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qfleaf.bootstarter.model.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_users")
public class User {
    @TableId
    private Long id;
    private String username;
    private String phone;
    private String email;
    private String password;
    private java.sql.Timestamp createTime;
    private java.sql.Timestamp updateTime;
    private UserStatus status;
    @TableField(exist = false)
    private Set<String> authorities;

    public User addAuthorities(String authority) {
        if (authority == null || authority.isBlank()) {
            return this;
        }
        if (authorities == null) {
            authorities = new HashSet<>();
        }
        authorities.add(authority);
        return this;
    }

    public User addAuthorities(Set<String> authorities) {
        if (this.authorities == null) {
            this.authorities = new HashSet<>();
        }
        if (authorities == null || authorities.isEmpty()) {
            return this;
        }
        this.authorities.addAll(authorities);
        return this;
    }
}
