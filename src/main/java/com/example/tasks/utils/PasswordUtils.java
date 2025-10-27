package com.example.tasks.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static javax.xml.bind.DatatypeConverter.printHexBinary;

public final class PasswordUtils {

    public static String md5(final CharSequence p) {

        try {
            final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(p.toString().getBytes());
            return printHexBinary(messageDigest.digest()).toLowerCase();
        } catch (final NoSuchAlgorithmException exception) {
            throw new IllegalArgumentException(exception);
        }
    }

    public static boolean matches(final CharSequence p1, final String hash) {
        return md5(p1).equals(hash);
    }
}
