package com.example.tasks.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PasswordUtilsTest {

    private static final String PASSWORD = "test_passworD@1";

    @Test
    void md5() {

        final String md5 = PasswordUtils.md5(PASSWORD);

        assertTrue(PasswordUtils.matches(PASSWORD, md5));
    }
}