package com.epam.vitebsk.utils;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;

public abstract class Encrypter {

    public static final Integer ITERATION_COUNT = 1024;
    public static final Integer KEY_STRENGTH = 128;
    public static final String ALGORITHM = "PBKDF2WithHmacSHA1";

    public static String toHashPassword(String password, String salt) {
        return toHashPassword(password, salt, ITERATION_COUNT, KEY_STRENGTH);
    }

    public static String toHashPassword(String password, String salt, Integer iterationCount, Integer keyStrength) {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), iterationCount, keyStrength);
            byte[] hash = factory.generateSecret(spec).getEncoded();
            return DatatypeConverter.printHexBinary(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            // TODO: exception
            throw new RuntimeException(e);
        }
    }
}
