package net.education.kyivstar.config;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class PasswordEncryptor {
    private final String SECRET_WORD = "joke";
    private final StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();

    public String encrypt(String password) {
        final StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(SECRET_WORD);
        return encryptor.encrypt(password);
    }

    public String decrypt(String password) {
        final StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(SECRET_WORD);
        return encryptor.decrypt(password);
    }
}
