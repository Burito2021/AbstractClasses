package net.education.kyivstar.threads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class CustomerRunnable implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(CustomerRunnable.class);

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + ">>>>> " + i);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.info("Error " + e);
            }
        }
    }
}
