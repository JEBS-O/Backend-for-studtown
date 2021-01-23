package com.studmisto.entities;

import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

public enum Role implements GrantedAuthority {
    USER(Set.of(Permission.READ_BASIC_INFO)),
    ADMIN(Set.of(Permission.CRUD_BASIC_INFO, Permission.CRUD_USERS));

    public Set<Permission> permissionSet;

    Role(Set<Permission> permissions) {
        this.permissionSet = permissions;
    }

    @Override
    public String getAuthority() {
        return name();
    }
}
