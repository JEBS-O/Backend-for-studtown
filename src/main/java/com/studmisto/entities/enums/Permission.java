package com.studmisto.entities.enums;

public enum Permission {
    CRUD_BASIC_INFO("crud_basic_info"),
    CRUD_USERS("crud_users"),
    READ_BASIC_INFO("read_basic_info"),
    READ_USERS("read_users");

    public String permission;

    Permission(String permission) {
        this.permission = permission;
    }
}
