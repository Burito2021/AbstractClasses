package net.education.kyivstar.config;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import static net.education.kyivstar.services.util.ConfigSchemaPassUtils.SECRET_WORD;

public class PasswordEncryptor {
    public static String encrypt(String password) {

        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(SECRET_WORD);

        return encryptor.encrypt(password);
    }
    public static String decrypt(String password){

        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(SECRET_WORD);
        return encryptor.decrypt(password);
    }
}
