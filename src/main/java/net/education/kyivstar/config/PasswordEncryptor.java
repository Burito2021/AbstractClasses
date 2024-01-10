package net.education.kyivstar.config;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class PasswordEncryptor {
    public static String encrypt(String password) {

        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword("joke");

        return encryptor.encrypt(password);
    }
    public static String decrypt(String password){

        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword("joke");
        return encryptor.decrypt(password);
    }
}
