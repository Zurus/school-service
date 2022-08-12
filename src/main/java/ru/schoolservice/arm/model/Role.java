package ru.schoolservice.arm.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER,
    ADMIN;

    public static final String ROLE = "ROLE_";

    @Override
    public String getAuthority() {
        //https://stackoverflow.com/a/19542316/548473
        return ROLE + name();
    }
}