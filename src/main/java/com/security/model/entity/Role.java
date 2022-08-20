package com.security.model.entity;

public enum Role {
    USER("USER"), ADMIN("ADMIN");

    protected String value;

    private Role(String value) {
        this.value = value;
    }
}
