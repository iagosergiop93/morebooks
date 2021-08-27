package com.morebooks.morebooks.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;

import java.nio.charset.StandardCharsets;

public class EncryptUtil {

    public static String createHash(String word) {

        byte[] byteArr = word.getBytes(StandardCharsets.UTF_8);
        String hash = BCrypt.hashpw(byteArr, BCrypt.gensalt(12));

        return hash;
    }

    public static boolean checkHash(String word, String hash) {

        byte[] byteArr = word.getBytes(StandardCharsets.UTF_8);
        return BCrypt.checkpw(byteArr, hash);
    }

}
