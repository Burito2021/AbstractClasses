package net.education.kyivstar.threads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class CustomThread extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(CustomThread.class);

    @Override
    public void run() {

        for (int i = 0; i < 10; i++) {
            logger.info(Thread.currentThread().getName()+">>>>> " + i);
            try {

                TimeUnit.SECONDS.sleep(1);

            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
