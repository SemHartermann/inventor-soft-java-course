package org.example.util;

import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

public class PasswordEncoder {
    public static String encode(String password) throws GeneralSecurityException, UnsupportedEncodingException {
        byte[] salt = "622836429".getBytes();
        int iterationCount = 10000;
        int keyLength = 128;

        SuperSecretKey object = new SuperSecretKey();
        SecretKeySpec key = object.generateSecretKey(
                password.toCharArray(), salt, iterationCount,
                keyLength);

        return object.encrypt(password, key);
    }
}
