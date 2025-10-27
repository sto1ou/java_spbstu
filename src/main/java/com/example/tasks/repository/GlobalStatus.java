package com.example.tasks.repository;

import java.util.Arrays;

public enum GlobalStatus {

    PENDING,
    SUCCESS,
    DELETED,
    FAIL;

    GlobalStatus() {
    }

    public static GlobalStatus getValue(final String status) {
        return Arrays.stream(values()).filter(v -> v.name().equals(status)).findFirst().orElse(PENDING);
    }
}
