package com.qfleaf.bootstarter.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.qfleaf.bootstarter.model.enums.UserStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SystemUserDetails implements UserDetails {
    private String username;
    private String password;
    private Set<String> permissions;
    @JsonIgnore
    private Set<? extends GrantedAuthority> authorities;
    private UserStatus userStatus;
    // 认证通过后才添加
    private String accessToken;

    public SystemUserDetails(String username, String password, Collection<String> authorities, UserStatus userStatus) {
        this.username = username;
        this.password = password;
        permissions = (Set<String>) authorities;
        this.authorities = authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
        this.userStatus = userStatus;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String username;
        private String password;
        private Collection<String> authorities;
        private UserStatus userStatus;

        private Builder() {
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder authorities(Collection<String> authorities) {
            this.authorities = authorities;
            return this;
        }

        public Builder userStatus(UserStatus userStatus) {
            this.userStatus = userStatus;
            return this;
        }

        public SystemUserDetails build() {
            return new SystemUserDetails(username, password, authorities, userStatus);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities == null) {
            authorities = permissions.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonLocked() {
        return userStatus != UserStatus.LOCKED;
    }

    @Override
    public boolean isEnabled() {
        return userStatus != UserStatus.BLOCKED;
    }
}
