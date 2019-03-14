package com.epam.vitebsk.mvc.controller.authorization;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;

public abstract class Encrypter {

    public static String toHashPassword(String password, String salt) {
        return toHashPassword(password, salt, 1024, 128);
    }

    public static String toHashPassword(String password, String salt, int iterationCount, int keyStrength) {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), iterationCount, keyStrength);
            byte[] hash = factory.generateSecret(spec).getEncoded();
            return DatatypeConverter.printHexBinary(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
}
