package net.education.kyivstar.stuff;

import net.education.kyivstar.config.ConfigDataBase;
import net.education.kyivstar.config.PasswordEncryptor;

public class CustomThread extends Thread {

    @Override
    public void run() {
        final var passwordEncryptor = new PasswordEncryptor();
        final var configDataBase = new ConfigDataBase(passwordEncryptor);

    }
}
