//package com.qfleaf.bootstarter.security.authentication.token;
//
//import lombok.Getter;
//import org.springframework.security.authentication.AbstractAuthenticationToken;
//import org.springframework.security.core.GrantedAuthority;
//
//import java.util.Collection;
//import java.util.Map;
//
//public class SocialAuthenticationToken extends AbstractAuthenticationToken {
//
//    private final Object principal;
//    @Getter
//    private final String provider;  // 第三方平台: wechat, github等
//    private String code;           // 授权码
//    private String state;          // 状态参数
//
//    public SocialAuthenticationToken(String provider, String code, String state) {
//        super(null);
//        this.provider = provider;
//        this.code = code;
//        this.state = state;
//        this.principal = null;
//        setAuthenticated(false);
//    }
//
//    public SocialAuthenticationToken(Object principal,
//                                   String provider,
//                                   Collection<? extends GrantedAuthority> authorities) {
//        super(authorities);
//        this.principal = principal;
//        this.provider = provider;
//        super.setAuthenticated(true);
//    }
//
//    @Override
//    public Object getCredentials() {
//        return Map.of("code", code, "state", state);
//    }
//
//    @Override
//    public Object getPrincipal() {
//        return this.principal;
//    }
//
//    @Override
//    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
//        if (isAuthenticated) {
//            throw new IllegalArgumentException(
//                "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
//        }
//        super.setAuthenticated(false);
//    }
//
//    @Override
//    public void eraseCredentials() {
//        super.eraseCredentials();
//        this.code = null;
//        this.state = null;
//    }
//}
