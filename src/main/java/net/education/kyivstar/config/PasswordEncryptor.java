package net.education.kyivstar.config;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class PasswordEncryptor {
    private final String SECRET_WORD = "joke";

    public static String encrypt(String password) {
        var secretWord = new PasswordEncryptor().SECRET_WORD;
        var encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(secretWord);

        return encryptor.encrypt(password);
    }
    public static String decrypt(String password){
        var secretWord = new PasswordEncryptor().SECRET_WORD;
        var encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(secretWord);

        return encryptor.decrypt(password);
    }
}
