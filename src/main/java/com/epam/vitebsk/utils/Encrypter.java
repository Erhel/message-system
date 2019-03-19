package com.epam.vitebsk.utils;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.epam.vitebsk.utils.exception.EncodeException;

public abstract class Encrypter {

    public static final Integer ITERATION_COUNT = 1024;
    public static final Integer KEY_STRENGTH = 128;
    public static final String ALGORITHM = "PBKDF2WithHmacSHA1";

    public static final String INCORRECT_ALGORITHM = "Can't find algorithm";
    public static final String UNABLE_GENERATE_KEY = "Can't generate key";

    public static String toHashPassword(String password, String salt) {
        return toHashPassword(password, salt, ITERATION_COUNT, KEY_STRENGTH);
    }

    public static String toHashPassword(String password, String salt, Integer iterationCount, Integer keyStrength) {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), iterationCount, keyStrength);
            byte[] hash = factory.generateSecret(spec).getEncoded();
            return DatatypeConverter.printHexBinary(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new EncodeException(INCORRECT_ALGORITHM, e);
        } catch (InvalidKeySpecException e) {
            throw new EncodeException(UNABLE_GENERATE_KEY, e);
        }
    }
}
