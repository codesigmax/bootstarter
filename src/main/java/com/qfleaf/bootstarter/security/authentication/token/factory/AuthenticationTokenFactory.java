package com.qfleaf.bootstarter.security.authentication.token.factory;

import com.qfleaf.bootstarter.security.authentication.token.EmailCodeAuthenticationToken;
import com.qfleaf.bootstarter.security.authentication.token.PasswordAuthenticationToken;
import com.qfleaf.bootstarter.security.authentication.token.SmsCodeAuthenticationToken;
import com.qfleaf.bootstarter.security.request.UnifiedLoginRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationTokenFactory {
    public Authentication createAuthenticatedToken(UnifiedLoginRequest unifiedLoginRequest) {
        String account = unifiedLoginRequest.getAccount();
        String password = unifiedLoginRequest.getPassword();

        return switch (unifiedLoginRequest.getType()) {
            case EMAIL -> new EmailCodeAuthenticationToken(account, password);
            case PHONE -> new SmsCodeAuthenticationToken(account, password);
            default -> new PasswordAuthenticationToken(account, password);
        };
    }
}
