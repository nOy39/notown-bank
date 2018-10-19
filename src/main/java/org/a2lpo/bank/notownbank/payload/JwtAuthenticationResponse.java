package org.a2lpo.bank.notownbank.payload;

import org.a2lpo.bank.notownbank.security.UserPrincipal;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * DTO Авторизации
 */
public class JwtAuthenticationResponse {
    private final String username;
    private final String email;
    private final Collection<? extends GrantedAuthority> roles;
    private String accessToken;
    private String tokenType = "Bearer";
    public JwtAuthenticationResponse(String accessToken,
                                     UserPrincipal principal) {
        this.accessToken = accessToken;
        this.username = principal.getUsername();
        this.email = principal.getEmail();
        this.roles = principal.getAuthorities();

    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Collection<? extends GrantedAuthority> getRoles() {
        return roles;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
