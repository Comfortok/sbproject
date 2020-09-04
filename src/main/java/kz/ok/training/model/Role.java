package kz.ok.training.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_USER, ROLE_ADMIN, ROLE_OPERATOR;

    @Override
    public String getAuthority() {
        return name();
    }
}