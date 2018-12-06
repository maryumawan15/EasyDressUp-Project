package com.easydressup.utils;

import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;

/**
 * This is utility class used to encryption of password
 *
 * @author Aman Multani
 */
public class PasswordUtils {

    /**
     * This is used for password encryption.
     *
     * @param password - Password string to be encrypted.
     * @return Encrypted password
     */
    public static String encryptPassword(String password) {
        String sha256hex = Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
        return sha256hex;
    }
    public static void main(String[] args) {
        System.out.println(encryptPassword("admin"));
    }
}
