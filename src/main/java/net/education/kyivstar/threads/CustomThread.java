package net.education.kyivstar.threads;

import java.util.concurrent.TimeUnit;

public class CustomThread extends Thread {



    @Override
    public void run() {

        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName()+">>>>> " + i);
            try {

                TimeUnit.SECONDS.sleep(1);

            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
