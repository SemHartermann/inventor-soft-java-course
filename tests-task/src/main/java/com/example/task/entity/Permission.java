package com.example.task.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {

    USER_READ("user:read"),
    USER_UPDATE("user:update"),
    USER_CREATE("user:create"),
    USER_DELETE("user:delete"),
    ADMIN_READ("user:read"),
    ADMIN_UPDATE("user:update"),
    ADMIN_CREATE("user:create"),
    ADMIN_DELETE("user:delete");


    private final String permission;
}
